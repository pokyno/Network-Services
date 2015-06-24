package nl.drijfhout.twitterclient;

import nl.drijfhout.twitterclient.tasks.UpdateStatusTask;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class TweetActivity extends Activity {

	private Button btnPost;
	private EditText edtTweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tweet);
		btnPost = (Button)findViewById(R.id.btnPost);
		edtTweet = (EditText)findViewById(R.id.edtTweet);
		
		btnPost.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				String tweet = edtTweet.getText().toString();
				if(tweet.length()>0){
				UpdateStatusTask tweeter = new UpdateStatusTask(TweetActivity.this);
				tweeter.execute(tweet);
				// je word meteen naar je tijdlijn gestuurd om de net gemaakte tweet te bekijken
				Intent i = new Intent(TweetActivity.this,UserProfileActivity.class);
				i.putExtra("ID", "0");
				startActivity(i);
				finish();
				}
				
			}
			
		});
		
	}

}
