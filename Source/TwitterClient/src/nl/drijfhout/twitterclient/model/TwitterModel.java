package nl.drijfhout.twitterclient.model;


import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;
import nl.drijfhout.twitterclient.LoginActivity;
import nl.drijfhout.twitterclient.TwitterApplication;
import nl.drijfhout.twitterclient.login.AuthorizationManager;
import nl.drijfhout.twitterclient.tasks.GetTimeLineTask;
import nl.drijfhout.twitterclient.tasks.GetTokenTask;
import nl.drijfhout.twitterclient.tasks.GetUserTask;
import nl.drijfhout.twitterclient.tasks.SearchTweetTask;
import nl.drijfhout.twitterclient.tweet.Tweet;
import nl.drijfhout.twitterclient.tweet.User;

public class TwitterModel extends Observable{
	
	private String user_token; 
	private String user_secret; 
	
	//alles van de current user
	private User currentUser;
	public ArrayList<Tweet> userTimeLine;
	
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
		userTimeLine = new ArrayList<Tweet>();
		
		if(user_token!=""){
			pullCurrentUser(); // voor als de user al eens ingelogd is
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
	
	public void clearUserToken(){
		setToken("");
		setUserSecret("");
		PreferenceManager.getDefaultSharedPreferences(context).edit().putString("TOKEN", "").commit();
		PreferenceManager.getDefaultSharedPreferences(context).edit().putString("SECRET", "").commit();
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
		setChanged();
		notifyObservers();
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
	
	public ArrayList<Tweet> getUserTimeLine(){
		return new ArrayList<Tweet>(userTimeLine);
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
	
	public void pullUserTimeLine() {
		GetTimeLineTask task = new GetTimeLineTask(user_token,user_secret,context, this);
		task.execute();
	}
	
}
