<%-- 
    Document   : manageItinerary
    Created on : 8-giu-2012, 15.22.05
    Author     : Andrea
--%>

<%@page import="Controller.ProxyUser"%>
<%@page import="Composite.StayTemplate"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
    ProxyUser proxy = (ProxyUser) session.getAttribute("proxy");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Itinerary</title>
        <script>
        function findCheck(path,opt){
            tag = document.getElementsByTagName("input");
            cid= new Array();
            c=0;
            for(i=0;i<tag.length;i++){
            if(tag[i].type=="checkbox" && tag[i].checked){
                cid[c]= tag[i].value;
                break;
            }
            }
            if (cid.length==0){
                window.alert("Seleziona un evento");
            }	      
            else{
                location.href = path+"/ControllerMVC?option="+opt+"Itinerario&id="+cid[0];
            }

        }

        function confermi(path,opt){
            tag = document.getElementsByTagName("input");
            cid= new Array();
            c=0;
            for(i=0;i<tag.length;i++){
            if(tag[i].type=="checkbox" && tag[i].checked){
                cid[c]= tag[i].value;
                break;
            }
            }
            if (cid.length==0){
                window.alert("Seleziona un evento");
            }	      
            else{
                if(confirm("Sei sicuro di voler eliminare l'elemento?"))
                    location.href = path+"/ControllerMVC?option="+opt+"Itinerario&id="+cid[0];
            }

        }
        </script>
    </head>
    <body>
        <a href="<%request.getContextPath();%>ControllerMVC?option=Logout">Logout</a>
        <a href="<%request.getContextPath();%>ControllerMVC?option=Home">Home</a>

        <h2>Manage Itinerary</h2>
        <a href="<%request.getContextPath();%>ControllerMVC?option=CreaItinerario">Crea Itinerario</a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="#" onclick="findCheck('<%= request.getContextPath()%>','Modifica')">Modifica Itinerario</a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="#" onclick="confermi('<%= request.getContextPath()%>','Elimina')">Elimina Itinerario</a>  
    
    
        <h2>My Itinerary:</h2>
        
        <% 
        if (proxy.getMyItinerary() == null) out.println("Not result found.");
        else {%>
            <form method="get" action="<%request.getContextPath();%>ControllerMVC">
                <table>
                    <th></th>
                    <th>Nome Itinerario</th>
                    <th>Descrizione</th>
                    <th>Categoria</th>
                    <th>Durata</th>
                    <%
                    Set<String> list  = proxy.getMyItinerary().keySet();
                    Iterator<String> iter = list.iterator();		
                    while(iter.hasNext()) {
                        String key = (String)iter.next();
                        StayTemplate value = (StayTemplate) proxy.getMyItinerary().get(key); %> 
                        <tr>
                            <td><input type="checkbox" value="<%=(key.toString().trim())%>" name="id"></td>
                            <%= value.toStringHTML()%>
                        </tr>
                    <% } %>
                </table>
                <input type="hidden" name="option" value="myIt">
                <input type="submit" value="Seleziona">
            </form>
        <% } %>
    </body>
</html>
