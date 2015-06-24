package nl.drijfhout.twitterclient.tasks;

import java.io.IOException;

import nl.drijfhout.twitterclient.TwitterApplication;
import nl.drijfhout.twitterclient.login.AuthorizationManager;
import oauth.signpost.exception.OAuthException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;

public class RetweetTask extends AsyncTask<String,Void,Void> {

	private AuthorizationManager manager;
	
	public RetweetTask(Context context){
		TwitterApplication app = (TwitterApplication) context.getApplicationContext();
		this.manager = app.getAuthorizationManager();
	}
	
	@Override
	protected Void doInBackground(String... params) {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("https://api.twitter.com/1.1/statuses/retweet/" + params[0] + ".json");
		
		try {
			manager.signWithUserToken(post);
		} catch (OAuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			client.execute(post);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
