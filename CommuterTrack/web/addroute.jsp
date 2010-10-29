<%--
    Document   : addroute
    Created on : 
    Author     : jdm
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Enumeration"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="CommuterTrack.CtUser"%>
<%@page import="CommuterTrack.CtRoute"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CommuterTrack: View All Routes</title>
    </head>

    <body>
        <%@ include file="functionbanner.jsp" %>
        <h1>View All Routes</h1>



    <p>
    <TABLE>
        <tr><td>Route Description</td><td>Start</td><td>End</td><td>Owner</td></tr>

        <%

               List myctroutehash = (List)session.getAttribute("ctRoutes");

               CtRoute indexRoute=new CtRoute();
                Iterator i = myctroutehash.iterator();

                while(i.hasNext()){
                 indexRoute = (CtRoute)i.next();
                 System.out.println(indexRoute.getDescription());


                //iterate through HashMap keys
               // while (e.hasMoreElements()) {
    %>
                    <TR>
                       <td><%= indexRoute.getDescription() %></td>
                        <td><%= indexRoute.getRouteStart() %></td>
                        <td><%= indexRoute.getRouteEnd() %></td>
                        <td><%= indexRoute.getCtUser().getUsername() %></td>

                    </TR>
                    <%
                }




    %></TABLE>

    <p><p>
        <h3>Add a Route</h3>
        <form action="CTRouteController" method="post">
        Route Description:<input name="description" type="text"><br>
        Start Location:<input name="start" type="text"><br>
        End Location:<input name="end" type="text"><br>
        <input type="submit" name="AddRoute">
        </form>
</body>

</html>
