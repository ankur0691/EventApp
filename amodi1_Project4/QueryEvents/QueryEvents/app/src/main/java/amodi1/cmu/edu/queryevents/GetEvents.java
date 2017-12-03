package amodi1.cmu.edu.queryevents;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ankur on 11/11/2017.
 */

public class GetEvents {
    EventsQuery eq = null;

    public void search(String searchTerm, EventsQuery eq) {
        this.eq = eq;
        new AsyncEventQuery().execute(searchTerm);
    }

    private class AsyncEventQuery extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {
            return getData(params[0]);
        }

        protected void onPostExecute(String stream) {
            if(stream!=null)
            eq.displayQuery(stream);
        }


        private String getData(String searchTerm) {
            //ArrayList<String> listItems = new ArrayList<String>();
            URL url = null;
            String stream = null;
            try {
                url = new URL("https://floating-sierra-37389.herokuapp.com/Event/" + searchTerm);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                // tell the server what format we want back
                conn.setRequestProperty("Accept", "text/plain");
                if (conn.getResponseCode() == 200) {
                    // if response code = 200 ok
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    // Read the BufferedInputStream
                    BufferedReader r = new BufferedReader(new InputStreamReader(in));
                    stream = r.readLine();
                    conn.disconnect();
                } else {
                    eq.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(eq, "Data Not Found : Retry search", Toast.LENGTH_SHORT).show();
                        }
                    });
                    conn.disconnect();
                }
            } catch (SocketTimeoutException bug) {
                eq.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(eq, "Network Error : No Data Received.", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stream;

        }
    }
}
