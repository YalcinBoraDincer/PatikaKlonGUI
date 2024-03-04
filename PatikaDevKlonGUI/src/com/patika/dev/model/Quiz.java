package com.patika.dev.model;

import com.patika.dev.Helper.DBConnector;
import com.patika.dev.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Quiz {
    private int course_id;
    private int question_id;
    private String question;
    private String option_a;
    private String option_b;
    private String option_c;
    private String option_d;
    private String answer;
    private Course relatedCourse;




    public static ArrayList<Quiz> getQuiz(int course_id) {
        ArrayList<Quiz> quiezes = new ArrayList<>();
        Quiz obj;

        try {
            String query = "SELECT * FROM quiz WHERE course_id = ?";
            PreparedStatement statement = DBConnector.getInstance().prepareStatement(query);
            statement.setInt(1, course_id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                int courseID=rs.getInt("course_id");
                int question_id=rs.getInt("question_id");
                String question=rs.getString("question");
                String option_a=rs.getString("option_a");
                String option_b=rs.getString("option_b");
                String option_c=rs.getString("option_c");
                String option_d=rs.getString("option_d");
                String true_answer=rs.getString("true_answer");

                obj=new Quiz(courseID,question_id,question,option_a,option_b,option_c,option_d,true_answer);
                quiezes.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Hata durumunda hata izini yazdır
        }

        return quiezes;
    }
    public static ArrayList<Quiz> getQuizzesByCourseId(int courseId) {
        ArrayList<Quiz> quizzes = new ArrayList<>();

        try {
            String query = "SELECT * FROM quiz WHERE course_id = ?";
            PreparedStatement statement = DBConnector.getInstance().prepareStatement(query);
            statement.setInt(1, courseId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int question_id = rs.getInt("question_id");
                String question = rs.getString("question");
                String option_a = rs.getString("option_a");
                String option_b = rs.getString("option_b");
                String option_c = rs.getString("option_c");
                String option_d = rs.getString("option_d");
                String true_answer = rs.getString("true_answer");

                Quiz quiz = new Quiz(courseId, question_id, question, option_a, option_b, option_c, option_d, true_answer);
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Hata durumunda hata izini yazdır
        }

        return quizzes;
    }

    public static boolean add(int courseID,String question,String option_a,String option_b,String option_c,String option_d,String true_answer){
        String query="INSERT INTO quiz (course_id,question,option_a,option_b,option_c,option_d,true_answer) VALUES (? , ? , ? ,? , ? , ? , ?)";

        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,courseID);
            pr.setString(2,question);
            pr.setString(3,option_a);
            pr.setString(4,option_b);
            pr.setString(5,option_c);
            pr.setString(6,option_d);
            pr.setString(7,true_answer);

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
        String query="DELETE FROM quiz WHERE question_id = ? ";

        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);

            return pr.executeUpdate() !=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }//Silme Islemi


    public static boolean update(int id,String question,String option_a,String option_b,String option_c,String option_d,String answer){
        String updateQuery="UPDATE quiz SET question=?,option_a=?,option_b=?,option_c=?,option_d=?,true_answer=? WHERE question_id =?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(updateQuery);
            pr.setString(1,question);
            pr.setString(2,option_a);
            pr.setString(3,option_b);
            pr.setString(4,option_c);
            pr.setString(5,option_d);
            pr.setString(6,answer);
            pr.setInt(7,id);

            return pr.executeUpdate() != -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }//Secilen sattira gore guncelleme























    public Quiz(int course_id,int question_id, String question, String option_a, String option_b, String option_c, String option_d, String answer) {
        this.course_id=course_id;
        this.question_id = question_id;
        this.question = question;
        this.option_a = option_a;
        this.option_b = option_b;
        this.option_c = option_c;
        this.option_d = option_d;
        this.answer = answer;
        relatedCourse=Course.getCourseByID(course_id);
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption_a() {
        return option_a;
    }

    public void setOption_a(String option_a) {
        this.option_a = option_a;
    }

    public String getOption_b() {
        return option_b;
    }

    public void setOption_b(String option_b) {
        this.option_b = option_b;
    }

    public String getOption_c() {
        return option_c;
    }

    public void setOption_c(String option_c) {
        this.option_c = option_c;
    }

    public String getOption_d() {
        return option_d;
    }

    public void setOption_d(String option_d) {
        this.option_d = option_d;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Course getRelatedCourse() {
        return relatedCourse;
    }

    public void setRelatedCourse(Course relatedCourse) {
        this.relatedCourse = relatedCourse;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }
}
