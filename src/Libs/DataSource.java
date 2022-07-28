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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author hp
 */
public class DataSource {
    
    private String USERNAME = "root";
    private String PASSWORD = "tTyAnp8wX73CscU3";
    private final String LINK = "jdbc:mysql://localhost?useTimezone=true&serverTimezone=UTC";
    private final String CONNECTOR = "com.mysql.cj.jdbc.Driver";
    
    public enum Type { Customer, Car }
    
    private static DataSource instance;
    private DataSource() {
    }
    
    public static DataSource getInstance() {
        if(instance == null)
            instance = new DataSource();
        return instance;
    }
    
    public void addData(Type type, Object Data) {
        try
        {
            Connection sql_con = DriverManager.getConnection(LINK, USERNAME, PASSWORD);
            
            String QueryStr = "";
            
            switch(type)
            {
                case Customer:
                    Customer customer = (Customer) Data;
                    QueryStr = String.format("INSERT INTO Customer VALUES(null, \"%s\", \"%s\", \"%s\");",
                            customer.getName(), customer.getContactNum(), customer.getAddress());
                    break;
                case Car:
                    ICar car = (ICar) Data;
                    QueryStr = String.format("INSERT INTO Car VALUES(null, \"%s\", \"%s\", \"9.8\", \"%d\", \"%s\", null);",
                            car.getModel(), car.getBrand(), car.getPrice(), car.getStatus(), car.getReturnDate(), car.getRentCust().getID());
                    break;
            }
            
            Statement SetDB = sql_con.createStatement();
            SetDB.executeUpdate("USE sa2;");
            
            Statement Query = sql_con.createStatement();
            Query.executeUpdate(QueryStr);
            
            sql_con.close();
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Object> readAllData(Type type) {
        try
        {
            Statement Query;
            ResultSet results;
            ArrayList<Object> objects = new ArrayList<>();
            Connection sql_con = DriverManager.getConnection(LINK, USERNAME, PASSWORD);
            
            String QueryStr = "";
            
            Statement SetDB = sql_con.createStatement();
            SetDB.executeUpdate("USE sa2;");
            
            switch(type)
            {
                case Customer:
                    Query = sql_con.createStatement();
                    results = Query.executeQuery("SELECT * FROM Customer");
                    while(results.next()) {
                        Customer cust = new Customer();
                        cust.setID(results.getInt("cust_id"));
                        cust.setName(results.getString("cust_name"));
                        cust.setContactNum(results.getString("cust_contact"));
                        cust.setAddress(results.getString("cust_address"));
                        
                        objects.add(cust);
                    }
                    break;
                case Car:
                    Query = sql_con.createStatement();
                    results = Query.executeQuery("SELECT * FROM Car");
                    while(results.next()) {
                        
                        // Switch Depending on Brand
                        ConcreteFactory cF = new ConcreteFactory();
                        ICar car = cF.createProduct(results.getString("car_brand"));
                        car.setModel(results.getString("car_model"));
                        car.setID(results.getInt("car_id"));
                        car.setDesc(results.getString("car_desc"));
                        car.setPrice(results.getDouble("car_price"));
                        car.setStatus(results.getBoolean("car_status"));
                        car.setReturnDate(results.getDate("car_returndate"));
                        
                        Integer rentcust_id = results.getInt("car_rentcust");
                        
                        if(true) continue;
                        
                        ResultSet sub_result = Query.executeQuery(
                            String.format("SELECT * FROM Customer WHERE cust_id = \"%d\"",
                                    rentcust_id)
                        );
                        while(sub_result.next()) {
                            Customer cust = new Customer();
                            cust.setID(results.getInt("cust_id"));
                            cust.setName(results.getString("cust_name"));
                            cust.setContactNum(results.getString("cust_contact"));
                            cust.setAddress(results.getString("cust_address"));
                            
                            car.setRentCust(cust);
                        }
                    }
                    break;
            }
            
            sql_con.close();
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void updateDate(Type type, int refId, Object Data) {
        //
    }
    
    public void removeData(Type type, int refId) {
        //
    }
}
