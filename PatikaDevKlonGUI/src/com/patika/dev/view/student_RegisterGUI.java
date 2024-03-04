package com.patika.dev.view;

import com.patika.dev.Helper.Config;
import com.patika.dev.Helper.Helper;
import com.patika.dev.model.User;

import javax.swing.*;
import java.awt.*;

public class student_RegisterGUI extends JFrame{
    private JPanel wrapper;
    private JTextField field_uname;
    private JTextField field_name;
    private JTextField field_surname;
    private JTextField field_pass;
    private JTextField field_pass2;
    private JButton btn_register;
    private JLabel lbl_kayit;
    private JLabel lbl_uname;
    private JLabel lbl_name;
    private JLabel lbl_pass;
    private JLabel lbl_information;
    private JLabel lbl_surname;
    private JLabel lbl_pass2;
    private JPanel pnl_1;
    private JPanel pnl_2;
    private JPanel pnl_3;
    private JComboBox comboBox1;
    private JTextField textField6;

    public student_RegisterGUI(){
        Helper.setLayout();
        add(wrapper);
        wrapper.setBackground(new Color(255, 251, 240)); // Kirik beyaz tonu
        pnl_1.setBackground(new Color(255, 251, 240)); // Kirik beyaz tonu
        pnl_2.setBackground(new Color(255, 251, 240)); // Kirik beyaz tonu
        pnl_3.setBackground(new Color(255, 251, 240)); // Kirik beyaz tonu
        field_uname.setBackground(new Color(255, 251, 240));
        field_name.setBackground(new Color(255, 251, 240));
        field_surname.setBackground(new Color(255, 251, 240));
        field_pass.setBackground(new Color(255, 251, 240));
        field_pass2.setBackground(new Color(255, 251, 240));
        comboBox1.setBackground(new Color(255, 251, 240));
        btn_register.setBackground(new Color(255, 251, 240));


        setSize(500,500);
        setResizable(false);
        int x= Helper.screenCenterLocation("x",getSize());
        int y=Helper.screenCenterLocation("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        Font font=new Font("Unispace",Font.BOLD,26);
        Font font1=new Font("Unispace",Font.PLAIN,12);
        lbl_kayit.setFont(font);
        lbl_uname.setFont(font1);
        lbl_name.setFont(font1);
        lbl_pass.setFont(font1);
        lbl_pass2.setFont(font1);
        lbl_information.setFont(font1);
        lbl_surname.setFont(font1);
        btn_register.setFont(font1);
        comboBox1.setFont(font1);


        btn_register.addActionListener(e -> {
            if (Helper.isFieldEmpty(field_name) ||Helper.isFieldEmpty(field_uname) || Helper.isFieldEmpty(field_pass)
                    ||Helper.isFieldEmpty(field_pass2) || Helper.isFieldEmpty(field_surname)){
                Helper.showMassage("fill");
            }else {
                if (field_pass.getText().equals(field_pass2.getText())){
                    String uname=field_uname.getText();
                    String firstname=field_name.getText();
                    String surname=field_surname.getText();
                    String pass=field_pass.getText();
                    String name=firstname+" "+surname;
                    User.addStudent(name,uname,pass,"student");
                    dispose();
                    loginGUI loginGUI=new loginGUI();


                }else {
                    Helper.showMassage("Şifreler Uyuşmuyor");
                }


            }

        });
    }

    public static void main(String[] args) {


        student_RegisterGUI studentRegisterGUI=new student_RegisterGUI();
    }


}
