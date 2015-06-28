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

public class ZoekActivity extends Activity implements Observer {

	private ListView listView;
	private Button btnZoek;
	private EditText edtZoekterm;
	private TwitterModel model;
	private TweetAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zoek);
		
		TwitterApplication app = (TwitterApplication) getApplicationContext();
		model = app.getModel();
		model.addObserver(this);
		TwitterModel.context = this; //om de context naar deze activitie te zetten
		
		edtZoekterm = (EditText) findViewById(R.id.edtTweet);
		
		//kijkt of de user is ingelogd
		if(model.getCurrentUser() != null){
			adapter = new TweetAdapter(this,0,model.getTweets(),model.getCurrentUser().getStrId(),model.isLoggedIn());
		}else{
			adapter = new TweetAdapter(this,0,model.getTweets(),model.isLoggedIn());	
		}
		
		listView = (ListView) findViewById(R.id.listViewTimeLine);
		listView.setAdapter(adapter);
		btnZoek = (Button) this.findViewById(R.id.BtnLogin);
		btnZoek.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String zoekterm = edtZoekterm.getText().toString();
				if (!zoekterm.equals("")) {
					model.searchForTweet(zoekterm);
					
				}
				
			}

		});
	}

	@Override
	public void update(Observable observable, Object data) {
		adapter.clear();
		adapter.addAll(model.getTweets());
		
	}
}
