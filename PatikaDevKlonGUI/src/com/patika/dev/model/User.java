package com.patika.dev.model;

import com.patika.dev.Helper.DBConnector;
import com.patika.dev.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
    //Modelling for user table and making objects
    private int id;
    private String name;
    private  String username;
    private String password;
    private String userType;


    public static ArrayList<User> getList(){
        ArrayList<User> userList=new ArrayList<>();
        String query="SELECT * FROM user";
        User obj;
        try {
            Statement st= DBConnector.getInstance().createStatement();
            ResultSet rs=st.executeQuery(query);
            while (rs.next()){
                obj=new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPassword(rs.getString("password"));
                obj.setUserType(rs.getString("utype"));
                userList.add(obj);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;

    } //Veritabanindaki User Bilgilerini ArrayList Seklinde Almak Icin
    public static User getFetch(String username) //Getfetch Metodlari veritabanindan bir tane veri cekmeye yarar
    {
        User obj=null;
        String sql="SELECT * FROM user WHERE username = ?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(sql);
            pr.setString(1,username);
            ResultSet rs=pr.executeQuery();
            if (rs.next()){
                obj=new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPassword(rs.getString("password"));
                obj.setUserType(rs.getString("utype"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;

    }

    public static User getFetch(int id) //Getfetch Metodlari veritabanindan bir tane veri cekmeye yarar
    {
        User obj=null;
        String sql="SELECT * FROM user WHERE id = ?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(sql);
            pr.setInt(1,id);
            ResultSet rs=pr.executeQuery();
            if (rs.next()){
                obj=new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPassword(rs.getString("password"));
                obj.setUserType(rs.getString("utype"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;

    }
    public static User getFetch(String username,String password) //Getfetch Metodlari veritabanindan bir tane veri cekmeye yarar
    {
        User obj=null;
        String sql="SELECT * FROM user WHERE username = ? AND password = ?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(sql);
            pr.setString(1,username);
            pr.setString(2,password);
            ResultSet rs=pr.executeQuery();
            if (rs.next()){
                switch (rs.getString("utype")){
                    case "operator":
                        obj=new Operator();
                        break;
                    default:
                        obj=new User();
                }
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPassword(rs.getString("password"));
                obj.setUserType(rs.getString("utype"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;

    }
    public static ArrayList<User> searchUserList(String query){
        ArrayList<User> userList=new ArrayList<>();

        User obj; // --> Her seferinde yeniden user nesnesi uretmemek icin bir tane burada belirleyip degistiriyoruz
        try {
            Statement st= DBConnector.getInstance().createStatement();
            ResultSet rs=st.executeQuery(query);
            while (rs.next()){
                obj=new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPassword(rs.getString("password"));
                obj.setUserType(rs.getString("utype"));
                userList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;

    }



    public static boolean add(String name,String username,String password,String type){
        String query="INSERT INTO user (name,username,password,utype) VALUES (?, ?, ?, ?)";
        User finduser=User.getFetch(username);
        if (finduser != null){
            Helper.showMassage("Bu kullanıcı adı daha önceden alınmış !");
            return false;
        }
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,username);
            pr.setString(3,password);
            pr.setString(4,type);

            int response= pr.executeUpdate();
            if (response == -1){
                Helper.showMassage("error");
            }
            return response != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }//Veritabanina User Ekeleme

    public static boolean addStudent(String name,String username,String password,String type){
        String query="INSERT INTO user (name,username,password,utype) VALUES (?, ?, ?, ?)";
        User finduser=User.getFetch(username);
        if (finduser != null){
            Helper.showMassage("Bu kullanıcı adı daha önceden alınmış !");
            return false;
        }
        Helper.showMassage("Kayıt Başarılı !");
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,username);
            pr.setString(3,password);
            pr.setString(4,type);

            int response= pr.executeUpdate();
            if (response == -1){
                Helper.showMassage("error");
            }
            return response != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public static boolean delete(int id){
        String query="DELETE FROM user WHERE id = ? ";
        ArrayList<Course> courseList=Course.getListByUser(id);
        for (Course c:courseList){
            Course.delete(c.getId());

        }
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);

            return pr.executeUpdate() !=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    //Silme Islemi
    public static boolean update(int id,String name,String username,String password,String type){
        String updateQuery="UPDATE user SET name=?,username=?,password=?,utype=? WHERE id =?";
        User finduser=User.getFetch(username);
        if (finduser != null && finduser.getId() != id){
            Helper.showMassage("Bu kullanıcı adı daha önceden alınmış !");
            return false;

        }// Eger kullanici ayni isimde biriyle guncellemeye calisirsa hata verecek

            try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(updateQuery);
            pr.setString(1,name);
            pr.setString(2,username);
            pr.setString(3,password);
            pr.setString(4,type);
            pr.setInt(5,id);
            return pr.executeUpdate() != -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }//Secilen sattira gore guncelleme



    //Dinamik Yapi Icin Query Metodu
    public static String searchQuery(String name,String username,String userType){
        String sqlQuery="SELECT * FROM user WHERE username LIKE '%{{username}}%' AND name LIKE '%{{name}}%' ";
        sqlQuery=sqlQuery.replace("{{username}}",username);
        sqlQuery=sqlQuery.replace("{{name}}",name);
        if (!userType.isEmpty()){
            sqlQuery+=" AND utype LIKE '{{utype}}' ";
            sqlQuery=sqlQuery.replace("{{utype}}",userType);
        }
        return sqlQuery;
    }










































    //Constructors
    public User() {
    }

    public User(int id, String name, String username, String password,String userType) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.userType=userType;
    }

 //Get Set Methods

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
