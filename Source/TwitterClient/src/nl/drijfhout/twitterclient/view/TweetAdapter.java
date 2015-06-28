package nl.drijfhout.twitterclient.view;

import java.util.List;

import nl.drijfhout.twitterclient.R;
import nl.drijfhout.twitterclient.UserProfileActivity;
import nl.drijfhout.twitterclient.tasks.RetweetTask;
import nl.drijfhout.twitterclient.tweet.Tweet;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TweetAdapter extends ArrayAdapter<Tweet> {

	private LayoutInflater inflater;
	private Context context;
	String userid = "";
	private boolean isIngelogd;

	/**
	 * maakt een tweet adapter aan deze wordt versie alleen gebruikt voor tweets
	 * die opgehaald kunnen worden zonder in te loggen
	 * 
	 * @param context
	 * @param resource
	 * @param objects
	 */
	public TweetAdapter(Context context, int resource, List<Tweet> objects,boolean isIngelogd) {
		super(context, resource, objects);
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.isIngelogd = isIngelogd;
	}

	/**
	 * maakt een tweet adapter met een extra parameten de current user id en
	 * wordt gebruikt moet gebruikt worden als de user wel is ingelogd
	 * 
	 * @param context
	 * @param resource
	 * @param objects
	 * @param id       current user id
	 */
	public TweetAdapter(Context context, int resource, List<Tweet> objects,	String id,boolean isIngelogd) {
		super(context, resource, objects);
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		userid = id;
		this.isIngelogd = isIngelogd;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.tweet, parent, false);
		}

		final Tweet t = getItem(position);
		
		ImageView profile_image = (ImageView) convertView.findViewById(R.id.imageView1);

		if (t.getUser().getProfile_image() != null) {
			profile_image.setImageBitmap(t.getUser().getProfile_image());
		}

		TextView username = (TextView) convertView.findViewById(R.id.tvUsername);
		TextView name = (TextView) convertView.findViewById(R.id.tvName);
		TextView text = (TextView) convertView.findViewById(R.id.tvText);
		Button btnRetweet = (Button) convertView.findViewById(R.id.btnRetweet);
		TextView createdAt = (TextView) convertView.findViewById(R.id.textViewCreatedAt);
		TextView retweetCount = (TextView) convertView.findViewById(R.id.textViewRetweetCount);
		TextView FavouriteCount = (TextView) convertView.findViewById(R.id.textViewFavouriteCount);

		if(isIngelogd){
			username.setMovementMethod(LinkMovementMethod.getInstance());
			name.setMovementMethod(LinkMovementMethod.getInstance());
		// controle zodat je jezelf niet kan retweeten
		if (userid != "" && !t.getUser().getStrId().equals(userid)) {
			btnRetweet.setVisibility(View.VISIBLE);
		} else {
			btnRetweet.setVisibility(View.GONE);
		}
		}else{
			btnRetweet.setVisibility(View.GONE);
		}
		btnRetweet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				RetweetTask retweet = new RetweetTask(context);
				retweet.execute(t.getId());
				Toast.makeText(context, "Geretweet", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(context, UserProfileActivity.class);
				i.putExtra("ID", "0");
				context.startActivity(i);
				((Activity) context).finish();
			}

		});

		

		username.setText(spanText(t.getUser().getScreen_name(), t.getUser().getStrId()));
		name.setText(spanText(t.getUser().getname(), t.getUser().getStrId()));
		text.setText(t.getText());

		text.setMovementMethod(LinkMovementMethod.getInstance());

		createdAt.setText(t.getCreated_at());
		FavouriteCount.setText("Favoutite count: " + t.getFavorite_count());
		retweetCount.setText("Retweet count: " + t.getRetweet_count());

		username.setTextColor(Color.BLACK);
		name.setTextColor(Color.BLACK);
		text.setTextColor(Color.BLACK);

		ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView2);
		if (t.getEntities().getMedia().size() != 0) {
			if (t.getEntities().getMedia().get(0).getImage() == null) {
				imageView.setVisibility(View.GONE);
			} else {
				imageView.setVisibility(View.VISIBLE);
				imageView.setImageBitmap(t.getEntities().getMedia().get(0).getImage());
			}
		} else {
			imageView.setVisibility(View.GONE);
		}

		return convertView;

	}

	/**
	 * spand de text zodat ze clickable worden (met een standaard functie)
	 * 
	 * @param text  de text om te spannen
	 * @param id    het id van de te bekijken user
	 * @return een gespande text
	 */
	private Spannable spanText(String text, final String id) {// final????
		Spannable WordtoSpan = new SpannableString(text);

		ClickableSpan clickableSpan = new ClickableSpan() {

			@Override
			public void onClick(View widget) {
				Log.i("test", "click");
				Intent intent = new Intent(context, UserProfileActivity.class);
				intent.putExtra("ID", id);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(intent);
			}
		};

		WordtoSpan.setSpan(clickableSpan, 0, text.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		return WordtoSpan;

	}

}
