package nl.drijfhout.twitterclient.tweet;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	private String name, screen_name;
	public User(JSONObject user){
		try {
			name = user.getString("name");
			screen_name = user.getString("screen_name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getname(){
		return name;
	}
	
	public String getScreen_name(){
		return screen_name;
	}
	
}
