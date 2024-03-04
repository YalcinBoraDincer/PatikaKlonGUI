package com.patika.dev.view;

import com.patika.dev.Helper.Config;
import com.patika.dev.Helper.Helper;
import com.patika.dev.Helper.Item;
import com.patika.dev.model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class StudentGUI extends JFrame{
    private JPanel wrapper;
    private JComboBox cmb_student_patika;
    private JComboBox cmb_courses;
    private JButton derseKaydolButton;
    private JButton btn_student_content;
    private JComboBox cmb_course_select;
    private JTextArea area_comment;
    private JComboBox cmb_educator;
    private JComboBox cmb_rating;
    private JButton btn_exit;
    private JButton yorumuKaydetButton;
    private JButton bnt_student_quiz;
    private JComboBox cmb_tarih;
    private int selectedPatika_id;
    private int course_ID;
    private int EducatorID;
    private int studentID;

    public StudentGUI(int id){
        this.studentID=id;
        add(wrapper);
        setSize(1000,500);
        int x= Helper.screenCenterLocation("x",getSize());
        int y=Helper.screenCenterLocation("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        loadPatikaCombobox();
        loadCoursesForSelectedPatika(selectedPatika_id);
        loadEducatorCombobox();
        loadCourseForSelectedEducator(EducatorID);







        cmb_student_patika.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedPatikaId = ((Item) cmb_student_patika.getSelectedItem()).getKey();
                selectedPatika_id=selectedPatikaId;
                // Seçilen patikanın derslerini yükle
                loadCoursesForSelectedPatika(selectedPatikaId);

            }
        });


        cmb_courses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Seçilen öğeyi al
                Object selectedItem = cmb_courses.getSelectedItem();

                // Seçilen öğenin null olup olmadığını kontrol et
                if (selectedItem instanceof Item) {
                    Item selectedCourseItem = (Item) selectedItem;

                    // Dersin ID'sini al
                    int courseId = selectedCourseItem.getKey();
                    course_ID = courseId;
                    System.out.println(courseId);
                } else {
                    Helper.showMassage("Hiçbir ders seçilmedi.");
                }
            }
        });


        cmb_educator.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedEducatorId = ((Item) cmb_educator.getSelectedItem()).getKey();
                EducatorID=selectedEducatorId;
                System.out.println(EducatorID);
                // Seçilen patikanın derslerini yükle
                loadCourseForSelectedEducator(selectedEducatorId);

            }
        });










        btn_student_content.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentGUIforSTUDENT contentGUIforSTUDENT=new contentGUIforSTUDENT(course_ID);
            }
        });
        btn_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        derseKaydolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDate today = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
                String date = today.format(formatter);
                Item courseItem= (Item) cmb_courses.getSelectedItem();
                if (courseItem==null){
                    Helper.showMassage("Lutfen Bir Ders Secin");
                }else {
                    if (Helper.confirm(courseItem.toString()+"Dersıne Kayıt Olmak Istedıgıne Emın Mısın ?")){
                        if (enrolledClases.add(courseItem.getKey(),studentID,date)){
                            Helper.showMassage("done");
                        }else {
                            //Helper.showMassage("error");
                        }
                    }
                }




            }
        });
        yorumuKaydetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Item courseItem= (Item) cmb_course_select.getSelectedItem();
                Item educatorItem= (Item) cmb_educator.getSelectedItem();
                String rating = (String) cmb_rating.getSelectedItem();
                String comment=area_comment.getText();

                if (courseItem == null || educatorItem == null || rating == null || comment.isEmpty()) {
                    Helper.showMassage("Boş alan bırakmayın");
                } else {
                    if (Helper.confirm(educatorItem.toString() + " Adlı hocaya yorum yapmak istediğine emin misin ?")) {
                        if (Educator_rating.add(EducatorID, courseItem.getKey(), rating, comment)) {
                            Helper.showMassage("done");
                        } else {
                            //Helper.showMassage("error");
                        }
                    }
                }

            }
        });
        bnt_student_quiz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (enrolledClases.isAlreadyEnrolled(course_ID,studentID)){
                    quizForSTUDENT quizForSTUDENT=new quizForSTUDENT(course_ID);
                }else {
                    Helper.showMassage("Quiz olabilmeniz icin derse kaydolmalisiniz! ");
                }
            }
        });
    }



    public void loadPatikaCombobox(){
        cmb_student_patika.removeAllItems();
        for (Patika obj:Patika.getList()){
            cmb_student_patika.addItem(new Item(obj.getId(),obj.getName()));


        }
    }
    public void loadCoursesForSelectedPatika(int patikaId) {
        cmb_courses.removeAllItems();

        try {
            // Seçilen patikanın derslerini al
            ArrayList<Course> courses = Course.getCoursesByPatikaId(patikaId);

            // Dersleri ComboBox'a ekle
            for (Course course : courses) {
                cmb_courses.addItem(new Item(course.getId(), course.getName()));
            }
        } catch (Exception ex) {
            // Diğer istisnaları işlemek için buraya kod ekleyebilirsiniz
            // Genel bir hata mesajı gösterebilir veya loglayabilirsiniz
            Helper.showMassage("Çok fazla deneme yaptınız !");
            dispose();



        }
    }
    public void loadEducatorCombobox(){
        cmb_educator.removeAllItems();
        for (User obj:User.getList()){
            if (obj.getUserType().equals("educator")){
                cmb_educator.addItem(new Item(obj.getId(),obj.getName()));
            }

        }
    }

    public void loadCourseForSelectedEducator(int educator) {
        cmb_course_select.removeAllItems();

        try {
            // Seçilen patikanın derslerini al
            ArrayList<Course> courses = Course.getCoursesByEducator(educator);

            // Dersleri ComboBox'a ekle
            for (Course course : courses) {
                cmb_course_select.addItem(new Item(course.getId(), course.getName()));
            }
        } catch (Exception ex) {
            // Diğer istisnaları işlemek için buraya kod ekleyebilirsiniz
            // Genel bir hata mesajı gösterebilir veya loglayabilirsiniz
            Helper.showMassage("Çok fazla deneme yaptınız !");
            dispose();



        }
    }









    public static void main(String[] args) {
        Helper.setLayout();
        StudentGUI studentGUI=new StudentGUI(1);
    }
}
