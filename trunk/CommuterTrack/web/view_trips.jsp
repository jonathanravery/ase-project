<%-- 
    Document   : view_trips
    Created on : Oct 30, 2010, 10:35:50 PM
    Author     : maria
--%>
<%@page import="CommuterTrack.CtTrip"%>
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
        <title>Trips</title>
    </head>
    <body>
        <div id="header"></div>
        <%@ include file="functionbanner.jsp" %>
        <h1>View all trips</h1>

        <%
    System.out.println("viewing trips");
        if (((CtUser) session.getAttribute("user")).getRole() != 1) {
                        // System.exit(1);
%>
                you are not an admin
    <%
        } else {
    %> you are an admin, you may choose to see all trips in the system
        <form method="POST" action="CTTripController">
            <input type="hidden" name="method" value="viewAllTrips">
            <input type="submit" name="submit" value="View all trips">
        </form>

        <form method="POST" action="CTTripController">
            <input type="hidden" name="method" value="viewUserTrips">
            <input type="submit" name="submit" value="View own trips">
        </form>
    <%
       }
    %>

    <p>
    <TABLE align="center">
        <tr><th>Trip Id</th><th>Route</th><th>Start time</th><th>End time</th><th>Edit</th></tr>

        <%

               List myCtTripHash = (List)session.getAttribute("ctTrips");

               CtTrip curTrip=new CtTrip();
                Iterator i = myCtTripHash.iterator();

                while(i.hasNext()){
                 curTrip = (CtTrip)i.next();
                 System.out.println(curTrip.getTripId().toString());


                //iterate through HashMap keys
               // while (e.hasMoreElements()) {
    %>
                    <TR>
                       <td><%= curTrip.getTripId() %></td>
                        <td><%= curTrip.getCtRoute().getDescription() %></td>
                        <td><%= curTrip.getStartTime() %></td>
                        <td><%= curTrip.getEndTime() %></td>
                        <td>Edit</td>
                    </TR>
                    <%
                }




    %></TABLE>
    </body>
</html>
