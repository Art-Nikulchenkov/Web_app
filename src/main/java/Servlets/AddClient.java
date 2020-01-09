package Servlets;

import DB.DataBaseClass;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

public class AddClient extends HttpServlet {
    DataBaseClass dB;
    HttpSession session;
    int sessionId;
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect("HTML/Login.html");
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
        System.out.println("doPost");

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        boolean create = Boolean.parseBoolean(request.getParameter("create"));
        System.out.println("Your login: " + login +
                " Your password: " + password);

        try {
            int user_id;
            dB = new DataBaseClass();
            if(!create)
                user_id = dB.authorization(login, password);
            else {
                int age = Integer.parseInt(request.getParameter("age"));
                String gender = request.getParameter("gender");
                System.out.println("Client gender: " + gender);
                create = dB.create(login, password, age, gender);
                if(create) {
                    response.setContentType("text/html");
                    response.getWriter().print("true");
                    return;
                }
                else
                {
                    response.setContentType("text/html");
                    response.getWriter().print("Chose another login");
                    return;
                }
            }
            System.out.println("USER: " + user_id);
            if(user_id > -1){
                System.out.println("correct");
                response.setContentType("text/html");
                response.getWriter().print("true");
                sessionId = user_id;

                session = request.getSession();
                System.out.println("if");
                if(session.getAttribute("sessionId") == null){
                    System.out.println(true);
                    session.setAttribute("sessionId",sessionId);

                    session.setAttribute("userLogin", dB.getUserLogin(sessionId));
                    session.setAttribute("userAge", dB.getUserAge(sessionId));
                    session.setAttribute("userGender", dB.getUserGender(sessionId));
                    session.setAttribute("userPhoto", dB.getPicture(4));

                    RequestDispatcher rd = request.getRequestDispatcher("/JSP/HomePage.jsp");
                    try {
                        System.out.println("forward to /HomePage");
                        rd.forward(request, response);

                    }catch (Exception ex){
                        System.out.println("rd: " + ex);
                    }
                }
                else{
                    session.removeAttribute("sessionId");
                }
            }
            else{
                response.setContentType("text/html");
                response.getWriter().print("false");
            }
            System.out.println("doPost: End");
        }catch (Exception ex){
            System.out.println(ex);
        }
    }
}