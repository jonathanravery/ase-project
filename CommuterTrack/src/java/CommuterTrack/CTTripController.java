/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CommuterTrack;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author dm2474
 */
@WebServlet(name = "CTTripController", urlPatterns = {"/CTTripController"})
public class CTTripController extends HttpServlet {

    CtTrip getTrip(Integer tripId) {
        final Context context;
        CTSessionRemote session;

        // Get the session bean
        try {
            context = new InitialContext();
            session = (CTSessionRemote) context.lookup("CommuterTrack.CTSessionRemote");
        } catch (NamingException ex) {
            Logger.getLogger(CTUserController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            // do stuff
        }
        return session.getTrip(tripId);
    }

    boolean addTrip(Integer routeId, Date startTime, Date endTime, Integer status) {
        final Context context;
        CTSessionRemote session;

        // Get the session bean
        try {
            context = new InitialContext();
            session = (CTSessionRemote) context.lookup("CommuterTrack.CTSessionRemote");
        } catch (NamingException ex) {
            Logger.getLogger(CTUserController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            // do stuff
        }

        return session.addTrip(routeId, startTime, endTime, status);
    }

    boolean editTrip(Integer tripId, Integer routeId, Date startTime, Date endTime, Integer status) {
        final Context context;
        CTSessionRemote session;

        // Get the session bean
        try {
            context = new InitialContext();
            session = (CTSessionRemote) context.lookup("CommuterTrack.CTSessionRemote");
        } catch (NamingException ex) {
            Logger.getLogger(CTUserController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            // do stuff
        }

        CtRoute route = session.getRoute(routeId);
        if (route == null) {
            return false;
        }
        return session.editTrip(tripId, route, startTime, endTime, status);
    }

    boolean delTrip(Integer tripId) {
        final Context context;
        CTSessionRemote session;

        // Get the session bean
        try {
            context = new InitialContext();
            session = (CTSessionRemote) context.lookup("CommuterTrack.CTSessionRemote");
        } catch (NamingException ex) {
            Logger.getLogger(CTUserController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            // do stuff
        }

        return session.delTrip(tripId);
    }

    List<CtTrip> getUserTrips(Integer userId) {
        final Context context;
        CTSessionRemote session;

        // Get the session bean
        try {
            context = new InitialContext();
            session = (CTSessionRemote) context.lookup("CommuterTrack.CTSessionRemote");
        } catch (NamingException ex) {
            Logger.getLogger(CTUserController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            // do stuff
        }

        return session.getUserTrips(userId);
    }

    List<CtTrip> getAllTrips() {
        final Context context;
        CTSessionRemote session;

        // Get the session bean
        try {
            context = new InitialContext();
            session = (CTSessionRemote) context.lookup("CommuterTrack.CTSessionRemote");
        } catch (NamingException ex) {
            Logger.getLogger(CTUserController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            // do stuff
        }

        return session.getAllTrips();
    }

    List getUserRoutes(CtUser ub) {
        final Context context;
        CTSessionRemote session;

        // Get the session bean
        try {
            context = new InitialContext();
            session = (CTSessionRemote) context.lookup("CommuterTrack.CTSessionRemote");
        } catch (NamingException ex) {
            Logger.getLogger(CTRouteController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        // Attempt to get the bean for the user who matches
        return session.getUserRoutes(ub);
    }

    CtTrip getActiveTrip(CtUser ub) {
        final Context context;
        CTSessionRemote session;

        // Get the session bean
        try {
            context = new InitialContext();
            session = (CTSessionRemote) context.lookup("CommuterTrack.CTSessionRemote");
        } catch (NamingException ex) {
            Logger.getLogger(CTRouteController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        // Attempt to get the bean for the user who matches
        return session.getActiveTrip(ub.getUserId());
    }
  
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method;
        String view;
        String currentMessage;
        CtUser curUser;
        HttpSession hsn = request.getSession();

        CtUser userBean = (CtUser) hsn.getAttribute("user");
        view = "fail.jsp";

        method = request.getParameter("method");

        // If the user bean is null, they can't be doing anything with trips, period.
        if (userBean == null) {
            view = "index.jsp";
        }
        else if(method == null || method.equals("view")) {
            Logger.getLogger(CTTripController.class.getName()).log(Level.WARNING, "No method was specified or method is 'view' ");
            view = "timer.jsp";
        } else if (method.equals("start")) {
            try {
                Date starttime = new Date();
                Integer routeId = Integer.parseInt(request.getParameter("routeId"));
                // Make sure the route actually belongs to the user
                final Context context;
                CTSessionRemote session;
                context = new InitialContext();
                session = (CTSessionRemote) context.lookup("CommuterTrack.CTSessionRemote");
                CtRoute route = session.getRoute(routeId);

                if (route.getCtUser().getUserId().intValue() != ((CtUser) hsn.getAttribute("user")).getUserId().intValue()) {
                    Logger.getLogger(CTTripController.class.getName()).log(Level.WARNING, "Trying to add trip to another user ");
                    currentMessage = (String) hsn.getAttribute("message");
                    currentMessage = (currentMessage == null ? "" : currentMessage + "<br>");
                    hsn.setAttribute("message", currentMessage + "<font color=red>You are not allowed to do that. Stop playing with the POST.</font>");
                } else if(this.addTrip(routeId, starttime, null, 0)) {
                    currentMessage = (String) hsn.getAttribute("message");
                    currentMessage = (currentMessage == null ? "" : currentMessage + "<br>");
                    hsn.setAttribute("message", currentMessage + "<font color=green>Your trip has been started.</font>");
                } else {
                    currentMessage = (String) hsn.getAttribute("message");
                    currentMessage = (currentMessage == null ? "" : currentMessage + "<br>");
                    hsn.setAttribute("message", currentMessage + "<font color=red>Unable to start your trip.</font>");
                }
            } catch (Exception e) {
                Logger.getLogger(CTTripController.class.getName()).log(Level.WARNING, "Unable to add trip ");
                currentMessage = (String) hsn.getAttribute("message");
                currentMessage = (currentMessage == null ? "" : currentMessage + "<br>");
                hsn.setAttribute("message", currentMessage + "<font color=red>Unable to start your trip. (Exception: " + e.toString() + "</font>");
            } finally {
            // do stuff
            }

            view = "timer.jsp";
        } else if (method.equals("stop")) {
            try {
                Date stopTime = new Date();
                CtTrip curTrip = this.getActiveTrip(userBean);
                if (curTrip == null) {
                    Logger.getLogger(CTTripController.class.getName()).log(Level.WARNING, "Stopping a non-existing trip ");
                    currentMessage = (String) hsn.getAttribute("message");
                    currentMessage = (currentMessage == null ? "" : currentMessage + "<br>");
                    hsn.setAttribute("message", currentMessage + "<font color=red>Trying to stop a non-existing trip.</font>");
                    view = "timer.jsp";
                } else {
                    curTrip.setEndTime(stopTime);
                    this.editTrip(curTrip.getTripId(), curTrip.getCtRoute().getRouteId(), curTrip.getStartTime(), stopTime, new Integer(1));
                    hsn.setAttribute("ctRoutes", this.getUserRoutes(userBean));
                    hsn.setAttribute("editTrip", curTrip);
                    view = "edit_trip.jsp";
                }
            } catch (Exception e) {
                Logger.getLogger(CTTripController.class.getName()).log(Level.WARNING, "Unable to stop a trip ");
                currentMessage = (String) hsn.getAttribute("message");
                currentMessage = (currentMessage == null ? "" : currentMessage + "<br>");
                hsn.setAttribute("message", currentMessage + "<font color=red>Unable to stop your trip. (Exception: " + e.toString() + "</font>");
                view = "timer.jsp";
            } finally {
            // do stuff
            }
            
        } else if (method.equals("viewUserTrips")) {
            // Make sure if they are setting the role to 1, the user is an admin
            if (userBean == null) {
                Logger.getLogger(CTUserController.class.getName()).log(Level.SEVERE, "USERBEAN IS NULL");

                currentMessage = (String) hsn.getAttribute("message");
                currentMessage = (currentMessage == null ? "" : currentMessage + "<br>");
                hsn.setAttribute("message", currentMessage + "<font color=red>You are not authorized to do that</font>");
                view = "index.jsp";

            } else {
                //Logger.getLogger(CTUserController.class.getName()).log(Level.SEVERE, "about to set ctTrips attribute to " + this.getUserTrips(userBean).toString());
                hsn.setAttribute("ctTrips", this.getUserTrips(userBean.getUserId()));
                view = "view_trips.jsp";
            }
        } else if (method.equals("viewAllTrips")) {
            // Make sure if they are setting the role to 1, the user is an admin
            if (userBean == null || userBean.getRole() != 1) {
                Logger.getLogger(CTUserController.class.getName()).log(Level.SEVERE, "USERBEAN IS NULL");

                currentMessage = (String) hsn.getAttribute("message");
                currentMessage = (currentMessage == null ? "" : currentMessage + "<br>");
                hsn.setAttribute("message", currentMessage + "<font color=red>You are not authorized to do that</font>");
                view = "index.jsp";

            } else {
                //Logger.getLogger(CTUserController.class.getName()).log(Level.SEVERE, "about to set ctTrips attribute to " + this.getUserTrips(userBean).toString());
                hsn.setAttribute("ctTrips", this.getAllTrips());
                view = "view_trips.jsp";
            }
        } else if (method.equals("viewEditTripPage")) {
            // TODO: check user role from session
            // an admin can edit any trip
            // regular user can edit only his own trips
            CtTrip trip = this.getTrip(Integer.parseInt(request.getParameter("tripId")));
            // Make sure we actually got something...
            if (trip == null) {
                Logger.getLogger(CTRouteController.class.getName()).log(Level.SEVERE, "The trip the user is about to edit does not exist");
                currentMessage = (String) hsn.getAttribute("message");
                currentMessage = (currentMessage == null ? "" : currentMessage + "<br>");
                hsn.setAttribute("message", currentMessage + "<font color=red>The trip you are trying to edit does not exist</font>");
                view = "view_trips.jsp";
            // Make sure the trip actually belongs to the user
            } else if (!trip.getCtRoute().getCtUser().getUserId().equals(userBean.getUserId())) {
                Logger.getLogger(CTRouteController.class.getName()).log(Level.WARNING, "The user is trying to edit a trip that does not belong to him");
                Logger.getLogger(CTRouteController.class.getName()).log(Level.WARNING, "(user is " + userBean.getUserId() + ", trip belongs to " + trip.getCtRoute().getCtUser().getUserId() + ")");
                currentMessage = (String) hsn.getAttribute("message");
                currentMessage = (currentMessage == null ? "" : currentMessage + "<br>");
                hsn.setAttribute("message", currentMessage + "<font color=red>That trip does not belong to you.</font><br>");
                hsn.setAttribute("ctTrips", this.getUserTrips(userBean.getUserId()));
                view = "view_trips.jsp";
            } else {
                hsn.setAttribute("ctRoutes", this.getUserRoutes(userBean));
                hsn.setAttribute("editTrip", trip);
                view = "edit_trip.jsp";
            }
        } else if (method.equals("editTrip")) {
            if (request.getParameter("button").equals("Update Trip")) {
                Integer tripId = Integer.valueOf(request.getParameter("tripId"));
                Integer routeId = Integer.valueOf(request.getParameter("routeId"));
                String start = request.getParameter("start");
                String end = request.getParameter("end");
                SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
                try {
                    Date startTime = new Date();
                    Date endTime = new Date();
                    if (start.compareTo("") != 0) {
                        startTime = formatter.parse(start);
                    }
                    if (end.compareTo("") != 0) {
                        endTime = formatter.parse(end);
                    }
                    Integer status = new Integer(2);
                    this.editTrip(tripId, routeId, startTime, endTime, status);
                    Logger.getLogger(CTTripController.class.getName()).log(Level.SEVERE, "setting new dates " + start + ", " + end);
                    Logger.getLogger(CTTripController.class.getName()).log(Level.SEVERE, "setting routeId " + routeId.toString());
                } catch (ParseException ex) {
                    Logger.getLogger(CTTripController.class.getName()).log(Level.SEVERE, "Could not parse dates " + start + ", " + end, ex);
                }
            } else { // discard trip
                Integer tripId = Integer.valueOf(request.getParameter("tripId"));
                this.delTrip(tripId);
            }
            Logger.getLogger(CTRouteController.class.getName()).log(Level.SEVERE, "about to set ctTrips attribute to " + this.getUserTrips(userBean.getUserId()).toString());
            hsn.setAttribute("ctTrips", this.getUserTrips(userBean.getUserId()));
            view = "view_trips.jsp";
        }

        //Logger.getLogger(CTTripController.class.getName()).log(Level.WARNING, "CTTripController is about to forward you to: " + view);

        RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
