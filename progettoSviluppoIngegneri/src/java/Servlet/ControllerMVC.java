package Servlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Controller.ProxyUser;
import Composite.*;
import Controller.Decora;
import Controller.SearchController;
import AgenziaCore.*;
import Decorator.UserDecorator;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Andrea
 */
@WebServlet(name = "ControllerMVC", urlPatterns = {"/ControllerMVC"})
public class ControllerMVC extends HttpServlet {
    
    @Override
    public void init(ServletConfig conf) throws ServletException{
        super.init(conf);
        ServletContext ctx = conf.getServletContext();
        String user = ctx.getInitParameter("DBuser");
        String url = ctx.getInitParameter("DBurl");
        String pwd= ctx.getInitParameter("DBpwd");
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
        } catch (SQLException ex) {
            System.out.println("Si è verificato un errore nell'accesso al DB "+ex.getMessage());
        }
       
    }
    
   // ProxyUser proxy = new ProxyUser();
    
    
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        

        HttpSession session = request.getSession();
        ProxyUser proxy = null;
        SearchController searchController = null;
        UserDecorator userDec = null;
        RequestDispatcher rd; 

        String option = request.getParameter("option");
        System.out.println("option: "+option);
       
        if(option == null){
            System.out.println("Proxy gia creato: "+option);
            if(session.isNew()){
                System.out.println("creoProxy: "+option);
                proxy = new ProxyUser();
                session.setAttribute("proxy",proxy);
                searchController = new SearchController(proxy);
                session.setAttribute("searchController",searchController);
                userDec = null;
                session.setAttribute("userDec",userDec);
            }
            else{
                proxy = (ProxyUser)session.getAttribute("proxy");
            }
        rd = getServletContext().getRequestDispatcher("/login.jsp");
        rd.forward(request,response);
        }
        else{
            proxy = (ProxyUser)session.getAttribute("proxy");
            searchController = (SearchController)session.getAttribute("searchController");
            userDec = (UserDecorator)session.getAttribute("userDec");
        }
         
        if(option.equals("login")){
            //proxy.setUser(request.getParameter("userid"));
            try {
                if(proxy.login(request.getParameter("userid"), request.getParameter("pwd"))){
                    //session.setAttribute("utente",proxy.getGenericUser());
                    searchController = (SearchController)session.getAttribute("searchController");
                    searchController.setUser(proxy.getGenericUser());
                    
                    rd = getServletContext().getRequestDispatcher("/home.jsp");
                    rd.forward(request,response);   
                }
                else{
                    session.setAttribute("error","User e/o password errate.");
                    rd = getServletContext().getRequestDispatcher("/login.jsp");
                    rd.forward(request,response);
                }
            } catch (SQLException ex) {
                session.setAttribute("error","Errore di connessione.");
                rd = getServletContext().getRequestDispatcher("/login.jsp");
                rd.forward(request,response);
            }
        }
        if(option.equals("Home")){
            rd = getServletContext().getRequestDispatcher("/home.jsp");
            rd.forward(request,response);  
        }
        if(option.equals("Logout")){
            session.invalidate();
            session = request.getSession();
            if(session.isNew()){
                proxy = new ProxyUser();
                session.setAttribute("proxy",proxy);
                searchController = new SearchController(proxy);
                session.setAttribute("searchController",searchController);
                userDec = null;
                session.setAttribute("userDec",userDec);
            }
            rd = getServletContext().getRequestDispatcher("/login.jsp"); 
            rd.forward(request,response);
        }
        if(option.equals("SearchItineraryOrJournay")){
            rd = getServletContext().getRequestDispatcher("/searchIJ.jsp"); 
            rd.forward(request,response);  
        }
        if(option.equals("SearchIJ")){
            try{
                searchController.searchIJ(request.getParameter("type"),request.getParameter("stay"),request.getParameter("activity"),request.getParameter("cost"),request.getParameter("duration"),request.getParameter("dateStart"),request.getParameter("dateEnd"));
            }catch(Exception err){session.setAttribute("error",err.toString());}
            rd = getServletContext().getRequestDispatcher("/searchIJ.jsp"); 
            rd.forward(request,response);
            
        }
        if(option.equals("ManageItinerary")){
            rd = getServletContext().getRequestDispatcher("/manageItinerary.jsp");
            rd.forward(request,response);  
        }     
        if(option.equals("CreaItinerario")){
            if (userDec== null)
                userDec=Decora.getInstance().decoration(proxy.getGenericUser());
            userDec.createItinerary();
            session.setAttribute("userDec",userDec);
            rd = getServletContext().getRequestDispatcher("/basicInfo.jsp");
            rd.forward(request,response);
            
        }
        if(option.equals("basicInfo")){
            if (userDec== null)
                userDec=Decora.getInstance().decoration(proxy.getGenericUser());
            userDec.provideBasicInfo(request.getParameter("name"),request.getParameter("desc"), request.getParameter("cat"));
            rd = getServletContext().getRequestDispatcher("/createItinerary.jsp");
            rd.forward(request,response);
        }
        if(option.equals("selectIJ")){
            out.println(searchController.view(request.getParameter("id")));
        }
        if(option.equals("myIt")){
            out.println(proxy.view(request.getParameter("id")));
        }
        if(option.equals("effettuaRicerca")){
            option = request.getParameter("optList");
            ArrayList resultSet = searchController.cerca(option,option);
            rd = getServletContext().getRequestDispatcher("/searchStay.jsp");
            rd.forward(request,response);
        }
        if(option.equals("fineRicerca")){
            rd = getServletContext().getRequestDispatcher("/selezionaTappa.jsp");
            rd.forward(request,response);
        }
        if(option.equals("tappaSelezionata")){
            
            Enumeration<String> en = request.getParameterNames();
            while(en.hasMoreElements()){
                String el = en.nextElement();
                if(!el.equals("option")){
                    String valore = request.getParameter(el);
                    String val[] = el.split("-");
                    if (userDec== null)
                        userDec=Decora.getInstance().decoration(proxy.getGenericUser());
                    userDec.configureStayParameter(val[0],val[1],valore);
                }
            }
            /*try{
                if (userDec== null)
                    userDec=Decora.getInstance().decoration(proxy.getGenericUser());
                List<Activity> act = searchController.searchActivity(userDec.getLocality());
                userDec.setActivity(act);
            }catch(Exception err){}*/
            rd = getServletContext().getRequestDispatcher("/configuraAttivita.jsp");
            rd.forward(request,response);
        }
        if(option.equals("configuraTappa")){
            if (userDec== null)
                userDec=Decora.getInstance().decoration(proxy.getGenericUser());
            StayTemplate stayOBJ = userDec.selectStay(searchController.get(Integer.parseInt(request.getParameter("tappa"))));
            rd = getServletContext().getRequestDispatcher("/configuraTappa.jsp");
            rd.forward(request,response);
        }
        if(option.equals("inserisciTappa")){
            if (userDec== null)
                userDec=Decora.getInstance().decoration(proxy.getGenericUser());
            StayTemplate stayOBJ = userDec.selectStay(searchController.get(Integer.parseInt(request.getParameter("id"))));
            userDec.addStay();
            rd = getServletContext().getRequestDispatcher("/createItinerary.jsp");
            rd.forward(request,response);
        }
        if(option.equals("SaveItinerary")){
            if (userDec== null)
                userDec=Decora.getInstance().decoration(proxy.getGenericUser());
            try {
                userDec.saveItinerary();
            } catch (SQLException ex) {
                Logger.getLogger(ControllerMVC.class.getName()).log(Level.SEVERE, null, ex);
            }
            //userDec = Decora.getInstance().deleteDecoration(userDec);
            session.removeAttribute("userDec");
            rd = getServletContext().getRequestDispatcher("/manageItinerary.jsp");
            rd.forward(request,response);
        }
        if(option.equals("AggiungiAttivita")){
            Enumeration<String> en = request.getParameterNames();
            while(en.hasMoreElements()){
                String el = en.nextElement();
                if(!el.equals("option")){
                    try {
                        int i = Integer.parseInt(request.getParameter(el));
                        if (userDec== null)
                            userDec=Decora.getInstance().decoration(proxy.getGenericUser());
                        userDec.addActivity(i);
                    } catch (Exception ex) {
                        session.setAttribute("error", ex.toString());
                    }
                }
            }
            rd = getServletContext().getRequestDispatcher("/configuraAttivita.jsp");
            rd.forward(request,response);
        }
        if(option.equals("EliminaAttivita")){
            Enumeration<String> en = request.getParameterNames();
            while(en.hasMoreElements()){
                String el = en.nextElement();
                if(!el.equals("option")){
                    try {
                        int i = Integer.parseInt(request.getParameter(el));
                        if (userDec== null)
                            userDec=Decora.getInstance().decoration(proxy.getGenericUser());
                        userDec.removeActivity(i);
                    } catch (Exception ex) {
                        session.setAttribute("error", ex.toString());
                    }
                }
            }
            rd = getServletContext().getRequestDispatcher("/configuraAttivita.jsp");
            rd.forward(request,response);
            
            
        }
        if(option.equals("Termina")){
            if (userDec== null)
                userDec=Decora.getInstance().decoration(proxy.getGenericUser());
            userDec.addStay();
            rd = getServletContext().getRequestDispatcher("/createItinerary.jsp");
            rd.forward(request,response);
            
        }
        
        if(option.equals("ModificaItinerario")){
            if (userDec== null)
                userDec=Decora.getInstance().decoration(proxy.getGenericUser());
            session.setAttribute("userDec",userDec);
            userDec.setItinerary(request.getParameter("id"));
            rd = getServletContext().getRequestDispatcher("/createItinerary.jsp");
            rd.forward(request,response);
              
        }
        
        if(option.equals("EliminaItinerario")){
            
            if (userDec== null)
                userDec=Decora.getInstance().decoration(proxy.getGenericUser());
            session.setAttribute("userDec",userDec);
            userDec.deleteItinerary(request.getParameter("id"));
            rd = getServletContext().getRequestDispatcher("/manageItinerary.jsp");
            rd.forward(request,response);
            
        }
        
        if(option.equals("ModificaTappa")){
            if (userDec== null)
                userDec=Decora.getInstance().decoration(proxy.getGenericUser());
            userDec.setStayOBJ(Integer.parseInt(request.getParameter("id")));
            rd = getServletContext().getRequestDispatcher("/modificaTappa.jsp");
            rd.forward(request,response);     
        }
        
        if(option.equals("EliminaTappa")){
            if (userDec== null)
                userDec=Decora.getInstance().decoration(proxy.getGenericUser());
            userDec.deleteStay(Integer.parseInt(request.getParameter("id")));
            rd = getServletContext().getRequestDispatcher("/createItinerary.jsp");
            rd.forward(request,response);
        }
        
        if(option.equals("ModificaConfigurazioneTappa")){
            Enumeration<String> en = request.getParameterNames();
            while(en.hasMoreElements()){
                String el = en.nextElement();
                System.out.println(el.toString());
                if(!el.equals("option")){
                    String valore = request.getParameter(el);
                    String val[] = el.split("-");
                    if (userDec== null)
                        userDec=Decora.getInstance().decoration(proxy.getGenericUser());
                    userDec.configureStayParameter(val[0],val[1],valore);
                }
            }
            userDec.setStayOBJNull();
            rd = getServletContext().getRequestDispatcher("/createItinerary.jsp");
            rd.forward(request,response);     
        }
        
        if(option.equals("ModificaAttivita")){
            if (userDec== null)
                userDec=Decora.getInstance().decoration(proxy.getGenericUser());
            userDec.setStayOBJ(Integer.parseInt(request.getParameter("id")));
            /*try{
                List<Activity> act = searchController.searchActivity(userDec.getLocality());
                userDec.setActivity(act);
            }catch(Exception err){}*/
            rd = getServletContext().getRequestDispatcher("/modificaAttivita.jsp");
            rd.forward(request,response);
        }
        if(option.equals("ModificaOrdinamento")){
            if (userDec== null)
                userDec=Decora.getInstance().decoration(proxy.getGenericUser());
            userDec.setStayOBJ(Integer.parseInt(request.getParameter("id")));
            rd = getServletContext().getRequestDispatcher("/modificaOrdinamento.jsp");
            rd.forward(request,response);
        }
        
        if(option.equals("ModificaConfigurazioneAttivita")){
            if (userDec== null)
                userDec=Decora.getInstance().decoration(proxy.getGenericUser());
            userDec.setStayOBJNull();
            rd = getServletContext().getRequestDispatcher("/createItinerary.jsp");
            rd.forward(request,response);     
        }
        
        if(option.equals("AggiungiModificaAttivita")){
            Enumeration<String> en = request.getParameterNames();
            while(en.hasMoreElements()){
                String el = en.nextElement();
                if(!el.equals("option")){
                    try {
                        int i = Integer.parseInt(request.getParameter(el));
                        if (userDec== null)
                            userDec=Decora.getInstance().decoration(proxy.getGenericUser());
                        userDec.addActivity(i);
                    } catch (Exception ex) {
                        session.setAttribute("error", ex.toString());
                    }
                }
            }
            rd = getServletContext().getRequestDispatcher("/modificaAttivita.jsp");
            rd.forward(request,response);
        }
        
        if(option.equals("EliminaModificaAttivita")){
            Enumeration<String> en = request.getParameterNames();
            while(en.hasMoreElements()){
                String el = en.nextElement();
                if(!el.equals("option")){
                    try {
                        int i = Integer.parseInt(request.getParameter(el));
                        if (userDec== null)
                            userDec=Decora.getInstance().decoration(proxy.getGenericUser());
                        userDec.removeActivity(i);
                    } catch (Exception ex) {
                        session.setAttribute("error", ex.toString());
                    }
                }
            }
            rd = getServletContext().getRequestDispatcher("/modificaAttivita.jsp");
            rd.forward(request,response);
        }
        
        if(option.equals("ModificaInfo")){
            rd = getServletContext().getRequestDispatcher("/basicInfo.jsp");
            rd.forward(request,response);
        }
        
        if(option.equals("effettuaRicercaTrasferimento")){
            option = request.getParameter("optList");
            if (option != null){
                String[] localita = option.split("-");
                ArrayList resultSet = searchController.cercaTrasferimento(localita[0].trim(),localita[1].trim());
            }
            rd = getServletContext().getRequestDispatcher("/searchStayTransfert.jsp");
            rd.forward(request,response);
        }
        if(option.equals("aggiornaOrdinamento")){
            if (userDec== null)
                userDec=Decora.getInstance().decoration(proxy.getGenericUser());
            userDec.ordina(Integer.parseInt(request.getParameter("ordinamento")));
            userDec.setStayOBJNull();
            rd = getServletContext().getRequestDispatcher("/createItinerary.jsp");
            rd.forward(request,response);
        }   
        
        if(option.equals("RicercaAttivita")){
            if (userDec== null)
                userDec=Decora.getInstance().decoration(proxy.getGenericUser());
            try{
                List<Activity> act = searchController.searchActivity(userDec.getLocality());
                userDec.setActivity(act);
            }catch(Exception err){}
            rd = getServletContext().getRequestDispatcher(request.getParameter("pag"));
            rd.forward(request,response);
        }    
    }    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
}

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
