<%-- 
    Document   : edit_user
    Created on : Oct 24, 2010, 10:26:09 PM
    Author     : dm2474
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Settings</title>
    </head>
    <body>
        <h1>User Settings</h1>
        <%@ include file="functionbanner.jsp" %>
         <form action="CTLoginController" method="post">
        Username:<input name="user" type="text"><br>
        Password:<input name="pass" type="password"><br>
        <input type="hidden" name="method" value="edituser">
        <input type="submit" name="Login" value="Edit User">
        </form>
    </body>
</html>
