package DB;

import java.io.*;
import java.sql.*;
import java.util.Base64;
import java.util.LinkedList;


public class DataBaseClass {
    Connection connection;
    Statement statement;
    ResultSet rs;

    public DataBaseClass()throws SQLException{
        String login = "root";
        String password = "root";
        String url = "jdbc:mysql://localhost:3306/test";
//            Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(url, login, password);
        statement = connection.createStatement();


    }

    public int authorization(String clientLogin, String clientPassword){
        try {
            rs = statement.executeQuery("select user_id, user_login, user_password from users");
            while (rs.next()) {
                int tmp = 1;
                int user_id = rs.getInt(tmp++);
                String user_login = rs.getString(tmp++);
                String user_password = rs.getString(tmp);
                if (clientLogin.equals(user_login) && clientPassword.equals(user_password)) {
                    return  user_id;
                }

            }
            rs = statement.executeQuery("select user_id, user_login, user_password, user_age, user_gender from users");
            while(rs.next()){
                int tmp = 1;
                int user_id = rs.getInt(tmp++);
                String user_login = rs.getString(tmp++);
                String user_password = rs.getString(tmp);
                if(clientLogin.equals(user_login)){
                    return -2;
                }
            }
            System.out.println("AUTHORIZATION");
        }catch (NullPointerException e) {
            System.out.println(e);
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return -1;
    }

    public boolean create(String clientLogin, String clientPassword, int clientAge, String clientGender)throws SQLException{
        if(authorization(clientLogin, clientPassword) == -2 || getIdByLogin(clientLogin) == -1){
            System.out.println("Exists");
            return false;
        }
        try{
            statement.executeUpdate("insert into users (user_login, user_password, user_age, user_gender, user_pictures)" +
                    " values('" + clientLogin + "', '" + clientPassword + "', " + clientAge + ", '" + clientGender + "', '" + 4 + "');");
            return true;
        }catch (SQLException e){
            System.out.println(e);
        }
        return false;
    }

    public boolean addPicture(int userId, byte[] pictureBytes) {
        try {
            StringBuffer buf = new StringBuffer();
            for(byte i:pictureBytes){
                buf.append((char)i);
            }
            for(int i = 0; i < 10; ++i)
                System.out.println(buf.charAt(i));
            System.out.println(buf);
            Blob coverBlob = connection.createBlob();
            OutputStream out = coverBlob.setBinaryStream(1);
            coverBlob.setBytes(1, pictureBytes);
            PreparedStatement stat = connection.prepareStatement("insert into pictures (picture) values (?)");
            stat.setBlob(1, coverBlob);
            stat.executeUpdate();
            rs = statement.executeQuery("select picture_id from pictures");
            rs.next();
            rs = statement.executeQuery("select user_id, user_pictures from users");
            while(rs.next()){
                int tmp = 1;
                int user_id = rs.getInt(tmp++);
                if(user_id == userId){
                    String tmpStr = rs.getString(tmp);
                    String tmpPictures;
                    rs = statement.executeQuery("select count(*) from pictures");
                    rs.next();
                    int count = rs.getInt(1);
                    if(tmpStr == null)
                        tmpPictures = "" + count;
                    else
                        tmpPictures = tmpStr + "," + count;

                    statement.executeUpdate("update users set user_pictures ='" +  tmpPictures + "' where user_id =" + userId + ";");
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public byte[] getPicture(int id) throws SQLException {
        byte[] tmPicture;
        rs = statement.executeQuery("select picture_id, picture from pictures");
        while(rs.next()){
            int tmp = 1;
            int picture_id = rs.getInt(tmp++);
            if(picture_id == id){
                tmPicture = rs.getBytes(tmp);
                return tmPicture;
            }
        }

        return null;
    }

    public LinkedList getPictureIDlist(int userId) throws SQLException {
        LinkedList<Integer> friendsId = new LinkedList<>();
        rs = statement.executeQuery("select user_id, user_pictures from users");
        while(rs.next()){
            int tmp = 1;
            int user_id = rs.getInt(tmp++);
            if(user_id == userId){
                String tmpS = rs.getString(tmp);
                if(tmpS == null)
                    return null;
                String[] friends = tmpS.split(",");
                for(String s:friends){
                    friendsId.add(Integer.parseInt(s));
                }
                return friendsId;
            }
        }
        return null;
    }

    public LinkedList getFriendIDlist(int id) throws SQLException {
        LinkedList<Integer> friendsId = new LinkedList<>();
        rs = statement.executeQuery("select user_id, user_friends from users");
        String friends;
        String[] substring;
        while(rs.next()){
            int tmp = 1;
            int user_id = rs.getInt(tmp++);
            if(user_id == id){
                friends = rs.getString(tmp);
                if(friends == null)
                    return null;
                substring = friends.split(",");
                for(String s:substring){
                    friendsId.add(Integer.parseInt(s));
                }
                return friendsId;
            }
        }
        return null;
    }

    public String getUserLogin(int id) throws SQLException{
        if(!containsUser(id))
            return null;
        rs = statement.executeQuery("select user_id, user_login from users");
        while(rs.next()){
            int tmp = 1;
            int user_id = rs.getInt(tmp++);
            if(user_id == id)
                return rs.getString(tmp);
        }
        return null;
    }

    public String getUserGender(int id) throws SQLException{
        if(!containsUser(id))
            return null;
        rs = statement.executeQuery("select user_id, user_gender from users");
        while(rs.next()){
            int tmp = 1;
            int user_id = rs.getInt(tmp++);
            if(user_id == id)
                return rs.getString(tmp);
        }
        return null;
    }

    public int getUserAge(int id) throws SQLException{
        if(!containsUser(id))
            return -1;
        rs = statement.executeQuery("select user_id, user_age from users");
        while(rs.next()){
            int tmp = 1;
            int user_id = rs.getInt(tmp++);
            if(user_id == id)
                return rs.getInt(tmp);
        }
        return -1;
    }

    public int getIdByLogin(String userLogin)throws SQLException{
        rs = statement.executeQuery("select user_login, user_id from users");
        while(rs.next()){
            int tmp = 1;
            String login = rs.getString(tmp++);
            if(login.equals(userLogin)){
                return rs.getInt(tmp);
            }
        }
        return -1;
    }

    public boolean addFriend(int userId, int newFriendId) throws SQLException {
        if(!containsUser(newFriendId) && userId == newFriendId)
            return false;
        if(isFriend(userId, newFriendId))
            return false;
        rs = statement.executeQuery("select user_id, user_friends from users");
        while(rs.next()){
            int tmp = 1;
            int user_id = rs.getInt(tmp++);
            if(user_id == userId){
                LinkedList arrTmp = getFriendIDlist(userId);
                if(arrTmp == null) {
                    statement.executeUpdate("update users set user_friends =" + newFriendId + " where user_id =" + userId + ";");
                    arrTmp = getFriendIDlist(newFriendId);
                    if(arrTmp == null || !arrTmp.contains(userId)) {
                        int tmp1 = userId < newFriendId ? userId : newFriendId;
                        int tmp2 = userId < newFriendId ? newFriendId : userId;
                        statement.executeUpdate("create table dialog" + tmp1 + "_" + tmp2 + "(" +
                                "message_id int(4) not null auto_increment," +
                                "message varchar(100) not null," +
                                "isPicture int(5)," +
                                "fromWho int(1)," +
                                "primary key(message_id)" +
                                ");");
                        addFriend(newFriendId,userId);
                        break;
                    }
                    return true;
                }
                for(int i = 0; i < arrTmp.size(); i++){
                    if(newFriendId == (Integer)arrTmp.get(i)){
                        return false;
                    }
                }
                String friendList = rs.getString(tmp++) + "," + newFriendId;
                statement.executeUpdate("update users set user_friends ='" + friendList + "' where user_id =" + userId + ";");

                arrTmp = getFriendIDlist(newFriendId);
                if(arrTmp == null || !arrTmp.contains(userId)) {
                    int tmp1 = userId < newFriendId ? userId : newFriendId;
                    int tmp2 = userId < newFriendId ? newFriendId : userId;
                    statement.executeUpdate("create table dialog" + tmp1 + "_" + tmp2 + "(" +
                            "message_id int(4) not null auto_increment," +
                            "message varchar(100) not null," +
                            "isPicture int(5)," +
                            "fromWho int(1)," +
                            "primary key(message_id)" +
                            ");");
                    addFriend(newFriendId,userId);
                    break;
                }
            }
        }
        return true;
    }

    public boolean containsUser(int userId) {
        try {
            rs = statement.executeQuery("select user_id from users");
            while(rs.next())
                if(rs.getInt(1) == userId)
                    return true;
        }catch(SQLException ex){
            System.out.println(ex);
        }
        return false;
    }

    public boolean isFriend(int userId, int unknownId){
        try {
            LinkedList friendsList = getFriendIDlist(userId);
            if(friendsList == null || !friendsList.contains(unknownId)){
                return false;
            }
            else
                return true;
        }catch (SQLException ex){
            System.out.println(ex);
            return false;
        }
    }

    public void writeMessage(int writerId, int friendId, String message, int image){
        try{
            if(!isFriend(writerId,friendId))
                return;
            int tmp1 = writerId < friendId ? writerId : friendId;
            int tmp2 = writerId < friendId ? friendId : writerId;
            statement.executeUpdate("insert into dialog" + tmp1 + "_" + tmp2 + "(message, isPicture, fromWho) values('" + message + "'," + image + "," + writerId + ");");

        }catch (SQLException ex){
            System.out.println(ex);
        }
    }

    public Message[] getAllMessages(int userId, int friendId){
        if(!isFriend(userId,friendId))
            return null;
        int tmp1 = userId < friendId ? userId : friendId;
        int tmp2 = userId < friendId ? friendId : userId;
        try {
            Message[] messages;
            int messagesCount;
            rs = statement.executeQuery("select count(*) from dialog" + tmp1 + "_" + tmp2);
            rs.next();
            messagesCount = rs.getInt(1);
            System.out.println("Messages count " + messagesCount);
            messages = new Message[messagesCount];
            rs = statement.executeQuery("select * from dialog" + tmp1 + "_" + tmp2);
            int count = 0;
            while(rs.next()){
                int tmp = 1;
                Message mes = new Message();
                mes.messageId = rs.getInt(tmp++);
                mes.message = rs.getString(tmp++);
                mes.isPicture = rs.getInt(tmp++);
                mes.fromWho = rs.getInt(tmp);
                messages[count++] = mes;
            }
            return messages;
        }catch (Exception ex){
            System.out.println(ex);
            return null;
        }
    }

    public LinkedList<byte[]> getGallery(int userId)throws SQLException{
        if(!containsUser(userId)){
            System.out.println("Not exists: " + userId);
            return null;
        }
        LinkedList<byte[]> gallery = new LinkedList<>();
        LinkedList<Integer> pictureId = getPictureIDlist(userId);
        for(Integer i: pictureId){
            try {
                byte[] picBytes = getPicture(i);
                Base64.Encoder enc = Base64.getEncoder();
                picBytes = enc.encode(picBytes);
                gallery.addLast(picBytes);
            }catch (Exception ex){
                System.out.println(ex);
            }
        }
        return gallery;
    }

}




