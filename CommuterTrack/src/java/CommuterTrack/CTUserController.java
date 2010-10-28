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

    CtUser loginUser(String user, String pass) {
        final Context context;
        CTSessionRemote session;
        CtUser userBean;

        // Get the session bean
        try {
            context = new InitialContext();
            session = (CTSessionRemote) context.lookup("CommuterTrack.CTSessionRemote");
        } catch (NamingException ex) {
            Logger.getLogger(CTUserController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        // Attempt to get the bean for the user who matches
        return session.getUser(user, pass);
    }

    boolean logout(HttpSession hsn) {
        hsn.invalidate();
        return true;
    }

    boolean addUser(String user, String pass, int role) {
        final Context context;
        CTSessionRemote session;
        CtUser userBean;

        // Get the session bean
        try {
            context = new InitialContext();
            session = (CTSessionRemote) context.lookup("CommuterTrack.CTSessionRemote");
        } catch (NamingException ex) {
            Logger.getLogger(CTUserController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        // Make sure the role is 1 (admin) or 2 (regular user)
        if (role < 1 || role > 2) {
            return false;
        }

        return session.addUser(user, pass, role);
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
        String username;
        String password;
        String method; // the method 'login' or 'logout' that we are running
        CtUser userBean;
        String view = null;
        String currentMessage;
        int role;


        method = request.getParameter("method");
        if (method == null) {
            method = "none";
        }

        // prevent caching
        response.addHeader("Pragma", "no-cache");
        response.addIntHeader("Expires", -1);
        response.addHeader("Cache-Control", "no-cache, must-revalidate");

        HttpSession hsn = request.getSession();
        hsn.setAttribute("message", "");
        //hsn.setAttribute("user", "none");

        //debug
        Logger.getLogger(CTUserController.class.getName()).log(Level.WARNING, "method: " + method, "REACHED LOGOUT");

        // If the request is the initial load of the page

        if (method.equals("none")) {
            view = "index.jsp";
        } // If the user submitted a login
        else if (method.equals("login")) {
            username = request.getParameter("user");
            password = request.getParameter("pass");

            userBean = (CtUser)this.loginUser(username, password);

            hsn.setAttribute("user", userBean);
            if (userBean.getUsername()==null) {
                Logger.getLogger(CTUserController.class.getName()).log(Level.SEVERE,"USERBEAN IS NULL", "NULL USERBEAN");

                hsn.setAttribute("message", "<font color=red>Invalid username of password</font>");
                view = "index.jsp";
            } else {
                Logger.getLogger(CTUserController.class.getName()).log(Level.SEVERE,"USERBEAN IS NOT NULL");

                view = "timer.jsp";
            }

            // If the request is a log out request
        } else if (method.equals("logout")) {
            Logger.getLogger(CTUserController.class.getName()).log(Level.WARNING, "REACHED LOGOUT", "REACHED LOGOUT");

            try {
                hsn = request.getSession();
                this.logout(hsn);
                hsn = request.getSession();
                currentMessage = (String) hsn.getAttribute("message");
                currentMessage = (currentMessage == null ? "" : currentMessage + "<br>");
                hsn.setAttribute("message", currentMessage + "You have successfully logged out.");
            } catch (Exception e) {
                Logger.getLogger(CTUserController.class.getName()).log(Level.SEVERE, e.toString(), e.toString());
            }
            view = "index.jsp";
        } else if (method.equals("new")) {
            Logger.getLogger(CTUserController.class.getName()).log(Level.WARNING, "REACHED NEW", "REACHED new");
            hsn.setAttribute("message", "<font color=green>Please enter your user values</font>");

            view = "new_user.jsp";
        } else if (method.equals("add")) {
            // Get the values out of the session
            username = request.getParameter("user");
            password = request.getParameter("pass");
            role = 0;

            try {
                role = Integer.parseInt(request.getParameter("role"));
                // If role isn't a numeric value, that means that the post was manually edited (or we screwed up somewhere bad
            } catch (NumberFormatException nfe) {
                Logger.getLogger(CTUserController.class.getName()).log(Level.SEVERE, nfe.toString(), nfe.toString());

                hsn.setAttribute("message", "<font color=red>Please select a role from the drop down menu--don't manually edit the post and try to mess with us</font>");
                view = "new_user.jsp";
                // If the role was not posted, then create the new user with a non-admin role
            } catch (NullPointerException npe) {
                Logger.getLogger(CTUserController.class.getName()).log(Level.SEVERE, npe.toString(), npe.toString());

                // Role 2 means regular user
                role = 2;

            }

            userBean = (CtUser) hsn.getAttribute("user");

            // Make sure if they are setting the role to 1, the user is an admin
            if (role == 1) {
                if (userBean == null || userBean.getRole() != 1) {
                    currentMessage = (String) hsn.getAttribute("message");
                    currentMessage = (currentMessage == null ? "" : currentMessage + "<br>");
                    hsn.setAttribute("message", currentMessage + "<font color=red>You are not authorized to do that</font>");
                    view = "new_user.jsp";
                }
            }
            // If there have not been any errors so far and the new user is added successfully
            if (view == null && this.addUser(username, password, role)) {
                hsn.setAttribute("message", "New user added successfully");
                if (userBean == null) {
                    view = "index.jsp";
                } else {
                    view = "timer.jsp";
                }
            } // If we could not add the user
            else {
                currentMessage = (String) hsn.getAttribute("message");
                currentMessage = (currentMessage == null ? "" : currentMessage + "<br>");
                hsn.setAttribute("message", currentMessage + "Unable to add the new user");
                view = "new_user.jsp";
            }
        } else {
            view = "fail.jsp";
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
                    Logger.getLogger(CTUserController.class.getName()).log(Level.WARNING, "about to forward you to: "+view, "fwding");

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
