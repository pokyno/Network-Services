package nl.drijfhout.twitterclient;

import java.util.Observable;
import java.util.Observer;

import nl.drijfhout.twitterclient.model.TwitterModel;
import nl.drijfhout.twitterclient.tweet.User;
import nl.drijfhout.twitterclient.view.TweetAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class UserProfileActivity extends Activity implements Observer{
	public static final String CURRENT_PROFILE = "0";
	private String id;
	private User user;
	private TwitterModel model;
	private TweetAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		
		TwitterApplication app = (TwitterApplication) getApplicationContext();
		model = app.getModel();
		model.addObserver(this);
		TwitterModel.context = this; //om de context naar deze activitie te zetten
		
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
		
		ImageView profile_image = (ImageView) findViewById(R.id.imageViewProfilePicture);
		
		if(user.getProfile_image() != null){
			profile_image.setImageBitmap(user.getProfile_image()); 
		}
		
		TextView username = (TextView) findViewById(R.id.textViewUsername);
		TextView name = (TextView) findViewById(R.id.textViewName);
		TextView beschrijving = (TextView) findViewById(R.id.textViewBeschrijving);
		TextView followers = (TextView) findViewById(R.id.textViewFollwers);
		TextView following = (TextView) findViewById(R.id.textViewFollowing);
		
		LinearLayout namelayout = (LinearLayout) findViewById(R.id.nameLayout);
		if(user.getScreen_name().length() + user.getname().length() > 25){
			namelayout.setOrientation(LinearLayout.VERTICAL);
		}else{
			namelayout.setOrientation(LinearLayout.HORIZONTAL);
		}
		
		
		username.setMovementMethod(LinkMovementMethod.getInstance());
		name.setMovementMethod(LinkMovementMethod.getInstance());
		beschrijving.setText(user.getBeschrijving());
		followers.setText("Followers count: "+user.getFollowersCount());
		following.setText("Following count: "+user.getFollowingCount());
		
		username.setText(spanText(user.getScreen_name(),user.getStrId()));
		name.setText(spanText(user.getname(),user.getStrId()));
		
		username.setTextColor(Color.BLACK);
		name.setTextColor(Color.BLACK);
		
		adapter = new TweetAdapter(this, 0, model.getUserTimeLine(),model.getCurrentUser().getStrId());
		ListView listViewTimeLine = (ListView) findViewById(R.id.listViewTimeLine);
		listViewTimeLine.setAdapter(adapter);
		
	}
	
	private Spannable spanText(String text,final String id) {// final????
		Spannable WordtoSpan = new SpannableString(text);
		
		ClickableSpan clickableSpan = new ClickableSpan() {

			@Override
			public void onClick(View widget) {
				Log.i("test","click");
				Intent intent = new Intent(UserProfileActivity.this,UserProfileActivity.class);
				intent.putExtra("ID", id);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		};

		WordtoSpan.setSpan(clickableSpan, 0, text.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		return WordtoSpan;
	
	}
	

	@Override
	public void update(Observable observable, Object data) {
		adapter.clear();
		adapter.addAll(model.getUserTimeLine());
	}

}
