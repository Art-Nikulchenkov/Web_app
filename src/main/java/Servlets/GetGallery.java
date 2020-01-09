package Servlets;

import DB.DataBaseClass;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

public class GetGallery extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("dP:GetGallery");
        String getGal = request.getParameter("getGallery");
        if(getGal != null && getGal.equals("true")){
            System.out.println("dP:GetGallery true");
            HttpSession session = request.getSession();
            int userId = Integer.parseInt(""+session.getAttribute("sessionId"));
            System.out.println("UserId: " + userId);
            try{
                DataBaseClass db = new DataBaseClass();
                LinkedList<byte[]> gallery = db.getGallery(userId);
                StringBuffer buf = new StringBuffer();
                for(byte[] i: gallery) {
                    String base64Encoded = new String(i, "UTF-8");
                    buf.append("<img class='galleryPicture'src='data:image/jpg;base64," + base64Encoded + "'>");
                }
                response.setContentType("text/html");
                response.getWriter().print(buf.toString());
                System.out.println("dP:GetGallery true end");
                return;

            }catch (SQLException ex){
                System.out.println(ex);
            }
        }
        System.out.println("dP:GetGallery false end");
    }
}