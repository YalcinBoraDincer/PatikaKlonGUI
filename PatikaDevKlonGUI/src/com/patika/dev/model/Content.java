package com.patika.dev.model;

import com.patika.dev.Helper.DBConnector;
import com.patika.dev.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Content {
    private int id;
    private int course_id;
    private String contentName;
    private String contentDesc;
    private String videoLink;
    private Course relatedCourse;

    public static ArrayList<Content> getList() {
        ArrayList<Content> contentList=new ArrayList<>();
        Content obj;

        try {
            Statement st= DBConnector.getInstance().createStatement();
            ResultSet rs=st.executeQuery("SELECT * FROM content");
            while (rs.next()){
                int id=rs.getInt("id");
                int course_id=rs.getInt("course_id");
                String contentName=rs.getString("contentName");
                String contentDesc=rs.getString("contentDesc");
                String videoLink=rs.getString("videoLink");

                obj=new Content(id,course_id,contentName,contentDesc,videoLink);
                contentList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contentList;

    }

    public static ArrayList<Content> getContent(int course_id) {
        ArrayList<Content> contentss = new ArrayList<>();
        Content obj;

        try {
            String query = "SELECT * FROM content WHERE course_id = ?";
            PreparedStatement statement = DBConnector.getInstance().prepareStatement(query);
            statement.setInt(1, course_id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                int id=rs.getInt("id");
                int courseID=rs.getInt("course_id");
                String contentName=rs.getString("contentName");
                String contentDesc=rs.getString("contentDesc");
                String videoLink=rs.getString("videoLink");

                obj=new Content(id,courseID,contentName,contentDesc,videoLink);
                contentss.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Hata durumunda hata izini yazdır
        }

        return contentss;
    }



    public static boolean add(int course_id,String contentName,String contentDesc,String videoLink){
        String query="INSERT INTO content (course_id,contentName,contentDesc,videoLink) VALUES (?, ?, ? ,?)";

        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,course_id);
            pr.setString(2,contentName);
            pr.setString(3,contentDesc);
            pr.setString(4,videoLink);

            int response= pr.executeUpdate();
            if (response == -1){
                Helper.showMassage("error");
            }
            return response != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }//Veritabanina Content Ekeleme

    public static boolean delete(int id){
        String query="DELETE FROM content WHERE id = ? ";
       /* ArrayList<Course> courseList=Course.getListByUser(id);
        for (Course c:courseList){
            Course.delete(c.getId());

        }*/
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);

            return pr.executeUpdate() !=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }//Silme Islemi

    public static boolean update(int id,String contentName,String contentDesc,String videoLink){
        String updateQuery="UPDATE content SET contentName=?,contentDesc=?,videoLink=? WHERE id =?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(updateQuery);
            pr.setString(1,contentName);
            pr.setString(2,contentDesc);
            pr.setString(3,videoLink);
            pr.setInt(4,id);
            return pr.executeUpdate() != -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }//Secilen sattira gore guncelleme

























    public Content(int id, int course_id, String contentName, String contentDesc, String videoLink){
        this.id = id;
        this.course_id = course_id;
        this.contentName = contentName;
        this.contentDesc = contentDesc;
        this.videoLink = videoLink;
        try {
            relatedCourse=Course.getCourseByID(course_id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getContentDesc() {
        return contentDesc;
    }

    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public Course getRelatedCourse() {
        return relatedCourse;
    }

    public void setRelatedCourse(Course relatedCourse) {
        this.relatedCourse = relatedCourse;
    }
}
