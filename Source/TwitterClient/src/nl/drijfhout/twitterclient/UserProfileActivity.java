package nl.drijfhout.twitterclient;

import java.util.Observable;
import java.util.Observer;

import nl.drijfhout.twitterclient.model.TwitterModel;
import nl.drijfhout.twitterclient.tweet.User;
import nl.drijfhout.twitterclient.view.TweetAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class UserProfileActivity extends Activity implements Observer{
	public static final String CURRENT_PROFILE = "0";
	private String id;
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
		
		id = getIntent().getStringExtra("ID");
		Log.i("id", id);
		if(id.equals(CURRENT_PROFILE)){ // current profile heb ik het id 0 gegeven
			user = model.getCurrentUser();
			model.pullCurrentUserTimeLine();
		}else{
			Log.i("test","i came this far");
			user = model.getSelectedUser(id);
			Log.i("test", user.toString());
			model.pullUserTimeLine(id);
			
		}
		
		profile_image = (ImageView) findViewById(R.id.imageView1);
		if(user.getProfile_image() != null){
			profile_image.setImageBitmap(user.getProfile_image()); 
		}
		TextView username = (TextView) findViewById(R.id.tvUsername);
		TextView name = (TextView) findViewById(R.id.tvName);
		
		username.setText(user.getScreen_name());
		name.setText(user.getname());
		
		adapter = new TweetAdapter(this, 0, model.getUserTimeLine(),model.getCurrentUser().getStrId());
		ListView listViewTimeLine = (ListView) findViewById(R.id.listViewTimeLine);
		listViewTimeLine.setAdapter(adapter);
		
	}

	@Override
	public void update(Observable observable, Object data) {
		adapter.clear();
		adapter.addAll(model.getUserTimeLine());
	}

}
