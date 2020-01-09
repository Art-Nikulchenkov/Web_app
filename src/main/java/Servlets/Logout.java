package Servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Logout extends HttpServlet {
    HttpSession session;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        session = request.getSession();
        System.out.println("Logout");
        response.setContentType("text/html");
        response.getWriter().print("true");
        session.invalidate();
        try {
            request.getRequestDispatcher("/add").forward(request, response);
        }catch (ServletException ex){
            System.out.println(ex);
        }
    }
}
