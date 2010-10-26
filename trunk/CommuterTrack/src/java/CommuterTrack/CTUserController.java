/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CommuterTrack;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "CTUserController", urlPatterns = {"/CTUserController"})
public class CTUserController extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username;
        String password;
        String method; // the method 'login' or 'logout' that we are running
        CTSessionRemote session;
        final Context context;

        username = request.getParameter("user");
        password = request.getParameter("pass");
        method = request.getParameter("method");

        // prevent caching
        response.addHeader("Pragma", "no-cache");
        response.addIntHeader("Expires", -1);
        response.addHeader("Cache-Control", "no-cache, must-revalidate");


        try {
            context = new InitialContext();
            session = (CTSessionRemote) context.lookup("CommuterTrack.CTSessionRemote");
        } catch (NamingException ex) {
            Logger.getLogger(CTUserController.class.getName()).log(Level.SEVERE, null, ex);
            RequestDispatcher rd = request.getRequestDispatcher("fail.jsp");
            rd.forward(request, response);
            session = null;
        }
        //hsn.setAttribute("user", "none");
            /* Create an HTTP session
         * Store the username so we can include it in the addRoute stuff
         */
        HttpSession hsn = null;// = request.getSession();
        //hsn.setAttribute("user", "none");


        if (method.equals("login")) {
            hsn = request.getSession();
            //hsn.setAttribute("user", "none");
            // try to login
            // Forward the request to the appropriate view
            if (session.logInUser(username, password)) {
                hsn.setAttribute("user", username);

                //System.out.println("user logged in");

                RequestDispatcher rd = request.getRequestDispatcher("timer.jsp");
                rd.forward(request, response);
            } else {
                hsn.invalidate();
                //System.out.println("user could not log in");
                
                /* TODO: instead of redirecting to login_fail, we should likely be seeing an 'error' or 'message' session var.
                 
                 */
                RequestDispatcher rd = request.getRequestDispatcher("login_fail.jsp");
                rd.forward(request, response);
            }
        } else if (method.equals("logout")) {
            Logger.getLogger(CTUserController.class.getName()).log(Level.WARNING, "REACHED LOGOUT", "REACHED LOGOUT");

            try {
                hsn = request.getSession();
                hsn.setAttribute("user", "none");
                hsn.invalidate();

            } catch (Exception e) {
                Logger.getLogger(CTUserController.class.getName()).log(Level.SEVERE, e.toString(), e.toString());


            }



            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);

        } else if (method.equals("new")) {
            Logger.getLogger(CTUserController.class.getName()).log(Level.WARNING, "REACHED NEW ACCOUNT", "REACHED LOGOUT");
            RequestDispatcher rd = request.getRequestDispatcher("new_user.jsp");
            rd.forward(request, response);
        }
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
