package info.androidhive.listviewfeed;

import info.androidhive.listviewfeed.adapter.FeedListAdapter;
import info.androidhive.listviewfeed.adapter.FeedListAdapter6;
import info.androidhive.listviewfeed.adapter.FeedListAdapter7;
import info.androidhive.listviewfeed.app.AppController;
import info.androidhive.listviewfeed.data.FeedItem;
import info.androidhive.listviewfeed.data.FeedItem2;
import info.androidhive.listviewfeed.data.FeedItem6;
import info.androidhive.listviewfeed.data.FeedItem7;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

import com.akarsh.streetfoodfinder4.AndroidFacebookConnectActivity;
import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

public class whatshotActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();
	private ListView listView;
	private FeedListAdapter7 listAdapter;
	private List<FeedItem7> feedItems;
	private String URL_FEED = "http://streettadka.comze.com/whatshot.php";
	InputStream is=null;
	String result=null;
	String line=null;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
////////////////////////////////////////////////////////////////////////////////////////////////////
		//news feed
		listView = (ListView) findViewById(R.id.listView99);

		feedItems = new ArrayList<FeedItem7>();

		listAdapter = new FeedListAdapter7(this, feedItems);
		listView.setAdapter(listAdapter);
		
		// These two lines not needed,
		// just to get the look of facebook (changing background color & hiding the icon)
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5998")));
		getActionBar().setIcon(
				   new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		String email=AndroidFacebookConnectActivity.email;
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs.add(new BasicNameValuePair("email",email));
		
		try
		{
		HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost(URL_FEED);
	      //  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        HttpResponse response = httpclient.execute(httppost); 
	        HttpEntity entity = response.getEntity();
	        is = entity.getContent();
	        Log.e("pass 1", "connection success ");
	}
	    catch(Exception e)
	{
	    	Log.e("Fail 1", e.toString());
	    	Toast.makeText(getApplicationContext(), "Invalid IP Address",
			Toast.LENGTH_LONG).show();
	}     
	    
	    try
	    {
	     	BufferedReader reader = new BufferedReader
				(new InputStreamReader(is,"iso-8859-1"),8);
	        	StringBuilder sb = new StringBuilder();
	        	while ((line = reader.readLine()) != null)
		{
	   		    sb.append(line + "\n");
	       	}
	        	is.close();
	        	result = sb.toString();
	        Log.e("pass 2", "connection success ");
	}
	    catch(Exception e)
		{
		Log.e("Fail 2", e.toString());
	}     
	   
		try
		{
			int i;
			JSONArray array=new JSONArray(result);
	    //	JSONObject json_data = new JSONObject(result);
			int t=array.length();
			for(i=0;i<t;i++)
		
			{JSONObject feedObj = (JSONObject) array.get(i);

			FeedItem7 item = new FeedItem7();
			item.setId(feedObj.getInt("User Id"));
			System.out.println(feedObj.getInt("User Id"));
			item.setName(feedObj.getString("V_name"));
			System.out.println(feedObj.getString("V_name"));

			// Image might be null sometimes
			String image = feedObj.isNull("V_image") ? null : feedObj
					.getString("V_image");
			item.setImge(image);
			System.out.println(feedObj.getString("V_image"));;
			item.setStatus(feedObj.getString("Tagline"));
			System.out.println(feedObj.getString("Tagline"));
			//item.setProfilePic(feedObj.getString("User Image"));
			//System.out.println(feedObj.getString("User Image"));
			//item.setTimeStamp(feedObj.getString("Timestamp"));
		//	System.out.println(feedObj.getString("Timestamp"));

			// url might be null sometimes
			String feedUrl = feedObj.isNull("Location") ? null : feedObj
					.getString("Location");
			item.setUrl(feedUrl);
			System.out.println(feedObj.getString("Location"));

			feedItems.add(item);
			
		}
			listAdapter.notifyDataSetChanged();
			}
	    catch(Exception e)
		{
	    	Log.e("Fail 3", e.toString());
		}

		// We first check for cached request
		/*Cache cache = AppController.getInstance().getRequestQueue().getCache();
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
		//////////////////////////////////////////////////////////////////
*/
	}

	/**
	 * Parsing json reponse and passing the data to feed view list adapter
	 * */
	private void parseJsonFeed(JSONObject response) {
		try {
			JSONArray feedArray = response.getJSONArray("feed");

			for (int i = 0; i < feedArray.length(); i++) {
				JSONObject feedObj = (JSONObject) feedArray.get(i);

				FeedItem7 item = new FeedItem7();
				item.setId(feedObj.getInt("User Id"));
				System.out.println(feedObj.getInt("User Id"));
				item.setName(feedObj.getString("V_name"));
				System.out.println(feedObj.getString("V_name"));

				// Image might be null sometimes
				String image = feedObj.isNull("V_image") ? null : feedObj
						.getString("V_image");
				item.setImge(image);
				System.out.println(feedObj.getString("V_image"));;
				item.setStatus(feedObj.getString(""));
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

