<%-- 
    Document   : index
    Created on : Oct 20, 2010, 7:00:55 PM
    Author     : dm2474
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="java.util.logging.Level"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.NamingException"%>
<%@page import="javax.naming.Context"%>
<%@page contentType="text/html" pageEncoding="UTF-8"
        import="CommuterTrack.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <link href="css/commuter.css" rel="stylesheet" type="text/css" media="screen" />
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="expires" content=-1>
        <meta http-equiv="no-cache"
              <%
              // trying to figure out how to get rid of cacheing
        response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
        response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
        response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
        response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility

        CtUser userBean = (CtUser) session.getAttribute("user");
        String userName = userBean.getUsername();
        boolean userintrip=false;



        if (null == userName) {

            System.out.println("UserName IS NULL");

            // request.setAttribute("Error", "Session has ended.  Please login.");
             //RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
             //rd.forward(request, response);
        }




        final Context context;
        CTSessionRemote sessionbean;

        List<CtRoute> routeList=null;
        // Get the session bean
        try {
            context = new InitialContext();
            sessionbean = (CTSessionRemote) context.lookup("CommuterTrack.CTSessionRemote");
            userintrip = sessionbean.userInTrip(userBean.getUserId());
            routeList = sessionbean.getUserRoutes(userBean);

        } catch (Exception ex) {
            Logger.getLogger(CTTripController.class.getName()).log(Level.SEVERE, null, ex);

        }

        String submitText = "";
%>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div id="header"></div>
        <%@ include file="functionbanner.jsp" %>

        <h3>timer page</h3>
        <% session.setAttribute("message", session.getAttribute("message") + ""); %>
        <%= ((String)session.getAttribute("message")) %>
        <% session.setAttribute("message", ""); %><br>
        Hello <%= ((CtUser)session.getAttribute("user")).getUsername() %>!
        <p>

            <%
            // if the user doesn't have any routes
            // simply display an error  "you must all add routes"
            // otherwise give start form

            if (routeList==null ||routeList.isEmpty()){
                    out.println("Before USING CommuterTrack, you must add routes!");

                } else {
        %>
        <form method="POST" action="CTTripController">
            <% if (!userintrip) { // show routes only on start %>
            <select name="routeId">
                <% for (int i = 0; i < routeList.size(); i++) { %>
                <option value="<%= routeList.get(i).getRouteId() %>"><%= routeList.get(i).getDescription() %></option>
                <% } %>
            </select>

            <p>
            <% } %>

            <% if (userintrip) { submitText = "Stop"; %>
                <input type="hidden" name="method" value="stop">
                <input type="image" src="red_button.png" name="submit" value="<%= submitText %>">
            <% } else { submitText = "Start"; %>
                <input type="hidden" name="method" value="start">
                <input type="image" src="green_button.png" name="submit" value="<%= submitText %>">
            <% } %>
            
        </form>
        <% } // else %>
    </body>
</html>
