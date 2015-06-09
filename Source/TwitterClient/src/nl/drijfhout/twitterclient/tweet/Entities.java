package nl.drijfhout.twitterclient.tweet;

import java.util.ArrayList;

import nl.drijfhout.twitterclient.tweet.entities.Entity;
import nl.drijfhout.twitterclient.tweet.entities.Hashtags_Entity;
import nl.drijfhout.twitterclient.tweet.entities.Media_Entity;
import nl.drijfhout.twitterclient.tweet.entities.Url_Entity;
import nl.drijfhout.twitterclient.tweet.entities.User_Mentions_Entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class Entities {

	private ArrayList<Entity> entities;

	// moet nog aan gewerkt worden
	public Entities(JSONObject entities) {
		Log.i("stage2", "reached");
		this.entities = new ArrayList<Entity>();
		try {
			JSONArray urls = entities.getJSONArray("urls");
			for (int counter = 0; counter < urls.length(); counter++) {
				JSONObject obj = urls.getJSONObject(counter);
				this.entities.add(new Url_Entity(obj));
			}
			Log.i("complete", "urls added");
		} catch (JSONException e) {
			Log.i("Fail", "no urls found in the file");
		}

		try {
			JSONArray media = entities.getJSONArray("media");

			for (int counter = 0; counter < media.length(); counter++) {
				JSONObject obj = media.getJSONObject(counter);
				this.entities.add(new Media_Entity(obj));
			}
			Log.i("complete", "media added");
		} catch (JSONException e) {
			Log.i("Fail", "no media found in the file");
		}

		try {
			JSONArray user_mentions = entities.getJSONArray("user_mentions");
			for (int counter = 0; counter < user_mentions.length(); counter++) {
				JSONObject obj = user_mentions.getJSONObject(counter);
				this.entities.add(new User_Mentions_Entity(obj));
			}
			Log.i("complete", "user mentions added");

		} catch (JSONException e) {
			Log.i("Fail", "no user mentions found in the file");
		}

		try {
			JSONArray hashtags = entities.getJSONArray("hashtags");
			for (int counter = 0; counter < hashtags.length(); counter++) {
				JSONObject obj = hashtags.getJSONObject(counter);
				this.entities.add(new Hashtags_Entity(obj));
			}
			Log.i("complete", "hash tags added");
		} catch (JSONException e) {
			Log.i("Fail", "no hashtags found in the file");
		}

	}

	public ArrayList<Url_Entity> getUrls() {
		ArrayList<Url_Entity> returnArray = new ArrayList<Url_Entity>();
		for (Entity e : entities) {
			if (e instanceof Url_Entity) {
				returnArray.add((Url_Entity) e);
				Log.i("return value array", e.toString());
			}
		}
		return returnArray;
	}

	public ArrayList<User_Mentions_Entity> getUser_Mentions() {
		ArrayList<User_Mentions_Entity> returnArray = new ArrayList<User_Mentions_Entity>();
		for (Entity e : entities) {
			if (e instanceof User_Mentions_Entity) {
				returnArray.add((User_Mentions_Entity) e);
			}
		}
		return returnArray;
	}

	public ArrayList<Media_Entity> getMedia() {
		ArrayList<Media_Entity> returnArray = new ArrayList<Media_Entity>();
		for (Entity e : entities) {
			if (e instanceof Media_Entity) {
				returnArray.add((Media_Entity) e);
			}
		}
		return returnArray;
	}

	public ArrayList<Hashtags_Entity> getHashtags() {
		ArrayList<Hashtags_Entity> returnArray = new ArrayList<Hashtags_Entity>();
		for (Entity e : entities) {
			if (e instanceof Hashtags_Entity) {
				returnArray.add((Hashtags_Entity) e);
			}
		}
		return returnArray;
	}
}
