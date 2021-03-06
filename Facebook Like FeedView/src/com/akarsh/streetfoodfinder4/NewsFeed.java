package com.akarsh.streetfoodfinder4;

import info.androidhive.listviewfeed.adapter.FeedListAdapter;
import info.androidhive.listviewfeed.app.AppController;
import info.androidhive.listviewfeed.data.FeedItem;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import info.androidhive.listviewfeed.MainActivity;
import info.androidhive.listviewfeed.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class NewsFeed extends Fragment {
Button button;
private static final String TAG = MainActivity.class.getSimpleName();
private ListView listView;
private FeedListAdapter listAdapter;
private List<FeedItem> feedItems;
private String URL_FEED = "http://streettadka.comze.com/feed.php";

	
@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_main, container, false);
		//button=(Button)rootView.findViewById(R.id.button1);
		listView = (ListView) rootView.findViewById(R.id.list);

		feedItems = new ArrayList<FeedItem>();

		listAdapter = new FeedListAdapter(getActivity(), feedItems);
		listView.setAdapter(listAdapter);
		/* button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
           	Intent intent=new Intent(getActivity(),info.androidhive.listviewfeed.MainActivity.class);
           	startActivity(intent);
            }
        });*/
		getActivity().getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FC0000")));
		getActivity().getActionBar().setIcon(
				   new ColorDrawable(getResources().getColor(android.R.color.transparent)));

		// We first check for cached request
		Cache cache = AppController.getInstance().getRequestQueue().getCache();
		Entry entry = cache.get(URL_FEED);
		if (entry != null) {
			// fetch the data from cache
			try {
				String data = new String(entry.data, "UTF-8");
				try {
					parseJsonFeed(new JSONObject(data));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		} else {
			// making fresh volley request and getting json
			JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
					URL_FEED, null, new Response.Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							VolleyLog.d(TAG, "Response: " + response.toString());
							if (response != null) {
								parseJsonFeed(response);
							}
						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							VolleyLog.d(TAG, "Error: " + error.getMessage());
						}
					});

			// Adding request to volley request queue
			AppController.getInstance().addToRequestQueue(jsonReq);
		}
		return rootView;
	}
/**
 * Parsing json reponse and passing the data to feed view list adapter
 * */
private void parseJsonFeed(JSONObject response) {
	try {
		JSONArray feedArray = response.getJSONArray("feed");

		for (int i = 0; i < feedArray.length(); i++) {
			JSONObject feedObj = (JSONObject) feedArray.get(i);

			FeedItem item = new FeedItem();
			item.setId(feedObj.getInt("User Id"));
			System.out.println(feedObj.getInt("User Id"));
			item.setName(feedObj.getString("Username"));
			System.out.println(feedObj.getString("Username"));

			// Image might be null sometimes
			String image = feedObj.isNull("Post Image") ? null : feedObj
					.getString("Post Image");
			item.setImge(image);
			System.out.println(feedObj.getString("Post Image"));;
			item.setStatus(feedObj.getString("Comments"));
			System.out.println(feedObj.getString("Comments"));
			item.setProfilePic(feedObj.getString("User Image"));
			System.out.println(feedObj.getString("User Image"));
			item.setTimeStamp(feedObj.getString("Timestamp"));
			System.out.println(feedObj.getString("Timestamp"));

			// url might be null sometimes
			String feedUrl = feedObj.isNull("Vendor Name") ? null : feedObj
					.getString("Vendor Name");
			item.setUrl(feedUrl);
			System.out.println(feedObj.getString("Vendor Name"));

			feedItems.add(item);
		}

		// notify data changes to list adapater
		listAdapter.notifyDataSetChanged();
	} catch (JSONException e) {
		e.printStackTrace();
	}
}
}
