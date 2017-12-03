package event;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This is the model class for the event web service
 * @author ankur
 */
public class EventModel {
    
    //This method return the JSONObject to be sent back to the clients based on the search string 
    public JSONObject getResponse(String search){
        
        //create url based on the search
        String url = createUrl(search);
        JSONObject json = null;
        
        try {
            //Read the json response from the Eventful API
            json = readJsonFromUrl(url);
        } catch (IOException | JSONException ex) {
            Logger.getLogger(EventModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Create the JSONObject reply to client based on the Json obtained from the API
        //This is a modified Json because we don't want to send unnecessary info to android
        JSONObject eventObj = json.getJSONObject("events");
        JSONArray jsonArray = eventObj.getJSONArray("event");
        int length=jsonArray.length();
        String[] events=new String[length];
        JSONArray ja = new JSONArray();
        JSONObject event_list = new JSONObject();
        //For each event we will be storing title, address and time
        for (int i=0;i<length;i++){
            JSONObject temp= (JSONObject) jsonArray.get(i);
            JSONObject jo = new JSONObject();
            jo.put("title", temp.getString("title"));
            jo.put("address",(temp.isNull("venue_name")? "": (temp.getString("venue_name")+ ", ")) + (temp.isNull("venue_address")? "" : (temp.getString("venue_address")+ ", ")) + (temp.isNull("city_name")? "": temp.getString("city_name")));
            jo.put("time",(temp.isNull("start_time")? "" : temp.getString("start_time")));
            ja.put(jo);
        }
        JSONObject mainObj = new JSONObject();
        mainObj.put("event_list", ja);
        System.out.println(mainObj);
        return mainObj;
    }
    
    //This method creates a url for the Eventful API based on the search String
    private String createUrl(String search){
        StringBuffer url = new StringBuffer("http://api.eventful.com/json/events/search?&app_key=7TKXTSPvSS3CDv9R");
        String[] searchQuery = search.split("&");
        StringBuffer result = new StringBuffer(""); 
        System.out.println(searchQuery);
        for(String s : searchQuery){
            result.append("&").append(s.replace(" ", "+").trim());
        }
        url.append(result);
        System.out.println(url);
        return url.toString();
    }
    
    //Source: https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java/4308662#4308662
    //Method that reads Json response from the API
    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
        is.close();
    }
    }
    
    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
  }
}
