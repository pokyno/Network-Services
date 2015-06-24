package nl.drijfhout.twitterclient.tasks;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Base64;

public class GetTokenTask extends AsyncTask<Void, Void, String> {

	private static final String API_KEY = "k4xSeNaViDzSHgN1pV4822y8Z";
	private static final String API_SECRET = "hcxPzrRWMKmCFLmKLWaegEaMfKPp8jyFCE3SnQQfojrRxvBGne";
	private String token = "";

	@Override
	protected String doInBackground(Void... params) {

		String authString = API_KEY + ":" + API_SECRET;
		String base64 = Base64.encodeToString(authString.getBytes(),Base64.NO_WRAP);

		HttpPost request = new HttpPost("https://api.twitter.com/oauth2/token");
		request.setHeader("Authorization", "Basic " + base64);
		request.setHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
		try {
			request.setEntity(new StringEntity("grant_type=client_credentials"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		HttpClient client = new DefaultHttpClient();

		ResponseHandler<String> handler = new BasicResponseHandler();
		String result = "";
		try {
			result = client.execute(request, handler);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			JSONObject obj = new JSONObject(result);
			token = obj.getString("access_token");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return token;
	}

}
