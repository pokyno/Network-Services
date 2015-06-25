package nl.drijfhout.twitterclient.login;

import java.util.concurrent.ExecutionException;

import org.apache.http.client.methods.HttpRequestBase;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthException;
import android.util.Log;
import nl.drijfhout.twitterclient.tasks.GetRequestTokenTask;
import nl.drijfhout.twitterclient.tasks.RetrieveAccessTokenTask;

public class AuthorizationManager {

	private OAuthConsumer consumer;
	private OAuthProvider provider;
	private static final String CONSUMER_KEY = "k4xSeNaViDzSHgN1pV4822y8Z";
	private static final String CONSUMER_SECRET = "hcxPzrRWMKmCFLmKLWaegEaMfKPp8jyFCE3SnQQfojrRxvBGne";
	private static final String OAUTH_REQUEST_URL = "https://api.twitter.com/oauth/request_token";
	private static final String OAUTH_ACCESSTOKEN_URL = "https://api.twitter.com/oauth/access_token";
	private String OAUTH_AUTHORIZE_URL = "https://api.twitter.com/oauth/authorize";

	/**
	 * Dit is de constructor van AuthorizationManager, hier wordt een nieuw object aangemaakt van zowel de OAuthconsumer als de Oauthprovider
	 */
	public AuthorizationManager(){
		consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		provider = new CommonsHttpOAuthProvider(OAUTH_REQUEST_URL,
				OAUTH_ACCESSTOKEN_URL, OAUTH_AUTHORIZE_URL);
	}
	
	/**
	 * kijkt of een gebruiker daadwerkelijk al is ingelogd
	 * @return true als de gebruiker is ingelogd
	 */
	public boolean isUserLoggedIn() {
		if (consumer.getToken() != "") {
			return true;
		}
		return false;
	}

	
	/**
	 * Als de gebruiker al eerder is ingelogd, en de app start, wordt hierin weer de consumer token en secret opgeslagen 
	 * @param token het consumertoken
	 * @param secret het consumer secret
	 */
	
	public void setTokenAndSecret(String token, String secret) {
		consumer.setTokenWithSecret(token, secret);
	}

	
	/**
	 * 
	 * @param verifier, de verifier code die wordt opgehaald vanuit de webview, als de gebruiker zicht geauthentiseerd heeft.
	 */
	public void setAccesToken(String verifier) {
		RetrieveAccessTokenTask acces_token_task = new RetrieveAccessTokenTask(
				provider, consumer);
		try {
			acces_token_task.execute(verifier).get();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * geeft de authorize url terug voor de webview
	 * @return de authorize_url variabel
	 */
	public String getAuthorizeUrl() {
		return OAUTH_AUTHORIZE_URL;
	}

	/**
	 * geeft de consumer token terug
	 * @return consumertoken
	 */
	public String getConsumerToken() {
		return consumer.getToken();
	}

	
	/**
	 * geeft de consumer secret terug
	 * @return consumer secret
	 */
	public String getConsumerTokenSecret() {
		return consumer.getTokenSecret();
	}

	
	/**
	 * initialiseert de manager, door de request token op te vragen, en hiervan de authorize url te maken.
	 */
	public void init() {
		
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

	
	/**
	 * een methode voor buitenaf, zodat elk request getekend kan worden door de consumer
	 * @param request, het requrest dat getekend moet worden voor authenticatie.
	 * @throws OAuthException
	 */
	public void signWithUserToken(HttpRequestBase request) throws OAuthException {
		consumer.sign(request);
	}

}
