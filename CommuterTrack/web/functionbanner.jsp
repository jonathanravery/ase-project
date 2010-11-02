<%@page import="CommuterTrack.CtUser"%>

	<div id="menu">
		<ul>
			<li><a href="/CommuterTrack/timer.jsp">Timer</a></li>
			<li><a href="/CommuterTrack/CTRouteController?method=viewUserRoutes">Routes</a></li>
                        <li><a href="/CommuterTrack/CTTripController?method=viewUserTrips">Trips</a></li>
			<li><a href="/CommuterTrack/CTUserController?method=viewEditPage&userId=<%= ((CtUser)session.getAttribute("user")).getUserId() %>">Account</a></li>
                        <%
                        if(((CtUser)session.getAttribute("user")).getRole()==1){


                        %>
                        <li><a href="/CommuterTrack/CTUserController?method=viewall">View All Users</a></li>
                        <%
                        }
                        %>
			<li><a href="/CommuterTrack/CTUserController?method=logout">Logout</a></li>
		</ul>
	</div>
	<!-- end #menu -->