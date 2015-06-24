package nl.drijfhout.twitterclient;

import java.util.Observable;
import java.util.Observer;

import nl.drijfhout.twitterclient.model.TwitterModel;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity implements Observer {

	private Button buttonProfile; 	
	private Button btnZoek;
	private Button btnTweet;
	private Button btnLogin;
	private Button btnLoguit;
	private Button btnEditProfile;
	private Button buttonFriends;
	private Button buttonFollowers;
	private TwitterModel model;
	private TwitterApplication app;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		app = (TwitterApplication) getApplicationContext();
		model = app.getModel();
		model.addObserver(this);
		TwitterModel.context = this; //om de context naar deze activitie te zetten
		btnEditProfile = (Button)findViewById(R.id.btnEditProfile);
		btnZoek = (Button)findViewById(R.id.BtnZoekActivity);
		btnTweet = (Button)findViewById(R.id.btnpostTweet);
		btnLogin = (Button)findViewById(R.id.BtnLogin);
		btnLoguit = (Button)findViewById(R.id.BtnLoguit);
		buttonProfile = (Button) findViewById(R.id.buttonProfile);
		buttonFriends = (Button) findViewById(R.id.buttonFriends);
		buttonFollowers = (Button) findViewById(R.id.buttonFollowers);
		
		if(model.isLoggedIn()){
			btnLoguit.setVisibility(View.VISIBLE);
			btnLogin.setVisibility(View.GONE);
			buttonProfile.setVisibility(View.VISIBLE);
			btnEditProfile.setVisibility(View.VISIBLE);
			btnTweet.setVisibility(View.VISIBLE);
			buttonFriends.setVisibility(View.VISIBLE);
			buttonFollowers.setVisibility(View.VISIBLE);
		}else{
			btnLoguit.setVisibility(View.GONE);
			btnLogin.setVisibility(View.VISIBLE);
			btnEditProfile.setVisibility(View.GONE);
			buttonProfile.setVisibility(View.GONE);
			buttonFriends.setVisibility(View.GONE);
			buttonFollowers.setVisibility(View.GONE);
			btnTweet.setVisibility(View.GONE);
		}
		
		
		btnEditProfile.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this,EditProfile.class);
				startActivity(i);
				
			}
			
		});
		
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
				intent.putExtra("ID",UserProfileActivity.CURRENT_PROFILE);
				startActivity(intent);
				
			}
		});
		
		btnTweet.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent  = new Intent(MainActivity.this,TweetActivity.class);
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
		
		buttonFriends.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, FriendsActivity.class);
				startActivity(intent);
			}
		});
		
		buttonFollowers.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, FollowersActivity.class);
				startActivity(intent);
				
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
			btnTweet.setVisibility(View.VISIBLE);
			buttonFriends.setVisibility(View.VISIBLE);
			buttonFollowers.setVisibility(View.VISIBLE);
			btnEditProfile.setVisibility(View.VISIBLE);
		}else{
			btnLoguit.setVisibility(View.GONE);
			btnLogin.setVisibility(View.VISIBLE);
			buttonProfile.setVisibility(View.GONE);
			btnTweet.setVisibility(View.GONE);
			buttonFriends.setVisibility(View.GONE);
			buttonFollowers.setVisibility(View.GONE);
			btnEditProfile.setVisibility(View.GONE);
		}
	}

	

}
