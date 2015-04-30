package nl.drijfhout.twitterclient;

import nl.drijfhout.twitterclient.model.TwitterModel;
import android.app.Application;

public class TwitterApplication extends Application{
	private TwitterModel model;

	@Override
	public void onCreate() {
		super.onCreate();
		model = new TwitterModel();
	}
	
	public TwitterModel getModel(){
		return model;
	}
}
