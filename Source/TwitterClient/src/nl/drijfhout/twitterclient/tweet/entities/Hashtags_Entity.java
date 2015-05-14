package nl.drijfhout.twitterclient.tweet.entities;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Hashtags_Entity extends Entity{
	
	private String text;
	
	public Hashtags_Entity(JSONObject entityObject) {
		super(entityObject);
		try {
			this.text = entityObject.getString("text");
		} catch (JSONException e) {
			Log.i("Fail", "at hashtag");
		}
	}

	public String getText(){
		return text;
	}
	
	
}
