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

@WebServlet(name = "OfficeCarsServlet", value = "/officeCars")
public class OfficeCarsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBHelper.makeConnection();
        ArrayList<Car> cars = DBHelper.getCars(request.getParameter("dl"));

        // Формирование и отправка JSON-ответа
        JsonArray jsonResponse = new JsonArray();
        for (Car car : cars) {
            JsonObject jsonCar = new JsonObject();
            jsonCar.put("plate", car.getPlateNumber());
            jsonCar.put("color", car.getColor());
            jsonCar.put("insurance", car.getInsurance());
            jsonCar.put("model", car.getModel());

            jsonResponse.add(jsonCar);
        }

        PrintWriter pw = response.getWriter();
        Jsoner.serialize(jsonResponse, pw);

        pw.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String license = request.getParameter("license");
        String model = request.getParameter("model");
        String plate = request.getParameter("plate");
        String color = request.getParameter("color");
        String insurance = request.getParameter("insurance");

        DBHelper.makeConnection();
        boolean status = DBHelper.insertCar(license, model, plate, color, insurance);

        if (!status) {
            // Ошибка сервера
            response.setStatus(500);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String plate = request.getParameter("plate");

        DBHelper.makeConnection();
        boolean status = DBHelper.deleteCar(plate);

        if (!status) {
            // Ошибка сервера
            response.setStatus(500);
        }
    }
}
