<%@page import="CommuterTrack.CtUser"%>
<table>
    <tr>
        <td><a href="/CommuterTrack/timer.jsp">timer</a></td>
        <td><a href="/CommuterTrack/addroute.jsp">routes</a><td>
        <td><a href="/CommuterTrack/edit_user.jsp">account</a></td>
        <%
        if(((CtUser)session.getAttribute("user")).getRole()==1){


        %>
        <td><a href="/CommuterTrack/CTUserController?method=viewall">View All Users</a></td>
        <%
        }
        %>
        <td><a href="/CommuterTrack/CTUserController?method=logout">logout</a></td>

    </tr>
</table>