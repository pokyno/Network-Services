package nl.drijfhout.twitterclient;

import java.util.Observable;
import java.util.Observer;

import nl.drijfhout.twitterclient.model.TwitterModel;
import nl.drijfhout.twitterclient.tweet.User;
import nl.drijfhout.twitterclient.view.TweetAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class UserProfileActivity extends Activity implements Observer{
	public static final int CURRENT_PROFILE = 0;
	private int function;
	private User user;
	private TwitterModel model;
	private TweetAdapter adapter;
	private ImageView profile_image;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		
		TwitterApplication app = (TwitterApplication) getApplicationContext();
		model = app.getModel();
		model.addObserver(this);
		model.context = this; //om de context naar deze activitie te zetten
		
		//optie voor later veranderen
		
		function = getIntent().getIntExtra("function", 0);
		if(function == CURRENT_PROFILE){
			model.pullCurrentUser();
			user = model.getCurrentUser();
			model.pullUserTimeLine();
		}
		
		profile_image = (ImageView) findViewById(R.id.imageView1);
		if(user.getProfile_image() != null){
			profile_image.setImageBitmap(user.getProfile_image()); 
		}
		TextView username = (TextView) findViewById(R.id.tvUsername);
		TextView name = (TextView) findViewById(R.id.tvName);
		
		username.setText(user.getScreen_name());
		name.setText(user.getname());
		
		adapter = new TweetAdapter(this, 0, model.getUserTimeLine());
		ListView listViewTimeLine = (ListView) findViewById(R.id.listViewTimeLine);
		listViewTimeLine.setAdapter(adapter);
		
	}

	@Override
	public void update(Observable observable, Object data) {
		adapter.clear();
		adapter.addAll(model.getUserTimeLine());
	}

}
