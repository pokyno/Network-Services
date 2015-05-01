package nl.drijfhout.twitterclient;

import nl.drijfhout.twitterclient.model.TwitterModel;
import nl.drijfhout.twitterclient.view.TweetAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TwitterApplication app = (TwitterApplication) getApplicationContext();
		TwitterModel model = app.getModel();
		listView = (ListView) findViewById(R.id.listView1);
		TweetAdapter adapter = new TweetAdapter(this, 0, model.getTweets(this));
		listView.setAdapter(adapter);
		
	}
	
}
