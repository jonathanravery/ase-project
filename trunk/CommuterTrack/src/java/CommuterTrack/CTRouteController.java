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
 * @author COMS 4156
 */
@WebServlet(name="CTRouteController", urlPatterns={"/CTRouteController"})
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
            session = (CTSessionRemote)context.lookup("CommuterTrack.CTSessionRemote");
        } catch (NamingException ex) {
            Logger.getLogger(CTUserController.class.getName()).log(Level.SEVERE, null, ex);
            RequestDispatcher rd = request.getRequestDispatcher("fail.jsp");
            rd.forward(request, response);
            session = null;
        }
//        RequestDispatcher rd  = request.getRequestDispatcher(routeDescription + routeStart + routeEnd + ".jsp");
//        rd.forward(request, response);

        /*TODO: forward user to a .jsp, rather than print HTML directly
         */
out.println("<HTML><BODY>");


        /*TODO: The Servlet checks whether the user is logged in, but doesnt enforce it, for testing purposes
         * at some point we should restrict functionality to logged in users.

        */
        HttpSession hsn = request.getSession();
        /*
        if (hsn.getAttribute("user") != null)
        {

              out.println("<B><P>user " + hsn.getAttribute("user") + "logged in<B>");
//            RequestDispatcher rd  = request.getRequestDispatcher("timer.jsp");
//            rd.forward(request, response);
        } else {

              out.println("<B><P>user " + hsn.getAttribute("user") + "not logged in<B>");

        }
         *
         */
        CtUser user = (CtUser) hsn.getAttribute("user");
        session.addARoute(user, routeDescription, routeStart, routeEnd);

        out.println("<H1>" + routeDescription + routeStart + routeEnd + "</H1>");


        out.println("</BODY></HTML>");
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CTRouteServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CTRouteServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
            */
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
