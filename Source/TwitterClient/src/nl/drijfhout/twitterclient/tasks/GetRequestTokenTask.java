package nl.drijfhout.twitterclient.tasks;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.os.AsyncTask;

public class GetRequestTokenTask extends AsyncTask<OAuthProvider, Void, String> {

	private static final String OAUTH_CALLBACK_URL = "http://www.saxion.nl/pgrtwitter/authenticated";
	private OAuthConsumer consumer;

	public GetRequestTokenTask(OAuthConsumer consumer) {
		this.consumer = consumer;
	}

	@Override
	protected String doInBackground(OAuthProvider... params) {
		String url = "";
		try {
			url = params[0].retrieveRequestToken(consumer, OAUTH_CALLBACK_URL);
		} catch (OAuthMessageSignerException e) {
			e.printStackTrace();
		} catch (OAuthNotAuthorizedException e) {
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			e.printStackTrace();
		}
		return url;
	}

}
