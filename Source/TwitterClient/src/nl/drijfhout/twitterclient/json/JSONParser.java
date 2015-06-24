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
	private ArrayList<Tweet> tweets;// kan voor beide aanvragen gebruikt worden want ze bestaan toch nooit tegelijk
	private Context context;
	
	public JSONParser(Context context){
		tweets = new ArrayList<Tweet>();
		this.context = context;
	}
	
	/**
	 * geeft een user object terug met als invoer een json based String
	 * @param userJSON json string met de user gegevens erin
	 * @return het gemaakte user object
	 * @throws IOException
	 */
	public User getUser(String userJSON) throws IOException {
		User user = null;
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
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
	
	/**
	 * geeft een lijst met user objecten terug met als invoer een json based String
	 * @param usersJSON	json string met alle user gegevens erin
	 * @return de lijst met gemaakte user objecten
	 * @throws IOException
	 */
	public ArrayList<User> getUsers(String usersJSON) throws IOException{
		ArrayList<User> userlist = new ArrayList<User>();
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
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
	
	/**
	 * geeft een lijst met Tweets(statusen) terug met als invoer een json based string
	 * @param statusJSON json string met de statusen erin
	 * @return	de lijst met de gemaakte Tweet objecten
	 */
	public ArrayList<Tweet> getTweets(String statusJSON){// tijdenlijk met file name
		try {
			readAssetIntoString(statusJSON,false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tweets;
	}
	
	/**
	 * geeft een lijst met Tweets(statusen) terug met als invoer een json based string
	 * @param statusJSON json string met de statusen erin
	 * @return	de lijst met de gemaakte Tweet objecten(time line)
	 */
	public ArrayList<Tweet> getTimeLine(String statusJSON){// tijdenlijk met file name
		try {
			readAssetIntoString(statusJSON,true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tweets;
	}
	
	/**
	 * leest de json uit en stopt de tweet objecten in de lijst(instantie variable)
	 * @param filename de in te lezen json
	 * @param isTimeLine geeft aan of het een timeline is of niet	
	 * @throws IOException
	 */
	private void readAssetIntoString(String filename,boolean isTimeLine) throws IOException {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		
		String line;
		try {
			InputStream is =  new ByteArrayInputStream(filename.getBytes(StandardCharsets.UTF_8));
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			
			try {
				JSONArray statussen;
				if(isTimeLine){//als het een timeline is moet minder gebeuren dus vandaar de isTimeLine
					statussen = new JSONArray(sb.toString());
				}else{
					JSONObject status = new JSONObject(sb.toString());
					statussen = status.getJSONArray("statuses");
				}
				
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
	}
	
}
