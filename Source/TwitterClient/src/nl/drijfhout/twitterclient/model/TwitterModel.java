package nl.drijfhout.twitterclient.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;
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
	private ArrayList<Tweet> userTimeLine; //deze wordt gebruikt als de userTimeLine en de current userTimeLine omdat die toch nooit tegelijk kunnen worden bekeken
	
	private ArrayList<User> friends;
	
	private ArrayList<User> followers;
	
	private static String token = "";
	
	public static Context context; //dit is gedaan zodat de context makelijk overal te verkrijgen was met name het ophalen van tweets
	
	public ArrayList<Tweet> tweets;
	
	public TwitterModel(Context context){
		TwitterModel.context = context;
		
		GetTokenTask taak = new GetTokenTask();
		user_token = PreferenceManager.getDefaultSharedPreferences(context).getString("TOKEN", "");
		Log.d("token!!", user_token);
		user_secret = PreferenceManager.getDefaultSharedPreferences(context).getString("SECRET", "");
		
		tweets = new ArrayList<Tweet>();
		userTimeLine = new ArrayList<Tweet>();
		friends = new ArrayList<User>();
		followers = new ArrayList<User>();
		
		if(isLoggedIn()){
			pullCurrentUser(); //als de user al eens is ingelogd  wordt de user data opgehaald
		}
		
		try {
			token = taak.execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * clears de user token en user secret
	 */
	public void clearUserToken(){
		setToken("");
		setUserSecret("");
		PreferenceManager.getDefaultSharedPreferences(context).edit().putString("TOKEN", "").commit();
		PreferenceManager.getDefaultSharedPreferences(context).edit().putString("SECRET", "").commit();
	}
	
	public String getUserToken(){
		return user_token;
	}
	
	/**
	 * kijkt of de user is ingelogd
	 * @return true als de user is ingelogd anders false
	 */
	public boolean isLoggedIn(){
		return user_token !="";
	}
	
	public String getUserSecret(){
		return user_secret;
	}
	
	/**
	 * sets de user token
	 * @param user_token 
	 */
	public void setToken(String user_token){
		this.user_token = user_token;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * sets de user secret
	 * @param user_secret
	 */
	public void setUserSecret(String user_secret){
		this.user_secret = user_secret;
	}
	
	/**
	 * zoekt naar de tweets met een async task
	 * @param searchTerm de zoekterm van wat je wilt zoeken
	 */
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
		return new ArrayList<Tweet>(tweets);
	}
	
	/**
	 * roept de onUpdate() aan van alle observers
	 */
	public void refresh(){
		setChanged();
		notifyObservers();
	}
	
	/**
	 * zet de variabel tweets naar de meegegeven parameter
	 * @param tweets een lijst met tweet objecten
	 */
	public void setTweets(ArrayList<Tweet> tweets){
		this.tweets = tweets;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * start een asynctask om de current user op te halen
	 */
	public void pullCurrentUser() {
		GetUserTask task = new GetUserTask(user_token,user_secret,context, this);
		task.execute();
	}
	
	/**
	 * start een asynctask om de current user timeline op te halen
	 */
	public void pullCurrentUserTimeLine() {
		userTimeLine.clear();
		GetCurrentUserTimeLineTask task = new GetCurrentUserTimeLineTask(user_token,user_secret,context, this);
		task.execute();
	}
	
	/**
	 * start een asynctask om een user timeline op te halen
	 * @param id van de eigenaar van de timeline
	 */
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
	 * start de asynctask om de friends van de current user op te halen
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
		return new ArrayList<User>(friends);
	}
	
	
	public void setFriends(ArrayList<User> friends){
		this.friends = friends;
	}
	
	/**
	 * start de asynctask om de followers van de current user op te halen
	 */
	public void pullFollowers() {
		followers.clear();
		GetFollowersTask task = new GetFollowersTask(user_token,user_secret,context, this);
		task.execute();
	}

	public List<User> getFollowers() {
		return new ArrayList<User>(followers);
	}
	
	public void setFollowers(ArrayList<User> followers){
		this.followers = followers;
	}
	
}
