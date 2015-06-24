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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class UserProfileActivity extends Activity implements Observer{
	public static final String CURRENT_PROFILE = "0"; //word gebruikt als basis voor als het om de current user gaat
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
		
		
		//kijkt welke stappen hij moet doen per situatie 
		id = getIntent().getStringExtra("ID");
		if(id.equals(CURRENT_PROFILE)){ // bij current user moet ik een andere tijdlijn gebruiken dan bij de rest
			user = model.getCurrentUser();
			model.pullCurrentUserTimeLine();
		}else{
			user = model.getSelectedUser(id);
			model.pullUserTimeLine(id);
		}
		
		ImageView profile_image = (ImageView) findViewById(R.id.imageViewProfilePicture);
		
		if(user.getProfile_image() != null){ //als iemand geen plaatje heeft krijgt hij een standaard plaatje
			profile_image.setImageBitmap(user.getProfile_image()); 
		}
		
		TextView username = (TextView) findViewById(R.id.textViewUsername);
		TextView name = (TextView) findViewById(R.id.textViewName);
		TextView beschrijving = (TextView) findViewById(R.id.textViewBeschrijving);
		TextView followers = (TextView) findViewById(R.id.textViewFollwers);
		TextView following = (TextView) findViewById(R.id.textViewFollowing);
		
		// als de namen bij elkaar te lang zijn voor de view worden ze op twee verschillende lijnen weergegeven (we hebben 25 als max length gemaakt op basis van trial en error)
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
	
	private Spannable spanText(String text,final String id) {
		Spannable WordtoSpan = new SpannableString(text);
		
		ClickableSpan clickableSpan = new ClickableSpan() {

			@Override
			public void onClick(View widget) {
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
