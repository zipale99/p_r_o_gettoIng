/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import Decorator.*;
/**
 *
 * @author Andrea
 */
public class Decora {
    private static Decora instance= null;

    public static Decora getInstance(){
        if(instance == null){
            instance = new Decora();
        }
        return instance;
    }

    public UserDecorator decoration(GenericUser user){
        if (user!=null){
            if(user.getType().equals("Customer")){
                UserDecorator userDecorator = new CreatorCustomerDecorator(user);
                return userDecorator;
            }
            if(user.getType().equals("TA")){
                UserDecorator userDecorator = new CreatorTADecorator(user);
                return userDecorator;
            }
        }
        return null;
    }
    
    public UserDecorator deleteDecoration(UserDecorator user){
        return null;
    }
    
    
    
}