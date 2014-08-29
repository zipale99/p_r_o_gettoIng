<%-- 
    Document   : searchStayTransfert
    Created on : 25-giu-2012, 12.38.18
    Author     : Andrea
--%>

<%@page import="Controller.SearchController"%>
<%@page import="Composite.StayTemplate"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tappa di trasferimento</title>
    </head>
    <body>
        <a href="<%request.getContextPath();%>ControllerMVC?option=Logout">Logout</a>
        <h1>Tappa di trasferimento</h1>

        <form method="post" action="<%request.getContextPath();%>ControllerMVC">
            <table>
                <tr>
                    <td>Inserisci il nome della localita' di partenza e di arrivo separato dal carattere -</td>
                    <td><input type="text" name="optList" value=""></td>
                </tr>
                <tr>
                    <td colspan="2" align="right"><input type="submit" value="ricerca">
                    <input type="reset" value="cancella"></td>
                </tr>
            </table>
            <input type="hidden" name="option" value="effettuaRicercaTrasferimento">
        </form>
        <h3> Risultati ricerca </h3>
        <%
        SearchController sc = (SearchController) session.getAttribute("searchController");
        ArrayList<StayTemplate> resultSet = sc.getResultSet();

        %>
        <%
        if ((resultSet == null)||(resultSet.size()==0)) out.println("Not result found\n");
        else{
                int length = resultSet.size();
                for(int i=0;i<length;i++){
                    out.println(((StayTemplate)resultSet.get(i)).getInformazioni()+"<br>");
            }
        }
        %>
        <br><br>
         <a href="<%request.getContextPath();%>ControllerMVC?option=fineRicerca">Fine ricerca</a>
    </body>
</html>

