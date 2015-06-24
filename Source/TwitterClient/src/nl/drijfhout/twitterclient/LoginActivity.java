package nl.drijfhout.twitterclient;


import nl.drijfhout.twitterclient.login.AuthorizationManager;
import nl.drijfhout.twitterclient.model.TwitterModel;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginActivity extends Activity {
	private WebView wv;
	private TwitterApplication app;
	private TwitterModel model;
	private AuthorizationManager manager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		app = (TwitterApplication) getApplicationContext();
		model = app.getModel();
		setContentView(R.layout.activity_login);
		wv = (WebView)this.findViewById(R.id.webView1);
		
		manager = app.getAuthorizationManager();
		
		if(model.getUserToken() != ""){
			manager.setTokenAndSecret(model.getUserToken(),model.getUserSecret());
			wv.setVisibility(View.GONE);
		}else{
		
		
		
		wv.loadUrl(manager.getAuthorizeUrl());
		
		wv.setWebViewClient(new WebViewClient(){
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view,String url){
				String callback = "http://www.saxion.nl/pgrtwitter/authenticated";
				if(url.startsWith(callback)){
					 Uri uriurl = Uri.parse(url);
					String verifier = uriurl.getQueryParameters("oauth_verifier").get(0);
					manager.setAccesToken(verifier);
					String token = manager.getConsumerToken();
					String secret = manager.getConsumerTokenSecret();
					model.setToken(token);
					model.setUserSecret(secret);
					model.pullCurrentUser(); //voor na het inloggen om meteen de current user op te halen
					PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putString("TOKEN", token).commit();
					PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putString("SECRET", secret).commit();
					finish();
					return true;
				}
				return false;
			}
			
		});
		
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
