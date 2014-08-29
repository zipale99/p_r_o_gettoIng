/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Decorator;
import java.util.*;
/**
 *
 * @author Andrea
 */
public class TA extends GenericUser{
    
     public TA(String user,String type){
        super(user,type);
    }
    
     public String getType(){
         return "TA";
     }

}
