<%-- 
    Document   : login
    Created on : 5-giu-2012, 11.11.20
    Author     : Andrea
--%>
<%@page import="java.util.Date"%>
<%@page import="Decorator.*"%>
<%@page import="Controller.ProxyUser"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%ProxyUser proxy = (ProxyUser)session.getAttribute("proxy");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body>
        <a href="<%request.getContextPath();%>ControllerMVC?option=Logout">Logout</a>
        <h2>Dati Utente:</h2>
        <p><strong>User:</strong> <%=proxy.getGenericUser().getUser()%><br/><strong>Type:</strong> <%=proxy.getGenericUser().getType()%></p>
        <h2>Menu:</h2>
        <a href="<%request.getContextPath();%>ControllerMVC?option=SearchItineraryOrJournay">SearchItineraryOrJournay</a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="<%request.getContextPath();%>ControllerMVC?option=ManageItinerary">ManageItinerary</a>
        
    </body>
</html>
