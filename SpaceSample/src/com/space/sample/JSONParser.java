package com.space.sample;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.content.Context;


@SuppressLint("SimpleDateFormat")
public class JSONParser {
	
	Context context;
	
	public JSONParser() {
		// TODO Auto-generated constructor stub
	}
	
	public  ArrayList<SpaceData> JSONPParsing(Context context){
		
		JSONArray contacts = null;
		String jsonStr = null;
		ArrayList<SpaceData> listOfData = new ArrayList<SpaceData>();
        try {
        	
            FileInputStream is = context.openFileInput("spacedata.json");
            
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonStr = new String(buffer, "UTF-8");
            
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                     
                    // Getting JSON Array node
                    contacts = jsonObj.getJSONArray(Constants.TAG_OBJECTS);
                    
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject object = contacts.getJSONObject(i);
                         
                        String title = object.getString(Constants.TAG_TITLE);
                        String text = object.getString(Constants.TAG_TEXT);
                        String createdDate = object.getString(Constants.TAG_DATECREATED);
                        
                        if(createdDate.contains("T")){
                        	createdDate = createdDate.substring(0,createdDate.indexOf("T"));
                        }
                        
                        Date date = new SimpleDateFormat("yyyy-mm-dd").parse(createdDate);
                        SpaceData data = new SpaceData();
                        data.setText(text);
                        data.setTitle(title);
                        data.setCreatedDate(date);
 
                        listOfData.add(data);
                    }
                }catch(Exception e){
                	e.printStackTrace();
                	
                }
                return listOfData;
            }
        }catch(Exception e){
        	e.printStackTrace();
        }
		return null;
	}
       
}
