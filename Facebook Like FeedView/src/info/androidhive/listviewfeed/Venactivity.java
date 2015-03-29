package info.androidhive.listviewfeed;

import info.androidhive.listviewfeed.adapter.FeedListAdapter2;
import info.androidhive.listviewfeed.app.AppController;

import info.androidhive.listviewfeed.data.FeedItem2;
import info.androidhive.listviewfeed.data.FeedItem3;

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
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.akarsh.streetfoodfinder4.SearchByLocation;
import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

public class Venactivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();
	private ListView listView;
	private FeedListAdapter2 listAdapter;
	public static List<FeedItem2> feedItems;
	//private String URL_FEED = "http://streettadka.comze.com/searchbyfoodfeed.php";
	 InputStream is=null;
		String result=null;
		String line=null;
		String URL_FEED;
		String passingitem;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.venactivity);
		StrictMode.ThreadPolicy policy = 
    	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy);
		Intent intent=getIntent();
		String code=intent.getStringExtra("code");
		System.out.println(code);
		if(code.equals("food")||code.equals("searchfood"))
			URL_FEED = "http://streettadka.comze.com/searchbyfoodfeed.php";
		else if(code.equals("location"))
			URL_FEED = "http://streettadka.comze.com/searchbyloc.php";
		
		
		int posn=intent.getIntExtra("pos",-1);
		if(code.equals("food"))
			passingitem=CategoryActivity.feedItems.get(posn).getFood();
		else if(code.equals("location"))
			passingitem=SearchByLocation.feedItemssbl.get(posn).getLoc();
		else if(code.equals("searchfood"))
			passingitem=intent.getStringExtra("data");
		listView = (ListView) findViewById(R.id.list);
		feedItems = new ArrayList<FeedItem2>();

		listAdapter = new FeedListAdapter2(this, feedItems);
		listView.setAdapter(listAdapter);
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View v, int pos, long id)  {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(Venactivity.this, VendorActivity.class);
				intent.putExtra("sno",pos);
				startActivity(intent);
			}
            
				// TODO Auto-generated method stub
				
			});
		// These two lines not needed,
		// just to get the look of facebook (changing background color & hiding the icon)
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#571f78")));
		getActionBar().setIcon(
				new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		
		
		ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>(1);
		 if(code.equals("food")||code.equals("searchfood"))
		nameValuePairs1.add(new BasicNameValuePair("food",passingitem));
		 else if(code.equals("location"))
			 nameValuePairs1.add(new BasicNameValuePair("loc",passingitem));
		try
		{
		HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost(URL_FEED);
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs1));
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

			FeedItem2 item = new FeedItem2();
			//item.setId(feedObj.getInt("User Id"));
			//System.out.println(feedObj.getInt("User Id"));
			item.setName(feedObj.getString("v_name"));
			System.out.println(feedObj.getString("v_name"));
			item.setLocation(feedObj.getString("l_name"));
			System.out.println(feedObj.getString("l_name"));
			// Image might be null sometimes
			//String image = feedObj.isNull("Post Image") ? null : feedObj
			//		.getString("Post Image");
			//item.setProfilePic(image);
			//System.out.println(feedObj.getString("Post Image"));;
			//item.setStatus(feedObj.getString("Comments"));
			//System.out.println(feedObj.getString("Comments"));
			item.setProfilePic(feedObj.getString("v_img_path"));
			System.out.println(feedObj.getString("v_img_path"));
			//item.setTimeStamp(feedObj.getString("Timestamp"));
			//System.out.println(feedObj.getString("Timestamp"));
			item.setRatings(feedObj.getString("avg_ratings"));
			System.out.println(feedObj.getString("avg_ratings"));
			// url might be null sometimes
			//String feedUrl = feedObj.isNull("Vendor Name") ? null : feedObj
			//		.getString("Vendor Name");
			//item.setUrl(feedUrl);
			//System.out.println(feedObj.getString("Vendor Name"));				
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

	}*/
	}

	/**
	 * Parsing json reponse and passing the data to feed view list adapter
	 * */
	private void parseJsonFeed(JSONObject response) {
		try {
			JSONArray feedArray = response.getJSONArray("feed");
			System.out.println("no:"+feedArray.length());
			for (int i = 0; i < feedArray.length(); i++) {
				JSONObject feedObj = (JSONObject) feedArray.get(i);

				FeedItem2 item = new FeedItem2();
				//item.setId(feedObj.getInt("User Id"));
				//System.out.println(feedObj.getInt("User Id"));
				item.setName(feedObj.getString("Vendorname"));
				System.out.println(feedObj.getString("Vendorname"));
				item.setLocation(feedObj.getString("Location"));
				System.out.println(feedObj.getString("Location"));
				// Image might be null sometimes
				//String image = feedObj.isNull("Post Image") ? null : feedObj
				//		.getString("Post Image");
				//item.setProfilePic(image);
				//System.out.println(feedObj.getString("Post Image"));;
				//item.setStatus(feedObj.getString("Comments"));
				//System.out.println(feedObj.getString("Comments"));
				item.setProfilePic(feedObj.getString("VendorImage"));
				System.out.println(feedObj.getString("VendorImage"));
				//item.setTimeStamp(feedObj.getString("Timestamp"));
				//System.out.println(feedObj.getString("Timestamp"));
				item.setRatings(feedObj.getString("Ratings"));
				System.out.println(feedObj.getString("Ratings"));
				// url might be null sometimes
				//String feedUrl = feedObj.isNull("Vendor Name") ? null : feedObj
				//		.getString("Vendor Name");
				//item.setUrl(feedUrl);
				//System.out.println(feedObj.getString("Vendor Name"));				
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
		getMenuInflater().inflate(R.menu.ven, menu);
		return true;
	}

}
