package nl.drijfhout.twitterclient.tweet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Entities {
	private JSONArray urls, media, hashtags, user_mentions;
	
	// moet nog aan gewerkt worden
	public Entities(JSONObject entities){
		try {
			urls = entities.getJSONArray("urls");
			media = entities.getJSONArray("media");
			hashtags = entities.getJSONArray("hashtags");
			user_mentions = entities.getJSONArray("user_mentions");
		} catch (JSONException e) {
			
		}
	}
	
	public JSONArray getURLs(){
		return urls;
	}
	
	public JSONArray getHashtags(){
		return hashtags;
	}
}
