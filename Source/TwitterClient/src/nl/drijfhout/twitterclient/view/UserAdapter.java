package nl.drijfhout.twitterclient.view;

import java.util.List;

import nl.drijfhout.twitterclient.R;
import nl.drijfhout.twitterclient.UserProfileActivity;
import nl.drijfhout.twitterclient.tweet.User;
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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserAdapter extends ArrayAdapter<User>{

	private LayoutInflater inflater;
	private Context context;
	
	public UserAdapter(Context context, int resource, List<User> objects) {
		super(context, resource, objects);
		this.context = context;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = inflater.inflate(R.layout.user, parent,false);
		}
		
		User user = super.getItem(position);
		
		ImageView profile_image = (ImageView) convertView.findViewById(R.id.imageViewProfilePicture);
		
		if(user.getProfile_image() != null){
			profile_image.setImageBitmap(user.getProfile_image()); 
		}
		
		TextView username = (TextView) convertView.findViewById(R.id.textViewUsername);
		TextView name = (TextView) convertView.findViewById(R.id.textViewName);
		TextView beschrijving = (TextView) convertView.findViewById(R.id.textViewBeschrijving);
		TextView followers = (TextView) convertView.findViewById(R.id.textViewFollwers);
		TextView following = (TextView) convertView.findViewById(R.id.textViewFollowing);
		
		LinearLayout namelayout = (LinearLayout) convertView.findViewById(R.id.nameLayout);
		if(user.getScreen_name().length() + user.getname().length() > 25){
			namelayout.setOrientation(LinearLayout.VERTICAL);
		}else{
			namelayout.setOrientation(LinearLayout.HORIZONTAL);
		}
		
		
		username.setMovementMethod(LinkMovementMethod.getInstance());
		name.setMovementMethod(LinkMovementMethod.getInstance());
		beschrijving.setText(user.getBeschrijving());
		followers.setText("Followers count: "+user.getFollowersCount());
		following.setText("Following count: "+user.getFollowingCount());
		
		username.setText(spanText(user.getScreen_name(),user.getStrId()));
		name.setText(spanText(user.getname(),user.getStrId()));
		
		username.setTextColor(Color.BLACK);
		name.setTextColor(Color.BLACK);
		
		
		
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
