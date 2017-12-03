package amodi1.cmu.edu.queryevents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EventsQuery extends AppCompatActivity {
    ArrayList<Event> eventList;
    CustomAdapter dataAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EventsQuery my = this;
        ListView listView = (ListView) findViewById(R.id.listView);
        eventList = new ArrayList<Event>();

        /*
         * Find the "search" button, and add a listener to it
         */
        Button searchButton = (Button)findViewById(R.id.searchButton);


        // Add a listener to the send button
        searchButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View viewParam) {
                String locationTerm = (((EditText)findViewById(R.id.locationTerm)).getText().toString());
                String keywordTerm =  ((EditText)findViewById(R.id.keywordTerm)).getText().toString();
                String categoryTerm = ((EditText)findViewById(R.id.categoryTerm)).getText().toString();
                StringBuffer sb = new StringBuffer("");
                String searchTerm = sb.append(locationTerm.equals("")?"":"&location="+locationTerm.replace(" ","+")).append(keywordTerm.equals("")?"":"&keywords="+keywordTerm.replace(" ","+")).append(categoryTerm.equals("")?"":"&category="+categoryTerm.replace(" ","+")).append("&date=Future").toString();
                GetEvents ge = new GetEvents();
                ge.search(searchTerm,my); // Done asynchronously in another thread.  It calls ip.pictureReady() in this thread when complete.
            }
        });
    }

    public void showToast(String text, int duration) {
        Toast toast = Toast.makeText(this.getBaseContext(), text, duration);
        toast.show();
    }

    public void displayQuery(String response){

        JSONObject responseObj = null;

        try {
            responseObj = new JSONObject(response);
            JSONArray eventListObj = responseObj.getJSONArray("event_list");
            eventList = new ArrayList<Event>();
            for (int i=0; i<eventListObj.length(); i++){
                JSONObject temp = (JSONObject) eventListObj.getJSONObject(i);
                Event entry = new Event();
                //get the country information JSON object
                entry.title = temp.getString("title").toString();
                entry.address = temp.getString("address").toString();
                entry.time = temp.getString("time").toString();
                //add to country array list
                eventList.add(entry);
            }

            //create an ArrayAdapter from the String Array
            dataAdapter = new CustomAdapter(this, R.layout.list_view1, eventList);
            ListView listView = (ListView) findViewById(R.id.listView);
            // Assign adapter to ListView
            listView.setAdapter(dataAdapter);

            //enables filtering for the contents of the given ListView
            listView.setTextFilterEnabled(true);

            /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // When clicked, show a toast with the TextView text
                    Country country = (Country) parent.getItemAtPosition(position);
                    Toast.makeText(getApplicationContext(),
                            country.getCode(), Toast.LENGTH_SHORT).show();
                }
            });*/



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
