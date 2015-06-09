package nl.drijfhout.twitterclient.tweet.entities;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import nl.drijfhout.twitterclient.TwitterApplication;
import nl.drijfhout.twitterclient.model.TwitterModel;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class Media_Entity extends Entity{
	
	private String media_url;
	private String url;
	private String display_url;
	private Bitmap image;
	private TwitterModel model;
	
	public Media_Entity(JSONObject entityObject) {
		super(entityObject);
		
		TwitterApplication app = (TwitterApplication) TwitterModel.context.getApplicationContext();
		model = app.getModel();
		
		try {
			this.media_url = entityObject.getString("media_url");
			this.url = entityObject.getString("url");
			this.display_url = entityObject.getString("display_url");
		} catch (JSONException e) {
			Log.i("Fail", "at media");
		}
		try{
			DownloadImagesTask task = new DownloadImagesTask();
			task.execute(entityObject.getString("media_url"));
		}catch(JSONException e){
			
		}
		
	}
	
	public String getMedia_url(){
		return media_url;
	}
	
	public String getUrl(){
		return url;
	}
	
	public String getDisplay_Url(){
		return display_url;
	}
	
	public Bitmap getImage(){
		return image;
	}
	
	public class DownloadImagesTask extends AsyncTask<String, Void, Bitmap> {
		
		@Override
		protected Bitmap doInBackground(String... urls) {
		    return download_Image(urls[0]);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
		   image = result;
		   model.refresh();
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
