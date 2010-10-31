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
        <link href="css/commuter.css" rel="stylesheet" type="text/css" media="screen" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CommuterTrack: View All Routes</title>
    </head>

    <body>
        <div id="header"></div>
        <%@ include file="functionbanner.jsp" %>
        <h1>View All Routes</h1>

    <TABLE align="center">
        <tr><th>Route Description</th><th>Start</th><th>End</th><th>Owner</th></tr>

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

        <h3>Add a Route</h3>
        <fieldset>
        <legend>Add new route</legend>
        <form action="CTRouteController" method="post">
            <label for="description">Route Description:</label><input id="description" name="description" type="text"><br>
        <label for="start">Start Location:</label><input id="start" name="start" type="text"><br>
        <label for="end">End Location:</label><input id="end" name="end" type="text"><br>
        <input type="submit" name="AddRoute">
        </form>
        </fieldset>
</body>

</html>
