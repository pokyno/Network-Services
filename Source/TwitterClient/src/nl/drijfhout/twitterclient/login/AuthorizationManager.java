package nl.drijfhout.twitterclient.login;

import java.util.concurrent.ExecutionException;

import android.util.Log;
import nl.drijfhout.twitterclient.tasks.GetRequestTokenTask;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;

public class AuthorizationManager {

	private OAuthConsumer consumer;
	private OAuthProvider provider;
	private	static final String	CONSUMER_KEY	= "k4xSeNaViDzSHgN1pV4822y8Z";
	private	static	final	String	CONSUMER_SECRET	= "hcxPzrRWMKmCFLmKLWaegEaMfKPp8jyFCE3SnQQfojrRxvBGne";
	private	static	final	String	OAUTH_REQUEST_URL	= "https://api.twitter.com/oauth/request_token";
	private	static	final	String	OAUTH_ACCESSTOKEN_URL	= "https://api.twitter.com/oauth/access_token";
	private	 String	OAUTH_AUTHORIZE_URL	= "https://api.twitter.com/oauth/authorize";
	
	
	//public boolean isUserLoggedIn(){
	//	
	//}
	public String getAuthorizeUrl(){
		return OAUTH_AUTHORIZE_URL;
	}
	
	
	public void init(){
		consumer=	new CommonsHttpOAuthConsumer(CONSUMER_KEY,	CONSUMER_SECRET);	
		provider	=	new CommonsHttpOAuthProvider(	OAUTH_REQUEST_URL, OAUTH_ACCESSTOKEN_URL,	OAUTH_AUTHORIZE_URL);
		GetRequestTokenTask getrequest = new GetRequestTokenTask(consumer);
		
		
		try {
			OAUTH_AUTHORIZE_URL = getrequest.execute(provider).get();
			
			Log.d("Authggggg", OAUTH_AUTHORIZE_URL);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
