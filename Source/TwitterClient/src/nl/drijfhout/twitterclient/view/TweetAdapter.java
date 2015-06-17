package nl.drijfhout.twitterclient.view;

import java.util.List;

import nl.drijfhout.twitterclient.R;
import nl.drijfhout.twitterclient.UserProfileActivity;
import nl.drijfhout.twitterclient.tweet.Tweet;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TweetAdapter extends ArrayAdapter<Tweet> {

	private LayoutInflater inflater;
	private Context context;
	
	public TweetAdapter(Context context, int resource, List<Tweet> objects) {
		super(context, resource, objects);
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public View getView(int position, View convertView, ViewGroup parent){
		if(convertView == null){
			convertView = inflater.inflate(R.layout.tweet, parent,false);
		}
		
		Tweet t = getItem(position);
		ImageView profile_image = (ImageView) convertView.findViewById(R.id.imageView1);
		
		if(t.getUser().getProfile_image() != null){
			profile_image.setImageBitmap(t.getUser().getProfile_image()); 
		}
		
		
		TextView username = (TextView) convertView.findViewById(R.id.tvUsername);
		TextView name = (TextView) convertView.findViewById(R.id.tvName);
		TextView text = (TextView) convertView.findViewById(R.id.tvText);
		
		username.setMovementMethod(LinkMovementMethod.getInstance());
		name.setMovementMethod(LinkMovementMethod.getInstance());
		
		username.setText(spanText(t.getUser().getScreen_name(),t.getUser().getStrId()));
		name.setText(spanText(t.getUser().getname(),t.getUser().getStrId()));
		text.setText(t.getText());
		
		text.setMovementMethod(LinkMovementMethod.getInstance());
		
		username.setTextColor(Color.BLACK);
		name.setTextColor(Color.BLACK);
		text.setTextColor(Color.RED);
		
		ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView2);
		if(t.getEntities().getMedia().size() != 0){
			if(t.getEntities().getMedia().get(0).getImage() == null){
				imageView.setVisibility(View.GONE);
			}else{
				imageView.setVisibility(View.VISIBLE);
				imageView.setImageBitmap(t.getEntities().getMedia().get(0).getImage());
			}
		}else{
			imageView.setVisibility(View.GONE);
		}
		
		return convertView;
		
	}
	
	private Spannable spanText(String text,final String id) {// final????
		Spannable WordtoSpan = new SpannableString(text);
		
		ClickableSpan clickableSpan = new ClickableSpan() {

			@Override
			public void onClick(View widget) {
				Log.i("test","click");
				Intent intent = new Intent(context,UserProfileActivity.class);
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
