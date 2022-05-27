package com.example.carsaccountingwebservice;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;

import com.github.cliftonlabs.json_simple.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        try {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//
//            String url = "jdbc:sqlserver://localhost:1433;";
//            String dbName = "databaseName=PoliceDB;";
//            String secureConnection = "encrypt=true;trustServerCertificate=true;";
//            String user = "user=sa;";
//            String userPassword = "password=123";
//
//            String connectionUrl = url + dbName + secureConnection + user + userPassword;
//            Connection con = DriverManager.getConnection(connectionUrl);
//
//            Statement statement = con.createStatement();
//            ResultSet rs = statement.executeQuery("SELECT * from Cars");
//            JsonArray carsResponse = new JsonArray();
//
//            while (rs.next()){
//                String plate = rs.getString("C_PLATE");
//                String color = rs.getString("C_COlOR");
//                String insurance = rs.getString("C_INSURANCE");
//                String model = rs.getString("C_MODEL");
//                int finesCount = rs.getInt("C_FINES");
//
//                JsonObject account = new JsonObject();
//                account.put("plate", plate);
//                account.put("color", color);
//                account.put("insurance", insurance);
//                account.put("model", model);
//                account.put("finesCount", finesCount);
//
//                carsResponse.add(account);
//            }
//
//            PrintWriter pw = response.getWriter();
//            Jsoner.serialize(carsResponse, pw);
//        }
//        catch (Exception ex) {
//            System.out.print(ex.getMessage());
//        }
    }
}