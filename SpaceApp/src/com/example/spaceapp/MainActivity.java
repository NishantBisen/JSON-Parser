package com.example.spaceapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.os.Build;

public class MainActivity extends ListActivity {
		 
	    private ProgressDialog progress;
	 
	    // URL to get contacts JSON
	    private static String url = "http://mydeatree.appspot.com/api/v1/public_ideas/";
	 
	    // JSON Node names
	    private static final String TAG_object = "objects";
	    private static final String final_title = "title";
	    private static final String final_text = "text";
	    private static final String final_date_created = "created_date";
	   
	 
	    // contacts JSONArray
	    JSONArray objects = null;
	 
	    // Hashmap for ListView
	    ArrayList<HashMap<String, String>> objectList;
	 
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	 
	        objectList = new ArrayList<HashMap<String, String>>();
	 
	        ListView lv = getListView();
	        
	        new GetObjects().execute();
	    }
	 
	    /**
	     * Async task class to get json by making HTTP call
	     * */
	    private class GetObjects extends AsyncTask<Void, Void, Void> {
	 
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            // Showing progress dialog
	            progress = new ProgressDialog(MainActivity.this);
	            progress.setMessage("Please wait...");
	            progress.setCancelable(false);
	            progress.show();
	 
	        }
	 
	        @Override
	        protected Void doInBackground(Void... arg0) {
	            // Creating service handler class instance
	            HTTP http_req = new HTTP();
	 
	            // Making a request to url and getting response
	            String jsonStr = http_req.makeServiceCall(url, HTTP.GET);
	 
	            Log.d("Response: ", "> " + jsonStr);
	 
	            if (jsonStr != null) {
	                try {
	                    JSONObject jsonObj = new JSONObject(jsonStr);
	                     
	                    // Getting JSON Array node
	                    objects = jsonObj.getJSONArray(TAG_object);
	 
	                    // looping through All Contacts
	                    for (int i = 0; i < objects.length(); i++) {
	                        JSONObject c = objects.getJSONObject(i);
	                         
	                        String title = c.getString(final_title);
	                        String text = c.getString(final_text);
	                        String date_created = c.getString(final_date_created);
	                        
	 
	                        // tmp hashmap for single contact
	                        HashMap<String, String> obj = new HashMap<String, String>();
	 
	                        // adding each child node to HashMap key => value
	                        obj.put(final_title, title);
	                        obj.put(final_text, text);
	                        obj.put(final_date_created, date_created);
	 
	                        // adding contact to contact list
	                        objectList.add(obj);
	                    }
	                } catch (JSONException e) {
	                    e.printStackTrace();
	                }
	            } else {
	                Log.e("HTTPHandler", "Couldn't get any data from the url");
	            }
	 
	            return null;
	        }
	 
	        @Override
	        protected void onPostExecute(Void result) {
	            super.onPostExecute(result);
	           
	            if (progress.isShowing())
	                progress.dismiss();
	           
	            ListAdapter adapter = new SimpleAdapter(
	                    MainActivity.this, objectList,
	                    R.layout.detail_list, new String[] { final_title, final_text,
	                            final_date_created }, new int[] { R.id.name,
	                            R.id.email, R.id.mobile });
	 
	            setListAdapter(adapter);
	        }
	 
	    }
	 
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
        
        case R.id.alpha:
        	
        case R.id.latest:
        	Collections.sort(objectList, new Comparator<HashMap<String, String>>()
        			{
        			    @Override
        			    public int compare(HashMap<String, String> a, HashMap<String, String> b)
        			    {
        			        return a.get(final_date_created).compareTo(b.get(final_date_created));
        			    }
        			});
        case R.id.oldest:
        	default:
        		

        }
        return super.onOptionsItemSelected(item);
    }
}
