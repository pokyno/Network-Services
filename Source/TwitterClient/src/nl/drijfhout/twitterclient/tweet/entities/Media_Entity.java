package nl.drijfhout.twitterclient.tweet.entities;

import org.json.JSONException;
import org.json.JSONObject;

public class Media_Entity extends Entity{
	
	private String media_url;
	private String url;
	private String display_url;
	
	public Media_Entity(JSONObject entityObject) {
		super(entityObject);
		try {
			this.media_url = entityObject.getString("media_url");
			this.url = entityObject.getString("url");
			this.display_url = entityObject.getString("display_url");
		} catch (JSONException e) {
			e.printStackTrace();
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

}
