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
import android.net.Uri;
import android.os.AsyncTask;

public class UpdateStatusTask extends AsyncTask<String, Void, String> {

	private Context context;
	private AuthorizationManager manager;

	public UpdateStatusTask(Context context) {
		this.context = context;
	}

	@Override
	protected String doInBackground(String... params) {
		TwitterApplication app = (TwitterApplication) context.getApplicationContext();
		this.manager = app.getAuthorizationManager();
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("https://api.twitter.com/1.1/statuses/update.json?status=" + Uri.encode(params[0]));
		try {
			manager.signWithUserToken(httppost);
		} catch (OAuthException e) {
			e.printStackTrace();
		}
		try {
			httpclient.execute(httppost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "gelukt";
	}

}
