package com.patika.dev.view;

import com.patika.dev.Helper.Config;
import com.patika.dev.Helper.Helper;
import com.patika.dev.model.Content;
import com.patika.dev.model.Quiz;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;

public class quizGUI extends JFrame {

    private JPanel wrapper;
    private JScrollPane pnl_edu_top;
    private JTextField field_question;
    private JTextField field_option_a;
    private JTextField field_option_b;
    private JButton btn_question_add;
    private JButton btn_content_exıt;
    private JTable tbl_quiz;
    private JTextField field_option_c;
    private JTextField field_option_d;
    private JComboBox cmb_answer;

    private DefaultTableModel mdl_quiz;
    Object [] row_quiz_list;
    private int courseID;
    private JPopupMenu quizMenu;

    public quizGUI(int courseID){
        this.courseID=courseID;
        Helper.setLayout();
        add(wrapper);
        setSize(1000,500);
        int x= Helper.screenCenterLocation("x",getSize());
        int y=Helper.screenCenterLocation("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);


        //##############  PopUp guncelleme silme islemleri  ##############//
        quizMenu=new JPopupMenu();
        JMenuItem deleteMenu=new JMenuItem("Sil");
        quizMenu.add(deleteMenu);

        tbl_quiz.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point=e.getPoint();
                int selectedROW=tbl_quiz.rowAtPoint(point);
                tbl_quiz.setRowSelectionInterval(selectedROW,selectedROW);
            }
        });//Sag tikladiginda mavi olma secme islemleri

        deleteMenu.addActionListener(e -> {
            if (Helper.confirm("sure")){
                int selected_row_id=Integer.parseInt(tbl_quiz.getValueAt(tbl_quiz.getSelectedRow(),1).toString());
                if (Quiz.delete(selected_row_id)){
                    Helper.showMassage("done");
                    loadQuizzes(courseID);
                }else {
                    Helper.showMassage("error");
                }

            }


        });




        mdl_quiz=new DefaultTableModel();
        Object[] col_quiz_list ={"Kurs Adı","Soru Numarasi","Soru","A Şıkkı","B Şıkkı","C Şıkkı","D Şıkkı","Cevap"};
        mdl_quiz.setColumnIdentifiers(col_quiz_list);
        row_quiz_list=new Object[col_quiz_list.length];
        //loadCoursesForEducator(educatorID);

        tbl_quiz.setModel(mdl_quiz);
        loadQuizzes(courseID);

        tbl_quiz.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_quiz.setComponentPopupMenu(quizMenu);
        tbl_quiz.getTableHeader().setReorderingAllowed(false);



        tbl_quiz.getModel().addTableModelListener(e -> {
            if (e.getType()== TableModelEvent.UPDATE){
                String question=tbl_quiz.getValueAt(tbl_quiz.getSelectedRow(),2).toString();
                String option_a=tbl_quiz.getValueAt(tbl_quiz.getSelectedRow(),3).toString();
                String option_b=tbl_quiz.getValueAt(tbl_quiz.getSelectedRow(),4).toString();
                String option_c=tbl_quiz.getValueAt(tbl_quiz.getSelectedRow(),5).toString();
                String option_d=tbl_quiz.getValueAt(tbl_quiz.getSelectedRow(),6).toString();
                String answer=tbl_quiz.getValueAt(tbl_quiz.getSelectedRow(),7).toString().toUpperCase();
                int question_id= Integer.parseInt(tbl_quiz.getValueAt(tbl_quiz.getSelectedRow(),1).toString());



                if (answer.equals("A")|| answer.equals("B") || answer.equals("C")||answer.equals("D")){
                    if (Quiz.update(question_id,question,option_a,option_b,option_c,option_d,answer)){
                        Helper.showMassage("done");
                        loadQuizzes(courseID);
                    }
                    }else {
                        Helper.showMassage("Lütfen geçerli şıklardan birini deneyin !  -->   (A,B,C,D) ");
                        loadQuizzes(courseID);
                    }



            }

        });







        btn_question_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(field_question) || Helper.isFieldEmpty(field_option_a) ||Helper.isFieldEmpty(field_option_b)
            ||Helper.isFieldEmpty(field_option_c) ||Helper.isFieldEmpty(field_option_d)){
                Helper.showMassage("fill");
            }else {
                int crsID=courseID;
                String questionText=field_question.getText();
                String optionAText=field_option_a.getText();
                String optionBText= field_option_b.getText();
                String optionCText= field_option_c.getText();
                String optionDText= field_option_d.getText();
                String answer= Objects.requireNonNull(cmb_answer.getSelectedItem()).toString();

                if (Quiz.add(crsID,questionText,optionAText,optionBText,optionCText,optionDText,answer)){
                    Helper.showMassage("done");
                    loadQuizzes(courseID);

                    field_question.setText(null);
                    field_option_a.setText(null);
                    field_option_b.setText(null);
                    field_option_c.setText(null);
                    field_option_d.setText(null);


                }


            }


        });
        btn_content_exıt.addActionListener(e -> {
            dispose();
            //loginGUI loginGUI=new loginGUI();
        });
    }
    public void loadQuizzes(int course_id) {
        // Önce tabloyu temizle
        DefaultTableModel tableModel = (DefaultTableModel) tbl_quiz.getModel();
        tableModel.setRowCount(0);

        // Belirli eğitmenin derslerini al
        ArrayList<Quiz> quizzes = Quiz.getQuiz(course_id);

        // Her bir ders için tabloya bir satır ekle
        for (Quiz quiz : quizzes) {
            Object[] row_quiz_list = new Object[8]; // Dizi boyutunu artırdık
            row_quiz_list[0] = quiz.getRelatedCourse().getName();
            row_quiz_list[1] = quiz.getQuestion_id();
            row_quiz_list[2] = quiz.getQuestion();
            row_quiz_list[3] = quiz.getOption_a();
            row_quiz_list[4] = quiz.getOption_b();
            row_quiz_list[5] = quiz.getOption_c();
            row_quiz_list[6] = quiz.getOption_d();
            row_quiz_list[7] = quiz.getAnswer();
            tableModel.addRow(row_quiz_list);
        }
    }



}
