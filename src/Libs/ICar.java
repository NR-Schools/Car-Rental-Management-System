/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Libs;

import java.sql.Date;


/**
 *
 * @author hp
 */
public interface ICar {
    public String getType();
    
    public int getID();
    public String getModel();
    public String getBrand();
    public String getDesc();
    public String getPlateNo();
    public double getPrice();
    public String getStatus();
    public Date getRentDate();
    public Date getReturnDate();
    public Customer getRentCust();
    
    
    
    public void setID(int ID);
    public void setModel(String model);
    public void setBrand(String brand);
    public void setDesc(String desc);
    public void setPlateNo(String plateNo);
    public void setPrice(double price);
    public void setStatus(String status);
    public void setRentDate(Date rentDate);
    public void setReturnDate(Date returnDate);
    public void setRentCust(Customer rentCust);
}
