package nl.drijfhout.twitterclient.tweet.entities;

import org.json.JSONException;
import org.json.JSONObject;

public class Url_Entity extends Entity{
	private String url;
	private String display_url;
	
	public Url_Entity(JSONObject entityObject) {
		super(entityObject);
		try {
			this.url = entityObject.getString("url");
			this.display_url = entityObject.getString("display_url");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getUrl(){
		return url;
	}
	
	public String getDisplay_url(){
		return display_url;
	}


}
