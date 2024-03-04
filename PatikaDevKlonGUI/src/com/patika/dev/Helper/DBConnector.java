package com.patika.dev.Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private Connection connect=null;


    //Veri Tabani Baglantisi
    public Connection connectDB(){
        try {
            this.connect= DriverManager.getConnection(Config.DB_URL,Config.DB_USERNAME,Config.DB_PASSWORD);
        } catch (SQLException e) {
            Helper.showMassage("Sql Serverine bağlanalıamadı ! ");
            System.out.println(e.getMessage());
        }
        return this.connect;
    }

    //Her seferinde nesne uretmemek icin Burada uretiyoruz
    public static Connection getInstance(){
        DBConnector db=new DBConnector();
        return db.connectDB();
    }
}
