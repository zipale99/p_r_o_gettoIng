/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Composite;
import java.util.*;
/**
 *
 * @author Andrea
 */
public class Itinerary extends StayTemplateComposite{ 
    private String itName;
    private String itDesc;
    private String itCategory;
    private int idItinerario;
    private String stato;
    //private int durata;

    public String getStato() {
        return stato;
    }

    public void setStato(boolean c) {
        if(c)
            this.stato = "completo";
        else
            this.stato = "parziale";
    }
    
    public Itinerary(){
        super();
        itName="";
        itDesc="";
        itCategory="";
        idItinerario=-1;
        stato="parziale";
    }

    public Itinerary(int idItinerario,String itName, String itDesc, String itCategory, String stato) {
        this.itName = itName;
        this.itDesc = itDesc;
        this.itCategory = itCategory;
        this.idItinerario = idItinerario;
        this.stato=stato;
    }
  
    public void setItName(String name){
        itName=name;
    }
    public void setItDesc(String desc){
        itDesc=desc;
    }
    public void setItCategory(String cat){
        itCategory=cat;
    }
    public String getItCategory(){
        return itCategory;
    }
    public String getItDesc(){
        return itDesc;
    }
    public String getItName(){
        return itName;
    }
    public void setInfo(String name,String desc, String cat){
        itName= name;
        itDesc= desc;
        itCategory = cat;
    }
    
    
    public String toString(){
        return itName+"-"+itDesc+"-"+itCategory+"\n"+super.toString();
        
    }

    public void setIdItinerario(int idItinerario) {
        this.idItinerario = idItinerario;
    }

    public int getIdItinerario() {
        return idItinerario;
    }
    public String toStringHTMLComposite(){
        return "<h2>ITINERARIO: </h2>"
                + "<table>"
                + "<th>Nome</th><th>Descrizione</th><th>Categoria</th><th>Durata</th><th>Stato</th>"
                + "<tr>"
                + "<td>"+itName+"</td><td>"+itDesc+"</td><td>"+itCategory+"</td><td>"+getDurata()+"</td><td>"+getStato()+"</td>"
                + "</tr>"
                + "</table><br/>"
                + super.toStringHTMLComposite()
                + "</table>";  
    }
    
    public String toStringHTML(){
        return "<td>"+itName+"</td><td>"+itDesc+"</td><td>"+itCategory+"</td><td>"+getDurata()+"</td>";
    }
}
