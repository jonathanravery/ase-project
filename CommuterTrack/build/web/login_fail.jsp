<%-- 
    Document   : index
    Created on : Oct 20, 2010, 7:00:55 PM
    Author     : dm2474
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

        <h3>Welcome to Commuter Track</h3>


        <font color="red">Invalid user name or password</font>
        <form action="CTLoginController" method="post">
        Username:<input name="user" type="text"><br>
        Password:<input name="pass" type="password"><br>
        <input type="hidden" name="method" value="login">
        <input type="submit" name="Login">
        </form>
    </body>
</html>
