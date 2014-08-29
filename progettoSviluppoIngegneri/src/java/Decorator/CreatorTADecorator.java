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
public class CreatorTADecorator extends UserDecorator{
    public CreatorTADecorator(GenericUser user){
        super(user);
    }
    public String getType(){
        return  "CreatorTADecorator";
    }
}
