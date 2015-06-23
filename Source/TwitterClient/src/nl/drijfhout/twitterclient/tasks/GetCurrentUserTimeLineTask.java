package nl.drijfhout.twitterclient.tasks;

import java.io.IOException;
import java.util.ArrayList;

import oauth.signpost.exception.OAuthException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import nl.drijfhout.twitterclient.TwitterApplication;
import nl.drijfhout.twitterclient.json.JSONParser;
import nl.drijfhout.twitterclient.login.AuthorizationManager;
import nl.drijfhout.twitterclient.model.TwitterModel;
import nl.drijfhout.twitterclient.tweet.Tweet;
import nl.drijfhout.twitterclient.tweet.User;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class GetCurrentUserTimeLineTask extends AsyncTask<String, Void, ArrayList<Tweet>> {
	private String user_token;
	private String user_secret;
	private Context context;
	private TwitterModel model;
	private AuthorizationManager manager;

	public GetCurrentUserTimeLineTask(String token, String secret, Context context,
			TwitterModel model) {
		this.user_token = token;
		this.user_secret = secret;
		this.context = context;
		TwitterApplication app = (TwitterApplication) context
				.getApplicationContext();
		this.model = model;
		this.manager = app.getAuthorizationManager();
	}

	@Override
	protected ArrayList<Tweet> doInBackground(String... params) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		
		manager.setTokenAndSecret(user_token, user_secret);

		DefaultHttpClient mClient = new DefaultHttpClient();

		HttpGet get = new HttpGet("https://api.twitter.com/1.1/statuses/home_timeline.json");
		try {
			manager.signWithUserToken(get);
			String response = mClient.execute(get, new BasicResponseHandler());
			Log.i("response", response);
			tweets = new JSONParser(context).getTimeLine(response);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tweets;
	}

	@Override
	protected void onPostExecute(ArrayList<Tweet> result) {
		super.onPostExecute(result);
		model.setUserTimeLine(result);
		model.notifyObservers();
	}
	
	

}
