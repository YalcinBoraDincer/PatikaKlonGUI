package com.patika.dev.model;

import com.patika.dev.Helper.DBConnector;
import com.patika.dev.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Educator_rating {
    private int id;
    private int educator_id;
    private int related_course_id;
    private String educator_rank;
    private String educator_evulation;




    public static boolean add(int educator_id,int related_course_id,String educator_rank,String educator_evulation){
        String query="INSERT INTO educator_rating (educator_id,related_course_id,educator_rank,educator_evulation) VALUES (? , ? , ?, ? )";
        try {
            PreparedStatement pr= DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,educator_id);
            pr.setInt(2,related_course_id);
            pr.setString(3,educator_rank);
            pr.setString(4,educator_evulation);


            int response= pr.executeUpdate();
            if (response == -1){
                Helper.showMassage("error");
            }
            return response != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }//Veritabanina Content Ekeleme


    public Educator_rating(int id, int educator_id, int related_course_id, String educator_rank, String educator_evulation) {
        this.id = id;
        this.educator_id = educator_id;
        this.related_course_id = related_course_id;
        this.educator_rank = educator_rank;
        this.educator_evulation = educator_evulation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEducator_id() {
        return educator_id;
    }

    public void setEducator_id(int educator_id) {
        this.educator_id = educator_id;
    }

    public String getEducator_rank() {
        return educator_rank;
    }

    public void setEducator_rank(String educator_rank) {
        this.educator_rank = educator_rank;
    }

    public String getEducator_evulation() {
        return educator_evulation;
    }

    public void setEducator_evulation(String educator_evulation) {
        this.educator_evulation = educator_evulation;
    }

    public int getRelated_course_id() {
        return related_course_id;
    }

    public void setRelated_course_id(int related_course_id) {
        this.related_course_id = related_course_id;
    }
}
