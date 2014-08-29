/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Decorator;

import AgenziaCore.UserNotAuthorizedException;
import Composite.Itinerary;
import Composite.StayTemplate;
import Controller.ServiceDB;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Andrea
 */
public abstract class User implements UserComponent{
    private String user, type;
    protected Map<String,StayTemplate> myItinerary = new HashMap<String,StayTemplate>();
    
    public User(String user){
        this.user = user;
	this.type =  null;
    }
    
    public User(String user, String type){
        this.user = user;
	this.type = type;
    }
    
    public User(){
        user=null;
        type=null;
    }
    
    @Override
    public String getUser(){
        return user;
    }
    
    @Override
    public String getType(){
        return type;
    }
    
    public void setUser(String user){
        this.user = user;
    }
    public void setType(String password){
        this.type = password;
    }
    
    public Map searchI(String stay, String activity,String duration) throws SQLException{
        return (ServiceDB.getInstance().searchI(stay,activity,duration));   
    }
    
    public Map searchJ(String stay, String activity, String cost,String duration,String dateStart,String dateEnd) throws SQLException{
        return (ServiceDB.getInstance().searchJ(stay,activity,cost,duration,dateStart,dateEnd));   
    }
    
    public ArrayList cerca(String startLoc,String endLoc){
        return ServiceDB.getInstance().cerca(startLoc,endLoc);
    }
    
    public void myItinerary() throws SQLException{
        myItinerary = (ServiceDB.getInstance().searchMyItinerary(user));  
    }
    
    public void setMyItinerary(Map result){
        myItinerary = result;
    }
    
    @Override
    public Map getMyItinerary(){
        return myItinerary;
    }
    public List searchActivity(String act) throws SQLException{
        return(ServiceDB.getInstance().searchActivity(act));
   }
    
    @Override
    public Itinerary getItinerary(String i){
        if (myItinerary.isEmpty()){
            return null;
        }
        return (Itinerary) myItinerary.get(i);
    }
    @Override
    public void deleteItinerary(String i){
       if (!myItinerary.isEmpty()){
            myItinerary.remove(i);
       }   
    }

    public void addItinerary(String key,Itinerary itinerary){
            myItinerary.put(key, itinerary);
    }

    public ArrayList<StayTemplate> cercaTrasferimento(String startLoc, String endLoc) {
         return ServiceDB.getInstance().cerca(startLoc,endLoc);
    }
    public String view(String id){
        return ((StayTemplate)myItinerary.get(id)).toStringHTMLComposite();
    }
       
}
