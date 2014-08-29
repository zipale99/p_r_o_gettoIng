<%-- 
    Document   : visualizzaTappaSelezionata
    Created on : 18-giu-2012, 14.19.55
    Author     : Andrea
--%>

<%@page import="Composite.StayTemplate"%>
<%@page import="Decorator.UserDecorator"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tappa selezionata</title>
    </head>
    <body>
        <a href="<%request.getContextPath();%>ControllerMVC?option=Logout">Logout</a>
        <%UserDecorator userDec = (UserDecorator)session.getAttribute("userDec");%>
        <%StayTemplate stayOBJ=  userDec.getStayOBJ(); %>
        <%
        out.println("<h2>Confermi di voler inserire la tappa?</h2>" +
        "<form name=\"invoke\" action=\"ControllerMVC?option=inserisciTappa\" method=\"POST\">"+
        "<input type=\"submit\" name=\"submit\" value=\"    Inserisci    \">&nbsp&nbsp&nbsp&nbsp"+
        "<input type=\"reset\" value=\"Cancella\"></p>" +
        "<input type=\"hidden\" name=\"id\" value=\"" + request.getParameter("option")+"\">"+
        "</form>");%>

    </body>
</html>