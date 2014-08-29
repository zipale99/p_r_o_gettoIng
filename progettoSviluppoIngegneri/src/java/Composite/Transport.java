/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Composite;

import java.util.ArrayList;


/**
 *
 * @author Andrea
 */
public class Transport extends StayTemplateLeaf{
    private String vehicle;

    public String getVehicle() {
        return vehicle;
    }
    
    public Transport(String vehicle,String startLoc,String endLoc,int durata){
            super(startLoc,endLoc,durata);
            this.vehicle = vehicle;
    }
    @Override
    public String toString(){
        return "Transport";
    }
    @Override
    public int getDurata(){
        return durata;
    }
    
    @Override
    public String toStringHTMLComposite(){
        return "<td>Transport</td><td></td><td></td><td></td><td>"+getStartLoc()+"</td><td>"+getEndLoc()+"</td><td>"+vehicle+"</td><td>"+activity.toString()+"</td><td>"+getDurata()+"</td>";
    }
    @Override
    public String getInformazioni(){
        return "Transport from: "+this.getStartLoc() + "  to: " + this.getEndLoc();
    }
    @Override
    public String toStringHTMLConfiguration(){
        String ris= this.getInformazioni()+"<br/>";
        if(option!=null){
            for(int i=0;i<option.size();i++){
                ris+=option.get(i).getNome()+"<br/>";
                for(int c=0;c<option.get(i).getPossibleValue().size();c++){
                    ris+="<input type=\"radio\" name=\""+i+"\" value=\""+option.get(i).getPossibleValue().get(c) +"\">"+option.get(i).getPossibleValue().get(c)+ "<br>";
                }
            }
        }
        return ris;
    }
    @Override
    public void toList(ArrayList list){
        list.add(this);
    }
    
    public String getLocality(){
        return "'"+getStartLoc()+ "', '" + getEndLoc()+"', ";
    }
   
    
}