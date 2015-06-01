package nl.drijfhout.twitterclient.view;

import java.util.List;

import nl.drijfhout.twitterclient.R;
import nl.drijfhout.twitterclient.tweet.Tweet;
import android.content.Context;
import android.graphics.Color;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TweetAdapter extends ArrayAdapter<Tweet> {

	LayoutInflater inflater;
	
	public TweetAdapter(Context context, int resource, List<Tweet> objects) {
		super(context, resource, objects);
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
				
		username.setText(t.getUser().getScreen_name());
		name.setText(t.getUser().getname());
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
	
	
}
