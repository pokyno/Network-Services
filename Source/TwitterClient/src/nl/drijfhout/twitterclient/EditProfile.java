package nl.drijfhout.twitterclient;

import nl.drijfhout.twitterclient.model.TwitterModel;
import nl.drijfhout.twitterclient.tasks.EditProfileTask;
import nl.drijfhout.twitterclient.tweet.User;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditProfile extends Activity {

	private EditText edtName,edtURL,edtLocatie,edtBeschrijving;
	private TwitterModel model;
	private Button btnEditProfile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		
		edtName = (EditText)findViewById(R.id.edtEditProfilenaam);
		edtURL = (EditText)findViewById(R.id.edtEditProfileURL);
		edtLocatie = (EditText)findViewById(R.id.edtEditProfileLocatie);
		edtBeschrijving = (EditText)findViewById(R.id.edtEditProfileBeschrijving);
		
		TwitterApplication app = (TwitterApplication) getApplicationContext();
		model = app.getModel();
		
		User ik = model.getCurrentUser();
		edtName.setText(ik.getname());
		edtLocatie.setText(ik.getLocatie());
		edtBeschrijving.setText(ik.getBeschrijving());
		btnEditProfile = (Button)findViewById(R.id.btnEditProfile);
		
		btnEditProfile.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				EditProfileTask taak = new EditProfileTask(edtName.getText().toString(),edtURL.getText().toString(),edtLocatie.getText().toString(),edtBeschrijving.getText().toString(),EditProfile.this);
				taak.execute();
				model.pullCurrentUser();
				finish();
				
			}
			
		});
		
	}

	
	
	
	
}
