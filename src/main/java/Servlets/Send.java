package Servlets;

import DB.DataBaseClass;

import javax.jms.Session;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

public class Send extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String message = request.getParameter("message");
        String to = request.getParameter("to");
        String isPic = request.getParameter("isPicture");
        System.out.println("Message to " + to + ": " + message);
        System.out.println("IsPicture: " + isPic);
        try {
            DataBaseClass db = new DataBaseClass();
            int toId = db.getIdByLogin(to);
            HttpSession session = request.getSession();
            int writerId = Integer.parseInt("" + session.getAttribute("sessionId"));
            System.out.println("Message from " + writerId + " to " + toId);

            int picId;
            picId = Integer.parseInt(isPic);
            LinkedList<Integer> pictureId = db.getPictureIDlist(writerId);
            System.out.println(pictureId);

            if(picId == 0)
                db.writeMessage(writerId, toId, message, 0);
            else {
                db.writeMessage(writerId, toId, message, pictureId.get(picId - 1));
            }


        }catch (Exception ex){
            System.out.println(ex);
        }
    }
}
