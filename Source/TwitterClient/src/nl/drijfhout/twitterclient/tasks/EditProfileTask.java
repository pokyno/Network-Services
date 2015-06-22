package nl.drijfhout.twitterclient.tasks;

import java.io.IOException;

import oauth.signpost.exception.OAuthException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import nl.drijfhout.twitterclient.TwitterApplication;
import nl.drijfhout.twitterclient.login.AuthorizationManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

public class EditProfileTask extends AsyncTask<Void,Void,Void> {

	private String naam,url,locatie,beschrijving;
	private AuthorizationManager manager;
	private Context context;
	
	public EditProfileTask(String naam,String url,String locatie,String beschrijving,Context context){
		this.naam = naam;
		this.url = url;
		this.locatie = locatie;
		this.beschrijving = beschrijving;
		this.context = context;
		TwitterApplication app = (TwitterApplication) context.getApplicationContext();
		this.manager = app.getAuthorizationManager();
		
	}
	
	
	@Override
	protected Void doInBackground(Void... params) {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("https://api.twitter.com/1.1/account/update_profile.json?name=" + Uri.encode(naam) + "&description=" + Uri.encode(beschrijving) + "&url=" + Uri.encode(url) + "&location=" + Uri.encode(locatie));
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
