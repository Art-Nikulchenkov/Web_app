package Servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import DB.DataBaseClass;

public class HomePageServlet extends HttpServlet {
    DataBaseClass db;
    int sessionId;
    HttpSession session;
    String userLogin;
    int userAge;
    String userGender;


//    public void doGet(HttpServletRequest request, HttpServletResponse response) {
//        System.out.println("HPdoGet");
//        try {
//            sessionId = (int) request.getSession().getAttribute("sessionId");
//        }catch (Exception ex){
//            sessionId = 0;
//        }
//
//        if(sessionId == 0){
//            System.out.println("sessionId == 0");
//            RequestDispatcher rd = request.getRequestDispatcher("/add");
//            try {
//                rd.forward(request, response);
//
//            }catch (ServletException ex){
//                System.out.println(ex);
//            }
//            catch (IOException ex){
//                System.out.println(ex);
//            }
//        }
//        else{
//            RequestDispatcher rd = request.getRequestDispatcher("JSP/HomePage.jsp");
//            try {
//                session.setAttribute("userLogin", userLogin);
//                session.setAttribute("userAge", userAge);
//                session.setAttribute("userGender", userGender);
//
//                session.setAttribute("friendsCount", db.getFriendIDlist(sessionId).size());
//
//                rd.forward(request, response);
//
//            }catch (Exception ex){
//                System.out.println(ex);
//            }
//        }
//    }

    public void doPost(HttpServletRequest request, HttpServletResponse response){
        session = request.getSession();
        System.out.println("dP");
        String tmpOut = request.getParameter("logout");
        if(tmpOut != null && tmpOut.equals("true")){
            System.out.println("logout: true");
            sessionId = 0;
            session.invalidate();
            try {
                request.getRequestDispatcher("HTML/Login.html").forward(request, response);
            }catch (Exception ex){
                System.out.println(ex);
            }
        }
        System.out.println("HomePageServlet");

        if(session.getAttribute("sessionId") == null){
            System.out.println("sessionId == null (HomePageServlet)");
            RequestDispatcher rd = request.getRequestDispatcher("HTML/Login.html");
            try {
                rd.forward(request, response);

            }catch (ServletException ex){
                System.out.println(ex);
            }
            catch (IOException ex){
                System.out.println(ex);
            }
        }
        else {
            System.out.println("sessionId != null");
            String tmp = session.getAttribute("sessionId").toString();
            sessionId = Integer.parseInt(tmp);
            System.out.println("User/Session id: " + sessionId);

            try {
                db = new DataBaseClass();
                userLogin = db.getUserLogin(sessionId);
                userAge = db.getUserAge(sessionId);
                userGender = db.getUserGender(sessionId);

                request.setAttribute("HEAD", "wow");

                System.out.println("doPost: forward to JSP/HomePage.jsp");
                request.getRequestDispatcher("JSP/HomePage.jsp").forward(request, response);
//                response.sendRedirect("JSP/HomePage.jsp");

            }catch (Exception ex){
                System.out.println(ex.toString());
            }
        }
        System.out.println("DpEnd");
    }

    String someMethod(){
        System.out.println("some method");
        return "someMethod";
    }
}
