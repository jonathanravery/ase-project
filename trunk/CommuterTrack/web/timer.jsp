<%-- 
    Document   : index
    Created on : Oct 20, 2010, 7:00:55 PM
    Author     : dm2474
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"
        import="CommuterTrack.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="expires" content=-1>
        <meta http-equiv="no-cache"
              <%
              // trying to figure out how to get rid of cacheing
        response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
        response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
        response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
        response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility

        String userName = ((CtUser) session.getAttribute("user")).getUsername();

        if (null == userName) {

            System.out.println("UserName IS NULL");

            // request.setAttribute("Error", "Session has ended.  Please login.");
             //RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
             //rd.forward(request, response);
        }
%>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@ include file="functionbanner.jsp" %>

        <h3>timer page</h3>
        <% session.setAttribute("message", session.getAttribute("message") + ""); %>
        <%= ((String)session.getAttribute("message")) %>
        <% session.setAttribute("message", ""); %><br>
        Hello <%= ((CtUser)session.getAttribute("user")).getUsername() %>!
    </body>
</html>
