/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Decorator;
import AgenziaCore.Activity;
import Composite.Itinerary;
import Composite.StayTemplate;
import Controller.ServiceDB;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Andrea
 */
public abstract class UserDecorator implements UserComponent{

    protected GenericUser user;
    protected StayTemplate itinerary;
    protected StayTemplate stayOBJ;
    
    //Lista delle Activity possibili per una determinata stayOBJ
    protected List<Activity> activity;
    
    
    
   
    public UserDecorator(GenericUser user){
        this.user = user;
        itinerary = null;
        stayOBJ = null;
        activity =null;
    }

    /**
     * Crea un oggetto Itinerario.
     * @return l'oggetto itinerario creato.
     */
    public void createItinerary(){
        itinerary = new Itinerary();   
    }
    /**
     * Sostituisce all'oggetto this.itinerary itinerario scelto
     * @param i - key con la quale determinare l'itinerario scelto.
     */
    public void setItinerary(String i){
            itinerary = user.getItinerary(i); 
    }
    /**
     * Ritorna l'oggetto Itinerario
     * @return l'oggetto this.itinerary
     */
    public StayTemplate getItinerary(){
        return itinerary;
    }
    /**
     * Salva l'oggetto this.itinerary nel DB. Se l'oggetto è già stato mappato nel DB viene sostituito.
     */
    public void saveItinerary() throws SQLException{
        ((Itinerary)itinerary).setStato(itinerary.consistenza());
        //Caso Modifica
        if(((Itinerary)itinerary).getIdItinerario() != -1){
            this.deleteItinerary(Integer.toString( ((Itinerary)itinerary).getIdItinerario() ));
            ServiceDB.getInstance().saveItinerary(((Itinerary)itinerary),user.getUser());
            user.addItinerary( Integer.toString (((Itinerary)itinerary).getIdItinerario()), (Itinerary) itinerary);
            //user.myItinerary()
        }
        else{
            ServiceDB.getInstance().saveItinerary(((Itinerary)itinerary),user.getUser());
            user.addItinerary(Integer.toString(((Itinerary)itinerary).getIdItinerario()), (Itinerary)itinerary);
        }
        itinerary=null;
        stayOBJ=null;
        activity=null;
    }
    /**
     * Elimina l'oggetto this.itinerary mappato nel DB.
     */
    @Override
    public void deleteItinerary(String key){
        if (user!=null){
            user.deleteItinerary(key);
            ServiceDB.getInstance().deleteItinerary(Integer.parseInt(key));
        }
    }
    /**
     * Modifica le informazioni dell'oggetto itinerario
     * @param name - nome dell'itinerario
     * @param desc - descrizione dell'itinerario
     * @param cat - categoria dell'itinerario
     */
    public void provideBasicInfo(String name,String desc, String cat){
        if(itinerary!=null){
            ((Itinerary)itinerary).setInfo(name,desc,cat);
        }
    }
    /**
     * Imposta l'oggetto this.stayOBJ a st
     * @param st 
     * @return l'oggetto stayOBJ
     */
    public StayTemplate selectStay(StayTemplate st){
        this.stayOBJ = (StayTemplate)st.clone();
        return this.stayOBJ;
    }
    /**
     * Aggiunge l'oggetto this.stayOBJ all'oggetto this.itinerary se quest'ultimo esiste. Imposta a null l'oggetto this.stayOBJ e this.activity 
     */
    public void addStay(){
        if(itinerary!=null){
                this.itinerary.add(this.stayOBJ);
        }
        stayOBJ=null;
        activity=null;
    }
    
    /**
     * Ritorna l'ggetto this.stayOBJ
     * @return stayOBJ
     */
    public StayTemplate getStayOBJ(){
        return stayOBJ;
    }
    /**
     * Imposta l'oggetto stayOBJ a l'iesimo elemento dell'oggetto this.itinerary
     * @param i - individua la tappa nell' oggetto this.itinerary
     */
    public void setStayOBJ(int i){
        if(itinerary!=null)
            stayOBJ = ((Itinerary)itinerary).get(i);
    }
    
    /**
     * Elimina dall'oggetto this.itinerary l'iesimo elemento
     * @param i - Individua l'oggetto da rimuovere
     */
    public void deleteStay(int i){
        if(itinerary!=null)
            itinerary.remove(i);
    }
    /**
     * Configura le opzioni di una tappa
     * @param i - indica elemento all'interno della stayOBJ; determina una StayTemplateLeaf
     * @param c - indica l'opzione che si vuole configurare di una StayTemplateLeaf
     * @param valore - indica il valore dell'opzione
     */
    public void configureStayParameter(String i, String c, String valore){
        stayOBJ.configureStayParameter(Integer.parseInt(i),Integer.parseInt(c),valore);
    }
    /**
     * Ritorna una stringa che rappresenta l'insieme di localita di this.stayOBJ. Es. Roma, Torino, Milano
     * @return l'insieme delle località
     */
    public String getLocality(){
        return (stayOBJ.getLocality());
    }
    /**
     * Imposta le possibili attività della stayOBJ. Vale null se stayOBJ = null
     * @param act 
     */
    public void setActivity(List act){
        if(stayOBJ!=null){
            activity = act;
        }
    }
    /**
     * Aggiunge un'attivita alle attività disponibili per una stayOBJ
     * @param i - identifica l'attivita da aggiungere dall'oggetto this.activity
     * @throws Exception - se l'attività da aggiungere è gia presente nella stayOBJ
     */
    
    public void addActivity(int i) throws Exception{
        if(stayOBJ!=null){
            if(stayOBJ.getActivity().contains((Activity)activity.get(i))) throw new Exception("Attivita gia presente");
            else stayOBJ.addActivity(activity.get(i));
        }
    }
    /**
     * Rimuove un attivita dalle attivita disponibili per una stayOBJ
     * @param i - identifica l'attivita da rimuovere.
     */
    public void removeActivity(int i) {
        stayOBJ.removeActivity(i);
    }
    /**
     * Ritorna la lista delle attività disponibile per l'oggetto this.stayOBJ. 
     * @return le attività disponibili o null se this.stayOBJ = null
     */
    public List<Activity> getActivity() {
        return activity;
    }
    
    @Override
    public String getType(){
        return "Creator";
    }
    @Override
    public Itinerary getItinerary(String i){   
        if (user!=null)
            return user.getItinerary(i);
        return null;
    }
    @Override
    public Map getMyItinerary(){
        return user.getMyItinerary();
    }
    
    public String getUser(){
        return user.getUser();
    }
    public void addItinerary(String key, Itinerary it){
        user.addItinerary(key, it);
    }
    /**
     * Modifica l'ordinamento di una tappa.
     * @param i - indica la posizione in cui inserire la tappa this.stayOBJ
     */
    public void ordina(int offset){
        if(itinerary!=null){
            itinerary.remove(stayOBJ);
            itinerary.add(offset, stayOBJ);
        }
    }
    /*
     * Imposta this.stayOBJ = null, this.activity=null
     * 
     */
    public void setStayOBJNull(){
        stayOBJ=null;
        activity=null;
    }

}
