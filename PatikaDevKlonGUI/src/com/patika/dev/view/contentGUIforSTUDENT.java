package com.patika.dev.view;

import com.patika.dev.Helper.Config;
import com.patika.dev.Helper.Helper;
import com.patika.dev.model.Content;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class contentGUIforSTUDENT extends JFrame {

    private JPanel wrapper;
    private JTable tbl_content;
    private JTextField field_educator_title;
    private JTextField field_educator_description;
    private JTextField field_educator_yt;
    private JButton btn_educator_content;
    private JButton btn_content_exit;
    private JScrollPane pnl_edu_top;

    //Modeller
    private DefaultTableModel mdl_content;
    private Object[] row_content_list;
    private int courseID;

    //JpopMenu Guncelleme Silme Icin
    //private JPopupMenu contentMenu;

    public contentGUIforSTUDENT(int courseId){
        this.courseID=courseId;
        Helper.setLayout();
        setResizable(false);
        add(wrapper);
        tbl_content.setEnabled(false);
        setSize(1500,500);
        int x= Helper.screenCenterLocation("x",getSize());
        int y=Helper.screenCenterLocation("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);



        //##############  PopUp guncelleme silme islemleri  ##############//
        //contentMenu=new JPopupMenu();
        //JMenuItem deleteMenu=new JMenuItem("Sil");
        //contentMenu.add(deleteMenu);

        tbl_content.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point=e.getPoint();
                int selectedROW=tbl_content.rowAtPoint(point);
                tbl_content.setRowSelectionInterval(selectedROW,selectedROW);
            }
        });//Sag tikladiginda mavi olma secme islemleri

        /*deleteMenu.addActionListener(e -> {
            if (Helper.confirm("sure")){
                int selected_row_id=Integer.parseInt(tbl_content.getValueAt(tbl_content.getSelectedRow(),0).toString());
                if (Content.delete(selected_row_id)){
                    Helper.showMassage("done");
                    loadContent(courseID);
                }else {
                    Helper.showMassage("error");
                }

            }


        });*/





        //Tablo Islemleri

        mdl_content=new DefaultTableModel();
        Object[] col_content_list ={"ID","Ders Adı","İçerik Adı","İçerik Açıklaması","Youtube Linki"};
        mdl_content.setColumnIdentifiers(col_content_list);
        row_content_list=new Object[col_content_list.length];
        loadContent(courseID);



        tbl_content.setModel(mdl_content);
        //tbl_content.setComponentPopupMenu(contentMenu);//Pop up menu eklemesi
        tbl_content.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_content.getTableHeader().setReorderingAllowed(false);

        tbl_content.getModel().addTableModelListener(e -> {
            int column = e.getColumn();
            if (e.getType() == TableModelEvent.UPDATE) {
                try {
                    int content_id = Integer.parseInt(tbl_content.getValueAt(tbl_content.getSelectedRow(), 0).toString());
                    String course_name = tbl_content.getValueAt(tbl_content.getSelectedRow(), 2).toString();
                    String contentDesc = tbl_content.getValueAt(tbl_content.getSelectedRow(), 3).toString();
                    String videoLink = tbl_content.getValueAt(tbl_content.getSelectedRow(), 4).toString();

                    if (column == 0) {
                        Helper.showMassage("ID değiştirilemez");
                        loadContent(courseID);
                    } else {
                        if (Content.update(content_id, course_name, contentDesc, videoLink)) {
                            Helper.showMassage("done");
                            loadContent(courseID);
                        }
                    }
                } catch (NumberFormatException ex) {
                    Helper.showMassage("ID sadece sayı içermelidir ve degistirelemez.");
                    loadContent(courseID);
                    // ID'yi doğru bir şekilde alamadık, işlemi iptal ediyoruz.
                }
            }
        });






        btn_content_exit.addActionListener(e -> {
            dispose();
            //loginGUI loginGUI=new loginGUI();

        });
    }

    public void loadContent(int course_id) {
        // Önce tabloyu temizle
        DefaultTableModel tableModel = (DefaultTableModel) tbl_content.getModel();
        tableModel.setRowCount(0);

        // Belirli eğitmenin derslerini al
        ArrayList<Content> contentList = Content.getContent(course_id);

        // Her bir ders için tabloya bir satır ekle
        int i=0;
        for (Content content : contentList) {
            i=0;
            row_content_list[i++]=content.getId();
            row_content_list[i++]=content.getRelatedCourse().getName();
            row_content_list[i++]=content.getContentName();
            row_content_list[i++]=content.getContentDesc();
            row_content_list[i++]=content.getVideoLink();
            mdl_content.addRow(row_content_list);
        }
    }

    public static void main(String[] args) {
        Helper.setLayout();
        contentGUIforSTUDENT contentGUIforSTUDENT=new contentGUIforSTUDENT(44);

    }




}

