package nl.drijfhout.twitterclient.tweet.entities;

import org.json.JSONException;
import org.json.JSONObject;

public class Hashtags_Entity extends Entity{
	
	private String text;
	
	public Hashtags_Entity(JSONObject entityObject) {
		super(entityObject);
		try {
			this.text = entityObject.getString("text");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getText(){
		return text;
	}
	
	
}
