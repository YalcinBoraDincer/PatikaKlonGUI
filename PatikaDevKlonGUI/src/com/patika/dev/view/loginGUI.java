package com.patika.dev.view;

import com.patika.dev.Helper.Config;
import com.patika.dev.Helper.Helper;
import com.patika.dev.model.Operator;
import com.patika.dev.model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class loginGUI extends   JFrame {
    private JPanel wrapper;
    private JPanel wrappertop;
    private JPanel wrapperbottom;
    private JTextField field_user_uname;
    private JPasswordField field_password;
    private JButton btn_login;
    private JButton btn_register;


    public loginGUI(){

        add(wrapper);
        setSize(500,500);
        int x= Helper.screenCenterLocation("x",getSize());
        int y=Helper.screenCenterLocation("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);


        btn_login.addActionListener(e -> {
            if (Helper.isFieldEmpty(field_user_uname) || Helper.isFieldEmpty(field_password)){
                Helper.showMassage("fill");
            }else {
                User u=User.getFetch(field_user_uname.getText(), field_password.getText());
                if (u==null){
                    Helper.showMassage("Kullanici Bulunamadi");
                }else {
                    switch (u.getUserType()){
                        case "operator":
                            operatorGUI opGUI=new operatorGUI((Operator)u);
                            break;
                        case "educator":
                            System.out.println("Giris Yapan Ogrtmen ID "+u.getId());
                            EducatorGUI educatorGUI=new EducatorGUI(u.getId());
                            break;
                        case "student":
                            System.out.println("Giris Yapan Ogrenci ID "+u.getId());
                            StudentGUI studentGUI=new StudentGUI(u.getId());
                            break;
                    }
                    dispose();
                }

            }

        });

        btn_register.addActionListener(e -> {
            dispose();
            student_RegisterGUI studentRegisterGUI=new student_RegisterGUI();

        });
    }


    public static void main(String[] args) {
        Helper.setLayout();

        loginGUI gui=new loginGUI();
    }
}
