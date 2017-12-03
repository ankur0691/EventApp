package event;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;

/**
 * This  is the Model class for doing all the analytic related computation by retrieving data from MongoDB
 * @author ankur
 */
public class AnalyticsModel {
    private MongoCollection<Document> col;
    private MongoClient mongo;
    
    //Analytics variablels
    private long latency;
    private String mostSearchedLocation="";
    private String mostPopularDevice="";
    
    //Used to perform few analtics related tasks
    Map<String, Integer> map;
    Map<String, Integer> map2;

    //Initialize all the variables and connect to MongoDB
    public AnalyticsModel(){
        try {
            map = new HashMap<String, Integer>();
            map2 = new HashMap<String, Integer>();
            MongoClientURI uri = new MongoClientURI("mongodb://ankur:ankur91@ds159235.mlab.com:59235/eventdb1");
            mongo = new MongoClient(uri);
            //System.out.println("Connected to MongoDB!");
            MongoDatabase db = mongo.getDatabase("eventdb1");
            col = db.getCollection("logs");
        } catch (MongoException ex) {
            Logger.getLogger(AnalyticsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public long getLatency() {
        return latency;
    }

    public String getMostSearchedLocation() {
        return mostSearchedLocation;
    }

    public String getMostPopularDevice() {
        return mostPopularDevice;
    }
    
    //this method performs analysis to get the latency, most searched dlocation and most popular device related information
    public void analyzeData(){
        SimpleDateFormat parser=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        long totalLatency = 0;
        int count = 0;
        MongoCursor<Document> cursorDoc = col.find().iterator();
        
        //Go through each MongoDB document to calculate the analytics  property
	while (cursorDoc.hasNext()) {
            try {
                count++;
                Document doc = cursorDoc.next();
                
                //keep on adding up the latency for each request
                totalLatency +=  (parser.parse((doc.get("ResponseTimestamp").toString())).getTime() - parser.parse((doc.get("RequestTimestamp").toString())).getTime());
                
                //get the location from the android request to analyze the most searched location
                String str = doc.get("AndroidRequest").toString();
                int startIndex = str.indexOf("&location=");
                int endIndex = str.indexOf("&",startIndex+10);
                if(startIndex > -1){
                    String location = str.substring(startIndex+10,endIndex).toLowerCase();
                    if(location !=null) map.put(location,(map.containsKey(location)? (map.get(location)+1):0));
                }
                
                //get the mobile type for reques to analyze the most used device
                String MobileType = doc.get("MobileType").toString();
                map2.put(MobileType,(map2.containsKey(MobileType)? (map2.get(MobileType)+1):0));
                 
            } catch (ParseException ex) {
                Logger.getLogger(AnalyticsModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        
        //calculate the analytics parameter
        if(count!=0)
            latency =  totalLatency/count;
        if(!map.isEmpty())
            mostSearchedLocation = Collections.max(map.entrySet(), Map.Entry.comparingByValue()).getKey();
        if(!map2.isEmpty())
            mostPopularDevice = Collections.max(map2.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}
