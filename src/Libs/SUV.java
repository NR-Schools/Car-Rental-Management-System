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
public class SUV implements ICar{
    
    private int ID;
    private String model;
    private String brand;
    private String desc;
    private String plateNo;
    private double price;
    private String status;
    private Date rentDate;
    private Date returnDate;
    private Customer rentCust;
    

    @Override
    public String getType() {
        return "SUV";
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public String getPlateNo() {
        return plateNo;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public Date getRentDate() {
        return rentDate;
    }

    @Override
    public Date getReturnDate() {
        return returnDate;
    }

    @Override
    public Customer getRentCust() {
        return rentCust;
    }

    @Override
    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void setRentDate(Date rentDate) {
        this.rentDate = rentDate;
    }

    @Override
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public void setRentCust(Customer rentCust) {
        this.rentCust = rentCust;
    }
    
}
