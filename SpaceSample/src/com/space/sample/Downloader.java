package com.space.sample;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class Downloader {

	
	//Download the file from given url and store it locally
	public static void DownloadfromURL(String URL,FileOutputStream fos){
		
		try {
			URL url = new URL(URL);//URL of the file
			
			//open the connection to that URL
			URLConnection connection  = url.openConnection();
			
			//Define input stream to read from the URL connection
			InputStream in = connection.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(in);
			
			//Define output stream to write to our file
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			
			//start reading from the url and from writing to or file
			byte data[] = new byte[1024];
			
			int count;
			//loop and  read the current chunk
			while((count = bis.read(data))!= -1){
				
				bos.write(data, 0, count);
			}
			
			bos.flush();
			bos.close();
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
