package com.delta.pragyan16;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;


public class EventsActivity extends Activity {

    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        t=(TextView)findViewById(R.id.cluster);
        GetEventsAPI g = new GetEventsAPI(EventsActivity.this,getApplicationContext());
        g.execute();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_events, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    class GetEventsAPI extends AsyncTask<Void,Void,Boolean> {                            //   todo call once if the database is empty
        ProgressDialog dialog;
        JSONObject jsonObject = null, descriptionObject = null;
        EventsAdapter eventsAdapter;
        Context context;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Fetching Events Data...");
            //dialog.setCancelable(false);
            dialog.show();
            eventsAdapter = new EventsAdapter(context);
        }

        public GetEventsAPI(Activity activity, Context c){
            this.context = c;
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String jsonstring = null;
            String description = null;
                HttpClient client = new DefaultHttpClient();
            try {
                HttpGet request = new HttpGet(MainActivity.URL+"/events/list");
                HttpResponse response = client.execute(request);
                HttpEntity httpEntity = response.getEntity();
                if(httpEntity != null) {
                    InputStream inputStream = httpEntity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    inputStream.close();
                    jsonstring = sb.toString();
                    Log.i("json",jsonstring);
                    jsonObject = new JSONObject(jsonstring);
                }
                request = new HttpGet(MainActivity.URL+"/events/desclist");
                response = client.execute(request);
                httpEntity = response.getEntity();
                if(httpEntity != null) {
                    InputStream inputStream = httpEntity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    inputStream.close();
                    description = sb.toString();
                    Log.i("json",jsonstring);
                    descriptionObject = new JSONObject(description);
                }
            } catch (IOException e) {
                Log.e("Buffer Error", "Error parsing result " + e.toString());
            }catch (JSONException e){
                Log.e("JSON Error", "Error parsing result " + e.toString());
            }
            if((jsonstring!=null && jsonObject!=null)||(description!=null && descriptionObject!=null)){
                try {
                    if(jsonObject.getJSONArray("data")!=null && descriptionObject.getJSONArray("data")!=null)
                        return true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(dialog.isShowing())
                dialog.dismiss();
            if(aBoolean) {
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    JSONArray descArray = descriptionObject.getJSONArray("data");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject tempjsonObject = jsonArray.getJSONObject(i);
                        JSONObject tempdescObject = descArray.getJSONObject(i);
                        if(tempdescObject.getString("event_name").equals(tempjsonObject.getString("event_name")))
                            Log.i("JSON Events","Mismatched contents in the API");
                        EventInfo eventInfo = new EventInfo();
                        eventInfo.name = tempjsonObject.getString("event_name");
                        eventInfo.id = tempjsonObject.getInt("event_id");
                        eventInfo.cluster = tempjsonObject.getString("event_cluster");
                        eventInfo.start_time = tempjsonObject.getString("event_start_time");
                        eventInfo.end_time = tempjsonObject.getString("event_end_time");
                        eventInfo.last_update_time = tempjsonObject.getString("event_last_update_time");
                        eventInfo.maxlimit = tempjsonObject.getInt("event_max_limit");
                        eventInfo.locx = tempjsonObject.getString("event_loc_x");
                        eventInfo.locy = tempjsonObject.getString("event_loc_y");
                        eventInfo.venue = tempjsonObject.getString("event_venue");
                        eventInfo.date = tempjsonObject.getString("event_date");
                        eventInfo.description = tempdescObject.getString("event_desc");
                        eventsAdapter.add_event(eventInfo);
                    }

                    Log.i("Cluster",eventsAdapter.getCluster().toString());
                    Log.i("Event",eventsAdapter.getEventnamesOfCluster("Amalgam").toString());
                    Log.i("Event Info",eventsAdapter.getEventInfo("Inspinature").description);

                } catch (JSONException e) {
                    Log.i("JSON"," Json exception in on post Execute");
                }
            }else{
                Toast.makeText(getApplicationContext(),"Events data not received",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
