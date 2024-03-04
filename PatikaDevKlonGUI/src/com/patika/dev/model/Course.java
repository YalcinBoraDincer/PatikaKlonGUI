package com.patika.dev.model;

import com.patika.dev.Helper.Config;
import com.patika.dev.Helper.DBConnector;
import com.patika.dev.Helper.Helper;

import javax.lang.model.element.NestingKind;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Course {
    private int id;
    private int user_id;
    private int patika_id;
    private String name;
    private String lang;

    private Patika patika;
    private User educator;



    public static ArrayList<Course> getList() {
        ArrayList<Course> courseList=new ArrayList<>();
        Course obj;

        try {
            Statement st= DBConnector.getInstance().createStatement();
            ResultSet rs=st.executeQuery("SELECT * FROM course");
            while (rs.next()){
                int id=rs.getInt("id");
                int user_id=rs.getInt("user_id");
                int patika_id=rs.getInt("patika_id");
                String name=rs.getString("name");
                String language=rs.getString("lang");
                obj=new Course(id,user_id,patika_id,name,language);
                courseList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courseList;

    }

    public static ArrayList<Course> getListByUser(int user_id) {
        ArrayList<Course> courseList=new ArrayList<>();
        Course obj;

        try {
            Statement st= DBConnector.getInstance().createStatement();
            ResultSet rs=st.executeQuery("SELECT * FROM course WHERE user_id = "+user_id);
            while (rs.next()){
                int id=rs.getInt("id");
                int user_ID=rs.getInt("user_id");
                int patika_id=rs.getInt("patika_id");
                String name=rs.getString("name");
                String language=rs.getString("lang");
                obj=new Course(id,user_id,patika_id,name,language);
                courseList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courseList;

    }

    public static ArrayList<Course> getCoursesByEducator(int educatorID) {
        ArrayList<Course> educatorCourses = new ArrayList<>();

        try {
            String query = "SELECT * FROM course WHERE user_id = ?";
            PreparedStatement statement = DBConnector.getInstance().prepareStatement(query);
            statement.setInt(1, educatorID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int patika_id = resultSet.getInt("patika_id");
                String name = resultSet.getString("name");
                String lang = resultSet.getString("lang");
                Course course = new Course(id, educatorID, patika_id, name, lang);
                educatorCourses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Hata durumunda hata izini yazdır
        }

        return educatorCourses;
    }

    public static Course getCourseByID(int courseID) {

        Course obj = null;

        try {
            Statement st= DBConnector.getInstance().createStatement();
            ResultSet rs=st.executeQuery("SELECT * FROM course WHERE id = "+courseID);
            while (rs.next()){
                int id=rs.getInt("id");
                int user_ID=rs.getInt("user_id");
                int patika_id=rs.getInt("patika_id");
                String name=rs.getString("name");
                String language=rs.getString("lang");
                obj=new Course(id,user_ID,patika_id,name,language);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;


    }
    public static ArrayList<Course> getCoursesByPatikaId(int patikaId) {
        // Burada, tüm derslerin listesini alınır
        ArrayList<Course> allCourses = getList();
        // Filtrelenmiş derslerin listesi için boş bir ArrayList oluşturulur
        ArrayList<Course> filteredCourses = new ArrayList<>();

        // Tüm derslerin listesi üzerinde döner ve patika ID'sine göre filtreleme yapılır
        for (Course course : allCourses) {
            if (course.getPatika_id() == patikaId) {
                // Eğer dersin patika ID'si istenilen patika ID'si ile eşleşirse, ders filteredCourses listesine eklenir
                filteredCourses.add(course);
            }
        }

        // Filtrelenmiş derslerin listesi döndürülür
        return filteredCourses;
    }





    public static boolean add(int user_id,int patika_id,String name,String lang){
        String query="INSERT INTO course (user_id,patika_id,name,lang) VALUES (?,?,?,?)";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,user_id);
            pr.setInt(2,patika_id);
            pr.setString(3,name);
            pr.setString(4,lang);


            int response= pr.executeUpdate();
            if (response == -1){
                Helper.showMassage("error");
            }
            return response != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean delete(int ID){
        String query=" DELETE FROM course WHERE id = ?";

        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,ID);

            return pr.executeUpdate() !=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
































    public Course(int id, int user_id, int patika_id, String name, String lang) {
        this.id = id;
        this.user_id = user_id;
        this.patika_id = patika_id;
        this.name = name;
        this.lang = lang;
        this.patika=Patika.getFetch(patika_id);
        this.educator=User.getFetch(user_id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPatika_id() {
        return patika_id;
    }

    public void setPatika_id(int patika_id) {
        this.patika_id = patika_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Patika getPatika() {
        return patika;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
    }

    public User getEducator() { if (educator == null) { educator = User.getFetch(this.user_id); } return educator; }

    public void setEducator(User educator) {
        this.educator = educator;
    }
}
