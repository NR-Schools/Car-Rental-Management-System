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
    protected ICar createProduct(int objectRequest) {
        // Create Car
        if (objectRequest == 0) car = new MiniVan();
        else if (objectRequest == 1) car = new SUV();
        
        return car;
    }
    
}
