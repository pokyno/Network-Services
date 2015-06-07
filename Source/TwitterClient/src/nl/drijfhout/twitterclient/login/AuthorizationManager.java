package nl.drijfhout.twitterclient.login;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;

public class AuthorizationManager {

	private OAuthConsumer consumer;
	private OAuthProvider provider;
	private String url;
	private	static final String	CONSUMER_KEY	= "k4xSeNaViDzSHgN1pV4822y8Z";
	private	static	final	String	CONSUMER_SECRET	= "hcxPzrRWMKmCFLmKLWaegEaMfKPp8jyFCE3SnQQfojrRxvBGne";
	private	static	final	String	OAUTH_REQUEST_URL	= "https://api.twitter.com/oauth/request_token";
	private	static	final	String	OAUTH_ACCESSTOKEN_URL	= "https://api.twitter.com/oauth/access_token";
	private	static	final	String	OAUTH_AUTHORIZE_URL	= "https://api.twitter.com/oauth/authorize";
	private	static final String	OAUTH_CALLBACK_URL	= "http://www.saxion.nl/pgrtwitter/authenticated";
	//public boolean isUserLoggedIn(){
	//	
	//}
	
	public void init(){
		consumer=	new CommonsHttpOAuthConsumer(CONSUMER_KEY,	CONSUMER_SECRET);	
		provider	=	new CommonsHttpOAuthProvider(	OAUTH_REQUEST_URL, OAUTH_ACCESSTOKEN_URL,	OAUTH_AUTHORIZE_URL);
		
	}
	
	
}
