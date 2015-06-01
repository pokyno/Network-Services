package nl.drijfhout.twitterclient;

import java.util.Observable;
import java.util.Observer;
import nl.drijfhout.twitterclient.model.TwitterModel;
import nl.drijfhout.twitterclient.view.TweetAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity implements Observer {

	private ListView listView;
	private Button button1;
	private EditText editText1;
	private TwitterModel model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TwitterApplication app = (TwitterApplication) getApplicationContext();
		model = app.getModel();
		model.addObserver(this);

		editText1 = (EditText) findViewById(R.id.editText1);

		// tijdelijk voordat er inlog is
		
		listView = (ListView) findViewById(R.id.listView1);
		model.searchForTweet("pieter");
		button1 = (Button) this.findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String zoekterm = editText1.getText().toString();
				if (!zoekterm.equals("")) {
					model.searchForTweet(zoekterm);
				}

			}

		});

	}

	@Override
	public void update(Observable observable, Object data) {
		listView.setAdapter(new TweetAdapter(this, 0, model.getTweets()));
	}

}
