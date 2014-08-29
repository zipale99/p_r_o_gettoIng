/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import Composite.StayTemplate;
import Controller.ServiceDB;
import Decorator.GenericUser;
import Decorator.Customer;
import Decorator.TA;
import Decorator.User;
import java.util.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrea
 */
public class ProxyUser extends User{
    
    private GenericUser user = null;
    //costruttore
    public ProxyUser(String user){
        super(user);
    }
    public ProxyUser(){
        super();
    }
    public GenericUser getGenericUser(){
        return user;
    }
    
    public boolean login(String username,String password)throws SQLException{
        String type = ServiceDB.getInstance().login(username,password);
        if(!type.equals("")){
            if(type.equals("Customer")){
                user = new Customer(username,"Customer");
                myItinerary();
                return true;
            }
            else if(type.equals("TA")){
                user = new TA(username,"TA");
                myItinerary();
                return true;
            }
            return false;
        }
        return false;
    }
    
    @Override
    public void myItinerary() throws SQLException{
        if(user!=null)
            user.myItinerary();
    }
    
    @Override
    public Map<String, StayTemplate> getMyItinerary(){
        if(user!=null)
            return user.getMyItinerary();
        else
            return null;
    }
    public String view(String id){
        if(user!=null)
            return user.view(id);
        else 
            return null;
    }
    

}
