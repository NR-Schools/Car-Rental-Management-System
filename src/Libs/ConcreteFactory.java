/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Libs;

/**
 *
 * @author hp
 */
public class ConcreteFactory extends IFactory {
    private ICar car;

    @Override
    protected ICar createProduct(String objectRequest) {
        if(objectRequest == "Mitsubishi") car =  new MitsubishiCar();
        if(objectRequest == "Nissan") car =  new NissanCar();
        if(objectRequest == "Jeep") car =  new JeepCar();
        return car;
    }
    
}
