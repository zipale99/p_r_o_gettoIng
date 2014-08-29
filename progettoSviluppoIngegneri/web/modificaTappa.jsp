<%-- 
    Document   : modificaTappa
    Created on : 23-giu-2012, 14.26.40
    Author     : Andrea
--%>

<%@page import="java.util.List"%>
<%@page import="Composite.StayTemplateComposite"%>
<%@page import="Composite.StayTemplate"%>
<%@page import="Decorator.UserDecorator"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
   UserDecorator userDec = (UserDecorator)session.getAttribute("userDec");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Configura Tappa</title>
    </head>
    <body>
        <a href="<%request.getContextPath();%>ControllerMVC?option=Logout">Logout</a>
        <h1>Configura Tappa:</h1>
        <form method="GET" action="<%request.getContextPath();%>ControllerMVC">
            <table>
                <% StayTemplate stay = userDec.getStayOBJ();
                //out.println(manageController.getStayOBJ().toStringHTMLConfiguration()); 
                List<StayTemplate> st = stay.getSTComposite().getTree();
                for(int i=0;i<st.size();i++){
                    if(!st.get(i).getOption().isEmpty()){
                        out.println(st.get(i).getInformazioni()+"<br/>");
                        for (int k=0;k<st.get(i).getOption().size();k++){
                            out.println(st.get(i).getOption().get(k).getNome()+"<br/>");
                            for(int c=0;c<st.get(i).getOption().get(k).getPossibleValue().size();c++){
                                if(st.get(i).getOption().get(k).getPossibleValue().get(c).equals(st.get(i).getOption().get(k).getValue()))
                                    out.println("<input checked type=\"radio\" name=\""+i+"-"+k+"\" value=\""+st.get(i).getOption().get(k).getPossibleValue().get(c) +"\">"+st.get(i).getOption().get(k).getPossibleValue().get(c)+"<br/>");
                                else
                                    out.println("<input type=\"radio\" name=\""+i+"-"+k+"\" value=\""+st.get(i).getOption().get(k).getPossibleValue().get(c) +"\">"+st.get(i).getOption().get(k).getPossibleValue().get(c)+"<br/>");
                            }
                        }
                    }
                }
                %>
                <tr>
                    <td colspan="2" align="right">
                        <input type="submit" value="Prosegui">
                        <input type="reset" value="cancella">
                    </td>
                </tr>
            </table>
            <input type="hidden" name="option" value="ModificaConfigurazioneTappa">
        </form>
    </body>
</html>

