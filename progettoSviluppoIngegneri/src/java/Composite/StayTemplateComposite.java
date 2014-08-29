/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Composite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Andrea
 */
public class StayTemplateComposite extends StayTemplate {
    private List<StayTemplate> tree = null;
    
    public StayTemplateComposite(){
        tree = new LinkedList<StayTemplate>();
    }
    public StayTemplateComposite(String nome,String startLoc, String endLoc){
        super(nome,startLoc,endLoc);
        tree = new LinkedList<StayTemplate>();
    }
    
    @Override
    public StayTemplateComposite getSTComposite(){
        return this;
    }
    @Override
    public void add(StayTemplate item){
            tree.add(item);
    }
    
    @Override
    public void add(int i,StayTemplate item){
            tree.add(i,item);       
    }
    @Override
    public void remove(StayTemplate item){
            tree.remove(item);    
    }
    @Override
    public void remove(int i){
            tree.remove(i);
    }

    public StayTemplate get(int i){
            return tree.get(i);
    }
    
    public List<StayTemplate> getTree(){return tree;}
    
    @Override
    public String toString(){
        String ris="";
        for(int i=0;i<tree.size();i++){
                ris+=tree.get(i).toString()+" -> ";
        }
        return ris;
    } 
    @Override
    public String getInformazioni(){
        String ris="";
        for(int i=0;i<tree.size();i++){
                ris+=tree.get(i).getInformazioni()+" -> ";
        }
        return ris;
    }
    /**
     * Calcola la durata dell'oggetto this.tree
     * @return 
     */
    @Override
    public int getDurata(){
        int ris=0;
        for(int i=0;i<tree.size();i++){
            ris+=tree.get(i).getDurata();
        }
        return ris;
    }
    /**
     * Controlla se un itinerario è completo o parziale. Un itinerario è completo quando vi è consistenza fra le località
     * @return true se l'itinerario è completo, false altrimenti.
     */
    @Override
    public boolean consistenza(){
        int length = tree.size();
        StayTemplate stayTemplate = null;
        List<StayTemplate> list = null;
        String localitaPrecedente =null;
        boolean completo = false;
        
        list = this.toListSt();
        length = list.size();
        if(length==0)
            return false;
                
        if(length==1)
            return true;
         
        localitaPrecedente =((StayTemplate)list.get(0)).getEndLoc();
        int i = 1;
        while (i<length){
        if (((StayTemplate)list.get(i)).getStartLoc().equals(localitaPrecedente)){ 
            localitaPrecedente =((StayTemplate)list.get(i++)).getEndLoc();
            completo=true;
        }
        else 
            return false;
        }
        return(completo);
     }
    /*
    @Override
    public String toStringHTMLComposite(){
        String ris="";
        for(int i=0;i<tree.size();i++){
            //if(tree.get(i) instanceof StayTemplateComposite){
            if(tree.get(i).getSTComposite() != null){
                ris+="<h3>TAPPA: </h3>"
                + "<table id=\"stay\">"
                + "<th></th><th>START LOC</th><th>END LOC</th><th>DURATA</th><th>ATTIVITA TAPPA</th><th>OFFSET</th>"        
                + "<tr><td><input type=\"checkbox\" name=\"idStay\" value=\""+i+"\"></td><td>"+tree.get(i).getStartLoc()+"</td><td>"+tree.get(i).getEndLoc()+"</td><td>"+tree.get(i).getDurata()+"</td><td>"+tree.get(i).activity.toString()+"</td><td>"+tree.indexOf(tree.get(i)) +"</td></tr>"
                + "</table>"
                + "<table id=\"leaf\">"
                + "<thead><th>Tipo Tappa</th><th>Tipo Acc.</th><th>Description Acc.</th><th>Category Acc.</th><th>StartLoc</th><th>EndLoc</th><th>Veicolo</th><th>Activity</th><th>Durata</th></thead>"
                +tree.get(i).toStringHTMLComposite()+"</table>";
            }
            else
                ris+="<tr>"+tree.get(i).toStringHTMLComposite()+"</tr>";
        }
        return ris;
    }*/
    @Override
    public String toStringHTMLComposite(){
        String ris="";
        Iterator<StayTemplate> iterator = tree.iterator();
        int i=0;
        while(iterator.hasNext()){
            StayTemplate currentElement = iterator.next();
            if(currentElement.getSTComposite()!=null){
                 ris+="<h3>TAPPA: </h3>"
                + "<table id=\"stay\">"
                + "<th></th><th>START LOC</th><th>END LOC</th><th>DURATA</th><th>ATTIVITA TAPPA</th><th>OFFSET</th>"        
                + "<tr><td><input type=\"checkbox\" name=\"idStay\" value=\""+i+"\"></td><td>"+currentElement.getStartLoc()+"</td><td>"+currentElement.getEndLoc()+"</td><td>"+currentElement.getDurata()+"</td><td>"+currentElement.activity.toString()+"</td><td>"+tree.indexOf(currentElement) +"</td></tr>"
                + "</table>"
                + "<table id=\"leaf\">"
                + "<thead><th>Tipo Tappa</th><th>Tipo Acc.</th><th>Description Acc.</th><th>Category Acc.</th><th>StartLoc</th><th>EndLoc</th><th>Veicolo</th><th>Activity</th><th>Durata</th></thead>"
                +currentElement.toStringHTMLComposite()+"</table>";
            }
            else
                ris+="<tr>"+currentElement.toStringHTMLComposite()+"</tr>";
            i++;
        }
        return ris;
    }
    
    @Override
    public String toStringHTMLConfiguration(){
        String ris="";
        for(int i=0;i<tree.size();i++){
            ris+="<tr>"+tree.get(i).toStringHTMLConfiguration()+"</tr>";
        }
        return ris;
    }

    /**
     * list contiene le foglie di this.tree
     * @param list 
     */
    @Override
    public void toList(ArrayList list){
        for(int i=0;i<tree.size();i++){
            tree.get(i).toList(list);
        }
    }
    /**
     * list contiene gli StayTemplateComposite di this.tree
     * @return list
     */
    @Override
    public List toListSt(){
        List<StayTemplate> a = new ArrayList<StayTemplate>();
        for(int i=0;i<tree.size();i++){
            //if(tree.get(i) instanceof StayTemplateComposite)
            if(tree.get(i).getSTComposite()!=null)
                a.add(tree.get(i));
        }
        return a;
    }
    
    /**
     * Contiene tutte località (startLoc,endLoc) presenti in this.tree
     * @return 
     */
    
    @Override
    public String getLocality(){
        String ris="";
        List<String> locality =new ArrayList<String>();
        for(int i=0;i<tree.size();i++){
            ris+=tree.get(i).getLocality();
        }
        return ris;
    }

}
