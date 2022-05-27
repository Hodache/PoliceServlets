package servlets;

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

@WebServlet(name = "CarsServlet", value = "/cars")
public class CarsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Проверка входа пользователя
        HttpSession userSession = request.getSession();
        if (userSession.getAttribute("sessionID") == null) {
            System.out.println("User is not logged in");
            response.setStatus(403);
            return;
        }

        // Отправка списка машин
        try {
            // Создание подключения и извлечение списка из БД
            DBHelper.makeConnection();
            ArrayList<Car> carsList = DBHelper.selectCarsByLicense(request.getParameter("dl")); // dl - driver license

            // Формирование и отправка JSON-ответа
            JsonArray jsonResponse = new JsonArray();
            for (Car car : carsList) {
                JsonObject account = new JsonObject();
                account.put("plate", car.getPlateNumber());
                account.put("color", car.getColor());
                account.put("insurance", car.getInsurance());
                account.put("model", car.getModel());
                account.put("finesCount", car.getFinesCount());

                jsonResponse.add(account);
            }

            PrintWriter pw = response.getWriter();
            Jsoner.serialize(jsonResponse, pw);

            pw.close();
        }
        catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
    }

}
