package com.space.sample;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import com.example.spacesample.R;

public class MainActivity extends ActionBarActivity {

	ConnectionCheck connectionCheck;
	ArrayList<SpaceData> listFromData;
	Context context;
	SpaceAdapter adapter;
	ListView listView;
	RadioGroup radioGroup;
	RadioButton titleRadio,oldestRadio,latestRadio;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initializeData();
		
		/*
		 * If network is available download the JSON from the Internet.
		 * If not then try to use the local file from last time.
		 */
		if(new ConnectionCheck(this).isNetworkAvailable()){
			//if available download the file from internet
			//download from internet
			DownloadTask  download = new DownloadTask();
			download.execute();				
		}else{
			//if not available try using local file
			Toast.makeText(context,"Please Check Internet Connection", Toast.LENGTH_LONG).show();
			Collections.sort(listFromData, SpaceData.titleComparator);
			displayData();	
		}
		
		radioButtonListener();
	}

	private void radioButtonListener() {
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioSort);        
	    radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() 
	    {
	        public void onCheckedChanged(RadioGroup group, int checkedId) {
	           switch (checkedId) {
			case R.id.radioSortTitle:
				Collections.sort(listFromData, SpaceData.titleComparator);
				displayData();
				break;
			case R.id.radioLatestFirst:
				Collections.sort(listFromData, SpaceData.dateComparatorLatest);
				displayData();
				break;
			case R.id.radioOldestFirst:
				Collections.sort(listFromData, SpaceData.dateComparatorOldest);
				displayData();
				break;
			default:
				break;
			}
	        }
	    });
		
	}

	private void initializeData() {
		listView = (ListView) findViewById(R.id.listView);
		listFromData = new ArrayList<SpaceData>();
		connectionCheck = new ConnectionCheck(this);
		context = getApplicationContext();	
		radioGroup = (RadioGroup) findViewById(R.id.radioSort);
		latestRadio = (RadioButton) findViewById(R.id.radioLatestFirst);
		oldestRadio = (RadioButton) findViewById(R.id.radioOldestFirst);
		titleRadio = (RadioButton) findViewById(R.id.radioSortTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.datecreated_latest:
			//Display data by latest date first
			Collections.sort(listFromData, SpaceData.dateComparatorLatest);
			radioGroup.check(R.id.radioLatestFirst);
			displayData();
			break;
		case R.id.datecreated_oldest:
			//Display data by oldest date first
			Collections.sort(listFromData, SpaceData.dateComparatorOldest);
			radioGroup.check(R.id.radioOldestFirst);
			displayData();
			break;
		case R.id.alphabetically:
			//Display data by title
			Collections.sort(listFromData, SpaceData.titleComparator);
			radioGroup.check(R.id.radioSortTitle);
			displayData();
			break;
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}

	private class DownloadTask extends AsyncTask<Void, Void, Void>{
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
		}
		@Override
		protected Void doInBackground(Void... params) {
			//Download the file from internet
			Log.v("in download task", "in async task");
			try {
				Downloader.DownloadfromURL(Constants.URL, openFileOutput("spacedata.json", Context.MODE_PRIVATE));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			 super.onPostExecute(result);
			
			 listFromData = new JSONParser().JSONPParsing(context);
			 Collections.sort(listFromData, SpaceData.titleComparator);
			 displayData();
		}
	}

	private void displayData() {
		adapter = new SpaceAdapter(MainActivity.this, -1, listFromData);
		listView.setAdapter(adapter);		
	}
	
	
}