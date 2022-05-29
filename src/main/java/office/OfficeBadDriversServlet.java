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

@WebServlet(name = "OfficeBadDriversServlet", value = "/officeBadDrivers")
public class OfficeBadDriversServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int finesCount = Integer.parseInt(request.getParameter("finesCount"));

        DBHelper.makeConnection();
        ArrayList<Driver> drivers = DBHelper.getBadDrivers(finesCount);

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
}
