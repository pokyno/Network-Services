package nl.drijfhout.twitterclient;

import nl.drijfhout.twitterclient.model.TwitterModel;
import nl.drijfhout.twitterclient.tweet.User;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class UserProfileActivity extends Activity {
	public static final int CURRENT_PROFILE = 0;
	private int function;
	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		
		TwitterApplication app = (TwitterApplication) getApplicationContext();
		TwitterModel model = app.getModel();
		
		
		function = getIntent().getIntExtra("function", 0);
		if(function == CURRENT_PROFILE){
			user = model.getCurrentUser();
		}
		TextView textViewProfileName = (TextView) findViewById(R.id.textViewProfileName);
		
		if(user == null){
			textViewProfileName.setText("fail");
		}else{
			textViewProfileName.setText(user.getname());
		}
	
		
	}

}
