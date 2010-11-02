<%-- 
    Document   : view_users
    Created on : Oct 28, 2010, 12:23:32 AM
    Author     : dm2474
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="CommuterTrack.CTSessionRemote"%>
<%@page import="javax.naming.Context"%>
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
        <link href="css/commuter.css" rel="stylesheet" type="text/css" media="screen" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CommuterTrack: View All Users</title>
    </head>

    <body>
        <div id="header"></div>
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
            if (session.getAttribute("message") != null) {
                out.println((String) session.getAttribute("message"));
            }
            session.setAttribute("message", "");

    %> 

    <p>
    <TABLE>
        <tr><th>USER ID</th><th>USERNAME</th><th>ACTIVE</th><th>Role</th><th>Edit</th><th>Deactivate</th></tr>

        <%
           final Context context;
            CTSessionRemote sessionbean;
            CtUser userBean;
            List myctuserhash;

            // Get the session bean
            try {
                context = new InitialContext();
                sessionbean = (CTSessionRemote) context.lookup("CommuterTrack.CTSessionRemote");
                myctuserhash = sessionbean.getAllUsers();
            } catch (Exception e) {
                myctuserhash = new ArrayList();
                %>
                <font color="red">ERROR: UNABLE TO LOAD USERS</font>
                <% }


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
