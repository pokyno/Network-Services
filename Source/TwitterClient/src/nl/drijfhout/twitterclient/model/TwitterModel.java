package nl.drijfhout.twitterclient.model;


import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;
import nl.drijfhout.twitterclient.login.AuthorizationManager;
import nl.drijfhout.twitterclient.tasks.GetTokenTask;
import nl.drijfhout.twitterclient.tasks.SearchTweetTask;
import nl.drijfhout.twitterclient.tweet.Tweet;

public class TwitterModel extends Observable{
	
	private String user_token; 
	private String user_secret; 
	private static String token = "";
	public static Context context;
	private AuthorizationManager manager;
	public ArrayList<Tweet> tweets;//comment
	
	public TwitterModel(Context context){
		this.context = context;
		GetTokenTask taak = new GetTokenTask();
		user_token = PreferenceManager.getDefaultSharedPreferences(context).getString("TOKEN", "");
		Log.d("token!!", user_token);
		user_secret = PreferenceManager.getDefaultSharedPreferences(context).getString("SECRET", "");
		tweets = new ArrayList<Tweet>();
		manager = new AuthorizationManager();
		manager.init();
		if(user_token!=""){
		manager.setTokenAndSecret(user_token,user_secret);
		}
		try {
			token = taak.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getUserToken(){
		return user_token;
	}
	public AuthorizationManager getManager(){
		return manager;
	}
	public void searchForTweet(String searchTerm){
		SearchTweetTask task = new SearchTweetTask(token,context);
		
		task.execute(searchTerm);
		
	}
	public void setAccesToken(String verifier){
		manager.setAccesToken(verifier);
		String token = manager.getConsumerToken();
		String secret = manager.getConsumerTokenSecret();
		PreferenceManager.getDefaultSharedPreferences(context).edit().putString("TOKEN", token).commit();
		PreferenceManager.getDefaultSharedPreferences(context).edit().putString("SECRET", secret).commit();
	}
	public ArrayList<Tweet> getTweets(){
		return tweets;
	}
	
	public void refresh(){
		setChanged();
		notifyObservers();
	}
	
	public void setTweets(ArrayList<Tweet> tweets){
		this.tweets = tweets;
		setChanged();
		notifyObservers();
	}
	
}
