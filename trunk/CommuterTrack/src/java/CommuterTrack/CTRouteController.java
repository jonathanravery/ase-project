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
        String routeDescription;
        String routeStart;
        String routeEnd;
        CTSessionRemote session;
        final Context context;

        routeDescription = request.getParameter("description");
        routeStart = request.getParameter("start");
        routeEnd = request.getParameter("end");


        try {
            context = new InitialContext();
            session = (CTSessionRemote) context.lookup("CommuterTrack.CTSessionRemote");
        } catch (NamingException ex) {
            Logger.getLogger(CTUserController.class.getName()).log(Level.SEVERE, null, ex);
            RequestDispatcher rd = request.getRequestDispatcher("fail.jsp");
            rd.forward(request, response);
            session = null;
        }


        HttpSession hsn = request.getSession();

            if (method.equals("viewall")) {
            CtUser userBean = (CtUser) hsn.getAttribute("user");
            String view;
            // Make sure if they are setting the role to 1, the user is an admin
            if (userBean == null) {
                Logger.getLogger(CTUserController.class.getName()).log(Level.SEVERE, "USERBEAN IS NULL");

                currentMessage = (String) hsn.getAttribute("message");
                currentMessage = (currentMessage == null ? "" : currentMessage + "<br>");
                hsn.setAttribute("message", currentMessage + "<font color=red>You are not authorized to do that</font>");
                view = "index.jsp";

            } else {
                Logger.getLogger(CTUserController.class.getName()).log(Level.SEVERE, "about to set ctUsers attribute to " + this.getAllRoutes(userBean).toString());
                hsn.setAttribute("ctRoutes", this.getAllRoutes(userBean));
                view = "addroute.jsp";
            }

            RequestDispatcher rd = request.getRequestDispatcher(view);
            rd.forward(request, response);

            // If it's the edit method
        } else {

            CtUser user = (CtUser) hsn.getAttribute("user");
            session.addARoute(user, routeDescription, routeStart, routeEnd);
            RequestDispatcher rd = request.getRequestDispatcher("addroute.jsp");
            rd.forward(request, response);

        }
    }

    List getAllRoutes(CtUser ub) {
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

        // Attempt to get the bean for the user who matches
        return session.getAllRoutes(ub);
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
