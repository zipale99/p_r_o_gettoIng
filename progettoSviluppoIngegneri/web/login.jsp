<%-- 
    Document   : index
    Created on : 30-mag-2012, 10.48.08
    Author     : Andrea
--%>

<%@page import="Controller.ProxyUser"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Applicazione</title>
</head>
<body>
<h2>User's Login</h2>
<p>ID di sessione: <%=session.getId()%><br/>Data di creazione: <%=new Date(session.getCreationTime())%>
    <% ProxyUser a = (ProxyUser) session.getAttribute("proxy");
    if(a!=null){
        out.println("ci sono");
    }
    else 
        out.println("null");
    %>
</p>
<form method="post" action="<%request.getContextPath();%>ControllerMVC">
    <table>
        <tr>
            <td>user id</td>
            <td><input type="text" name="userid" value="andrea"></td>
        </tr>
        <tr>
            <td>password</td>
            <td><input type="password" name="pwd" value="andrea"></td>
        </tr>
        <tr>
            <td colspan="2" align="right"><input type="submit" value="entra">
            <input type="reset" value="cancella"></td>
        </tr>
    </table> 
    <% String error = (String) session.getAttribute("error");
        if (error != null) { %>
            Errore: <%= error %>
    <% } %>
    <% session.removeAttribute("error"); %>
    <input type="hidden" name="option" value="login">
</form>
