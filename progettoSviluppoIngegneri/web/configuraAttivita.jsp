<%-- 
    Document   : configuraAttivita
    Created on : 20-giu-2012, 10.24.05
    Author     : Andrea
--%>

<%@page import="java.util.*"%>
<%@page import="Decorator.UserDecorator"%>
<%@page import="AgenziaCore.Activity"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Composite.*"%>
<%@page import="Controller.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
   UserDecorator userDec = (UserDecorator)session.getAttribute("userDec");
   List<Activity> activity = userDec.getActivity();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Configura Attivita'</title>
    </head>
    <body>
        <a href="<%request.getContextPath();%>ControllerMVC?option=Logout">Logout</a>
        <h1>Configura Attivita'</h1>
        <a href="<%request.getContextPath();%>ControllerMVC?option=Termina">Termina</a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="<%request.getContextPath();%>ControllerMVC?option=RicercaAttivita&pag=/configuraAttivita.jsp">Ricerca Attività</a>&nbsp;&nbsp;&nbsp;&nbsp;
        <h2>Attività da Aggiungere:</h2>
        <form method="POST" action="<%request.getContextPath();%>ControllerMVC">
        <% 
        if(activity == null){
            out.println("No Activity to add");
        }
        else{
            
            out.println("<table>");
            for(int i=0;i<activity.size();i++){
                out.println("<tr><td><input type=\"checkbox\" name=\"attivita\" value=\""+i+"\"></td>"+activity.get(i).toStringHTML()+"</tr>");
            }
        }
        %>
         <tr>
            <td colspan="2" align="right">
                <input type="submit" value="Aggiungi Attivita">
                <input type="reset" value="cancella">
            </td>
        </tr>
        </table>
        <input type="hidden" name="option" value="AggiungiAttivita">
        </form>
        
        <h2>My Activity</h2>
        <% String error = (String) session.getAttribute("error");
        if (error != null) { %>
            Errore: <%= error %><br/>
            
        <% } %>
        <% session.removeAttribute("error"); %>
        <form>
            <table>
        <%
            StayTemplate stay = userDec.getStayOBJ();
            List<Activity> myActivity = stay.getActivity();
            out.println("<table>");
            for(int i=0;i<myActivity.size();i++){
                out.println("<tr><td><input type=\"checkbox\" name=\"attivita\" value=\""+i+"\"></td>"+myActivity.get(i).toStringHTML()+"</tr>");
            }
        %>
        
        <tr>
            <td colspan="2" align="right">
                <input type="submit" value="Elimina Attivita">
                <input type="reset" value="cancella">
            </td>
        </tr>
        </table>
        <input type="hidden" name="option" value="EliminaAttivita">
        </form>
    </body>
</html>
