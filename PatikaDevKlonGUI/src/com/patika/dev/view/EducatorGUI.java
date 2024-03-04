package com.patika.dev.view;

import com.patika.dev.Helper.Config;
import com.patika.dev.Helper.Helper;
import com.patika.dev.model.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class EducatorGUI extends JFrame  {
    private JPanel wrapper;
    private JScrollPane pnl_edu_top;
    private JTable tbl_edu_egitimler;
    private JButton btn_edu_content;
    private JButton btn_edu_exit;
    private JTextField field_edu_coursepnl;
    private JTextField fileld_edu_quiz;
    private JButton btn_edu_quiz;

    //Table Modeller
    private DefaultTableModel mdl_educator_patika;
    private Object[] row_educator_list;
    private int educatorID;



    public EducatorGUI(int educatorID){
        Helper.setLayout();
        this.educatorID=educatorID;
        add(wrapper);
        setSize(1000,500);
        setResizable(false);
        int x= Helper.screenCenterLocation("x",getSize());
        int y=Helper.screenCenterLocation("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);





        //Tablo Islemleri

        mdl_educator_patika=new DefaultTableModel();
        Object[] col_edu_course_list ={"ID","Ders Adı","Programlama Dili","Patika","Eğitmen"};
        mdl_educator_patika.setColumnIdentifiers(col_edu_course_list);
        row_educator_list=new Object[col_edu_course_list.length];
        loadCoursesForEducator(educatorID);

        tbl_edu_egitimler.setModel(mdl_educator_patika);

        tbl_edu_egitimler.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_edu_egitimler.getTableHeader().setReorderingAllowed(false);







        btn_edu_content.addActionListener(e -> {
            int courseID= Integer.parseInt(field_edu_coursepnl.getText());
            contentGUI contentGUI=new contentGUI(courseID);

        });


        btn_edu_exit.addActionListener(e -> {
            dispose();
            loginGUI loginGUI=new loginGUI();

        });
        btn_edu_quiz.addActionListener(e -> {
            int courseID= Integer.parseInt(fileld_edu_quiz.getText());
            quizGUI quizGUI=new quizGUI(courseID);

        });
    }

    public void loadCoursesForEducator(int educatorID) {
        // Önce tabloyu temizle
        DefaultTableModel tableModel = (DefaultTableModel) tbl_edu_egitimler.getModel();
        tableModel.setRowCount(0);

        // Belirli eğitmenin derslerini al
        ArrayList<Course> educatorCourses = Course.getCoursesByEducator(educatorID);

        // Her bir ders için tabloya bir satır ekle
        int i=0;
        for (Course course : educatorCourses) {
            i=0;
            row_educator_list[i++]=course.getId();
            row_educator_list[i++]=course.getName();
            row_educator_list[i++]=course.getLang();
            row_educator_list[i++]=course.getPatika().getName();
            row_educator_list[i++]=course.getEducator().getName();
            mdl_educator_patika.addRow(row_educator_list);
        }
    }


    public static void main(String[] args) {
        Helper.setLayout();
        EducatorGUI educatorGUI=new EducatorGUI(31);
    }
}

