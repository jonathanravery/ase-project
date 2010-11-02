/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CommuterTrack;

import java.io.IOException;
import java.io.PrintWriter;
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
 * @author jdm
 */
@WebServlet(name = "CTRouteController", urlPatterns = {"/CTRouteController"})
public class CTRouteController extends HttpServlet {

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
        String currentMessage;


        method = request.getParameter("method");
        if (method == null) {
            method = "none";
        }



        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        CTSessionRemote session;
        final Context context;

        try {
            context = new InitialContext();
            session = (CTSessionRemote) context.lookup("CommuterTrack.CTSessionRemote");
        } catch (NamingException ex) {
            Logger.getLogger(CTRouteController.class.getName()).log(Level.SEVERE, null, ex);
            RequestDispatcher rd = request.getRequestDispatcher("fail.jsp");
            rd.forward(request, response);
            session = null;
        }


        HttpSession hsn = request.getSession();
        CtUser userBean = (CtUser) hsn.getAttribute("user");
        String view = "index.jsp";

        if (userBean == null) {
            Logger.getLogger(CTRouteController.class.getName()).log(Level.SEVERE, "USERBEAN IS NULL");
            currentMessage = (String) hsn.getAttribute("message");
            currentMessage = (currentMessage == null ? "" : currentMessage + "<br>");
            hsn.setAttribute("message", currentMessage + "<font color=red>You are not authorized to do that</font>");
            view = "index.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(view);
            rd.forward(request, response);
            return;
        }

        if (method.equals("viewUserRoutes")) {
            Logger.getLogger(CTRouteController.class.getName()).log(Level.SEVERE, "about to set ctUsers attribute to " + this.getUserRoutes(userBean).toString());
            hsn.setAttribute("ctRoutes", this.getUserRoutes(userBean));
            view = "view_routes.jsp";
        } else if (method.equals("viewAllRoutes")) {
            if (userBean.getRole() != 1) {
                Logger.getLogger(CTRouteController.class.getName()).log(Level.SEVERE, "Non-admin user trying to access all routes.");
                currentMessage = (String) hsn.getAttribute("message");
                currentMessage = (currentMessage == null ? "" : currentMessage + "<br>");
                hsn.setAttribute("message", currentMessage + "<font color=red>You are not authorized to do that</font>");
                view = "timer.jsp";
            } else {
                Logger.getLogger(CTRouteController.class.getName()).log(Level.SEVERE, "about to set ctUsers attribute to " + this.getAllRoutes().toString());
                hsn.setAttribute("ctRoutes", this.getAllRoutes());
                view = "view_routes.jsp";
            }
        } else if (method.equals("viewEditRoutePage")) {
            // TODO: check user role from session
            // an admin can edit any route
            // regular user can edit only his own
            CtRoute route = this.getRoute(Integer.parseInt(request.getParameter("routeId")));
            // Make sure we actually got something...
            if (route == null) {
                Logger.getLogger(CTRouteController.class.getName()).log(Level.SEVERE, "The route the user is about to edit does not exist");
                currentMessage = (String) hsn.getAttribute("message");
                currentMessage = (currentMessage == null ? "" : currentMessage + "<br>");
                hsn.setAttribute("message", currentMessage + "<font color=red>The route you are trying to edit does not exist</font>");
                view = "view_routes.jsp";
            } else {
                hsn.setAttribute("editRoute", route);
                view = "edit_route.jsp";
            }
        } else if (method.equals("addRoute")) {
            CtUser user = (CtUser) hsn.getAttribute("user");
            String routeDescription = request.getParameter("description");
            String routeStart = request.getParameter("start");
            String routeEnd = request.getParameter("end");
            // For some reason, the try/catch in the session bean doesn't want to catch this exception, so we'll get it here
            try {
                // Add the route and check to see if it was successful
                if (session.addARoute(user, routeDescription, routeStart, routeEnd)) {
                    currentMessage = (String) hsn.getAttribute("message");
                    currentMessage = (currentMessage == null ? "" : currentMessage + "<br>");
                    hsn.setAttribute("message", currentMessage + "<font color=green>Route added successfully</font>");
                } else {
                    Logger.getLogger(CTRouteController.class.getName()).log(Level.SEVERE, "Adding route returned false");
                    currentMessage = (String) hsn.getAttribute("message");
                    currentMessage = (currentMessage == null ? "" : currentMessage + "<br>");
                    hsn.setAttribute("message", currentMessage + "<font color=red>Unable to add your route</font>");
                }
            } catch (Exception e) {
                Logger.getLogger(CTRouteController.class.getName()).log(Level.SEVERE, "Adding route threw exception");
                currentMessage = (String) hsn.getAttribute("message");
                currentMessage = (currentMessage == null ? "" : currentMessage + "<br>");
                hsn.setAttribute("message", currentMessage + "<font color=red>Unable to add your route</font>");
            }
            Logger.getLogger(CTRouteController.class.getName()).log(Level.SEVERE, "about to set ctUsers attribute to " + this.getUserRoutes(userBean).toString());
            hsn.setAttribute("ctRoutes", this.getUserRoutes(userBean));
            view = "view_routes.jsp";

        } else if (method.equals("editRoute")) {
            Integer routeId = Integer.valueOf(request.getParameter("routeId"));
            String routeDescription = request.getParameter("description");
            String routeStart = request.getParameter("start");
            String routeEnd = request.getParameter("end");
            if (session.updateRoute(routeId, routeDescription, routeStart, routeEnd)) {
                Logger.getLogger(CTRouteController.class.getName()).log(Level.SEVERE, "EDITR: about to set ctUsers attribute to " + this.getUserRoutes(userBean).toString());
                currentMessage = (String) hsn.getAttribute("message");
                currentMessage = (currentMessage == null ? "" : currentMessage + "<br>");
                hsn.setAttribute("message", currentMessage + "<font color=green>Route updated</font>");
                hsn.setAttribute("ctRoutes", this.getUserRoutes(userBean));
                view = "view_routes.jsp";
            } else {
                Logger.getLogger(CTRouteController.class.getName()).log(Level.SEVERE, "Edit route failed (returned false)");
                currentMessage = (String) hsn.getAttribute("message");
                currentMessage = (currentMessage == null ? "" : currentMessage + "<br>");
                hsn.setAttribute("message", currentMessage + "<font color=red>Unable to update route</font>");
                view = "edit_route.jsp";
            }
            
        } else if (method.equals("deleteRoute")) {
            Integer routeId = Integer.valueOf(request.getParameter("routeId"));
            session.delRoute(routeId);
            Logger.getLogger(CTRouteController.class.getName()).log(Level.SEVERE, "DELR: about to set ctUsers attribute to " + this.getUserRoutes(userBean).toString());
            hsn.setAttribute("ctRoutes", this.getUserRoutes(userBean));
            view = "view_routes.jsp";
        }

        RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);
    }

    CtRoute getRoute(Integer routeId) {
        final Context context;
        CTSessionRemote session;
        CtRoute route;
        // Get the session bean
        try {
            context = new InitialContext();
            session = (CTSessionRemote) context.lookup("CommuterTrack.CTSessionRemote");
        } catch (NamingException ex) {
            Logger.getLogger(CTRouteController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return session.getRoute(routeId);
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

    List getAllRoutes() {
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

        return session.getAllRoutes();
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
            throws ServletException,
            IOException {
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
            throws ServletException,
            IOException {
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
