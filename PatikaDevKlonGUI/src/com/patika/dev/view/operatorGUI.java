package com.patika.dev.view;
import com.patika.dev.Helper.Config;
import com.patika.dev.Helper.Helper;
import com.patika.dev.Helper.Item;
import com.patika.dev.model.Course;
import com.patika.dev.model.Operator;
import com.patika.dev.model.Patika;
import com.patika.dev.model.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

public class operatorGUI extends JFrame {
    Operator operator=new Operator(); //Bu Operator Ekranini Cagirmak Icin Kurucu Metoda Operator Nesnesi Verme Zorunlulugu getiriyoruz
    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_operator_exit;
    private JPanel pnl_user_lst;
    private JScrollPane scrl_user_lst;
    private JTable tbl_user_lst;
    private JPanel pnl_user_form;
    private JTextField field_user_name;
    private JLabel lbl_username;
    private JTextField field_user_username;
    private JTextField field_user_password;
    private JComboBox cmb_user_type;
    private JButton btn_user_add;
    private JTextField field_user_id;
    private JButton btn_user_delete;
    private JTextField field_sh_username;
    private JTextField field_sh_user_uname;
    private JComboBox cmb_sh_user_utype;
    private JButton btn_user_sh;
    private JPanel pnl_patika_list;
    private JScrollPane scrl_patika_list;
    private JTable tbl_patika_list;
    private JPanel pnl_patika_add;
    private JTextField field_patika_name;
    private JButton btn_patika_add;
    private JPanel pnl_course_list;
    private JScrollPane scrl_course_list;
    private JTable tbl_course_list;
    private JPanel pnl_course_add;
    private JTextField field_course_name;
    private JTextField field_course_lang;
    private JComboBox cmb_course_patika;
    private JComboBox cmb_course_user;
    private JButton btn_course_add;
    private JTextField field_course_operations;
    private JButton btn_course_delete;
    private JButton btn_content;
    private JButton btn_quiz;
    private JButton btn_course_update;

    //Tablolar Modellerle Calisir Burada Tablo Modelleri Icin Degiskenler Olusturuyoruz
    private DefaultTableModel mdl_user_lst;
    private Object[] row_user_list;

    //Patika icin modeller
    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;

    //Patika kisminda sag tiklandiginda silmek ve guncellemek icin kullanilan popup menu
    private JPopupMenu patikaManu;
    //Course Icin Modeller
    private DefaultTableModel mdl_course_list;
    private  Object[] row_course_list;





    //OperatorGuiConstructor
    public operatorGUI(Operator operator)  {
        this.operator=operator;


        //Gorsel Islemler

        add(wrapper);
        setSize(1100,600);
        int x=Helper.screenCenterLocation("x",getSize());
        int y=Helper.screenCenterLocation("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);


        // ##### Tablo Model Islemleri ######### //




        //UserList
        mdl_user_lst=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column==0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };//ID rowunun degistirilebilir olmasini engelledik
        Object[] col_user_list={"ID","Ad Soyad","Kullanıcı Adı","Şifre","Üyelik Tipi"};//Tablonun Satirindaki Basliklar
        mdl_user_lst.setColumnIdentifiers(col_user_list);//Tablo Modeline Attik
        row_user_list=new Object[col_user_list.length];
        loadUserModel();//Bu Metod siraya gore ekleme islemini yapiyor


        tbl_user_lst.setModel(mdl_user_lst);//Ustte bir model olusturuyoruz sonra oldusturdugumuz modeli bos olana atiyoruz.
        tbl_user_lst.getTableHeader().setReorderingAllowed(false);//Tablo basliklarinin kaydirilmasini engelledik.

        tbl_user_lst.getModel().addTableModelListener(e -> {
            if (e.getType()==TableModelEvent.UPDATE){
                int user_id=Integer.parseInt(tbl_user_lst.getValueAt(tbl_user_lst.getSelectedRow(),0).toString());
                String user_name=tbl_user_lst.getValueAt(tbl_user_lst.getSelectedRow(),1).toString();
                String user_username=tbl_user_lst.getValueAt(tbl_user_lst.getSelectedRow(),2).toString();
                String user_password=tbl_user_lst.getValueAt(tbl_user_lst.getSelectedRow(),3).toString();
                String user_type=tbl_user_lst.getValueAt(tbl_user_lst.getSelectedRow(),4).toString();
                if (User.update(user_id,user_name,user_username,user_password,user_type)){
                    Helper.showMassage("done");
                }loadUserModel();
                loadCourseModel();
                loadEducatorCombobox();

            }

        });//Tabloda tiklanan satira gore guncelleme islemi yapar


        //User List Tablo Islemleri Sonu

        //######################################################################################################################

        //Patika Islemleri

        //##############  PopUp guncelleme silme islemleri  ##############//
        patikaManu=new JPopupMenu();
        JMenuItem updateMenu=new JMenuItem("Güncelle");
        JMenuItem deleteMenu=new JMenuItem("Sil");
        patikaManu.add(updateMenu);
        patikaManu.add(deleteMenu);


        updateMenu.addActionListener(e -> {
            int selected_row_id=Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(),0).toString());
            updatePatikaGUI updatePatikaGUI=new updatePatikaGUI(Patika.getFetch(selected_row_id));
            updatePatikaGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPatikaModel();
                    loadPatikaCombobox();
                    loadCourseModel();
                }
            });//Acilan guncelleme penceresi kapandiginda tabloyu gunceller



        });//Acilan popup pnecerede guncelleme islemini dinliyoruz






        deleteMenu.addActionListener(e -> {
            if (Helper.confirm("sure")){
                int selected_row_id=Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(),0).toString());
                if (Patika.delete(selected_row_id)){
                    Helper.showMassage("done");
                    loadPatikaModel();
                    loadPatikaCombobox();
                    loadCourseModel();
                }else {
                    Helper.showMassage("error");
                }

            }


        });//Patikalar kisminda Acilan Pop Up pencerede silme islemini yapar

        //##############  PopUp guncelleme silme islemleri Sonu  ##############//

        //##############  Model Islemleri   ##############//

        mdl_patika_list=new DefaultTableModel();
        Object[] col_patika_list={"ID","Patika Adı"};
        mdl_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list=new Object[col_patika_list.length];
        loadPatikaModel();

        tbl_patika_list.setModel(mdl_patika_list);
        tbl_patika_list.setComponentPopupMenu(patikaManu);//Silme guncelleme pop up manunun gorunurlugu icin attik.
        tbl_patika_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point=e.getPoint();//Mause ile nereye tiklandiginin koordinatlarini aliyoruz.
                int selected_row=tbl_patika_list.rowAtPoint(point);//Bize tiklanan pointin hangi rowda oldugunu soyluyor
                tbl_patika_list.setRowSelectionInterval(selected_row,selected_row);//Tiklanan rowu seciyor ve mavi yapiyor.
            }
        });
        tbl_patika_list.getTableHeader().setReorderingAllowed(false);
        tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(75);//Gorunus olarak tabloda id isim arasindaki boslugu azalttik

        //  Patika Model Islemleri Sonu
        //######################################################################################################################

        //Course Model Islermleri
        mdl_course_list=new DefaultTableModel();
        Object[] col_course_list ={"ID","Ders Adı","Programlama Dili","Patika","Eğitmen"};
        mdl_course_list.setColumnIdentifiers(col_course_list);
        row_course_list=new Object[col_course_list.length];
        loadCourseModel();

        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);

        loadPatikaCombobox();
        loadEducatorCombobox();
































        //Course Model Islermleri Sonu
        //######################################################################################################################














        // ##### Tablo Model Islemleri SONU ######### //







        //Listener Islemleri

        //User
        btn_user_add.addActionListener(e -> {
                if (Helper.isFieldEmpty(field_user_name) || Helper.isFieldEmpty(field_user_username)||Helper.isFieldEmpty(field_user_password)){
                    Helper.showMassage("fill");
                }else {
                    String name=field_user_name.getText();
                    String username=field_user_username.getText();
                    String pass=field_user_password.getText();
                    String type= Objects.requireNonNull(cmb_user_type.getSelectedItem()).toString();
                    if (User.add(name,username,pass,type)){
                        Helper.showMassage("done");
                        loadUserModel();
                        loadEducatorCombobox();
                        loadPatikaCombobox();
                        field_user_name.setText(null);
                        field_user_username.setText(null);
                        field_user_password.setText(null);

                    }else {

                    }

                }


        });//Operator ekrani kullanici ekleme butonu

        btn_user_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(field_user_id)){
                Helper.showMassage("fill");
            }else {
                if (Helper.confirm("sure")){
                    try {
                        int user_id= Integer.parseInt(field_user_id.getText());
                        if (User.delete(user_id)){
                            Helper.showMassage("done");
                            field_user_id.setText(null);
                            loadUserModel();
                            loadEducatorCombobox();
                            loadCourseModel();
                            field_user_id.setText(null);
                        }else {
                            Helper.showMassage("error");
                        }
                    }catch (Exception exeption){
                        Helper.showMassage("Gecersiz Deger Girdiniz !");
                        System.out.println(exeption.getMessage());
                    }
                }
            }

        });//User silme butonu islemleri

        btn_user_sh.addActionListener(e -> {
            String name= field_sh_username.getText();
            String userName=field_sh_user_uname.getText();
            String user_Type=cmb_sh_user_utype.getSelectedItem().toString();
            String query=User.searchQuery(name,userName,user_Type);
            ArrayList<User> searching_User=User.searchUserList(query);
            loadUserModel(searching_User);
            loadEducatorCombobox();




        });//Name,Username ve usertype gore arama islemleri

        btn_operator_exit.addActionListener(e -> {
            dispose();
            loginGUI loginGUI=new loginGUI();


        });//Cikis yapma tusu


        //Patika
        btn_patika_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(field_patika_name)){
                Helper.showMassage("fill");
            }else {
                if (Patika.add(field_patika_name.getText())){
                    Helper.showMassage("done");
                    loadPatikaModel();
                    loadPatikaCombobox();
                    loadEducatorCombobox();
                    field_patika_name.setText(null);
                }else {
                    Helper.showMassage("error");
                }

            }

        }); //Patika ekleme butonu


        //Course
        btn_course_add.addActionListener(e -> {
            Item patikaItem= (Item) cmb_course_patika.getSelectedItem();
            Item userItem= (Item) cmb_course_user.getSelectedItem();
            if (Helper.isFieldEmpty(field_course_name) || Helper.isFieldEmpty(field_course_lang)){
                Helper.showMassage("fill");
            }else {
                if (Course.add(userItem.getKey(),patikaItem.getKey(),field_course_name.getText(),field_course_lang.getText())){
                    Helper.showMassage("done");
                    loadCourseModel();
                    field_course_lang.setText(null);
                    field_course_name.setText(null);
                }else {
                    Helper.showMassage("error");
                }

            }

        });

        btn_course_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(field_course_operations)){
                Helper.showMassage("fill");
            }else {
                if (Helper.confirm("sure")){
                    try {
                        int course_id= Integer.parseInt(field_course_operations.getText());
                        if (Course.delete(course_id)){
                            Helper.showMassage("done");
                            field_course_operations.setText(null);
                            loadUserModel();
                            loadEducatorCombobox();
                            loadCourseModel();
                        }else {
                            Helper.showMassage("error");
                        }
                    }catch (Exception exeption){
                        Helper.showMassage("Gecersiz Deger Girdiniz !");
                        System.out.println(exeption.getMessage());
                    }
                }
            }


        });
        btn_content.addActionListener(e -> {
           try {
               int id= Integer.parseInt(field_course_operations.getText());
               contentGUI contentGUI=new contentGUI(id);
           }catch (Exception exception){
               Helper.showMassage("ID girin");

           }

        });
        btn_quiz.addActionListener(e -> {

            try {
                int id= Integer.parseInt(field_course_operations.getText());
                quizGUI quizGUI=new quizGUI(id);

            }catch (Exception exception){
                Helper.showMassage("ID girin");
            }
        });

        btn_course_update.addActionListener(e -> {

        });





        //Listener Islemleri Sonu






        //Label Islemleri
        lbl_welcome.setText("Hoşgeldiniz "+operator.getName());



    }//EndOfOperatorGuiConstructor


    //Load Metoadlari
    public void loadUserModel(){
        DefaultTableModel clearModel= (DefaultTableModel) tbl_user_lst.getModel();
        clearModel.setRowCount(0);
        int i;

        for (User obj:User.getList()){ //getList metodu userlari databaseden cekip arrayliste atiyordu ve onu donduruyor.
             i=0;
            row_user_list[i++]=obj.getId();
            row_user_list[i++]=obj.getName();
            row_user_list[i++]=obj.getUsername();
            row_user_list[i++]=obj.getPassword();
            row_user_list[i++]=obj.getUserType();
            mdl_user_lst.addRow(row_user_list);//Bu Metod siraya gore ekleme islemini yapiyor
        }
    }

    public void loadUserModel(ArrayList<User> list){
        DefaultTableModel clearModel= (DefaultTableModel) tbl_user_lst.getModel();
        clearModel.setRowCount(0);
        int i;

        for (User obj:list){ //getList metodu userlari databaseden cekip arrayliste atiyordu ve onu donduruyor.
             i=0;
            row_user_list[i++]=obj.getId();
            row_user_list[i++]=obj.getName();
            row_user_list[i++]=obj.getUsername();
            row_user_list[i++]=obj.getPassword();
            row_user_list[i++]=obj.getUserType();
            mdl_user_lst.addRow(row_user_list);//Bu Metod siraya gore ekleme islemini yapiyor
        }
    }//Ustteki ile ayni ama arraylist aliyor(Overloading)
    public void loadPatikaModel(){
        DefaultTableModel clearmodel= (DefaultTableModel) tbl_patika_list.getModel();
        clearmodel.setRowCount(0);
        int i;

        for (Patika obj: Patika.getList()){
             i=0;
            row_patika_list[i++]=obj.getId();
            row_patika_list[i++]=obj.getName();
            mdl_patika_list.addRow(row_patika_list);

        }

    }

    public void loadCourseModel(){
        DefaultTableModel cleartable= (DefaultTableModel) tbl_course_list.getModel();
        cleartable.setRowCount(0);

        int i=0;
        for (Course obj: Course.getList()){
            i=0;
            row_course_list[i++]=obj.getId();
            row_course_list[i++]=obj.getName();
            row_course_list[i++]=obj.getLang();
            row_course_list[i++]=obj.getPatika().getName();
            row_course_list[i++]=obj.getEducator().getName();
            mdl_course_list.addRow(row_course_list);

        }

    }

    public void loadPatikaCombobox(){
        cmb_course_patika.removeAllItems();
        for (Patika obj:Patika.getList()){
            cmb_course_patika.addItem(new Item(obj.getId(),obj.getName()));
        }
    } //Course kisminda patikalar comoboxuna patika verilerini ceker

    public void loadEducatorCombobox(){
        cmb_course_user.removeAllItems();
        for (User obj:User.getList()){
            if (obj.getUserType().equals("educator")){
                cmb_course_user.addItem(new Item(obj.getId(),obj.getName()));
            }

        }
    }





    public static void main(String[] args) {
        Helper.setLayout();
        Operator op=new Operator();
        op.setId(1);
        op.setName("YalcinBora");
        op.setPassword("1234");
        op.setUsername("yabodi");
        op.setUserType("operator");

        operatorGUI opgui=new operatorGUI(op);



    }


}
