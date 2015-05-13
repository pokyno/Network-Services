package nl.drijfhout.twitterclient.tweet.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class Entity {
	private int[] indices;
	
	public Entity(JSONObject entityObject){
		indices = new int[2];
		try {
			JSONArray indices = entityObject.getJSONArray("indices");
			this.indices[0] = indices.getInt(0);
			this.indices[1] = indices.getInt(1);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	}
	/**
	 * returns the begin or end indices according to the input
	 * @param index 0 = begin, 1 = end
	 * @return
	 */
	public int getindice(int index){
		if(!(index >= 0 ||index < 2)){
			throw new IllegalArgumentException();
		}
		return indices[index];
	}
}
