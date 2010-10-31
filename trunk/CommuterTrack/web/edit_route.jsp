<%--
    Document   : edit route
    Created on : 
    Author     : Travel Timers
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Enumeration"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="CommuterTrack.CtRoute"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <link href="css/commuter.css" rel="stylesheet" type="text/css" media="screen" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CommuterTrack: Edit Route</title>
    </head>

    <body>
        <div id="header"></div>
        <%@ include file="functionbanner.jsp" %>
        <%
           CtRoute route = (CtRoute) session.getAttribute("editRoute");
        %>
        <h3>Edit a Route</h3>
        <fieldset>
        <legend>Edit route</legend>
        <form action="CTRouteController" method="post">
            <input type="hidden" name="method" value="editRoute">
            <input type="hidden" name="routeId" value="<%= route.getRouteId() %>">
            <label for="description">Route Description:</label><input id="description" name="description" type="text" value="<%= route.getDescription() %>"><br>
            <label for="start">Start Location:</label><input id="start" name="start" type="text" value="<%= route.getRouteStart() %>"><br>
            <label for="end">End Location:</label><input id="end" name="end" type="text" value="<%= route.getRouteEnd() %>"><br>
        <input type="submit" name="Edit Route">
        </form>
        </fieldset>
</body>

</html>