package nl.drijfhout.twitterclient.model;


import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.ExecutionException;
import android.content.Context;
import nl.drijfhout.twitterclient.tasks.GetTokenTask;
import nl.drijfhout.twitterclient.tasks.SearchTweetTask;
import nl.drijfhout.twitterclient.tweet.Tweet;

public class TwitterModel extends Observable{
	
	private static String token = "";
	public static Context context;
	
	public ArrayList<Tweet> tweets;//comment
	
	public TwitterModel(Context context){
		this.context = context;
		GetTokenTask taak = new GetTokenTask();
		tweets = new ArrayList<Tweet>();
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

	public void searchForTweet(String searchTerm){
		SearchTweetTask task = new SearchTweetTask(token,context);
		
		task.execute(searchTerm);
		
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
