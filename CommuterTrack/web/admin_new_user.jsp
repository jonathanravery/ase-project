<%-- 
    Document   : new_user.jsp
    Created on : Oct 24, 2010, 11:28:34 PM
    Author     : dm2474
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Account</title>
    </head>
    <body>
        <h1>New Account</h1>
        <!-- <%@ include file="functionbanner.jsp" %> -->
        <%= session.getAttribute("message") %>
        <form action="CTUserController" method="post">
        Username:<input name="user" type="text"><br>
        Password:<input name="pass" type="password"><br>
        <input type="hidden" name="method" value="creatuser">
        <input type="submit" name="Login" value="Create User">
        </form>
    </body>
</html>
