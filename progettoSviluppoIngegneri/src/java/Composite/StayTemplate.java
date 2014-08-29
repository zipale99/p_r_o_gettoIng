/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Composite;
import AgenziaCore.*;

/**
 *
 * @author Andrea
 */
import java.util.*;
public abstract class StayTemplate implements Cloneable{
    
    private String name;
    private String startLoc;
    private String endLoc;
    protected int durata;
    protected List<OptionValue> option = new ArrayList();
    protected List<Activity> activity = new ArrayList();
    
    public StayTemplate(String nome,String startLoc, String endLoc){
        this.name=nome;
        this.startLoc=startLoc;
        this.endLoc=endLoc;
    }
    
    public StayTemplate(String startLoc, String endLoc,int durata){
        this.startLoc=startLoc;
        this.endLoc=endLoc;
        this.durata=durata;
    }
    
    public StayTemplate(){
    }
    
    public String getEndLoc() {
        return endLoc;
    }

    public void setEndLoc(String endLoc) {
        this.endLoc = endLoc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartLoc() {
        return startLoc;
    }

    public void setStartLoc(String startLoc) {
        this.startLoc = startLoc;
    }
    public void setDurata(int durata) {
        this.durata = durata;
    }
    public int getDurata() {
        return durata;
    }
    public List<OptionValue> getOption(){
        return option;
    }
    public List<Activity> getActivity(){
        return activity;
    }
    public void addActivity(Activity a){
        activity.add(a);
    }
    
    @Override
    public Object clone(){
        try{
            StayTemplate stl = (StayTemplate)super.clone();
            return stl;
        }catch(CloneNotSupportedException r){return null;}
    }
    public void addOption(OptionValue o){
        option.add(o);
    }
    public void removeActivity(int i) {
        activity.remove(i);
    }
    
    public StayTemplateComposite getSTComposite(){
        return null;
    }
    
    //Metodi Interfaccia.
    
    //public List<StayTemplate> getTree(){return this.getTree();}
    
    public void add(StayTemplate a){}
    public void add(int i, StayTemplate a){}
    public void remove(StayTemplate a){}
    public void remove(int i){}
    public String toStringHTMLComposite(){
        return "";
    }
    public String toStringHTMLConfiguration(){
        return "";
    }
    public String toStringHTML(){
        return "";
    }
    public String getInformazioni(){
        return "";
    }
    public void toList(ArrayList list){}
    public List toListSt(){return new ArrayList();}
    public boolean consistenza(){ return true;}
   
    public void configureStayParameter(int i, int c, String valore){
       this.getSTComposite().get(i).getOption().get(c).setValue(valore);
    }
    public String getLocality(){
       return this.getLocality();
    }
    



    
    



}