package nl.drijfhout.twitterclient.tweet.entities;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class User_Mentions_Entity extends Entity {
	private String id_str;
	private String screen_name;
	private String name;

	public User_Mentions_Entity(JSONObject entityObject) {
		super(entityObject);
		try {
			this.id_str = entityObject.getString("id_str");
			this.screen_name = entityObject.getString("screen_name");
			this.name = entityObject.getString("name");
		} catch (JSONException e) {
			Log.i("Fail", "at user mentions");
		}

	}

	public String getId_str() {
		return id_str;
	}

	public String getScreen_name() {
		return screen_name;
	}

	public String getName() {
		return name;
	}

}
