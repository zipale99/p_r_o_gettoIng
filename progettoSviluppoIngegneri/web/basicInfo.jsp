<%-- 
    Document   : basicInfo
    Created on : 12-giu-2012, 10.29.30
    Author     : Andrea
--%>

<%@page import="Composite.StayTemplate"%>
<%@page import="Decorator.UserDecorator"%>
<%@page import="Composite.Itinerary"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
   UserDecorator userDec = (UserDecorator)session.getAttribute("userDec");

   StayTemplate it = userDec.getItinerary();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Basic Info</title>
    </head>
    <body>
        <a href="<%request.getContextPath();%>ControllerMVC?option=Logout">Logout</a>
        <h2>Insert Basic Info for Itinerary</h2>
        <form method="get" action="<%request.getContextPath();%>ControllerMVC">
            <table>
                <tr>
                    <td>Name</td>
                    <td><input type="text" name="name" value="<%= ((Itinerary)it).getItName() %>"></td>
                </tr>
                <tr>
                    <td>Descrizione</td>
                    <td><input type="text" name="desc" value="<%= ((Itinerary)it).getItDesc() %>"></td>
                </tr>
                <tr>
                    <td>Category</td>
                    <td><input type="text" name="cat" value="<%= ((Itinerary)it).getItCategory() %>"></td>
                </tr>
                <tr>
                    <td colspan="2" align="right"><input type="submit" value="entra">
                    <input type="reset" value="cancella"></td>
                </tr>
            </table>
            <input type="hidden" name="option" value="basicInfo">
        </form>
    </body>
</html>
