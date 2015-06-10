package nl.drijfhout.twitterclient;

import java.util.Observable;
import java.util.Observer;

import nl.drijfhout.twitterclient.model.TwitterModel;
import nl.drijfhout.twitterclient.view.TweetAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity implements Observer {

	private ListView listView;
	private Button btnZoek;
	private EditText edtZoekterm;
	private TwitterModel model;
	private TweetAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TwitterApplication app = (TwitterApplication) getApplicationContext();
		model = app.getModel();
		model.addObserver(this);

		edtZoekterm = (EditText) findViewById(R.id.editText1);
		adapter = new TweetAdapter(this,0,model.getTweets());
		
		// tijdelijk voordat er inlog is
	
		
		
		listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(adapter);
		btnZoek = (Button) this.findViewById(R.id.button1);
		btnZoek.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//String zoekterm = edtZoekterm.getText().toString();
				//if (!zoekterm.equals("")) {
					//model.searchForTweet(zoekterm);
					
				//}
				Intent i = new Intent(MainActivity.this,LoginActivity.class);
				startActivity(i);
			}

		});

	}

	@Override
	public void update(Observable observable, Object data) {
		adapter.clear();
		adapter.addAll(model.getTweets());
	}

}
