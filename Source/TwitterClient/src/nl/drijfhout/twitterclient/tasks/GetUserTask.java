package nl.drijfhout.twitterclient.tasks;

import java.io.IOException;
import oauth.signpost.exception.OAuthException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import nl.drijfhout.twitterclient.TwitterApplication;
import nl.drijfhout.twitterclient.json.JSONParser;
import nl.drijfhout.twitterclient.login.AuthorizationManager;
import nl.drijfhout.twitterclient.model.TwitterModel;
import nl.drijfhout.twitterclient.tweet.User;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class GetUserTask extends AsyncTask<String, Void, User> {
	private String user_token;
	private String user_secret;
	private Context context;
	private TwitterModel model;
	private AuthorizationManager manager;

	public GetUserTask(String token, String secret, Context context, TwitterModel model) {
		this.user_token = token;
		this.user_secret = secret;
		this.context = context;
		TwitterApplication app = (TwitterApplication) context.getApplicationContext();
		this.model = model;
		this.manager = app.getAuthorizationManager();
	}

	@Override
	protected User doInBackground(String... params) {

		User user = null;
		manager.setTokenAndSecret(user_token, user_secret);

		DefaultHttpClient mClient = new DefaultHttpClient();

		HttpGet get = new HttpGet("https://api.twitter.com/1.1/account/verify_credentials.json");
		try {
			manager.signWithUserToken(get);
			String response = mClient.execute(get, new BasicResponseHandler());
			user = new JSONParser(context).getUser(response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (OAuthException e) {
			e.printStackTrace();
		}
		
		return user;

	}

	@Override
	protected void onPostExecute(User result) {
		super.onPostExecute(result);
		if (result != null) {
			model.setCurrentUser(result);
		} else {
			Log.i("fail", "current user retuned null");
		}
	}

}
