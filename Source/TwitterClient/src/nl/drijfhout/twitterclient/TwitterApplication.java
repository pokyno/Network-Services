package nl.drijfhout.twitterclient;

import nl.drijfhout.twitterclient.login.AuthorizationManager;
import nl.drijfhout.twitterclient.model.TwitterModel;
import android.app.Application;

public class TwitterApplication extends Application{
	private TwitterModel model;
	private AuthorizationManager manager;
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		manager = new AuthorizationManager();
		manager.init();
		model = new TwitterModel(getApplicationContext());
	}
	
	public AuthorizationManager getAuthorizationManager(){
		return manager;
	}
	public TwitterModel getModel(){
		return model;
	}
}
