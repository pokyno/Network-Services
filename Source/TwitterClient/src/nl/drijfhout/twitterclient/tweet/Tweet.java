package nl.drijfhout.twitterclient.tweet;

import java.util.ArrayList;

import nl.drijfhout.twitterclient.tweet.entities.Hashtags_Entity;
import nl.drijfhout.twitterclient.tweet.entities.Url_Entity;
import nl.drijfhout.twitterclient.tweet.entities.User_Mentions_Entity;

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
	private String str_id;
	private Entities entities;
	private User user;
	private String created_at;
	private Spannable text;
	private int favorite_count, retweet_count;
	private Context context;

	public Tweet(JSONObject tweet, Context context) {
		this.context = context;
		// dit vult alle variabelen door ze zelf het werk te laten doen
		try {
			user = new User(tweet.getJSONObject("user"));
			entities = new Entities(tweet.getJSONObject("entities"));
			created_at = tweet.getString("created_at");
			favorite_count = tweet.getInt("favorite_count");
			retweet_count = tweet.getInt("retweet_count");
			str_id = tweet.getString("id_str");
			text = spanText(tweet.getString("text"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public String getId() {
		return str_id;
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

	/**
	 * dit spand een text zodat hij clickable(niet alles wordt clickable
	 * gemaakt) wordt en kleurt ze
	 * 
	 * @param text  de te spannen text
	 * @return de gespande text
	 */
	private Spannable spanText(String text) {
		Spannable WordtoSpan = null;

		ArrayList<Url_Entity> urls = entities.getUrls();
		// hier worden alle urls clickable gemaakt en ook wordt de onclick
		// geregelt
		if (urls.size() == 0 || urls == null) {
			WordtoSpan = new SpannableString(text);
		} else {
			for (int j = 0; j < urls.size(); j++) {
				Url_Entity url = urls.get(j);
				Log.d("lalalalalalala", url.getUrl());
				int begin = url.getindice(0);
				int end = url.getindice(1);

				WordtoSpan = new SpannableString(text);

				ClickableSpan clickableSpan = new ClickableSpan() {

					@Override
					public void onClick(View widget) {
						// als er op een item geclickt wordt dan wordt een
						// webbrowser gestart op met aangegeven link
						TextView tv = (TextView) widget;
						Spanned s = (Spanned) tv.getText();
						int start = s.getSpanStart(this);
						int end = s.getSpanEnd(this);
						Spannable url = (Spannable) s.subSequence(start, end);
						Intent i = new Intent(Intent.ACTION_VIEW);
						i.setData(Uri.parse(url.toString()));
						context.startActivity(i);

					}
				};

				WordtoSpan.setSpan(clickableSpan, begin, end,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

			}

		}
		// voor alle hashtags wordt de kleur blauw gebruikt
		ArrayList<Hashtags_Entity> hashtags = entities.getHashtags();

		if (hashtags != null) {
			for (int h = 0; h < hashtags.size(); h++) {
				Hashtags_Entity hashtag = hashtags.get(h);
				int begin = hashtag.getindice(0);
				int end = hashtag.getindice(1);
				WordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE),	begin, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}

		// voor alle usermentions wordt de kleur cyan gebruikt
		ArrayList<User_Mentions_Entity> userMentions = entities
				.getUser_Mentions();

		if (userMentions != null) {
			for (int h = 0; h < userMentions.size(); h++) {
				User_Mentions_Entity userMention = userMentions.get(h);
				int begin = userMention.getindice(0);
				int end = userMention.getindice(1);
				WordtoSpan.setSpan(new ForegroundColorSpan(Color.CYAN),	begin, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}

		return WordtoSpan;

	}

}
