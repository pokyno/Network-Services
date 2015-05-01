package nl.drijfhout.twitterclient.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import android.content.res.AssetManager;
import nl.drijfhout.twitterclient.tweet.Tweet;

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
			InputStream is = context.getAssets().open(filename, AssetManager.ACCESS_BUFFER);
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
	
	
	
}
