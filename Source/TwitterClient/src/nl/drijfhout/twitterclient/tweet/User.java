package nl.drijfhout.twitterclient.tweet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class User {
	private String name, screen_name;
	private Bitmap profile_image;
	
	public User(JSONObject user){
		try {
			name = user.getString("name");
			screen_name = user.getString("screen_name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try{
			DownloadImagesTask task = new DownloadImagesTask();
			task.execute(user.getString("profile_image_url"));
		}catch(JSONException e){
			
		}
		
	}
	
	public String getname(){
		return name;
	}
	
	public String getScreen_name(){
		return screen_name;
	}
	
	public Bitmap getProfile_image(){
		return profile_image;
	}
	
	public class DownloadImagesTask extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... urls) {
		    return download_Image(urls[0]);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
		   profile_image = result;
		}


		private Bitmap download_Image(String url) {
		    //---------------------------------------------------
		    Bitmap bm = null;
		    try {
		        URL aURL = new URL(url);
		        URLConnection conn = aURL.openConnection();
		        conn.connect();
		        InputStream is = conn.getInputStream();
		        BufferedInputStream bis = new BufferedInputStream(is);
		        bm = BitmapFactory.decodeStream(bis);
		        bis.close();
		        is.close();
		    } catch (IOException e) {
		        Log.e("Hub","Error getting the image from server : " + e.getMessage().toString());
		    } 
		    return bm;
		    //---------------------------------------------------
		}
	}
}
