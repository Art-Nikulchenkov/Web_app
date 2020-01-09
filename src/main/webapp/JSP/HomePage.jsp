<!-- <!DOCTYPE html> -->
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page isELIgnored="false"%>

<%@ page import ="java.sql.*"%>
                <%@ page import ="DB.*"%>
                <%@ page import="java.util.Base64"%>
                <%@ page import="java.util.LinkedList"%>
                <%@ page import ="java.beans.Encoder"%>
<html>
    <head>
        <link rel='stylesheet' href="../CSS/HomePageStyle.css">
        <script src="../JS/HomePage.js"></script>
        <!-- JSP -->

    </head>
    <body>
        <h2 id="PHOTOGRAMtext">PHOTOGRAM</h2>
        <div class="container" id="HeadContainer">
            <button class="mainButton" onclick=logout()>Log out</button>
            <button class="mainButton" onclick=openSearchById() id="searchButton">Search by id</button>
            <input type="text" class="textField"  id="searchField" hidden=true>
            <button class="mainButton" onclick=leftDialog()>Left dialog</button>
            <button class="mainButton" onclick=showGallery() id="galleryButton">Show gallery</button>
            <button class="mainButton" onclick=refreshMessages()>Refresh messages</button>
        </div>
        <div class="container" id="FriendsContainer">
                <div class="scrollbar" id="scrollFriends">
                    <h1>Your Friends:</h1>
                        <% 
                        DataBaseClass dbc = new DataBaseClass();
                        LinkedList friendList = dbc.getFriendIDlist(Integer.parseInt(""+session.getAttribute("sessionId")));

                        if(friendList != null){
                            for(int i = 0; i < friendList.size(); i++){
                                    LinkedList frIdList = dbc.getPictureIDlist(Integer.parseInt(""+friendList.get(i)));
                                    if(frIdList == null)
                                        continue;

                                    int friendPicId = Integer.parseInt(""+frIdList.getFirst());

                                    byte[] userPhoto = dbc.getPicture(friendPicId);
                                    Base64.Encoder enc = Base64.getEncoder();
                                    userPhoto = enc.encode(userPhoto);
                                    String base64Encoded = new String(userPhoto, "UTF-8");

                                    session.setAttribute("friendPhoto" + Integer.parseInt(""+friendList.get(i)), base64Encoded);
                                    out.print("<p><img class='friendPicture'src='data:image/jpg;base64," + base64Encoded + "'><button class='friendButton' onclick=chooseFriend('"+dbc.getUserLogin(Integer.parseInt(""+friendList.get(i)))+"')>" + dbc.getUserLogin(Integer.parseInt(""+friendList.get(i))) + "</button></p>");
                            }
                        }
                        %>
                </div>
        </div>
        <div class="container" id="MessagesContainer" hidden=true>
            <div class="scrollbar" id="scrollMessages">
        
            </div>
            <button class="sendingButton" onclick=choosePicture() id="sendPicButton">Choose pic</button>
            <button class="sendingButton" onclick=send() id="sendButton">Send</button>
            <input type="text" class="textField"  id="messageField">
            <div class="container" id="chosePic">

            </div>

        </div>
        <div class="container" id="UserDataContainer">
            <!-- <div> -->
                
                <%
                LinkedList userPhotos = dbc.getPictureIDlist(Integer.parseInt(""+session.getAttribute("sessionId")));
                byte[] userPhoto = dbc.getPicture(Integer.parseInt(""+userPhotos.get(0)));
                Base64.Encoder enc = Base64.getEncoder();
                userPhoto = enc.encode(userPhoto);
                String base64Encoded = new String(userPhoto, "UTF-8");
                session.setAttribute("userPhoto", base64Encoded);
                %>
                <img class='userPicture' src="data:image/jpg;base64,${userPhoto}" id="userPhoto">
        
                    <h1></h1>
                    <h1>Name: ${userLogin}</h1>
                    <h1>Id: ${sessionId}</h1>
                    <h1>Age: ${userAge}</h1>
                    <h1>Gender: ${userGender}</h1>
            <!-- </div> -->
    </div>
    </body>
</html>