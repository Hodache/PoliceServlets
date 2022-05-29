package office;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import database.DBHelper;
import dataclasses.Car;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "OfficeFinesSumServlet", value = "/officeFinesSum")
public class OfficeFinesSumServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBHelper.makeConnection();
        int sum = DBHelper.getFinesSum();

        // Формирование и отправка JSON-ответа
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.put("sum", sum);

        PrintWriter pw = response.getWriter();
        Jsoner.serialize(jsonResponse, pw);

        pw.close();
    }
}
