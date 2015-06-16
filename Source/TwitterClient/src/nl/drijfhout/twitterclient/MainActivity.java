package nl.drijfhout.twitterclient;

import java.util.Observable;
import java.util.Observer;

import nl.drijfhout.twitterclient.model.TwitterModel;
import nl.drijfhout.twitterclient.view.TweetAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity implements Observer {

	private Button buttonProfile; 	
	private Button btnZoek;
	private Button btnLogin;
	private Button btnLoguit;
	private TwitterModel model;
	private TwitterApplication app;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		app = (TwitterApplication) getApplicationContext();
		model = app.getModel();
		model.addObserver(this);
		model.context = this; //om de context naar deze activitie te zetten
		btnZoek = (Button)findViewById(R.id.BtnZoekActivity);
		btnLogin = (Button)findViewById(R.id.BtnLogin);
		btnLoguit = (Button)findViewById(R.id.BtnLoguit);
		buttonProfile = (Button) findViewById(R.id.buttonProfile);
		
		if(model.isLoggedIn()){
			btnLoguit.setVisibility(View.VISIBLE);
			btnLogin.setVisibility(View.GONE);
			buttonProfile.setVisibility(View.VISIBLE);
		}else{
			btnLoguit.setVisibility(View.GONE);
			btnLogin.setVisibility(View.VISIBLE);
			buttonProfile.setVisibility(View.GONE);
		}
		
		btnLogin.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,LoginActivity.class);
				startActivity(intent);
				
			}
			
		});
		
		buttonProfile.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
				intent.putExtra("function",UserProfileActivity.CURRENT_PROFILE);
				startActivity(intent);
				
			}
		});
		
		btnZoek.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,ZoekActivity.class);
				startActivity(intent);
				
			}
			
		});
		
		btnLoguit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				
				model.clearUserToken();
				app.getAuthorizationManager().setTokenAndSecret("", "");
			
			}
			
		});
		
	}


	@Override
	public void update(Observable observable, Object data) {
		Log.d("Werkt dit", "hoop het wel :P");
		if(model.isLoggedIn()){
			btnLoguit.setVisibility(View.VISIBLE);
			btnLogin.setVisibility(View.GONE);
			buttonProfile.setVisibility(View.VISIBLE);
		}else{
			btnLoguit.setVisibility(View.GONE);
			btnLogin.setVisibility(View.VISIBLE);
			buttonProfile.setVisibility(View.GONE);
		}
	}

	

}
