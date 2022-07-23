/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Libs;

import java.util.Date;

/**
 *
 * @author hp
 */
public interface ICar {
    public String getBrand();
    public String getModel();
    public String getDescription();
    public boolean getStatus();
    public String getRentPrice();
    public Date getDateAvailable();
}
