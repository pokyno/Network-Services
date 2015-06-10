package nl.drijfhout.twitterclient;


import nl.drijfhout.twitterclient.model.TwitterModel;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginActivity extends Activity {
	private WebView wv;
	private TwitterApplication app;
	private TwitterModel model;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (TwitterApplication) getApplicationContext();
		model = app.getModel();
		setContentView(R.layout.activity_login);
		wv = (WebView)this.findViewById(R.id.webView1);
		
		wv.loadUrl(model.getManager().getAuthorizeUrl());
		
		wv.setWebViewClient(new WebViewClient(){
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view,String url){
				String callback = "http://www.saxion.nl/pgrtwitter/authenticated";
				if(url.startsWith(callback)){
					 Uri uriurl = Uri.parse(url);
					String verifier = uriurl.getQueryParameters("oauth_verifier").get(0);
					model.setAccesToken(verifier);
					return true;
				}
				return false;
			}
			
		});
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
