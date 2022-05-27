import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.*;
import java.io.PrintWriter;

@WebServlet(name = "TestServlet", value = "/TestServlet")
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        PrintWriter pw = response.getWriter();

        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");

        if (loggedIn == null) {
            pw.println("123321");
            loggedIn = false;
            session.setAttribute("loggedIn", loggedIn);
        }

        if (! (Boolean) loggedIn) {
            String login = request.getParameter("login");
            String password = request.getParameter("password");

            if (login != null && password != null && login.equals("denis") && password.equals("123")) {
                loggedIn = true;
                session.setAttribute("loggedIn", loggedIn);
            }
            else {
                pw.println("<h1> Leave! </h1>");
                return;
            }
        }

        pw.println("<h1> Welcome! </h1>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
