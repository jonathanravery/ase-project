<%-- 
    Document   : addroute
    Created on : Oct 23, 2010, 10:18:00 PM
    Author     : COMS 4156
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h3>Add a Route</h3>
        <%@ include file="functionbanner.jsp" %>
        <form action="CTRouteController" method="post">
        Route Description:<input name="description" type="text"><br>
        Start Location:<input name="start" type="text"><br>
        End Location:<input name="end" type="text"><br>
        <input type="submit" name="AddRoute">
        </form>
    </body>
</html>
