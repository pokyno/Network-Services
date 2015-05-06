package nl.drijfhout.twitterclient.tweet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Tweet {
	private Entities entities;
	private User user;
	private String created_at;
	private Spannable text;
	private int favorite_count, retweet_count;
	private Context context;

	public Tweet(JSONObject tweet, Context context) {
		this.context = context;
		try {
			user = new User(tweet.getJSONObject("user"));
			entities = new Entities(tweet.getJSONObject("entities"));

			created_at = tweet.getString("created_at");
			favorite_count = tweet.getInt("favorite_count");
			retweet_count = tweet.getInt("retweet_count");

			text = spanText(tweet.getString("text"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public Entities getEntities() {
		return entities;
	}

	public User getUser() {
		return user;
	}

	public String getCreated_at() {
		return created_at;
	}

	public Spannable getText() {
		return text;
	}

	public int getFavorite_count() {
		return favorite_count;
	}

	public int getRetweet_count() {
		return retweet_count;
	}

	private Spannable spanText(String text) {
		try {
			JSONObject c;
			JSONArray urls = entities.getURLs();
			JSONArray hashtags = entities.getHashtags();
			Spannable WordtoSpan = null;
			
			
			if (urls.length() == 0) {
				WordtoSpan = new SpannableString(text);
			} else {
				for (int j = 0; j < urls.length(); j++) {
					c = urls.getJSONObject(j);
					Log.d("lalalalalalala", c.getString("url"));
					JSONArray indices = c.getJSONArray("indices");
					int begin = indices.getInt(0);
					int end = indices.getInt(1);

					WordtoSpan = new SpannableString(text);

					ClickableSpan clickableSpan = new ClickableSpan() {

						@Override
						public void onClick(View widget) {
							TextView tv = (TextView) widget;
							Spanned s = (Spanned) tv.getText();
							int start = s.getSpanStart(this);
							int end = s.getSpanEnd(this);
							Spannable url = (Spannable) s.subSequence(start,
									end);
							Intent i = new Intent(Intent.ACTION_VIEW);
							i.setData(Uri.parse(url.toString()));
							context.startActivity(i);

						}
					};
					
					WordtoSpan.setSpan(clickableSpan, begin, end,
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					
					
					
				}

			}
			if(hashtags!= null){
			for(int h = 0; h< hashtags.length();h++){
				JSONObject hashtag = hashtags.getJSONObject(h);
				JSONArray indices = hashtag.getJSONArray("indices");
				int begin = indices.getInt(0);
				int end = indices.getInt(1);
				WordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), begin+1, end,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			}
			return WordtoSpan;
		} catch (JSONException e) {

		}
		return null;

	}

}
