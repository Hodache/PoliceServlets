package office;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import database.DBHelper;
import dataclasses.Driver;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "officeDr", value = "/officeDrivers")
public class OfficeDriversServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBHelper.makeConnection();
        ArrayList<Driver> drivers = DBHelper.getDrivers();

        // Формирование и отправка JSON-ответа
        JsonArray jsonResponse = new JsonArray();
        for (Driver driver : drivers) {
            JsonObject jsonDriver = new JsonObject();
            jsonDriver.put("license", driver.getLicense());
            jsonDriver.put("firstName", driver.getFirstName());
            jsonDriver.put("lastName", driver.getLastName());

            jsonResponse.add(jsonDriver);
        }

        PrintWriter pw = response.getWriter();
        Jsoner.serialize(jsonResponse, pw);

        pw.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String license = request.getParameter("license");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        DBHelper.makeConnection();
        boolean status = DBHelper.insertDriver(license, firstName, lastName);

        if (!status) {
            // Ошибка сервера
            response.setStatus(500);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String license = request.getParameter("license");

        DBHelper.makeConnection();
        boolean status = DBHelper.deleteDriver(license);

        if (!status) {
            // Ошибка сервера
            response.setStatus(500);
        }
    }
}
