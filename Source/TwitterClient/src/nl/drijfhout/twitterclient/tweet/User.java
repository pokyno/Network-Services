package nl.drijfhout.twitterclient.tweet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import nl.drijfhout.twitterclient.TwitterApplication;
import nl.drijfhout.twitterclient.model.TwitterModel;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class User {
	private String name, screen_name;
	private Bitmap profile_image;
	private String id_str;
	private TwitterModel model; //gebruiken we om te refreshen als de fotos binnen komen
	private String beschrijving;
	private String url;
	private String locatie;
	
	private int followers_count;
	private int friends_count;
	
	
	
	public User(JSONObject user){
		
		TwitterApplication app = (TwitterApplication) TwitterModel.context.getApplicationContext();
		model = app.getModel();
		
		try {
			name = user.getString("name");
			screen_name = user.getString("screen_name");
			id_str = user.getString("id_str");
			beschrijving = user.getString("description");
			url = user.getString("url");
			locatie = user.getString("location");
			friends_count = user.getInt("friends_count");
			followers_count = user.getInt("followers_count");
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try{
			DownloadImagesTask task = new DownloadImagesTask();
			task.execute(user.getString("profile_image_url"));
		}catch(JSONException e){
			
		}
		
	}
	public String getBeschrijving(){
		return beschrijving;
	}
	public String getURL(){
		return url;
	}
	
	public String getLocatie(){
		return locatie;
	}
	public String getStrId(){
		return id_str;
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
	
	/**
	 * deze async word gebruikt om een profile foto van het net op te halen
	 * @author pietervanberkel
	 *
	 */
	public class DownloadImagesTask extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... urls) {
		    return download_Image(urls[0]);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
		   profile_image = result;
		   if(model!=null){
		   model.refresh();
		   }
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
	
	@Override
	public String toString(){
		return name + " " + screen_name;
	}
	public int getFollowersCount() {
		return followers_count;
	}
	
	public int getFollowingCount() {
		return friends_count;
	}
}
