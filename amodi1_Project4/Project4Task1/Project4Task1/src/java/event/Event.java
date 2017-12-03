/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import java.io.IOException;
import java.io.OutputStreamWriter;
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
        //get the search parameters passed in the URL
         String search = (request.getPathInfo()).substring(1);
         //System.out.println(search);
         //get the JSONObject for the result of the search query from the Model class method
         JSONObject result = em.getResponse(search);
         
         //If result is obtained then good else the API might be unreachbale or no query results
         if(result!=null)
            response.setStatus(200);
         else response.setStatus(401);
         //Write it to the client
         OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
         writer.write(result.toString());
         writer.flush();
         writer.close();
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
