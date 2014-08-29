<%-- 
    Document   : selezionaTappa
    Created on : 18-giu-2012, 14.19.37
    Author     : Andrea
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="Controller.*"%>
<%@page import="java.util.*"%>
<%@page import="Composite.*"%>



<%!  public String formattazioneRisultato(ArrayList resultSet){
        String out = "";
        int length = resultSet.size();

        if (length == 0) out = "<h3> Non vi sono tappe disponibili. </h3>";
        else {
            out = "<h3>Le tappe disponibili sono:</h3>";
            out = out + "<form name=\"invoke\" action=\"ControllerMVC?option=configuraTappa\" method=\"POST\">";
            out = out + "<p><select name=\"tappa\">";
            for(int i=0;i<length;i++){
                if(i==0)out = out + "<option value= \"" + i  +"\" selected >" + ((StayTemplate)resultSet.get(i)).getInformazioni()+"</option>";
                else out = out + "<option value= \"" + i+"\" >" +  ((StayTemplate)resultSet.get(i)).getInformazioni() + "</option>";
            }
            out = out + "</select></p><p>";
            out = out + "<input type=\"submit\" name=\"submit\" value=\"    Invio    \">&nbsp&nbsp&nbsp&nbsp";
            out = out + "<input type=\"reset\" value=\"Cancella\"></p>";
            out = out + "</form>";
        }
        return out;
    }
%>



<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tappe disponibili</title>
    </head>
    <body>
        <a href="<%request.getContextPath();%>ControllerMVC?option=Logout">Logout</a>
        <%
        SearchController sc = (SearchController) session.getAttribute("searchController");
        ArrayList<StayTemplate> resultSet = sc.getResultSet(); 
        %>
        <%= formattazioneRisultato(resultSet) %>
    </body>
</html>