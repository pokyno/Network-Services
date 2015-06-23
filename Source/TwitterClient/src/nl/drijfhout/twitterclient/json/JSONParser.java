package nl.drijfhout.twitterclient.json;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import nl.drijfhout.twitterclient.tweet.Tweet;
import nl.drijfhout.twitterclient.tweet.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class JSONParser {
	private ArrayList<Tweet> tweets;
	private Context context;
	
	public JSONParser(Context context){
		tweets = new ArrayList<Tweet>();
		this.context = context;
		
	}
	
	public ArrayList<Tweet> getTweets(String filename){// tijdenlijk met file name
		try {
			readAssetIntoString(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tweets;
	}
	
	private String readAssetIntoString(String filename) throws IOException {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String text = "";
		String line;
		try {
			InputStream is =  new ByteArrayInputStream(filename.getBytes(StandardCharsets.UTF_8));
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			
			try {
				JSONObject status = new JSONObject(sb.toString());
				JSONArray statussen = status.getJSONArray("statuses");
				
				for(int i = 0; i<statussen.length();i++){
					JSONObject tweet = statussen.getJSONObject(i);
					tweets.add(new Tweet(tweet, context));
				}
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
            throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return text;		
	}

	public User getUser(String userJSON) throws IOException {
		User user = null;
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String text = "";
		String line;
		try {
			InputStream is =  new ByteArrayInputStream(userJSON.getBytes(StandardCharsets.UTF_8));
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			try {
			JSONObject userJ = new JSONObject(sb.toString());
			user = new User(userJ);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
            throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return user;
	}
	
	public ArrayList<User> getUsers(String usersJSON) throws IOException{
		ArrayList<User> userlist = new ArrayList<User>();
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String text = "";
		String line;
		try {
			InputStream is =  new ByteArrayInputStream(usersJSON.getBytes(StandardCharsets.UTF_8));
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			
			try {
				JSONObject friendsObject = new JSONObject(sb.toString());
				JSONArray users = friendsObject.getJSONArray("users"); 
				
				for(int i = 0; i<users.length();i++){
					JSONObject user = users.getJSONObject(i);
					Log.i("userJSON", user+"");
					userlist.add(new User(user));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
            throw e;
		}
		return userlist;
	}
	
	
	//kan ook in een ff overleggen met frank
	public ArrayList<Tweet> getTimeLine(String filename){// tijdenlijk met file name
		try {
			readTimeLineAssetsIntoString(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tweets;
	}
	
	private String readTimeLineAssetsIntoString(String filename) throws IOException {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String text = "";
		String line;
		try {
			InputStream is =  new ByteArrayInputStream(filename.getBytes(StandardCharsets.UTF_8));
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			
			try {
				JSONArray statussen = new JSONArray(sb.toString());
				
				for(int i = 0; i<statussen.length();i++){
					JSONObject tweet = statussen.getJSONObject(i);
					tweets.add(new Tweet(tweet, context));
				}
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
            throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return text;		
	}
	
	
	
}
