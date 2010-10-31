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
        <link href="css/commuter.css" rel="stylesheet" type="text/css" media="screen" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div id="header"></div>
        <h3>Welcome to CommuterTrack</h3>
        <% session.setAttribute("message", session.getAttribute("message") + ""); %>
        <%= ((String)session.getAttribute("message")) %>
        <% session.setAttribute("message", ""); %>
        <fieldset align="center">
        <legend>Login</legend>
        <form action="CTUserController" method="post">
            <label for="user">Username:</label><input id="user" name="user" type="text"><br>
            <label for="pass">Password:</label><input id="pass" name="pass" type="password"><br>
        <input type="hidden" name="method" value="login">
        <input type="submit" name="Login" value="Login">
        </form>
        </fieldset>
        <p>
        <form action="CTUserController" method="post">
        <input type="hidden" name="method" value="new">
        <input type="submit" name="Create a New Account" value="Create a New Account">
        </form>
    </body>
</html>
