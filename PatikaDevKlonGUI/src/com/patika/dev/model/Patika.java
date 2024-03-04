package com.patika.dev.model;

import com.patika.dev.Helper.DBConnector;
import com.patika.dev.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Queue;

public class Patika {
    private int id;
    private String name;


    public static ArrayList<Patika> getList(){
        ArrayList <Patika> patikaList=new ArrayList<>();
        Patika obj;

        try {
            Statement st= DBConnector.getInstance().createStatement();
            ResultSet rs= st.executeQuery("SELECT * FROM patika");
            while (rs.next()){
                obj=new Patika(rs.getInt("id"),rs.getString("name"));
                patikaList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return patikaList;

    }
    public static Patika getFetch(int id) //Getfetch Metodlari veritabanindan bir tane veri cekmeye yarar
    {
        Patika obj=null;
        String query="SELECT * FROM patika WHERE id = ?";

        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs= pr.executeQuery();
            if (rs.next()){
                obj=new Patika(rs.getInt("id"),rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;


    }








    public static boolean add(String name){
        String query="INSERT INTO patika (name) VALUES (?)";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);

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
        String query=" DELETE FROM patika WHERE id = ?";
        ArrayList<Course> courseList = Course.getList();
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);

            // Patika silinirken ona bağlı olan kursları da sil
            for (Course course : courseList) {
                if (course.getPatika_id() == id) {
                    Course.delete(course.getId());
                }
            }

            // Patikayı sil
            return pr.executeUpdate() !=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean update(int id,String name){
        String query="UPDATE patika SET name =? WHERE id = ?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setInt(2,id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





































    public Patika(int id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
