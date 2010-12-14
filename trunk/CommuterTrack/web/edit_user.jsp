<%-- 
    Document   : edit_user
    Created on : Oct 24, 2010, 10:26:09 PM
    Author     : Travel Timers
--%>

<% if (session.getAttribute("user") == null) {
       response.sendRedirect("index.jsp");
   } else {
%>

<%@page import="CommuterTrack.CTConsts"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <link href="css/commuter.css" rel="stylesheet" type="text/css" media="screen" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Settings</title>
    </head>
    <body>
        <div id="header"></div>
        <%@ include file="functionbanner.jsp" %>
        <h1>User Settings</h1>
        <%  CtUser user = (CtUser)session.getAttribute("editUser");
            CtUser userBean = (CtUser)session.getAttribute("user");
            if (session.getAttribute("message")!=null){
                out.println((String)session.getAttribute("message"));
            }
            session.setAttribute("message", "");
        %>
        
        
        <fieldset>
        <legend>User settings</legend>
         <form action="CTUserController" method="post">
             <input type="hidden" name="userId" value="<%= user.getUserId() %>">
             <label for="user">Username:</label><input id="user" name="user" type="text" value="<%= user.getUsername() %>"><br>
             <label for="newpass">New Password:</label><input id="newpass" name="newpass" type="password" value=""><br>
             <% if (userBean.getRole() == CTConsts.ADMIN_USER) { %>
                <select name="role">
                    <option value="<%=CTConsts.ADMIN_USER%>" <% if ( user.getRole() == CTConsts.ADMIN_USER) { %>selected="1" <% } %> >Admin</option>
                    <option value="<%=CTConsts.REGULAR_USER%>" <% if ( user.getRole() != CTConsts.ADMIN_USER) { %>selected="1" <% } %> >Regular User</option>
                </select><br>
             <% } %>
        <input type="hidden" name="method" value="edituser">
        <input type="submit" name="Login" value="Edit User">
        </form>
        </fieldset>
    </body>
</html>

<% } %>