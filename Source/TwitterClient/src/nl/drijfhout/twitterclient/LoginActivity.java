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
		
		//start het initieren van authenticatie, door een authorize url aan te maken
		app.initAuthentication();
		model = app.getModel();
		setContentView(R.layout.activity_login);
		wv = (WebView)this.findViewById(R.id.webView1);
		manager = app.getAuthorizationManager();
		
		
		//laad de webview met de authorizeurl van de manager
		wv.loadUrl(manager.getAuthorizeUrl());
		
		wv.setWebViewClient(new WebViewClient(){
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view,String url){
				//Kijkt of de callback gelijk staat aan de callback van de applicatie.
				String callback = "http://www.saxion.nl/pgrtwitter/authenticated";
				if(url.startsWith(callback)){
					 Uri uriurl = Uri.parse(url);
					String verifier = uriurl.getQueryParameters("oauth_verifier").get(0);
					//haalt het accesstoken op
					manager.setAccesToken(verifier);
					//set zowel de token als secret in het model
					String token = manager.getConsumerToken();
					String secret = manager.getConsumerTokenSecret();
					model.setToken(token);
					model.setUserSecret(secret);
					model.pullCurrentUser(); //voor na het inloggen om meteen de current user op te halen
					//token en secret worden opgeslagen in sharedpreferences zodat als de applicatie opnieuw wordt gestart, deze opgehaald kunnnen worden.
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
