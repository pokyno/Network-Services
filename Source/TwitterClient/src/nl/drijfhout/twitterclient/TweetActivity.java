package nl.drijfhout.twitterclient;

import nl.drijfhout.twitterclient.tasks.UpdateStatusTask;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
				Intent i = new Intent(TweetActivity.this,UserProfileActivity.class);
				i.putExtra("ID", "0");
				startActivity(i);
				}
				
			}
			
		});
		
		edtTweet.addTextChangedListener(new TextWatcher(){

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String text = edtTweet.getText().toString();
				Log.d("COUNT", String.valueOf(text.length()));
				
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tweet, menu);
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
