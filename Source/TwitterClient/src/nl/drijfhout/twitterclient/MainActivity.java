package nl.drijfhout.twitterclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import nl.drijfhout.twitterclient.json.JSONParser;
import nl.drijfhout.twitterclient.model.TwitterModel;
import nl.drijfhout.twitterclient.tweet.Tweet;
import nl.drijfhout.twitterclient.view.TweetAdapter;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {
	private	static final String	API_KEY	= "k4xSeNaViDzSHgN1pV4822y8Z";
	private	static	final	String	API_SECRET	= "hcxPzrRWMKmCFLmKLWaegEaMfKPp8jyFCE3SnQQfojrRxvBGne";
	private static String token = "";
	private Context context; 
	private ListView listView;
	private Button button1;
	private EditText editText1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		context = getApplicationContext();
		TwitterApplication app = (TwitterApplication) getApplicationContext();
		TwitterModel model = app.getModel();
		listView = (ListView) findViewById(R.id.listView1);
		editText1 = (EditText)findViewById(R.id.editText1);
		button1 = (Button)this.findViewById(R.id.button1);
		getTokenTask taak = new getTokenTask();
		taak.execute();
		
		
		button1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				String zoekterm = editText1.getText().toString();
				if(!zoekterm.equals("")){
					searchTweetTask zoektaak = new searchTweetTask();
					TweetAdapter adapter;
					try {
						adapter = new TweetAdapter(context, 0, zoektaak.execute(zoekterm).get());
						listView.setAdapter(adapter);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
				
				
				
			}

			
			
		});
		
		
		
		
		
	}
	public class searchTweetTask extends AsyncTask<String,Void,ArrayList<Tweet>>{

		@Override
		protected ArrayList<Tweet> doInBackground(String... params) {
			ArrayList<Tweet> tweets = new ArrayList<Tweet>();
			String encoded = "";
			try {
				 encoded = URLEncoder.encode(params[0],	"UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			HttpClient client = new DefaultHttpClient();	
			ResponseHandler<String> handler= new BasicResponseHandler();	
			HttpGet httpGet = new HttpGet("https://api.twitter.com/1.1/search/tweets.json?q=" + encoded);
			httpGet.setHeader("Authorization", "Bearer " + token);
			try {
				String searchJSON = client.execute(httpGet, handler);
				tweets = new JSONParser(context).getTweets(searchJSON);
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return tweets;
		}
		
	}
	
	public class getTokenTask extends AsyncTask<Void,Void,Void>{

		@Override
		protected Void doInBackground(Void... params) {
			
			String	authString	=	API_KEY + ":"	+ API_SECRET;
			
			
			String	base64	=	Base64.encodeToString(authString.getBytes(),Base64.NO_WRAP);	
			
//				Maak een	POST-request	
			
			HttpPost request = new HttpPost("https://api.twitter.com/oauth2/token");	
			
//				Voeg key+secret	toe	
			request.setHeader("Authorization", "Basic "	+	base64);	
			request.setHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");	
//				Vul	body:	vertel dat	je	inlogt	met	app	key	en	secret	
			try {
				request.setEntity(	new StringEntity("grant_type=client_credentials"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			HttpClient client = new DefaultHttpClient();	
			
				
			ResponseHandler<String> handler = new BasicResponseHandler();	
			String result = "";
			try 
			{
				result = client.execute(request, handler);
			} catch (ClientProtocolException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				JSONObject obj = new JSONObject(result);
				token = obj.getString("access_token");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return null;
		}

		
		
	}
}
