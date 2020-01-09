package Servlets;

import DB.DataBaseClass;
import DB.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;

public class GetMessages extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String message = request.getParameter("getMessages");
        String friendLogin = request.getParameter("friendLogin");
        if(message != null && message.equals("true")){
            try{
                HttpSession session = request.getSession();
                DataBaseClass db = new DataBaseClass();
                int userId = Integer.parseInt(""+session.getAttribute("sessionId"));
                int friendId = db.getIdByLogin(friendLogin);
                if(db.isFriend(userId, friendId)) {
                    Message[] messages = db.getAllMessages(userId, friendId);
                    StringBuffer buf = new StringBuffer();
                    for(int i = 0; i < messages.length; i++){
                        if(messages[i].getFromWho() != userId) {
                            if(messages[i].getIsPicture() == 0) {
                                buf.append("<h1 class='friendMessage'>" + messages[i].getMessage() + "</h1>");
                            }else {
                                byte[] userPhoto = db.getPicture(messages[i].getIsPicture());
                                Base64.Encoder enc = Base64.getEncoder();
                                userPhoto = enc.encode(userPhoto);
                                String base64Encoded = new String(userPhoto, "UTF-8");
                                buf.append("<p><h2>Picture from: " + friendLogin + "</h2><img class='messagePicture'src='data:image/jpg;base64," + base64Encoded + "'></p>");
                            }
                        }
                        else {
                            if(messages[i].getIsPicture() == 0) {
                                buf.append("<h1 class='myMessage'>" + messages[i].getMessage() + "</h1>");
                            }else {
                                byte[] userPhoto = db.getPicture(messages[i].getIsPicture());
                                Base64.Encoder enc = Base64.getEncoder();
                                userPhoto = enc.encode(userPhoto);
                                String base64Encoded = new String(userPhoto, "UTF-8");
                                buf.append("<p><h2>My pic:</h2><img class='messagePicture'src='data:image/jpg;base64," + base64Encoded + "'></p>");
                            }
                        }
                    }
                    response.setContentType("text/html");
                    response.getWriter().print(buf.toString());
                }
                else{
                    System.out.println("Not friends");
                }
            }catch (SQLException ex){
                System.out.println(ex);
            }
        }

    }
}
