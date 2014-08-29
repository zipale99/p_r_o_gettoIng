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
public abstract class GenericUser extends User{


	public GenericUser(String user, String type){
		super(user,type);
	}
        public GenericUser(String user){
		super(user);
	}
        public String getType(){
            return this.getType();
        }
}