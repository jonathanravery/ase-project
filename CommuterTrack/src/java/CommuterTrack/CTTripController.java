/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CommuterTrack;

import java.io.IOException;
import java.io.PrintWriter;
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
        }

        return session.getAllTrips();
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


        view = "fail.jsp";

        method = request.getParameter("method");

        if (method == null || method.equals("view")) {
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
            }

            view = "timer.jsp";
        }

        Logger.getLogger(CTTripController.class.getName()).log(Level.WARNING, "CTTripController is about to forward you to: " + view);

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
