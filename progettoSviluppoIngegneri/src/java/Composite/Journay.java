/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Composite;

/**
 *
 * @author Andrea
 */
public class Journay extends StayTemplateComposite {
    private String startDate;
    private String endDate;
    private Double costo;
    private int nPersone;

    public Journay(String startDate, String endDate, Double costo, int nPersone) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.costo = costo;
        this.nPersone = nPersone;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getnPersone() {
        return nPersone;
    }

    public void setnPersone(int nPersone) {
        this.nPersone = nPersone;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    public String toString(){
        return startDate+"-"+endDate+"-"+costo+"-"+nPersone+"\n"+super.toString();
        
    }
    public String toStringHTMLComposite(){
        return "<h2>VIAGGIO: </h2>"
                + "<table>"
                + "<th>Start Date</th><th>End Date</th><th>Costo</th><th>nPerosne</th>"
                + "<tr>"
                + "<td>"+startDate+"</td><td>"+endDate+"</td><td>"+costo+"</td><td>"+nPersone+"</td>"
                + "</tr>"
                + "</table>"
                + super.toStringHTMLComposite();
        
    }
    public String toStringHTML(){
        return  "<td>"+startDate+"</td><td>"+endDate+"</td><td>"+costo+"</td><td>"+nPersone+"</td>";
    }
    
            
    
}
