/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gtl.mmf.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 07662
 */
    
public class DbConnection {
    private static final String BO_DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String BO_DB_URL = "jdbc:mysql://localhost:3306/mmfdb";
    private static final String BO_DB_USER = "mmfuser";
    private static final String BO_DB_PASSWORD = "mmfuser";
    private static DbConnection dBConnection = null;
    private Connection connection = null;
   
    private DbConnection(){}
   
    public static DbConnection getInstance() {
        if(dBConnection == null){
            dBConnection = new DbConnection();       
        }
        return dBConnection;
    }
   
    public Connection getConnection() {
        try {
            Class.forName(BO_DB_DRIVER);
            connection = DriverManager.getConnection(BO_DB_URL
                +"?user="+BO_DB_USER
                +"&password="+BO_DB_PASSWORD);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

}
