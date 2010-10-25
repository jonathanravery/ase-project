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
        response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
        response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
        response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
        response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
        String userName = (String) session.getAttribute("user");
        if (null == userName) {
             request.setAttribute("Error", "Session has ended.  Please login.");
             RequestDispatcher rd = request.getRequestDispatcher("login_fail.jsp");
             rd.forward(request, response);
        }
%>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h3>Placeholder for the timer page</h3>
        Hello <%= ((String)session.getAttribute("user"))%>!
        <a href="/CommuterTrack/CTLoginController?method=logout">logout</a>
    </body>
</html>
