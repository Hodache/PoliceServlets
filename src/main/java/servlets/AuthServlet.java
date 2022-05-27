package servlets;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import database.DBHelper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;

@WebServlet(name = "AuthServlet", value = "/auth")
public class AuthServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBHelper.makeConnection();

        String sessionID = "0";

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        boolean status = DBHelper.checkAccount(login, password);

        if (status) {
            HttpSession userSession = request.getSession();
            sessionID = userSession.getId();

            userSession.setAttribute("sessionID", sessionID);
            userSession.setMaxInactiveInterval(1800);
        }

        JsonObject statusResponse = new JsonObject();
        statusResponse.put("sessionID", sessionID);

        PrintWriter pw = response.getWriter();
        Jsoner.serialize(statusResponse, pw);
        pw.close();
    }
}
