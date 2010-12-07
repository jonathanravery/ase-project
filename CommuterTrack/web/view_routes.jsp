<%-- 
    Document   : view_routes
    Created on : Oct 30, 2010, 11:54:19 PM
    Author     : maria
--%>

<% if (session.getAttribute("user") == null) {
       response.sendRedirect("index.jsp");
   } else {
%>

<%@page import="CommuterTrack.CtRoute"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <link href="css/commuter.css" rel="stylesheet" type="text/css" media="screen" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Routes</title>
    </head>
    <body>
        <div id="header"></div>
        <%@ include file="functionbanner.jsp" %>
        <h1>View all Routes</h1>
        <% if (session.getAttribute("message") != null) {
            out.print(session.getAttribute("message"));
            session.setAttribute("message", "");
           } %>
        <%
            //System.out.println("viewing routes");
            if (((CtUser) session.getAttribute("user")).getRole() != 1) {
                        // System.exit(1);
%>
               <!-- you are not an admin -->
    <%
        } else {
    %> <!-- you are an admin, you may choose to see all routes in the system -->
        <form method="POST" action="CTRouteController">
            <input type="hidden" name="method" value="viewAllRoutes">
            <input type="submit" name="submit" value="View all routes">
        </form>

        <form method="POST" action="CTRouteController">
            <input type="hidden" name="method" value="viewUserRoutes">
            <input type="submit" name="submit" value="View own routes">
        </form>
    <%
       }
    %>

    <TABLE align="center">
        <tr><th>Route Description</th><th>Start</th><th>End</th><th>Owner</th><th>Edit</th></tr>

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
                        <td>
                            <form method="POST" action="CTRouteController">
                                <input type="hidden" name="routeId" value="<%= indexRoute.getRouteId() %>">
                                <input type="hidden" name="method" value="viewEditRoutePage">
                                <input type="submit" name="submit" value="Edit">
                            </form>
                        </td>
                    </TR>
                <%
                }
                %>
    </TABLE>

    <fieldset>
        <legend>Add new route</legend>
        <form action="CTRouteController" method="post">
            <input type="hidden" name="method" value="addRoute">
            <label for="description">Route Description:</label><input id="description" name="description" type="text"><br>
            <label for="start">Start Location:</label><input id="start" name="start" type="text"><br>
            <label for="end">End Location:</label><input id="end" name="end" type="text"><br>
            <input type="submit" name="Add Route">
        </form>
    </fieldset>



</body>
</html>

<% } %>