/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Libs;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hp
 */
public class DataSource {
    
    private String USERNAME = "root";
    private String PASSWORD = "tTyAnp8wX73CscU3";
    private final String LINK = "jdbc:mysql://localhost?useTimezone=true&serverTimezone=UTC";
    private final String CONNECTOR = "com.mysql.cj.jdbc.Driver";
    
    public enum Type {}
    
    private static DataSource instance;
    private DataSource() {
        try { Class.forName(CONNECTOR); } catch(ClassNotFoundException ex) {}
        
        try
        {
            Connection sql_con = DriverManager.getConnection(LINK, USERNAME, PASSWORD);
            
            // Setup Database
            if(!isDBExists("car_rental_management_system")) {
                Statement Query = sql_con.createStatement();
                Query.executeUpdate(
                        "CREATE DATABASE car_rental_management_system;"
                );
            }
            
            Statement UseDBQuery = sql_con.createStatement();
            UseDBQuery.executeUpdate(
                    "USE car_rental_management_system;"
            );
            
            // Setup Tables
            if(!isTableExists("Car")) {
                    Statement Query = sql_con.createStatement();
                    Query.executeUpdate(
                            "CREATE TABLE Car ()"
                    );
                }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static DataSource getInstance() {
        if(instance == null)
            instance = new DataSource();
        return instance;
    }
    
    public void addData(Type type, Object Data) {
        //
    }
    
    public Object readAllData(Type type) {
        //
        return null;
    }

    public void updateDate(Type type, int refId, Object Data) {
        //
    }
    
    public void removeData(Type type, int refId) {
        //
    }
    
    // Source: https://stackoverflow.com/questions/838978/how-to-check-if-mysql-database-exists
    private boolean isDBExists(String DatabaseName) {
        try {
            Connection sql_con = DriverManager.getConnection(LINK, USERNAME, PASSWORD);
        
            try (ResultSet resultSet = sql_con.getMetaData().getCatalogs()) {
                while (resultSet.next()) {
                    String databaseName = resultSet.getString(1);
                    if(databaseName.equals(DatabaseName)){
                        return true;
                    }
                }
            }
        }
        catch(SQLException ex) {
            Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    private boolean isTableExists(String TableName) {
        try {
            Connection sql_con = DriverManager.getConnection(LINK, USERNAME, PASSWORD);
            
            Statement Query = sql_con.createStatement();
            Query.executeUpdate(
                    "USE crud_app;"
            );
            
            DatabaseMetaData meta = sql_con.getMetaData();
            ResultSet resultSet = meta.getTables("crud_app", null, TableName, new String[] {"TABLE"});
            return resultSet.next();
        
        }
        catch(SQLException ex) {
            Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
