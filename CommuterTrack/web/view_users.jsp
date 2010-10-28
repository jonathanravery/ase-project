<%-- 
    Document   : view_users
    Created on : Oct 28, 2010, 12:23:32 AM
    Author     : dm2474
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Enumeration"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="CommuterTrack.CtUser"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CommuterTrack: View All Users</title>
    </head>

    <body>
        <%@ include file="functionbanner.jsp" %>
        <h1>View All Users</h1>


    <%
    System.out.println("here");
        if (((CtUser) session.getAttribute("user")).getRole() != 1) {
                        // System.exit(1);
%>
                you are not an admin
    <%
        } else {
    %> you are an admin

    <p>
    <TABLE>
        <tr><td>USER ID</td><td>USERNAME</td><td>ACTIVE</td><td>Role</td></tr>

        <%

               List myctuserhash = (List)session.getAttribute("ctUsers");

               CtUser indexUser=new CtUser();
                Iterator i = myctuserhash.iterator();

                while(i.hasNext()){
                 indexUser = (CtUser)i.next();
                 System.out.println(indexUser.getUsername());
                

                //iterate through HashMap keys
               // while (e.hasMoreElements()) {
    %>
                    <TR>
                       <td><%= indexUser.getUserId() %></td>
                        <td><%= indexUser.getUsername() %></td>
                        <td><%= indexUser.getActive() %></td>
                        <td><%= indexUser.getRole() %></td>
                        <td>
                            <form method="POST" action="CTUserController">
                                <input type="hidden" name="method" value="viewEditPage">
                                <input type="hidden" name="userId" value="<%= indexUser.getUserId() %>">
                                <input type="submit" name="submit" value="Edit">
                            </form>
                        </td>
                        <td>
                            <form method="POST" action="CTUserController">
                                <input type="hidden" name="method" value="delete">
                                <input type="hidden" name="userId" value="<%= indexUser.getUserId() %>">
                                <% if (indexUser.getActive() == 1) { %>
                                    <input type="submit" name="submit" value="Deactivate">
                                <% } else { %>
                                    <input type="submit" name="submit" value="Activate">
                                <% } %>
                            </form>
                        </td>

                    </TR>
                    <%
                }
                
            }
        

    %></TABLE>
</body>

</html>
