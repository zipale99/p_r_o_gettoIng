<%-- 
    Document   : searchIJ
    Created on : 9-giu-2012, 16.10.41
    Author     : Andrea
--%>


<%@page import="Controller.SearchController"%>
<%@page import="Composite.*"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
    SearchController sc = (SearchController) session.getAttribute("searchController");
    Map<String,StayTemplate> ij = sc.getSearchIJ();
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search IJ</title>
    </head>
    <body>
        <a href="<%request.getContextPath();%>ControllerMVC?option=Logout">Logout</a>
        <a href="<%request.getContextPath();%>ControllerMVC?option=Home">Home</a>
        <h2>Search Itinerary or Journay</h2>
        <form method="get" action="<%request.getContextPath();%>ControllerMVC">
            <table>
                <tr>
                    <td>Type</td>
                    <td>Itineray<input type="radio" name="type" value="Itinerary"  checked=""></td>
                    <td>Journay<input type="radio" name="type" value="Journay"></td>
                </tr>
                <tr>
                    <td>Stay</td>
                    <td><input type="text" name="stay" value=""></td>
                </tr>
                <tr>
                    <td>Attivita</td>
                    <td><input type="text" name="activity" value=""></td>
                </tr>
                <tr>
                    <td>Costo</td>
                    <td><input type="text" name="cost" value=""></td>
                </tr>
                <tr>
                    <td>Durata</td>
                    <td><input type="text" name="duration" value=""></td>
                </tr>
                <tr>
                    <td>Data Inizio</td>
                    <td><input type="text" name="dateStart" value=""></td>
                </tr>
                <tr>
                    <td>Data Fine</td>
                    <td><input type="text" name="dateEnd" value=""></td>
                </tr>
                
                <tr>
                    <td colspan="2" align="right"><input type="submit" value="CERCA">
                    <input type="reset" value="cancella"></td>
                    <input type="hidden" name="option" value="SearchIJ">
                </tr>
            </table> 
            <% String error = (String) session.getAttribute("error");
                if (error != null) { %>
                    Errore: <%= error %>
            <% } %>
            <% session.removeAttribute("error"); %>
        </form>   
        <h3>Risultati Ricerca</h3>
        
        <% 
        if (ij==null || ij.isEmpty()) out.println("Not result found.");
        else {%>
            <form method="get" action="<%request.getContextPath();%>ControllerMVC">
            <table>
                <th></th>
                <th>Nome Itinerario</th>
                <th>Descrizione</th>
                <th>Categoria</th>
                <th>Durata</th>
                <%
            Set<String> list  = ij.keySet();
            Iterator<String> iter = list.iterator();		
            while(iter.hasNext()) {
                String key = (String)iter.next();
                StayTemplate value = (StayTemplate) ij.get(key); %> 
                <tr>
                    <td><input type="checkbox" value="<%=(key.toString().trim())%>" name="id"></td>
                    <%= value.toStringHTML()%>
                </tr>
           <% } %>
           </table>
            <input type="hidden" name="option" value="selectIJ">
            <input type="submit" value="Seleziona">
            </form><%  
        }
        %>
       
         
        
        
    </body>
</html>
