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
        <% CtUser user = (CtUser)session.getAttribute("editUser");
           CtUser userBean = (CtUser)session.getAttribute("user");
        %>
        <h1>User Settings</h1>
        <%@ include file="functionbanner.jsp" %>
         <form action="CTUserController" method="post">
             <input type="hidden" name="userId" value="<%= user.getUserId() %>">
             Username:<input name="user" type="text" value="<%= user.getUsername() %>"><br>
             New Password:<input name="newpass" type="password" value=""><br>
             <% if (userBean.getRole() == 1) { %>
                <select name="role">
                    <option value="1" <% if ( user.getRole() == 1) { %>selected="1" <% } %> >Admin</option>
                    <option value="2" <% if ( user.getRole() != 1) { %>selected="1" <% } %> >Regular User</option>
                </select><br>
             <% } %>
        <input type="hidden" name="method" value="edituser">
        <input type="submit" name="Login" value="Edit User">
        </form>
    </body>
</html>
