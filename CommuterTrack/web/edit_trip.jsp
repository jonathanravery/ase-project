<%-- 
    Document   : edit_trip
    Created on : Nov 1, 2010, 6:26:01 PM
    Author     : maria
--%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Enumeration"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="CommuterTrack.CtTrip"%>
<%@page import="CommuterTrack.CtRoute"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <link href="css/commuter.css" rel="stylesheet" type="text/css" media="screen" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CommuterTrack: Edit Trip</title>
    </head>

    <body>
        <div id="header"></div>
        <%@ include file="functionbanner.jsp" %>
        <%
           CtTrip trip = (CtTrip) session.getAttribute("editTrip");
           // get all routes
           List myctroutehash = (List)session.getAttribute("ctRoutes");

           CtRoute curRoute = new CtRoute();
           Iterator i = myctroutehash.iterator();
           SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
           String start = "";
           String end = "";
           if (trip.getStartTime() != null) {
               start = formatter.format(trip.getStartTime());
           }
           if (trip.getEndTime() != null) {
               end = formatter.format(trip.getEndTime());
           }
           
        %>
        <h3>Edit a Trip</h3>
        <fieldset>
        <legend>Edit trip</legend>
        <form action="CTTripController" method="post">
            <input type="hidden" name="method" value="editTrip">
            <input type="hidden" name="tripId" value="<%= trip.getTripId() %>">
            <label for="routeId">Route:</label>
            <select name="routeId">
                <%
                while (i.hasNext()){
                 curRoute = (CtRoute) i.next();
                 %>
                <option value="<%= curRoute.getRouteId() %>"><%= curRoute.getDescription() %></option>
                <% } %>
            </select><br>
            <label for="start">Start Time:</label><input id="start" name="start" type="text" value="<%= start %>"><br>
            <label for="end">End Time:</label><input id="end" name="end" type="text" value="<%= end %>"><br>
        <input type="submit" name="editTrip" value="Edit Trip">
        </form>
        </fieldset>
</body>

</html>

