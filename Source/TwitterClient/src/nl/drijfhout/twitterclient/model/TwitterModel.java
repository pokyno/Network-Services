package nl.drijfhout.twitterclient.model;

import java.util.ArrayList;

import android.content.Context;
import nl.drijfhout.twitterclient.json.JSONParser;
import nl.drijfhout.twitterclient.tweet.Tweet;

public class TwitterModel{
	private ArrayList<Tweet> tweets;
	
	public TwitterModel(){
		
	}
	
	/**
	 * pulls the tweets and then returns the list with tweets 
	 * @param context   activity context
	 * @return list with tweets
	 */
	public ArrayList<Tweet> getTweets(Context context){
		tweets = new JSONParser(context).getTweets("file.json");
		return tweets;
	}

	public Tweet getTweet(int index , Context context){
		if(tweets.isEmpty()){
			getTweets(context);
		}
		return tweets.get(index);
	}

}
