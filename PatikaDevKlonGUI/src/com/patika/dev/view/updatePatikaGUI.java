package com.patika.dev.view;

import com.patika.dev.Helper.Config;
import com.patika.dev.Helper.Helper;
import com.patika.dev.model.Patika;

import javax.swing.*;

public class updatePatikaGUI extends  JFrame{
    private JPanel wrapper;
    private JTextField field_patika_name;
    private JButton btn_update;
    private Patika patika;

    public updatePatikaGUI(Patika patika) {
        this.patika=patika;
        add(wrapper);
        setSize(300,150);
        int x=Helper.screenCenterLocation("x",getSize());
        int y=Helper.screenCenterLocation("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        field_patika_name.setText(patika.getName());


        btn_update.addActionListener(e -> {
            if (Helper.isFieldEmpty(field_patika_name)){
                Helper.showMassage("fill");
            }else {
                if (Patika.update(patika.getId(),field_patika_name.getText())){
                    Helper.showMassage("done");
                }
                dispose();
            }



        });
    }


}
