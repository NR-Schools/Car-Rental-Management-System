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
    
    private String USERNAME = "sql6509591";
    private String PASSWORD = "UD7kfEHXrR";
    private final String LINK = "jdbc:mysql://sql6.freemysqlhosting.net:3306?useTimezone=true&serverTimezone=UTC";
    private final String CONNECTOR = "com.mysql.cj.jdbc.Driver";
    
    public enum Type { Customer, Car }
    
    private static DataSource instance;
    private DataSource() {
        try { Class.forName(CONNECTOR); } catch(ClassNotFoundException ex) {}
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
                            customer.getName(),
                            customer.getContactNum(),
                            customer.getAddress());
                    break;
                case Car:
                    ICar car = (ICar) Data;
                    
                    QueryStr = String.format("INSERT INTO Car VALUES (null, \"%s\", \"%s\", \"%s\", \"%s\", %f, \"%s\", null, null, null);",
                            car.getModel(),
                            car.getBrand(),
                            car.getType(),
                            car.getPlateNo(),
                            car.getPrice(),
                            car.getStatus());
                    break;
            }
            
            Statement SetDB = sql_con.createStatement();
            SetDB.executeUpdate("USE sql6509591;");
            
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
            SetDB.executeUpdate("USE sql6509591;");
            
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
                        
                        ConcreteFactory cF = new ConcreteFactory();
                        ICar car = cF.doSomething(results.getString("car_type"));
                        car.setID(results.getInt("car_id"));
                        car.setModel(results.getString("car_model"));
                        car.setBrand(results.getString("car_brand"));
                        car.setPlateNo(results.getString("car_plateno"));
                        car.setPrice(results.getDouble("car_price"));
                        car.setStatus(results.getString("car_status"));
                        car.setRentDate(results.getDate("car_rentdate"));
                        car.setReturnDate(results.getDate("car_returndate"));
            
                        int rentcust_id = results.getInt("car_rentcust");           
                        
                        if(rentcust_id != 0) {
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
                        
                        objects.add(car);
                    }
                    break;
            }
            
            sql_con.close();
            
            return objects;
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void updateData(Type type, Object Data) {
        try
        {
            Connection sql_con = DriverManager.getConnection(LINK, USERNAME, PASSWORD);
            
            String QueryStr = "";
            
            switch(type)
            {
                case Customer:
                    Customer customer = (Customer) Data;
                    QueryStr = String.format("UPDATE Customer SET cust_name=\"%s\", cust_contact=\"%s\", cust_address=\"%s\", WHERE cust_id = %d",
                            customer.getName(), customer.getContactNum(), customer.getAddress(), customer.getID());
                    break;
                case Car:
                    ICar car = (ICar) Data;
                    QueryStr = String.format("UPDATE Car SET car_model=\"%s\", car_brand=\"%s\", car_type=\"%s\", car_plateno=\"%s\", car_price=%f, car_status=\"%s\", car_rentdate=\"%s\", car_returndate=\"%s\", car_rentcust=%d) WHERE car_id=%d",
                            car.getModel(), car.getBrand(), car.getType(), car.getPlateNo(), car.getPrice(), car.getStatus(), car.getRentDate(), car.getReturnDate(), car.getRentCust().getID(), car.getID());
                    break;
            }
            
            Statement SetDB = sql_con.createStatement();
            SetDB.executeUpdate("USE sql6509591;");
            
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
    
    public void removeData(Type type, int refId) {
        try
        {
            Connection sql_con = DriverManager.getConnection(LINK, USERNAME, PASSWORD);
            
            String QueryStr = "";
            
            switch(type)
            {
                case Customer:
                    QueryStr = String.format("DELETE FORM Customer WHERE cust_id=%d",
                            refId);
                    break;
                case Car:
                    QueryStr = String.format("DELETE FROM Car WHERE car_id=%d",
                            refId);
                    break;
            }
            
            Statement SetDB = sql_con.createStatement();
            SetDB.executeUpdate("USE sql6509591;");
            
            
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
}
