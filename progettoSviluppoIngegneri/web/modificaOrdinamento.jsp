<%-- 
    Document   : modificaOrdinamento
    Created on : 1-lug-2012, 12.23.53
    Author     : Andrea
--%>

<%@page import="Composite.StayTemplate"%>
<%@page import="Decorator.UserDecorator"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
   UserDecorator userDec = (UserDecorator)session.getAttribute("userDec");
   StayTemplate stay = userDec.getStayOBJ();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modifica Ordinamento</title>
    </head>
    <body>
        <a href="<%request.getContextPath();%>ControllerMVC?option=Logout">Logout</a>
        <h1>Modifica Ordinamento</h1>
        <table>
            <th>START LOC</th><th>END LOC</th><th>DURATA</th><th>ATTIVITA TAPPA</th>
            <tr><td><%=stay.getStartLoc()%></td><td><%=stay.getEndLoc()%></td><td><%=stay.getDurata()%></td><td><%=stay.getActivity().toString() %></td></tr>
        </table>
        <form method="post" action="<%request.getContextPath();%>ControllerMVC">
            <p>Inserisci ordinamento:</p>
            <input type="text" name="ordinamento" value="<%=userDec.getItinerary().getSTComposite().getTree().indexOf(stay) %>">
            <input type="hidden" name="option" value="aggiornaOrdinamento">
            <input type="submit" value="Aggiorna Ordinamento">
        </form>
    </body>
</html>
