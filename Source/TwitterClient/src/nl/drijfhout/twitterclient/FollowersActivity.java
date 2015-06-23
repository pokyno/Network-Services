package nl.drijfhout.twitterclient;

import java.util.Observable;
import java.util.Observer;

import nl.drijfhout.twitterclient.model.TwitterModel;
import nl.drijfhout.twitterclient.view.UserAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class FollowersActivity extends Activity implements Observer{
	private UserAdapter adapter;
	private TwitterModel model;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends);
		
		TwitterApplication app = (TwitterApplication) getApplicationContext();
		model = app.getModel();
		model.addObserver(this);
		model.context = this;  //om de context naar deze activitie te zetten
		model.pullFollowers();
		
		adapter = new UserAdapter(this, 0, model.getFollowers());
		
		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(adapter);
		
	}


	@Override
	public void update(Observable observable, Object data) {
		adapter.clear();
		adapter.addAll(model.getFollowers());
	}

	
}
