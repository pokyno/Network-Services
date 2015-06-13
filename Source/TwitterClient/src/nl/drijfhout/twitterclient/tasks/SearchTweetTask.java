package nl.drijfhout.twitterclient.tasks;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import nl.drijfhout.twitterclient.TwitterApplication;
import nl.drijfhout.twitterclient.json.JSONParser;
import nl.drijfhout.twitterclient.model.TwitterModel;
import nl.drijfhout.twitterclient.tweet.Tweet;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;

public class SearchTweetTask extends AsyncTask<String,Void,ArrayList<Tweet>>{
	
	private String token;
	private Context context;
	private TwitterModel model;
	
	public SearchTweetTask(String token, Context context){
		this.token = token;
		this.context = context;
		TwitterApplication app = (TwitterApplication) context.getApplicationContext();
		model = app.getModel();
	}
	
	@Override
	protected ArrayList<Tweet> doInBackground(String... params) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		String encoded = "";
		try {
			 encoded = URLEncoder.encode(params[0],	"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpClient client = new DefaultHttpClient();	
		ResponseHandler<String> handler= new BasicResponseHandler();
		HttpGet httpGet = new HttpGet("https://api.twitter.com/1.1/search/tweets.json?q=" + encoded);
		httpGet.setHeader("Authorization", "Bearer " + token);
		try {
			String searchJSON = client.execute(httpGet, handler);
			tweets = new JSONParser(context).getTweets(searchJSON);
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tweets;
	}
	@Override
	protected void onPostExecute(ArrayList<Tweet> result) {
		super.onPostExecute(result);
		model.setTweets(result);
	}
}
