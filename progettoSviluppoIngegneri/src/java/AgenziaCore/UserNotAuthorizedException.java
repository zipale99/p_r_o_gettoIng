/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AgenziaCore;

/**
 *
 * @author Andrea
 */
public class UserNotAuthorizedException extends Exception {
    public UserNotAuthorizedException(){
        super("Utente non autorizzato");
    }
    
}  
