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
public abstract class IFactory {
    protected abstract ICar createProduct(String objectRequest);
    
    public ICar doSomething(String object) {
        ICar color = createProduct(object);
        return color;
    }
}
