package servlets;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import database.DBHelper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import dataclasses.Driver;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "DriversServlet", value = "/drivers")
public class DriversServlet extends HttpServlet {
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
        Driver driver = DBHelper.getDriverByLicense(request.getParameter("dl")); //dl - driver license

        // Если водитель не найден - вернуть код Bad Request
        if (driver == null) {
            response.setStatus(400);
            return;
        }

        JsonObject driverJSON = new JsonObject();

        driverJSON.put("firstName", driver.getFirstName());
        driverJSON.put("lastName", driver.getLastName());

        PrintWriter pw = response.getWriter();
        Jsoner.serialize(driverJSON, pw);

        pw.close();
    }
}
