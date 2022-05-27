package servlets;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import database.DBHelper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "DeleteFinesServlet", value = "/DeleteFinesServlet")
public class DeleteFinesServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Проверка входа пользователя
        HttpSession userSession = request.getSession();
        if (userSession.getAttribute("sessionID") == null) {
            System.out.println("User is not logged in");
            response.setStatus(403);
            return;
        }

        int fineID = Integer.parseInt(request.getParameter("fineID"));

        boolean deleted = false;

        DBHelper.makeConnection();
        deleted = DBHelper.deleteFine(fineID);

        JsonObject statusResponse = new JsonObject();
        statusResponse.put("deleted", deleted);

        PrintWriter pw = response.getWriter();
        Jsoner.serialize(statusResponse, pw);
        pw.close();
    }
}
