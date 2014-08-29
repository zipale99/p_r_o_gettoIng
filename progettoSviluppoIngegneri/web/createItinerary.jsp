<%-- 
    Document   : createItinerary
    Created on : 18-giu-2012, 17.16.55
    Author     : Andrea
--%>

<%@page import="Decorator.UserDecorator"%>
<%@page import="Composite.Itinerary"%>
<%@page import="Composite.StayTemplate"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
   UserDecorator userDec = (UserDecorator)session.getAttribute("userDec");
%>
<html>
    <head>
        <link type="text/css" href="./template.css" rel="stylesheet">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Itinerary</title>
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
                location.href = path+"/ControllerMVC?option="+opt+"&id="+cid[0];
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
                    location.href = path+"/ControllerMVC?option="+opt+"&id="+cid[0];
            }

        }
        </script>
    </head>
    <body>
        <a href="<%request.getContextPath();%>ControllerMVC?option=Logout">Logout</a>
        <a href="<%request.getContextPath();%>ControllerMVC?option=Home">Home</a>
        
        
        
        
        <h1>Create Itinerary</h1>
        <h3>Menu:</h3>
        <a href="<%request.getContextPath();%>ControllerMVC?option=effettuaRicerca">Inserisci Tappa</a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="<%request.getContextPath();%>ControllerMVC?option=effettuaRicercaTrasferimento">Inserisci Tappa Trasferimento</a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="#" onclick="findCheck('<%= request.getContextPath()%>','ModificaTappa')">Modifica Tappa</a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="#" onclick="confermi('<%= request.getContextPath()%>','EliminaTappa')">Elimina Tappa</a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="#" onclick="findCheck('<%= request.getContextPath()%>','ModificaAttivita')">Modifica Attivita'</a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="<%= request.getContextPath()+"/ControllerMVC?option=ModificaInfo"%>">Modifica Info</a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="#" onclick="findCheck('<%= request.getContextPath()%>','ModificaOrdinamento')">Modifica Ordinamento</a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="<%request.getContextPath();%>ControllerMVC?option=SaveItinerary">Salva Itinerario</a>&nbsp;&nbsp;&nbsp;&nbsp;
        
        <%= userDec.getItinerary().toStringHTMLComposite() %>
    </body>
</html>
