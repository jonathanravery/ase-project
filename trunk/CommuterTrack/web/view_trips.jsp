<%-- 
    Document   : view_trips
    Created on : Oct 30, 2010, 10:35:50 PM
    Author     : maria
--%>

<% if (session.getAttribute("user") == null) {
       response.sendRedirect("index.jsp");
   } else {
%>

<%@page import="java.util.Calendar"%>
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
            String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

    System.out.println("viewing trips");
        CtUser userBean = (CtUser) session.getAttribute("user");
        //if (userBean.getRole() != 1) {
                        // System.exit(1);
%>
               <!-- you are not an admin -->
    <%
        //} else {
            if (session.getAttribute("message") != null) {
                out.print(session.getAttribute("message"));
                session.setAttribute("message", "");
          // } %>
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


        <!-- Combo-handled YUI CSS files: -->
<!--<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/combo?2.8.2r1/build/datatable/assets/skins/sam/datatable.css">-->
<link rel="stylesheet" type="text/css" href="css/datatable.css">

<!-- Combo-handled YUI JS files: -->
<!--<script type="text/javascript" src="http://yui.yahooapis.com/combo?2.8.2r1/build/yahoo-dom-event/yahoo-dom-event.js&2.8.2r1/build/dragdrop/dragdrop-min.js&2.8.2r1/build/element/element-min.js&2.8.2r1/build/datasource/datasource-min.js&2.8.2r1/build/datatable/datatable-min.js"></script> -->
<script type="text/javascript" src="js/datatable-min.js"></script>



<div id="basic"></div>


    <TABLE align="center">
        <!--<tr><th>Trip Id</th><th>Route</th><th>Start time</th><th>End time</th><th>Status</th><th>Edit</th></tr>-->

        <%

               List myCtTripHash = (List)session.getAttribute("ctTrips");

               CtTrip curTrip=new CtTrip();
                Iterator i = myCtTripHash.iterator();

                %>
                <script type="text/javascript">  YAHOO.example.Data = {
	    trips: [
            <%

            while(i.hasNext()){
                 curTrip = (CtTrip)i.next();
                 System.out.println(curTrip.getTripId().toString());

                 if (userBean.getRole() == 1 || curTrip.getStatus().intValue() == 2) {
          %>

    {"Description":"<%= curTrip.getCtRoute().getDescription() %>","Start Time":"<%= curTrip.getStartTime() %>","End Time":"<%= curTrip.getEndTime() %>","Start Location":"<%= curTrip.getCtRoute().getRouteStart() %>", "End Location":"<%= curTrip.getCtRoute().getRouteEnd() %>", "Trip Duration":"<% Calendar cal = Calendar.getInstance(); if(curTrip.getEndTime()==null){out.print("  ");}else{cal.setTime(curTrip.getEndTime()); long endTime = cal.getTimeInMillis(); cal.setTime(curTrip.getStartTime()); long duration = endTime - cal.getTimeInMillis(); out.print(duration / 60 / 60 /1000 + " hours, " + (duration / 60000) % 60 + " minutes, " + ((duration / 1000) % 60) + " seconds");} %>", "Day of the Week":"<%= days[curTrip.getStartTime().getDay()] %>", Edit:'<form method="POST" action="CTTripController"><input type="hidden" name="tripId" value="<%= curTrip.getTripId() %>"><input type="hidden" name="method" value="viewEditTripPage"><input type="submit" name="submit" value="Edit">'},

                
                
                    <%
                        
	}}
                %> ]};</script> <div id="basic"></div>
<%


                   
                




    %></TABLE>

    <script type="text/javascript">
    YAHOO.util.Event.addListener(window, "load", function() {
	    YAHOO.example.Basic = function() {
	        var myColumnDefs = [
	            // {key:"id", sortable:true, resizeable:true},
	            {key:"Description", sortable:true, resizeable:true},
                    {key:"Start Time",formatter:YAHOO.widget.DataTable.formatDate, sortable:true, sortOptions:{defaultDir:YAHOO.widget.DataTable.CLASS_DESC},resizeable:true},
                    {key:"End Time",formatter:YAHOO.widget.DataTable.formatDate, sortable:true, sortOptions:{defaultDir:YAHOO.widget.DataTable.CLASS_DESC},resizeable:true},
                    {key:"Start Location",sortable:true, resizable:true},
                    {key:"End Location", sortable:true, resizable:true},
                    {key:"Trip Duration", sortable:true, resizable:true},
                    {key:"Day of the Week", sortable:true, resizable:true},
	            // {key:"status", formatter:YAHOO.widget.DataTable.formatNumber, sortable:true, resizeable:true},
	            {key:"Edit", sortable:false},

	        ];

	        var myDataSource = new YAHOO.util.DataSource(YAHOO.example.Data.trips);
	        myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSARRAY;
	        myDataSource.responseSchema = {
	            // fields: ["id","description","start","end","status", "edit"]
                    fields: ["Description","Start Time","End Time", "Start Location", "End Location", "Trip Duration", "Day of the Week", "Edit"]
	        };

	        var myDataTable = new YAHOO.widget.DataTable("basic",
	                myColumnDefs, myDataSource, {caption:"Trips"});

	        return {
	            oDS: myDataSource,
	            oDT: myDataTable
	        };
	    }();
	});




</script>
    </body>
</html>
<% } %>