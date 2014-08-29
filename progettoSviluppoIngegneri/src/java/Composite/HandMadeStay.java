/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Composite;

/**
 *
 * @author Andrea
 */
public class HandMadeStay extends StayTemplateLeaf{
    
    
    public HandMadeStay(String startLoc,String endLoc,int durata){
            super(startLoc,endLoc,durata);
    }
    /*public String toString(){
        return this.getStartLoc()+"-"+this.getEndLoc();
    }*/
    public String toString(){
        return "HMS";
    }
    public int getDurata(){
        return durata;
    }
    
     public String toStringHTMLComposite(){
        return "<td>HMS</td><td></td><td></td><td></td><td>"+getStartLoc()+"</td><td>"+getEndLoc();
    }

}
