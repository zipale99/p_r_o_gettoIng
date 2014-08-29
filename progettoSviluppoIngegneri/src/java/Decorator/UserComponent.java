/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Decorator;

import Composite.Itinerary;
import AgenziaCore.UserNotAuthorizedException;
import Composite.StayTemplate;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author Andrea
 */
public interface UserComponent{
    
    public String getType();
    public Itinerary getItinerary(String i);
    public Map getMyItinerary();
    public void deleteItinerary(String i);
    public void addItinerary(String key,Itinerary it);
    public String getUser();
    

}
