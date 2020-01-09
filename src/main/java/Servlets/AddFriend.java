package Servlets;

import DB.DataBaseClass;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class AddFriend extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("dP:addFriend");
        String addFriend = request.getParameter("addFriend");
        String friendId = request.getParameter("friendId");

        if(addFriend != null && addFriend.equals("true")){
            HttpSession session = request.getSession();
            int userId = Integer.parseInt(""+session.getAttribute("sessionId"));
            System.out.println("UserId: " + userId);
            int friend_id = Integer.parseInt(friendId);
            System.out.println("friend_id: " + friend_id);
            try{
                DataBaseClass db = new DataBaseClass();
                if(!db.isFriend(userId, friend_id) && db.containsUser(friend_id)){
                    System.out.println(friend_id + " contains");
                    db.addFriend(userId, friend_id);
                    response.setContentType("text/html");
                    response.getWriter().print("true");
                }
            }catch (SQLException ex){
                System.out.println(ex);
                response.setContentType("text/html");
                response.getWriter().print("false");
            }
        }
        response.setContentType("text/html");
        response.getWriter().print("false");
    }
}
