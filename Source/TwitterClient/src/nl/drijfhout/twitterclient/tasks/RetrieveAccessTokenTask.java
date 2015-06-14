package nl.drijfhout.twitterclient.tasks;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.os.AsyncTask;


	

public class RetrieveAccessTokenTask extends AsyncTask<String, Void,String> {
	private OAuthProvider provider;
	private OAuthConsumer consumer;
	public RetrieveAccessTokenTask(OAuthProvider provider, OAuthConsumer consumer){
		this.provider = provider;
		this.consumer = consumer;
	}
	@Override
	protected String doInBackground(String... params) {
		try {
			provider.retrieveAccessToken(consumer, params[0]);
		} catch (OAuthMessageSignerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthNotAuthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return consumer.getToken();
	}
	
}
