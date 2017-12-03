/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.*;
/**
 * This class is the controller for the Event application web service
 * @author ankur
 */
@WebServlet(name = "Event", urlPatterns = {"/Event/*"})
public class Event extends HttpServlet {
    //define an object for the model class
    EventModel em = null;
 
    @Override
    public void init() {
        em = new EventModel();
    }
    /**
     * Handles the HTTP <code>GET</code> method.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
         LogEntry le = new LogEntry();
         le.setMobileType(request.getHeader("User-Agent"));
         le.setRequestTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(System.currentTimeMillis())));
         
        //get the JSONObject for the result of the search query from the Model class method   
         JSONObject result = em.getResponse(le,(request.getPathInfo()).substring(1));
         
         //If result is obtained then good else the API might be unreachbale or no query results
         if(result!=null)
            response.setStatus(200);
         else response.setStatus(401);
         le.setResponseTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(System.currentTimeMillis())));
         //Write it to the client
         OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
         writer.write(result.toString());
         writer.flush();
         writer.close();
         em.insertDBObject(le);
         em.printAll();
    }
    

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
