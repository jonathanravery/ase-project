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
        return false;
    }

    boolean editTrip(Integer tripId, Integer routeId, Date startTime, Date endTime, Integer status) {
        return false;
    }

    boolean delTrip(Integer tripId) {
        return false;
    }

    List<CtTrip> getUserTrips(Integer userId) {
        return null;
    }

    List<CtTrip> getAllTrips() {
        return null;
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
        CtUser curUser;
        HttpSession hsn = request.getSession();


        view = "fail.jsp";

        method = request.getParameter("method");

        if (method == null || method.equals("view")) {
            view = "timer.jsp";
        }
        /*
        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);

        } else if (method.equals("new")) {
        Logger.getLogger(CTUserController.class.getName()).log(Level.WARNING, "REACHED NEW ACCOUNT", "REACHED LOGOUT");
        RequestDispatcher rd = request.getRequestDispatcher("new_user.jsp");
        rd.forward(request, response);
        }
         */
        Logger.getLogger(CTTripController.class.getName()).log(Level.WARNING, "about to forward you to: " + view);

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
