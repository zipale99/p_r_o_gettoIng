/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Decorator;
/**
 *
 * @author Andrea
 */
public class Customer extends GenericUser{
    
    public Customer(String user,String type){
        super(user,type);
    }
    
    @Override
    public String getType(){
        return "Customer";
    }

}