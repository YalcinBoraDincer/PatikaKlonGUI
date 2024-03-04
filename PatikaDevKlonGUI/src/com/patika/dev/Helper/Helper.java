package com.patika.dev.Helper;

import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;

public class Helper {









     public static int screenCenterLocation(String axis, Dimension size){
        int point;
        switch (axis){
            case "x":
                point=(Toolkit.getDefaultToolkit().getScreenSize().width-size.width)/2;
                break;
            case "y":
                point=(Toolkit.getDefaultToolkit().getScreenSize().height-size.height)/2;
                break;
            default:
                point=0;
        }
        return point;
    }//Ekranin Ortasini Veren Yardimci Method

    public static void setLayout(){
        for (UIManager.LookAndFeelInfo info:UIManager.getInstalledLookAndFeels()){
            if ("Nimbus".equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }//NimbusLayout Temasini Belirler

        public static void setComboBoxBackground(JComboBox<?> comboBox) {
            // ComboBox'un arka plan rengini ayarlama
            comboBox.setBackground(new Color(244, 229, 192));
        }

    public static boolean isFieldEmpty(JTextField field){
         return field.getText().trim().isEmpty();
    }//Field kisimlarinin bos olup olmadigini sorgular

    public static void showMassage(String str){
         String msg;
         String title;
         optionPaneTR();
         switch (str){
             case "fill":
                 msg="Lütfen Tüm Alanları Doldurun ";
                 title="Hata";
                 break;
             case "done":
                 msg="İşlem Başarılı";
                 title="Sonuç";
                 break;
             case "error":
                 msg="Bir hata oluştu";
                 title="Hata";
                 break;
             default:
                 msg=str;
                 title="Mesaj";
         }
         JOptionPane.showMessageDialog(null,msg,title,JOptionPane.INFORMATION_MESSAGE);
    } //Uyari Mesaji Gosterme

    public static boolean confirm(String str){
         String msg;
         optionPaneTR();
         switch (str){
             case "sure":
                 msg="Bu işlemi gerçekleştirmek istediğinize emin misiniz ?";
                 break;
             default:
                 msg=str;
         }
         return JOptionPane.showConfirmDialog(null,msg,"Son kararın mı ? ",JOptionPane.YES_NO_OPTION)==0;


     }//Emin misin penceresi

    public static void optionPaneTR(){
         UIManager.put("OptionPane.okButtonText","Tamam");
         UIManager.put("OptionPane.yesButtonText","Evet");
        UIManager.put("OptionPane.noButtonText","Hayır");


    }//Cikan pencerelerdeki ingilice ifadeleri yerellestirme
}
