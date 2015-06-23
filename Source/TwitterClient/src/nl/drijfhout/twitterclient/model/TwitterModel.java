package nl.drijfhout.twitterclient.model;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;
import nl.drijfhout.twitterclient.LoginActivity;
import nl.drijfhout.twitterclient.TwitterApplication;
import nl.drijfhout.twitterclient.login.AuthorizationManager;
import nl.drijfhout.twitterclient.tasks.GetCurrentUserTimeLineTask;
import nl.drijfhout.twitterclient.tasks.GetFollowersTask;
import nl.drijfhout.twitterclient.tasks.GetFriendsTask;
import nl.drijfhout.twitterclient.tasks.GetTokenTask;
import nl.drijfhout.twitterclient.tasks.GetUserTask;
import nl.drijfhout.twitterclient.tasks.GetUserTimeLineTask;
import nl.drijfhout.twitterclient.tasks.SearchTweetTask;
import nl.drijfhout.twitterclient.tweet.Tweet;
import nl.drijfhout.twitterclient.tweet.User;

public class TwitterModel extends Observable{
	
	private String user_token; 
	private String user_secret; 
	
	//alles van de current user
	private User currentUser;
	private ArrayList<Tweet> userTimeLine;
	
	//friends
	private ArrayList<User> friends;
	
	private ArrayList<User> followers;
	
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
		friends = new ArrayList<User>();
		followers = new ArrayList<User>();
		
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
	
	public void setUserTimeLine(ArrayList<Tweet> statusus){
		this.userTimeLine = statusus;
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
	
	public void pullCurrentUserTimeLine() {
		userTimeLine.clear();
		GetCurrentUserTimeLineTask task = new GetCurrentUserTimeLineTask(user_token,user_secret,context, this);
		task.execute();
	}
	
	public void pullUserTimeLine(String id){
		userTimeLine.clear();
		GetUserTimeLineTask task = new GetUserTimeLineTask(user_token,user_secret,context, this,id);
		task.execute();
	}
	
	/**
	 * geeft het user object bij een specefiek id
	 * @param id het id van de tevinden persoon
	 * @return user bij het id
	 */
	public User getSelectedUser(String id) {
		User user = null;
		for(Tweet t : userTimeLine){
			User temp = t.getUser(); //tijdelijke vast houden voor check
			if(temp.getStrId().equals(id)){
				user = temp;
			}
		}
		for(Tweet t : tweets){
			User temp = t.getUser(); //tijdelijke vast houden voor check
			if(temp.getStrId().equals(id)){
				user = temp;
			}
		}
		for(User u: friends){
			if(u.getStrId().equals(id)){
				user = u;
			}
		}
		for(User u : followers){
			if(u.getStrId().equals(id)){
				user = u;
			}
		}
		return user;
	}
	
	
	/**
	 * start de task om de friends van de current user op te halen
	 */
	public void pullFriends() {
		friends.clear();
		GetFriendsTask task = new GetFriendsTask(user_token,user_secret,context, this);
		task.execute();
	}
	/**
	 * geeft een lijst met de current users zijn frienden
	 * @return friends van current user
	 */
	public ArrayList<User> getFriends() {
		return friends;
	}
	
	
	public void setFriends(ArrayList<User> friends){
		this.friends = friends;
	}
	
	/**
	 * start de task om de followers van de current user op te halen
	 */
	public void pullFollowers() {
		followers.clear();
		GetFollowersTask task = new GetFollowersTask(user_token,user_secret,context, this);
		task.execute();
	}

	public List<User> getFollowers() {
		return followers;
	}
	
	public void setFollowers(ArrayList<User> followers){
		this.followers = followers;
	}
	
}
