
package event;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is the web servlet and the controller class to perform analytics
 * @author ankur
 */
@WebServlet(name = "Analytics", urlPatterns = {"/Analytics"})
public class Analytics extends HttpServlet {
    AnalyticsModel am = null;
    
    @Override
    public void init() {
        am = new AnalyticsModel();
    }
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //analyze the data to get the analytics  parameters
         am.analyzeData(); 
         
         //set the latency parameters in the request
         request.setAttribute("Latency",am.getLatency());
         request.setAttribute("MostSearchedLocation",am.getMostSearchedLocation());
         request.setAttribute("MostPopularDevice",am.getMostPopularDevice());
        
         RequestDispatcher view = request.getRequestDispatcher("analytics.jsp");
         view.forward(request, response); 
    }
  

}
