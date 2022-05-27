package servlets;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import database.DBHelper;
import dataclasses.Fine;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;;
import java.util.ArrayList;

@WebServlet(name = "FinesServlet", value = "/fines")
public class FinesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Проверка входа пользователя
        HttpSession userSession = request.getSession();
        if (userSession.getAttribute("sessionID") == null) {
            System.out.println("User is not logged in");
            response.setStatus(403);
            return;
        }

        DBHelper.makeConnection();
        ArrayList<Fine> finesList = DBHelper.selectFinesByLicense(request.getParameter("dl")); //dl - driver license

        // Формирование и отправка JSON-ответа
        JsonArray jsonResponse = new JsonArray();
        for (Fine fine : finesList) {
            JsonObject account = new JsonObject();
            account.put("date", fine.getDate());
            account.put("description", fine.getDescription());
            account.put("size", fine.getSize());

            jsonResponse.add(account);
        }

        PrintWriter pw = response.getWriter();
        Jsoner.serialize(jsonResponse, pw);

        pw.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Проверка входа пользователя
        HttpSession userSession = request.getSession();
        if (userSession.getAttribute("sessionID") == null) {
            System.out.println("User is not logged in");
            response.setStatus(403);
            return;
        }

        String license = request.getParameter("license");
        String description = request.getParameter("description");
        int size = Integer.parseInt(request.getParameter("size"));

        boolean inserted = false;

        DBHelper.makeConnection();
        inserted = DBHelper.insertFine(license, description, size);

        JsonObject statusResponse = new JsonObject();
        statusResponse.put("inserted", inserted);

        PrintWriter pw = response.getWriter();
        Jsoner.serialize(statusResponse, pw);
        pw.close();
    }
}
