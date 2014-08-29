/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import AgenziaCore.Activity;
import Composite.StayTemplate;
import Decorator.User;
import Decorator.UserComponent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Andrea
 */
public class SearchController {
    User user;
    //Search Itinerary or Journay
    Map searchIJ = new HashMap<String,StayTemplate>();
    //SearchStay
    ArrayList<StayTemplate> resultSet;
    //SearchActivity
    List<Activity> activity;
    
    public SearchController(User user){ 
        this.user=user;
    }
    public UserComponent getUser(){
        return user;
    }
    public void setUser(User user){
            this.user=user;
    }

    public Map<String,StayTemplate> getSearchIJ() {
        return searchIJ;
    }
    
    public void searchIJ(String type, String stay, String activity, String cost,String duration,String dateStart,String dateEnd)throws Exception {
        if(type.equals("")) throw new Exception("Campo Type obbligatorio");
        if(type.equals("Itinerary")){
            if(!cost.equals("")||!dateStart.equals("")||!dateEnd.equals("")) throw new Exception("La scelta itinerario non prevede che i campi cost e data siano settati");
        }
        if(type.equals("Itinerary")){
            searchIJ = user.searchI(stay,activity,duration);
        }
        else{
            searchIJ = user.searchJ(stay,activity,cost,duration,dateStart,dateEnd); 
        }
    }
    public String view(String id){
        return ((StayTemplate)searchIJ.get(id)).toStringHTMLComposite();
    }
    
    public ArrayList cerca(String startLoc,String endLoc){
        resultSet = this.user.cerca(startLoc,endLoc);
        return resultSet;
    }
    public ArrayList cercaTrasferimento(String startLoc,String endLoc){
        resultSet = this.user.cercaTrasferimento(startLoc,endLoc);
        return resultSet;
    }
    
    public List searchActivity(String act) throws SQLException{
        return activity=user.searchActivity(act);
    }

    public List<Activity> getActivity() {
        return activity;
    }

    public StayTemplate get(int id){
        return resultSet.get(id);
    }

    public ArrayList<StayTemplate> getResultSet() {
        return resultSet;
    }
   
    
}
