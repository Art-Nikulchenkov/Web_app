package Servlets;

import DB.DataBaseClass;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;

public class ChosePic extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("dP:ChosePic");
        String chosePic = request.getParameter("chosePic");
        if(chosePic != null && chosePic.equals("true")){
            System.out.println("dP:ChosePic true");
            HttpSession session = request.getSession();
            int userId = Integer.parseInt(""+session.getAttribute("sessionId"));
            try{
                DataBaseClass db = new DataBaseClass();
                LinkedList<byte[]> picBytes = db.getGallery(userId);
                StringBuffer buf = new StringBuffer();
                int it = 0;
                for(byte[] i: picBytes) {
                    String base64Encoded = new String(i, "UTF-8");
                    buf.append("<img class='chosePicture'onclick=choseThisPic(" + it + ") src='data:image/jpg;base64," + base64Encoded + "'>");
                    it++;
                }
                response.setContentType("text/html");
                response.getWriter().print(buf.toString());
                System.out.println("dP:ChosePic true end");

            }catch (Exception ex){
                System.out.println();
            }
        }
    }
}
