//package Tests;

//import DB.DataBaseClass;
//import org.junit.Test;
//
//import java.sql.SQLException;
//
//import static org.junit.Assert.*;
//
//public class DataBaseClassTest {
//
//    @Test
//    public void authorization() {
//        try{
//            DataBaseClass db = new DataBaseClass();
//            assertEquals(5, db.authorization("aaa", "123"));
//        }catch (SQLException ex){assertEquals(0, 1);}
//    }
//
//    @Test
//    public void getPicture() {
//        try{
//            DataBaseClass db = new DataBaseClass();
//            byte[] b = db.getPicture(5);
//            assertEquals(1, 1);
//        }catch (SQLException ex){assertEquals(0, 1);}
//    }
//
//    @Test
//    public void getPictureIDlist() {
//        try{
//            DataBaseClass db = new DataBaseClass();
//            db.getPictureIDlist(1);
//            assertEquals(1, db.getPictureIDlist(1).size());
//        }catch (SQLException ex){assertEquals(0, 1);}
//    }
//
//    @Test
//    public void getFriendIDlist() {
//        try{
//            DataBaseClass db = new DataBaseClass();
//            db.getPictureIDlist(1);
//            assertEquals(3, db.getFriendIDlist(1).size());
//        }catch (SQLException ex){assertEquals(0, 1);}
//    }
//
//    @Test
//    public void getUserLogin() {
//        try{
//            DataBaseClass db = new DataBaseClass();
//            String login = db.getUserLogin(1);
//            if(login.equals("User_number_one")){
//                assertEquals(1, 1);
//            }else {
//                assertEquals(1, 2);
//            }
//        }catch (SQLException ex){assertEquals(0, 1);}
//    }
//
//    @Test
//    public void getUserGender() {
//        try{
//            DataBaseClass db = new DataBaseClass();
//            String str = db.getUserGender(1);
//            if(str.equals("male")){
//                assertEquals(1, 1);
//            }else {
//                assertEquals(1, 2);
//            }
//        }catch (SQLException ex){assertEquals(0, 1);}
//    }
//
//    @Test
//    public void getUserAge() {
//        try{
//            DataBaseClass db = new DataBaseClass();
//            int str = db.getUserAge(1);
//            if(str == 23){
//                assertEquals(1, 1);
//            }else {
//                assertEquals(1, 2);
//            }
//        }catch (SQLException ex){assertEquals(0, 1);}
//    }
//
//    @Test
//    public void getIdByLogin() {
//        try{
//            DataBaseClass db = new DataBaseClass();
//            int str = db.getIdByLogin("User_number_one");
//            if(str == 1){
//                assertEquals(1, 1);
//            }else {
//                assertEquals(1, 2);
//            }
//        }catch (SQLException ex){assertEquals(0, 1);}
//    }
//
//    @Test
//    public void containsUser() {
//        try{
//            DataBaseClass db = new DataBaseClass();
//            if(db.containsUser(1)){
//                assertEquals(1, 1);
//            }else {
//                assertEquals(1, 2);
//            }
//        }catch (SQLException ex){assertEquals(0, 1);}
//    }
//
//    @Test
//    public void containsUser2() {
//        try{
//            DataBaseClass db = new DataBaseClass();
//            if(db.containsUser(100)){
//                assertEquals(0, 1);
//            }else {
//                assertEquals(1, 1);
//            }
//        }catch (SQLException ex){assertEquals(0, 1);}
//    }
//
//    @Test
//    public void isFriend() {
//        try{
//            DataBaseClass db = new DataBaseClass();
//            if(db.isFriend(5, 6)){
//                assertEquals(1, 1);
//            }else {
//                assertEquals(1, 2);
//            }
//        }catch (SQLException ex){assertEquals(0, 1);}
//    }
//
//    @Test
//    public void isFriend2() {
//        try{
//            DataBaseClass db = new DataBaseClass();
//            if(db.isFriend(5, 60)){
//                assertEquals(1, 2);
//            }else {
//                assertEquals(1, 1);
//            }
//        }catch (SQLException ex){assertEquals(0, 1);}
//    }
//}
