package office;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import database.DBHelper;
import dataclasses.Driver;
import dataclasses.Fine;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "OfficeFinesServlet", value = "/officeFines")
public class OfficeFinesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("GET fines");

        DBHelper.makeConnection();
        ArrayList<Fine> finesList = DBHelper.selectFinesByLicense(request.getParameter("license"));

        // Формирование и отправка JSON-ответа
        JsonArray jsonResponse = new JsonArray();
        for (Fine fine : finesList) {
            JsonObject jsonFine = new JsonObject();
            jsonFine.put("fineId", fine.getId());
            jsonFine.put("date", fine.getDate());
            jsonFine.put("description", fine.getDescription());
            jsonFine.put("size", fine.getSize());

            jsonResponse.add(jsonFine);
        }

        PrintWriter pw = response.getWriter();
        Jsoner.serialize(jsonResponse, pw);

        pw.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("POST fines");

        String license = request.getParameter("license");
        String description = request.getParameter("description");
        int size = Integer.parseInt(request.getParameter("size"));

        DBHelper.makeConnection();
        DBHelper.insertFine(license, description, size);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DELETE fines");

        String fineId = request.getParameter("fineId");

        DBHelper.makeConnection();
        boolean status = DBHelper.deleteFine(fineId);

        if (!status) {
            // Ошибка сервера
            response.setStatus(500);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("PUT fines");

        int id = Integer.parseInt(request.getParameter("fineId"));
        String license = request.getParameter("license");
        String description = request.getParameter("description");
        int size = Integer.parseInt(request.getParameter("size"));

        DBHelper.makeConnection();
        DBHelper.updateFine(id, license, description, size);
    }
}
