package nl.drijfhout.twitterclient.model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.ExecutionException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import nl.drijfhout.twitterclient.TwitterApplication;
import nl.drijfhout.twitterclient.json.JSONParser;
import nl.drijfhout.twitterclient.tweet.Tweet;

public class TwitterModel extends Observable{
	private	static final String	API_KEY	= "k4xSeNaViDzSHgN1pV4822y8Z";
	private	static	final	String	API_SECRET	= "hcxPzrRWMKmCFLmKLWaegEaMfKPp8jyFCE3SnQQfojrRxvBGne";
	private static String token = "";
	private Context context;
	
	public ArrayList<Tweet> tweets;//comment
	
	public TwitterModel(Context context){
		this.context = context;
		getTokenTask taak = new getTokenTask();
		taak.execute();
	}

	public void searchForTweet(String searchTerm){
		searchTweetTask task = new searchTweetTask();
		
		try {
			tweets = task.execute(searchTerm).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			Log.i("test","null");
			//e.printStackTrace();
		}
	}
	
	public ArrayList<Tweet> getTweets(){
		return tweets;
	}
	
	/////////////////////////////////////////////////////////////////////
	
	public class searchTweetTask extends AsyncTask<String,Void,ArrayList<Tweet>>{
	
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
			setChanged();
			notifyObservers();
		}
		
	}
	
	public class getTokenTask extends AsyncTask<Void,Void,Void>{

		@Override
		protected Void doInBackground(Void... params) {
			
			String	authString	=	API_KEY + ":"	+ API_SECRET;
			
			
			String	base64	=	Base64.encodeToString(authString.getBytes(),Base64.NO_WRAP);	
			
//				Maak een	POST-request	
			
			HttpPost request = new HttpPost("https://api.twitter.com/oauth2/token");	
			
//				Voeg key+secret	toe	
			request.setHeader("Authorization", "Basic "	+	base64);	
			request.setHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");	
//				Vul	body:	vertel dat	je	inlogt	met	app	key	en	secret	
			try {
				request.setEntity(	new StringEntity("grant_type=client_credentials"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			HttpClient client = new DefaultHttpClient();	
			
				
			ResponseHandler<String> handler = new BasicResponseHandler();	
			String result = "";
			try 
			{
				result = client.execute(request, handler);
			} catch (ClientProtocolException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				JSONObject obj = new JSONObject(result);
				token = obj.getString("access_token");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return null;
		}

		
		
	}
}
