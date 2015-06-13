package nl.drijfhout.twitterclient.model;


import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;
import nl.drijfhout.twitterclient.TwitterApplication;
import nl.drijfhout.twitterclient.login.AuthorizationManager;
import nl.drijfhout.twitterclient.tasks.GetTokenTask;
import nl.drijfhout.twitterclient.tasks.GetUserTask;
import nl.drijfhout.twitterclient.tasks.SearchTweetTask;
import nl.drijfhout.twitterclient.tweet.Tweet;
import nl.drijfhout.twitterclient.tweet.User;

public class TwitterModel extends Observable{
	
	private String user_token; 
	private String user_secret; 
	private User currentUser;
	private static String token = "";
	public static Context context;
	
	public ArrayList<Tweet> tweets;//comment
	
	public TwitterModel(Context context){
		this.context = context;
		GetTokenTask taak = new GetTokenTask();
		user_token = PreferenceManager.getDefaultSharedPreferences(context).getString("TOKEN", "");
		Log.d("token!!", user_token);
		user_secret = PreferenceManager.getDefaultSharedPreferences(context).getString("SECRET", "");
		tweets = new ArrayList<Tweet>();
		
		if(user_token!=""){
			
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
		
		pullCurrentUser(); // voor als de user al eens ingelogd is
	}
	public String getUserToken(){
		return user_token;
	}
	public boolean isLoggedIn(){
		return user_token !="";
	}
	public String getUserSecret(){
		return user_secret;
	}
	
	public void setToken(String user_token){
		this.user_token = user_token;
	}
	public void setUserSecret(String user_secret){
		this.user_secret = user_secret;
	}
	public void searchForTweet(String searchTerm){
		SearchTweetTask task = new SearchTweetTask(token,context);
		
		task.execute(searchTerm);
		
	}
	public void setCurrentUser(User user){
		currentUser = user;
	}
	
	public User getCurrentUser(){
		return currentUser;
	}
	
	public String getToken(){
		return token;
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
	public void pullCurrentUser() {
		GetUserTask task = new GetUserTask(user_token,user_secret,context, this);
		task.execute();
	}
	
}
