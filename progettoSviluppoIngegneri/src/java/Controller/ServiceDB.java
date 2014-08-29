/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import AgenziaCore.Activity;
import AgenziaCore.OptionValue;
import Composite.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Andrea
 */
public class ServiceDB{
    private static ServiceDB instance= null;
    static String url=null;
    static String user=null;
    static String pwd=null;        
    
    
    
    protected ServiceDB() {
        url ="jdbc:derby://localhost:1527/Agenzia";
        user="root";
        pwd= "andrea"; 
    }
    
    public static ServiceDB getInstance(){
        if(instance == null){
            instance = new ServiceDB();
        }
        return instance;
    }
    
    public synchronized String login(String login,String password) throws SQLException{
        Connection conn = DriverManager.getConnection(url,user,pwd);
        Statement st = conn.createStatement();
        ResultSet rs=st.executeQuery("SELECT * FROM UTENTI where login='"+login+"' and password='"+password+"'");
        if(rs.next()){
            return rs.getString("type");
        }
        rs.close();
        st.close();
        conn.close();
        return "";
    }
     public synchronized Map searchI(String stay, String activity,String duration) throws SQLException{
        Connection conn = DriverManager.getConnection(url,user,pwd);
        Statement stItinerary = conn.createStatement();
        Statement stLeaf = conn.createStatement();
        Statement stStay = conn.createStatement();
        Statement stOption = conn.createStatement();
        Statement stValue = conn.createStatement();
        Statement stActivity = conn.createStatement();
        Statement stActivityST = conn.createStatement();
        String activityWhere="";
        String activityFrom="";
        if (stay.equals(""))
            stay="%";
        if (!activity.equals("")){
            activityFrom=", ATTIVITA_LEAF as AL, ATTIVITA as A, ATTIVITA_ST as AST";
            activityWhere=" AND  A.tipo='"+activity+"' AND (( A.idAttivita=AL.idActivity AND AL.idLeaf=L.idLeaf ) OR ( A.idAttivita=AST.idActivity AND AST.IDSTAYTEMPL=ST.IDSTAYTEMPL AND AST.IDSTAYTEMPL=ST.IDSTAYTEMPL ) )";
        }
        if(duration.equals(""))
            duration="%";
        System.out.println("SELECT Distinct(I.idItinerario), I.* FROM ITINERARIO as I,STAYTEMPLATE as ST,Leaf as L"+activityFrom+" where (I.durata LIKE '"+duration+"' AND ( L.startLoc LIKE '"+stay+"'or L.startEnd LIKE '"+stay+"') ) and ST.idItinerario=I.idItinerario and L.idStayTempl=ST.idStayTempl"+activityWhere);
        
        ResultSet rsItinerary=stItinerary.executeQuery("SELECT Distinct(I.idItinerario), I.* FROM ITINERARIO as I,STAYTEMPLATE as ST,Leaf as L"+activityFrom+" where (I.durata LIKE '"+duration+"' AND ( L.startLoc LIKE '"+stay+"'or L.startEnd LIKE '"+stay+"') ) and ST.idItinerario=I.idItinerario and L.idStayTempl=ST.idStayTempl"+activityWhere);
 
        Map<String,StayTemplate> result = new HashMap<String,StayTemplate>();
        StayTemplate it = null;
        StayTemplate stayT = null;
        StayTemplate stc = null;
        ResultSet rsLeaf=null;
        ResultSet rsStay=null;
        ResultSet rsOption=null;
        ResultSet rsValue=null;
        ResultSet rsActivityL=null;
        ResultSet rsActivityST=null;
        while(rsItinerary.next()){   
            int idItinerario= Integer.parseInt(rsItinerary.getString("idItinerario"));
            it = new Itinerary(idItinerario,rsItinerary.getString("itName"),rsItinerary.getString("itDesc"),rsItinerary.getString("itCategory"),rsItinerary.getString("stato"));
            result.put(rsItinerary.getString("idItinerario"), it);
            
            rsStay=stStay.executeQuery("SELECT ST.* FROM STAYTEMPLATE as ST where ST.idItinerario="+idItinerario);
            while(rsStay.next()){
                int idStayTempl= Integer.parseInt(rsStay.getString("idStayTempl"));
                
                stc = new StayTemplateComposite(rsStay.getString("nome"), rsStay.getString("startLoc"), rsStay.getString("endLoc"));
                String query="Select * from ATTIVITA_ST as AST, ATTIVITA as A where A.idAttivita=AST.idActivity AND AST.idStayTempl="+idStayTempl;
                rsActivityST=stActivityST.executeQuery(query);
                while(rsActivityST.next()){
                    stc.getActivity().add(new Activity(rsActivityST.getString("tipo"),rsActivityST.getString("citta"),rsActivityST.getString("descrizione"),Integer.parseInt(rsActivityST.getString("durata")),rsActivityST.getInt("idActivity"),rsActivityST.getString("obbligatoria")));
                }
                rsActivityST.close();
                rsLeaf = stLeaf.executeQuery("SELECT L.* from Leaf as L where L.idStayTempl="+idStayTempl);
                while(rsLeaf.next()){  
                    int idLeaf = Integer.parseInt(rsLeaf.getString("idLeaf"));
                    if(rsLeaf.getString("typeLeaf").equals("Transport")){
                        stayT = new Transport(rsLeaf.getString("veicolo"),rsLeaf.getString("StartLoc"),rsLeaf.getString("startEnd"),Integer.parseInt(rsLeaf.getString("duarata")));
                    }
                    if(rsLeaf.getString("typeLeaf").equals("HMS")){
                        stayT = new HandMadeStay(rsLeaf.getString("startLoc"),rsLeaf.getString("startEnd"),Integer.parseInt(rsLeaf.getString("duarata")));
                    }
                    if(rsLeaf.getString("typeLeaf").equals("Accomodation")){
                        stayT = new Accomodation(rsLeaf.getString("tipo"),rsLeaf.getString("description"),rsLeaf.getString("category"),rsLeaf.getString("startLoc"),rsLeaf.getString("startEnd"),Integer.parseInt(rsLeaf.getString("duarata")));
                    }

                    rsOption = stOption.executeQuery("Select O.*, OV.valore  from OptionValue as OV, OPT as O where O.idOption=OV.idOption and OV.idLeaf="+idLeaf);

                    while(rsOption.next()){
                        OptionValue ov = new OptionValue(rsOption.getString("valore"),rsOption.getString("descrizione"),rsOption.getString("nome"),Integer.parseInt(rsOption.getString("idOption")));        
                        rsValue = stValue.executeQuery("Select O.*  from OPTVALUE as O where O.idOption="+Integer.parseInt(rsOption.getString("idOption"))); 
                        while(rsValue.next()){
                            ov.add(rsValue.getString("valore"));  
                        }
                        rsValue.close();
                        stayT.addOption(ov);
                    }
                    rsOption.close();
                    String queryActivityLeaf = "Select * from ATTIVITA_LEAF , ATTIVITA  where ATTIVITA.idAttivita=ATTIVITA_LEAF.idActivity AND ATTIVITA_LEAF.idleaf="+idLeaf;
                    rsActivityL = stActivity.executeQuery(queryActivityLeaf);
                    while(rsActivityL.next()){
                        Activity act = new Activity(rsActivityL.getString("TIPO"),rsActivityL.getString("CITTA"),rsActivityL.getString("DESCRIZIONE"),rsActivityL.getInt("DURATA"),rsActivityL.getInt("IDATTIVITA"),"SI");
                        stayT.addActivity(act);
                    }
                    rsActivityL.close();
                    
                    stc.add(stayT);  
                }
                rsLeaf.close();
                result.get(rsItinerary.getString("idItinerario")).add(stc);
            }
            rsStay.close();
        }
        stLeaf.close();
        stStay.close();
        stOption.close();
        stValue.close();
        stActivity.close();
        stActivityST.close();
        stItinerary.close();
        rsItinerary.close();
        conn.close();
        return result;
     }
     
     public synchronized Map searchJ(String stay, String activity, String cost,String duration,String dateStart,String dateEnd)  throws SQLException{
        Connection conn = DriverManager.getConnection(url,user,pwd);
        Statement stItinerary = conn.createStatement();
        Statement stViaggio = conn.createStatement();
        Statement stLeaf = conn.createStatement();
        Statement stStay = conn.createStatement();
        Statement stOption = conn.createStatement();
        Statement stValue = conn.createStatement();
        Statement stActivity = conn.createStatement();
        Statement stActivityST = conn.createStatement();
        
        StayTemplate it = null;
        StayTemplate stayT = null;
        StayTemplate stc = null;
        ResultSet rsLeaf=null;
        ResultSet rsStay=null;
        ResultSet rsOption=null;
        ResultSet rsValue=null;
        ResultSet rsActivityL=null;
        ResultSet rsActivityST=null;
        
        String activityWhere="";
        String activityFrom="";
        
        if (stay.equals(""))
            stay="%";
        if (!activity.equals("")){
            activityFrom=", ATTIVITA_LEAF as AL, ATTIVITA as A, ATTIVITA_ST as AST";
            activityWhere=" AND  A.tipo='"+activity+"' AND (( A.idAttivita=AL.idActivity AND AL.idLeaf=L.idLeaf ) OR ( A.idAttivita=AST.idActivity ) )";
        }
        if(duration.equals(""))
            duration="%";
        if (dateStart.equals(""))
            dateStart="%";
        if (dateEnd.equals(""))
            dateEnd="%";
        if(cost.equals(""))
            cost="%";

        ResultSet rsViaggio = stViaggio.executeQuery("SELECT Distinct(V.idViaggio),V.*, I.* FROM Viaggio as V, ITINERARIO as I,STAYTEMPLATE as ST,Leaf as L"+activityFrom+" where (I.durata LIKE '"+duration+"' AND ( L.startLoc LIKE '"+stay+"'or L.startEnd LIKE '"+stay+"') AND V.startDate LIKE '"+dateStart+"'AND V.endDate LIKE '"+dateEnd+"' AND V.COSTO LIKE '"+cost+"' ) and ST.idItinerario=I.idItinerario and L.idStayTempl=ST.idStayTempl and V.idItinerario=I.idItinerario"+activityWhere);
        System.out.println(rsViaggio);
        Map<String,StayTemplate> result = new HashMap<String,StayTemplate>();
        StayTemplate journay = null;
        
        while(rsViaggio.next()){   
            int idViaggio = rsViaggio.getInt("idViaggio");
            journay = new Journay(rsViaggio.getString("startDate"),rsViaggio.getString("endDate"),new Double(rsViaggio.getString("costo")),Integer.parseInt(rsViaggio.getString("nPersone")));
            
            int idItinerario= rsViaggio.getInt("idItinerario");
            it = new Itinerary(idItinerario,rsViaggio.getString("itName"),rsViaggio.getString("itDesc"),rsViaggio.getString("itCategory"),rsViaggio.getString("stato"));
            
           
            
            rsStay=stStay.executeQuery("SELECT ST.* FROM STAYTEMPLATE as ST where ST.idItinerario="+idItinerario);
            while(rsStay.next()){
                int idStayTempl= Integer.parseInt(rsStay.getString("idStayTempl"));
                
                stc = new StayTemplateComposite(rsStay.getString("nome"), rsStay.getString("startLoc"), rsStay.getString("endLoc"));
                String query="Select * from ATTIVITA_ST as AST, ATTIVITA as A where A.idAttivita=AST.idActivity AND AST.idStayTempl="+idStayTempl;
                rsActivityST=stActivityST.executeQuery(query);
                while(rsActivityST.next()){
                    stc.getActivity().add(new Activity(rsActivityST.getString("tipo"),rsActivityST.getString("citta"),rsActivityST.getString("descrizione"),Integer.parseInt(rsActivityST.getString("durata")),rsActivityST.getInt("idActivity"),rsActivityST.getString("obbligatoria")));
                }
                
                rsLeaf = stLeaf.executeQuery("SELECT L.* from Leaf as L where L.idStayTempl="+idStayTempl);
                while(rsLeaf.next()){  
                    int idLeaf = Integer.parseInt(rsLeaf.getString("idLeaf"));
                    if(rsLeaf.getString("typeLeaf").equals("Transport")){
                        stayT = new Transport(rsLeaf.getString("veicolo"),rsLeaf.getString("StartLoc"),rsLeaf.getString("startEnd"),Integer.parseInt(rsLeaf.getString("duarata")));
                    }
                    if(rsLeaf.getString("typeLeaf").equals("HMS")){
                        stayT = new HandMadeStay(rsLeaf.getString("startLoc"),rsLeaf.getString("startEnd"),Integer.parseInt(rsLeaf.getString("duarata")));
                    }
                    if(rsLeaf.getString("typeLeaf").equals("Accomodation")){
                        stayT = new Accomodation(rsLeaf.getString("tipo"),rsLeaf.getString("description"),rsLeaf.getString("category"),rsLeaf.getString("startLoc"),rsLeaf.getString("startEnd"),Integer.parseInt(rsLeaf.getString("duarata")));
                    }

                    rsOption = stOption.executeQuery("Select O.*, OV.valore  from OptionValue as OV, OPT as O where O.idOption=OV.idOption and OV.idLeaf="+idLeaf);

                    while(rsOption.next()){
                        OptionValue ov = new OptionValue(rsOption.getString("valore"),rsOption.getString("descrizione"),rsOption.getString("nome"),Integer.parseInt(rsOption.getString("idOption")));        
                        rsValue = stValue.executeQuery("Select O.*  from OPTVALUE as O where O.idOption="+Integer.parseInt(rsOption.getString("idOption"))); 
                        while(rsValue.next()){
                            ov.add(rsValue.getString("valore"));  
                        }
                        stayT.addOption(ov);
                    }
                    String queryActivityLeaf = "Select * from ATTIVITA_LEAF , ATTIVITA  where ATTIVITA.idAttivita=ATTIVITA_LEAF.idActivity AND ATTIVITA_LEAF.idleaf="+idLeaf;
                    rsActivityL = stActivity.executeQuery(queryActivityLeaf);
                    while(rsActivityL.next()){
                        Activity act = new Activity(rsActivityL.getString("TIPO"),rsActivityL.getString("CITTA"),rsActivityL.getString("DESCRIZIONE"),rsActivityL.getInt("DURATA"),rsActivityL.getInt("IDATTIVITA"),"SI");
                        stayT.addActivity(act);
                    }
                    
                    stc.add(stayT);  
                }
                it.add(stc);
                journay.add(it);
                result.put(rsViaggio.getString("idViaggio"), journay);
            }

        }
        rsLeaf.close();
        rsStay.close();
        rsOption.close();
        rsValue.close();
        rsActivityL.close();
        rsActivityST.close();
        stItinerary.close();
        stLeaf.close();
        stStay.close();
        stOption.close();
        stValue.close();
        stActivity.close();
        stActivityST.close();
        conn.close();
        return result;   
     }
    

     public synchronized List<Activity> searchActivity(String location) throws SQLException{
        Connection conn = DriverManager.getConnection(url,user,pwd);
        Statement st = conn.createStatement();
        ResultSet rs = null;
        List<Activity> activity = new ArrayList<Activity>();
        if(location.length()!=0){
            location = location.substring(0, location.length()-2);
            location="IN ( "+location+" ) ";
        }
        String query = "Select * from Attivita as A where citta "+location;
        rs = st.executeQuery(query);
        while(rs.next()){
            activity.add( new Activity(rs.getString("tipo"),rs.getString("citta"),rs.getString("descrizione"),Integer.parseInt(rs.getString("durata")),rs.getInt("idAttivita"),"NO"));   
        }
        if(activity.isEmpty()){
            rs.close();
            st.close();
            conn.close();
            return null;
        }
        rs.close();
        st.close();
        conn.close();
        return activity;
        
        
     }

     public synchronized ArrayList cerca(String optList, String optList2){
         if((optList == null) || (optList2 == null)) return new ArrayList<StayTemplate>();
         if (optList.equals(optList2)) {return cercaAll(optList, optList2);}
         else {return cercaTrasferimento(optList, optList2);}
     }
     
     private synchronized ArrayList cercaAll(String optList, String optList2){
        int durata = 0;
        boolean primo = true;
        int idStayTemplateOld = 0;
        int idStayTemplateNew = 0;
        ArrayList<StayTemplate> resultSet = new ArrayList<StayTemplate>();
        StayTemplate stayTemplate = null;
        String strStay = "Select Distinct(STAYTEMPLATE.idStayTempl),STAYTEMPLATE.* from LEAF,STAYTEMPLATE where (leaf.startLoc = '" + optList + "' or leaf.startEnd = '" + optList2 + "') and leaf.idstaytempl = staytemplate.idstaytempl and staytemplate.idItinerario is null";

        try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            Statement st = conn.createStatement();
            Statement stStay = conn.createStatement();
            Statement st2 = conn.createStatement();
            Statement st3 = conn.createStatement();
            Statement st4 = conn.createStatement();
            Statement st5 = conn.createStatement();
            ResultSet rsStay=stStay.executeQuery(strStay);
            ResultSet rs=null;
            ResultSet rs2=null;
            ResultSet rs3=null;
            ResultSet rs4=null;
            ResultSet rs5=null;

            while(rsStay.next()){

            String strLeaf = "Select L.* from Leaf as L where L.idStayTempl= " + rsStay.getInt("idStayTempl");
            rs=st.executeQuery(strLeaf);
                while(rs.next()){
                    try{
                        durata = Integer.parseInt(rs.getString("duarata"));
                    }catch(Exception e){
                        durata = 0;
                    }

                    try{
                        idStayTemplateNew = Integer.parseInt(rs.getString("IDSTAYTEMPL"));
                    }catch(Exception e){
                        idStayTemplateNew = -1;
                    }

                    if((primo) || (idStayTemplateOld != idStayTemplateNew)){
                        stayTemplate = new StayTemplateComposite(rsStay.getString("nome"), rsStay.getString("startLoc"), rsStay.getString("endLoc"));
                        String query="Select * from ATTIVITA_ST as AST, ATTIVITA as A where A.idAttivita=AST.idActivity AND AST.idStayTempl="+idStayTemplateNew+" AND AST.obbligatoria='NO'";
                        rs4=st4.executeQuery(query);
                        while(rs4.next()){
                            stayTemplate.getActivity().add(new Activity(rs4.getString("tipo"),rs4.getString("citta"),rs4.getString("descrizione"),Integer.parseInt(rs4.getString("durata")),rs4.getInt("idActivity"),rs4.getString("obbligatoria")));
                        }

                        resultSet.add(stayTemplate);
                        primo = false;
                        idStayTemplateOld = idStayTemplateNew;
                    }
                    String query="Select O.*  from OptionValue as OV, OPT as O where  O.idOption=OV.idOption and OV.idLeaf="+Integer.parseInt(rs.getString("idLeaf"));
                    rs2 = st2.executeQuery(query);

                    if (rs.getString("TYPELEAF").equals("Accomodation")){
                        Accomodation accomodation = new Accomodation(rs.getString("TIPO"),rs.getString("DESCRIPTION"),rs.getString("CATEGORY"),rs.getString("STARTLOC"),rs.getString("STARTEND"),durata);
                        while(rs2.next()){
                            OptionValue ov = new OptionValue(null,rs2.getString("descrizione"),rs2.getString("nome"),Integer.parseInt(rs2.getString("idOption")));
                            String query2="Select O.*  from OPTVALUE as O where O.idOption="+Integer.parseInt(rs2.getString("idOption"));
                            rs3 = st3.executeQuery(query2);
                            while(rs3.next()){
                                ov.add(rs3.getString("valore"));
                            }
                            accomodation.addOption(ov);
                        }

                        //carico activity delle leaf
                        String queryActivityLeaf = "Select * from ATTIVITA_LEAF , ATTIVITA  where ATTIVITA.idAttivita=ATTIVITA_LEAF.idActivity AND ATTIVITA_LEAF.idleaf="+Integer.parseInt(rs.getString("idLeaf"));
                        rs5 = st5.executeQuery(queryActivityLeaf);
                        while(rs5.next()){
                            Activity act = new Activity(rs5.getString("TIPO"),rs5.getString("CITTA"),rs5.getString("DESCRIZIONE"),rs5.getInt("DURATA"),rs5.getInt("IDATTIVITA"),"SI");
                            accomodation.addActivity(act);
                        }

                        stayTemplate.add(accomodation);
                    } else if (rs.getString("TYPELEAF").equals("Transport")){
                        Transport transport = new Transport (rs.getString("VEICOLO"),rs.getString("STARTLOC"),rs.getString("STARTEND"),durata);
                        while(rs2.next()){
                            OptionValue ov = new OptionValue(null,rs2.getString("descrizione"),rs2.getString("nome"),Integer.parseInt(rs2.getString("idOption")));
                            String query2="Select O.* from OPTVALUE as O where O.idOption="+Integer.parseInt(rs2.getString("idOption"));
                            rs3 = st3.executeQuery(query2);
                            while(rs3.next()){
                                ov.add(rs3.getString("valore"));
                            }
                            transport.addOption(ov);
                        }
                        stayTemplate.add(transport);
                    }

                }
            }
            rs.close();
            st.close();
            rs2.close();
            st2.close();
            rs3.close();
            st3.close();
            rs4.close();
            st4.close();
            rs5.close();
            st5.close();
            conn.close();
        } catch (Exception e) {}
        return resultSet;
     }


     private synchronized ArrayList cercaTrasferimento(String optList, String optList2){
        int durata = 0;
        boolean primo = true;
        int idStayTemplateOld = 0;
        int idStayTemplateNew = 0;
        ArrayList<StayTemplate> resultSet = new ArrayList<StayTemplate>();
        StayTemplate stayTemplate = null;
        String strStay = "Select Distinct(STAYTEMPLATE.idStayTempl),STAYTEMPLATE.* from LEAF,STAYTEMPLATE where (leaf.startLoc = '" + optList + "' and leaf.startEnd = '" + optList2 + "') and leaf.TYPELEAF = 'Transport' and leaf.idstaytempl = staytemplate.idstaytempl and staytemplate.idItinerario is null";

        try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            Statement st = conn.createStatement();
            Statement stStay = conn.createStatement();
            Statement st2 = conn.createStatement();
            Statement st3 = conn.createStatement();
            Statement st4 = conn.createStatement();
            Statement st5 = conn.createStatement();
            ResultSet rsStay=stStay.executeQuery(strStay);
            ResultSet rs=null;
            ResultSet rs2=null;
            ResultSet rs3=null;
            ResultSet rs4=null;
            ResultSet rs5=null;

            while(rsStay.next()){

            //String strLeaf = "Select L.* from Leaf as L where L.idStayTempl= " + rsStay.getInt("idStayTempl");
            String strLeaf = "Select L.* from Leaf as L where L.IDSTAYTEMPL = any(Select L.IDSTAYTEMPL from Leaf as L where L.idStayTempl=" +rsStay.getInt("idStayTempl")+" group by L.IDSTAYTEMPL having count (L.IDSTAYTEMPL) = 1)";
            rs=st.executeQuery(strLeaf);
                while(rs.next()){
                    try{
                        durata = Integer.parseInt(rs.getString("durata"));
                    }catch(Exception e){
                        durata = 0;
                    }

                    try{
                        idStayTemplateNew = Integer.parseInt(rs.getString("IDSTAYTEMPL"));
                    }catch(Exception e){
                        idStayTemplateNew = -1;
                    }

                    if((primo) || (idStayTemplateOld != idStayTemplateNew)){
                        stayTemplate = new StayTemplateComposite(rsStay.getString("nome"), rsStay.getString("startLoc"), rsStay.getString("endLoc"));
                        String query="Select * from ATTIVITA_ST as AST, ATTIVITA as A where A.idAttivita=AST.idActivity AND AST.idStayTempl="+idStayTemplateNew+" AND AST.obbligatoria='NO'";
                        rs4=st4.executeQuery(query);
                        while(rs4.next()){
                            stayTemplate.getActivity().add(new Activity(rs4.getString("tipo"),rs4.getString("citta"),rs4.getString("descrizione"),Integer.parseInt(rs4.getString("durata")),rs4.getInt("idActivity"),rs4.getString("obbligatoria")));
                        }

                        resultSet.add(stayTemplate);
                        primo = false;
                        idStayTemplateOld = idStayTemplateNew;
                    }
                    String query="Select O.*  from OptionValue as OV, OPT as O where  O.idOption=OV.idOption and OV.idLeaf="+Integer.parseInt(rs.getString("idLeaf"));
                    rs2 = st2.executeQuery(query);
                    
                    if (rs.getString("TYPELEAF").equals("Transport")){
                        Transport transport = new Transport (rs.getString("VEICOLO"),rs.getString("STARTLOC"),rs.getString("STARTEND"),durata);
                        while(rs2.next()){
                            OptionValue ov = new OptionValue(null,rs2.getString("descrizione"),rs2.getString("nome"),Integer.parseInt(rs2.getString("idOption")));
                            String query2="Select O.* from OPTVALUE as O where O.idOption="+Integer.parseInt(rs2.getString("idOption"));
                            rs3 = st3.executeQuery(query2);
                            while(rs3.next()){
                                ov.add(rs3.getString("valore"));
                            }
                            transport.addOption(ov);
                        }
                        stayTemplate.add(transport);
                    }

                }
            }
            rs.close();
            st.close();
            rs2.close();
            st2.close();
            rs3.close();
            st3.close();
            rs4.close();
            st4.close();
            rs5.close();
            st5.close();
            conn.close();
        } catch (Exception e) {}
        return resultSet;
     }




     public synchronized void deleteItinerary(int keyItinerary){
        int keyStayTemplate = -1;
        int keyLeaf = -1;
        try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            Statement stStayTemplate = conn.createStatement();
            String query="Select * from ROOT.STAYTEMPLATE where idItinerario = "+keyItinerary;
            ResultSet rsStayTemplate=stStayTemplate.executeQuery(query);
            while(rsStayTemplate.next()){
                Statement stDeleteActivity = conn.createStatement();
                Statement stSelectLeaf = conn.createStatement();
                Statement stDeleteOption = conn.createStatement();
                Statement stDeleteLeaf = conn.createStatement();
                Statement stDeleteStayTemplate = conn.createStatement();
                Statement stDeleteActivityLeaf = conn.createStatement();
                 
                keyStayTemplate = rsStayTemplate.getInt("IDSTAYTEMPL");
                String str = "DELETE FROM ROOT.ATTIVITA_ST  WHERE IDSTAYTEMPL = " + keyStayTemplate;
                stDeleteActivity.executeUpdate(str);

                str = "Select * from ROOT.LEAF where IDSTAYTEMPL = " + keyStayTemplate;
                ResultSet rsLeaf=stSelectLeaf.executeQuery(str);
                while(rsLeaf.next()){
                    keyLeaf = rsLeaf.getInt("IDLEAF");
                    str = "DELETE FROM ROOT.OPTIONVALUE WHERE IDLEAF = " + keyLeaf;
                    stDeleteOption.executeUpdate(str);

                    //cancellare activity leaf controllare
                    str = "DELETE FROM ROOT.ATTIVITA_LEAF WHERE IDLEAF = " + keyLeaf;
                    stDeleteActivityLeaf.executeUpdate(str);

                }
                rsLeaf.close();

                str = "DELETE FROM ROOT.LEAF WHERE IDSTAYTEMPL = " + keyStayTemplate;
                stDeleteLeaf.executeUpdate(str);

                str = "DELETE FROM ROOT.STAYTEMPLATE WHERE IDSTAYTEMPL = " + keyStayTemplate;
                stDeleteStayTemplate.executeUpdate(str);

                stDeleteOption.close();
                stDeleteActivity.close();
                stSelectLeaf.close();
                stDeleteLeaf.close();
                stDeleteStayTemplate.close();
                stDeleteActivityLeaf.close();
            }

            String strDelete = "DELETE FROM ROOT.ITINERARIO WHERE IDITINERARIO = " + keyItinerary;
            Statement stDeleteItinerary = conn.createStatement();
            stDeleteItinerary.executeUpdate(strDelete);
            
            stDeleteItinerary .close();
            stStayTemplate.close();
            rsStayTemplate.close();
            conn.close();
        }catch(Exception e){}
     }
    
     public synchronized  void saveItinerary(Itinerary itinerary, String utente){
         String startLoc = itinerary.getStartLoc();
         String endLoc = itinerary.getEndLoc();
         String durata = ""+itinerary.getDurata();
         String itName = itinerary.getItName();
         String itDescription = itinerary.getItDesc();
         String itCategory = itinerary.getItCategory();
         String stato = itinerary.getStato();
         String veicolo = null;
         int keyItinerary = -1;
         Statement stAttivita = null;
         PreparedStatement statementLeaf = null;
         Statement stOptionValue = null;
         Statement stActivityLeaf = null;
         PreparedStatement stItinerario = null;
         StayTemplate stayTemplateItinerary = null;
         
         String str = "INSERT INTO ROOT.ITINERARIO (CREATORUSER, STARTLOC, ENDLOC, DURATA, ITNAME, ITDESC, ITCATEGORY, STATO) VALUES ('"+utente+"', '"+startLoc+"', '"+endLoc+"', '"+durata+"', '"+itName+"','"+ itDescription +"', '"+itCategory+"','"+stato+"')";
         try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            PreparedStatement st = conn.prepareStatement(str,Statement.RETURN_GENERATED_KEYS);
            st.executeUpdate();
            ResultSet keys = st.getGeneratedKeys();
            keys.next();
            keyItinerary = keys.getInt(1);
            itinerary.setIdItinerario(keyItinerary);

            List<StayTemplate> tree = itinerary.getSTComposite().getTree();
            int numStayTemplate = tree.size();
            for(int k=0; k<numStayTemplate; k++){
                stayTemplateItinerary = tree.get(k);

                str = "INSERT INTO ROOT.STAYTEMPLATE (IDITINERARIO, NOME, STARTLOC, ENDLOC) VALUES (" + keyItinerary + ", '"+stayTemplateItinerary.getName() + "','"+stayTemplateItinerary.getStartLoc()+ "', '"+stayTemplateItinerary.getEndLoc()+ "')";
                stItinerario = conn.prepareStatement(str,Statement.RETURN_GENERATED_KEYS);
                stItinerario.executeUpdate();
                keys = stItinerario.getGeneratedKeys();
                keys.next();
                int keyStateTemplate = keys.getInt(1);


                //recupero la lista delle attivita.
                List<Activity> activityList = stayTemplateItinerary.getActivity();
                if(activityList!=null){
                    int lun = activityList.size();
                    for(int i = 0; i<lun;i++){
                        str = "INSERT INTO ROOT.ATTIVITA_ST (IDSTAYTEMPL,OFFSET,IDACTIVITY, OBBLIGATORIA) VALUES ("+keyStateTemplate+", '"+activityList.get(i).getOffset() +"', "+activityList.get(i).getIdActivity()+",'"+activityList.get(i).getObbligatorie()+"')";
                        stAttivita =  conn.createStatement();
                        int ris = stAttivita.executeUpdate(str);
                    }

                }

                //recupero le leaf
                ArrayList list = new ArrayList();
                StayTemplate stayTemplate = null;
                stayTemplateItinerary.toList(list);

                int lenght = list.size();
                for(int i = 0; i< lenght; i++ ){
                    stayTemplate = (StayTemplate)list.get(i);
                    startLoc = stayTemplate.getStartLoc();
                    endLoc = stayTemplate.getEndLoc();
                    durata = stayTemplate.getDurata() + "";
                    if(stayTemplate instanceof Accomodation){
                        str = "INSERT INTO ROOT.LEAF (TYPELEAF, DUARATA, STARTLOC, STARTEND,IDSTAYTEMPL) VALUES ('Accomodation', '"+durata+"','"+startLoc+"', '"+endLoc+"',"+keyStateTemplate+")";
                    }
                    if(stayTemplate instanceof Transport){
                        veicolo = ((Transport) stayTemplate).getVehicle();
                        str = "INSERT INTO ROOT.LEAF (TYPELEAF, DUARATA, VEICOLO, STARTLOC, STARTEND,IDSTAYTEMPL) VALUES ('Transport', '"+durata+"','"+veicolo+"','"+startLoc+"', '"+endLoc+"',"+keyStateTemplate+")";
                    }

                    statementLeaf = conn.prepareStatement(str,Statement.RETURN_GENERATED_KEYS);
                    statementLeaf.executeUpdate();
                    System.out.println("\ninserito foglia numero" + i);
                    ResultSet keyLeaf = statementLeaf.getGeneratedKeys();
                    keyLeaf.next();
                    int primarykeyLeaf = keyLeaf.getInt(1);

                    List<OptionValue> optionLeaf = stayTemplate.getOption();
                    if(optionLeaf!=null){
                        int lenghtOptionLeaf = optionLeaf.size();
                        OptionValue optionValue = null;
                        for(int j = 0; j<lenghtOptionLeaf;j++){
                            optionValue = optionLeaf.get(j);
                            String valore = optionValue.getValue();
                            str = "INSERT INTO ROOT.OPTIONVALUE (IDLEAF, IDOPTION, VALORE) VALUES (" + primarykeyLeaf + "," + optionValue.getIdOption() + ",'"+valore+"')";
                            stOptionValue =  conn.createStatement();
                            int ris = stOptionValue.executeUpdate(str);
                            if (ris == 1)System.out.println("Inserimento Option value ok");
                            else System.out.println("Inserimento Option value ko");

                        }
                    }

                    // attivita leaf
                    List<Activity> activityLeaf = stayTemplate.getActivity();
                    if(activityLeaf!=null){
                        int lenghtActivityLeaf = activityLeaf.size();
                        for(int j = 0; j<lenghtActivityLeaf;j++){
                            Activity activity = activityLeaf.get(j);
                            str = "INSERT INTO ROOT.ATTIVITA_LEAF (IDLEAF, IDACTIVITY) VALUES (" + primarykeyLeaf + "," + activity.getIdActivity() + ")";
                            stActivityLeaf  =  conn.createStatement();
                            int ris = stActivityLeaf.executeUpdate(str);
                        }
                    }
                }
            }
            keys.close();
            st.close();
            stItinerario.close();
            stAttivita.close();
            statementLeaf.close();
            stOptionValue.close();
            stActivityLeaf.close();
            conn.close();
         }catch(Exception e){}

     }

    public Map<String, StayTemplate> searchMyItinerary(String utente) throws SQLException {
        Connection conn = DriverManager.getConnection(url,user,pwd); 
        Statement stItinerary = conn.createStatement();
        Statement stLeaf = conn.createStatement();
        Statement stStay = conn.createStatement();
        Statement stOption = conn.createStatement();
        Statement stValue = conn.createStatement();
        Statement stActivity = conn.createStatement();
        Statement stActivityST = conn.createStatement();
        
        System.out.println("SELECT Distinct(I.idItinerario), I.* FROM ITINERARIO as I where I.creatorUser LIKE '"+utente+"'");
        
        ResultSet rsItinerary=stItinerary.executeQuery("SELECT Distinct(I.idItinerario), I.* FROM ITINERARIO as I where I.creatorUser LIKE '"+utente+"'");
 
        Map<String,StayTemplate> result = new HashMap<String,StayTemplate>();
        StayTemplate it = null;
        StayTemplate stayT = null;
        StayTemplate stc = null;
        ResultSet rsLeaf=null;
        ResultSet rsStay=null;
        ResultSet rsOption=null;
        ResultSet rsValue=null;
        ResultSet rsActivityL=null;
        ResultSet rsActivityST=null;
        while(rsItinerary.next()){   
            int idItinerario= Integer.parseInt(rsItinerary.getString("idItinerario"));
            it = new Itinerary(idItinerario,rsItinerary.getString("itName"),rsItinerary.getString("itDesc"),rsItinerary.getString("itCategory"),rsItinerary.getString("stato"));
            result.put(rsItinerary.getString("idItinerario"), it);
            
            rsStay=stStay.executeQuery("SELECT ST.* FROM STAYTEMPLATE as ST where ST.idItinerario="+idItinerario);
            while(rsStay.next()){
                int idStayTempl= Integer.parseInt(rsStay.getString("idStayTempl"));
                
                stc = new StayTemplateComposite(rsStay.getString("nome"), rsStay.getString("startLoc"), rsStay.getString("endLoc"));
                String query="Select * from ATTIVITA_ST as AST, ATTIVITA as A where A.idAttivita=AST.idActivity AND AST.idStayTempl="+idStayTempl;
                rsActivityST=stActivityST.executeQuery(query);
                while(rsActivityST.next()){
                    stc.getActivity().add(new Activity(rsActivityST.getString("tipo"),rsActivityST.getString("citta"),rsActivityST.getString("descrizione"),Integer.parseInt(rsActivityST.getString("durata")),rsActivityST.getInt("idActivity"),rsActivityST.getString("obbligatoria")));
                }
                rsActivityST.close();
                rsLeaf = stLeaf.executeQuery("SELECT L.* from Leaf as L where L.idStayTempl="+idStayTempl);
                while(rsLeaf.next()){  
                    int idLeaf = Integer.parseInt(rsLeaf.getString("idLeaf"));
                    if(rsLeaf.getString("typeLeaf").equals("Transport")){
                        stayT = new Transport(rsLeaf.getString("veicolo"),rsLeaf.getString("StartLoc"),rsLeaf.getString("startEnd"),Integer.parseInt(rsLeaf.getString("duarata")));
                    }
                    if(rsLeaf.getString("typeLeaf").equals("HMS")){
                        stayT = new HandMadeStay(rsLeaf.getString("startLoc"),rsLeaf.getString("startEnd"),Integer.parseInt(rsLeaf.getString("duarata")));
                    }
                    if(rsLeaf.getString("typeLeaf").equals("Accomodation")){
                        stayT = new Accomodation(rsLeaf.getString("tipo"),rsLeaf.getString("description"),rsLeaf.getString("category"),rsLeaf.getString("startLoc"),rsLeaf.getString("startEnd"),Integer.parseInt(rsLeaf.getString("duarata")));
                    }

                    rsOption = stOption.executeQuery("Select O.*, OV.valore  from OptionValue as OV, OPT as O where O.idOption=OV.idOption and OV.idLeaf="+idLeaf);

                    while(rsOption.next()){
                        OptionValue ov = new OptionValue(rsOption.getString("valore"),rsOption.getString("descrizione"),rsOption.getString("nome"),Integer.parseInt(rsOption.getString("idOption")));        
                        rsValue = stValue.executeQuery("Select O.*  from OPTVALUE as O where O.idOption="+Integer.parseInt(rsOption.getString("idOption"))); 
                        while(rsValue.next()){
                            ov.add(rsValue.getString("valore"));  
                        }
                        rsValue.close();
                        stayT.addOption(ov);
                    }
                    rsOption.close();
                    String queryActivityLeaf = "Select * from ATTIVITA_LEAF , ATTIVITA  where ATTIVITA.idAttivita=ATTIVITA_LEAF.idActivity AND ATTIVITA_LEAF.idleaf="+idLeaf;
                    rsActivityL = stActivity.executeQuery(queryActivityLeaf);
                    while(rsActivityL.next()){
                        Activity act = new Activity(rsActivityL.getString("TIPO"),rsActivityL.getString("CITTA"),rsActivityL.getString("DESCRIZIONE"),rsActivityL.getInt("DURATA"),rsActivityL.getInt("IDATTIVITA"),"SI");
                        stayT.addActivity(act);
                    }
                    rsActivityL.close();
                    
                    stc.add(stayT);  
                }
                rsLeaf.close();
                result.get(rsItinerary.getString("idItinerario")).add(stc);
            }
            rsStay.close();
        }
        stLeaf.close();
        stStay.close();
        stOption.close();
        stValue.close();
        stActivity.close();
        stActivityST.close();
        stItinerary.close();
        rsItinerary.close();
        conn.close();
        return result;
    }
    
    
    
    
}
