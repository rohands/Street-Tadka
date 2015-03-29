package info.androidhive.listviewfeed;

import info.androidhive.listviewfeed.adapter.FeedListAdapter2;
import info.androidhive.listviewfeed.adapter.FeedListAdapter3;
import info.androidhive.listviewfeed.app.AppController;
import info.androidhive.listviewfeed.data.FeedItem2;
import info.androidhive.listviewfeed.data.FeedItem3;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.Button;
import android.widget.EditText;
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

public class VendorActivity extends Activity {
	private static final String TAG = VendorActivity.class.getSimpleName();
	private ListView listView;
	private FeedListAdapter3 listAdapter;
	public static List<FeedItem3> feedItems;
	 InputStream is=null;
		String result=null;
		String line=null;
		
	private String URL_FEED = "http://streettadka.comze.com/vencomm.php";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vendoractivity);
		Button add=(Button)findViewById(R.id.button1);
		StrictMode.ThreadPolicy policy = 
    	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy);
		
		
		listView = (ListView) findViewById(R.id.list2);
		feedItems = new ArrayList<FeedItem3>();
		final EditText com=(EditText)findViewById(R.id.textView1);
		listAdapter = new FeedListAdapter3(this, feedItems);
		listView.setAdapter(listAdapter);
		Intent intent = getIntent();
		//String message = ((intent.getIntExtra("sno", 0)).toString());
		int k=intent.getIntExtra("sno", -1);
		System.out.println(k);
		final String ven_name=Venactivity.feedItems.get(k).getName();
		System.out.println(ven_name);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#571f78")));
		getActionBar().setIcon(
				new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		//String comments=com.getText().toString();
		
		final ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>(1);
 		 
		nameValuePairs1.add(new BasicNameValuePair("ven_name",ven_name));
		
		
		try
		{
		HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://streettadka.comze.com/vencomm.php");
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
			
			FeedItem3 item = new FeedItem3();
			//item.setId(feedObj.getInt("User Id"));
			//System.out.println(feedObj.getInt("User Id"));
			item.setName(feedObj.getString("Username"));
			System.out.println(feedObj.getString("Username"));
			item.setComments(feedObj.getString("Comments"));
			System.out.println(feedObj.getString("Comments"));
			// Image might be null sometimes
			//String image = feedObj.isNull("Post Image") ? null : feedObj
			//		.getString("Post Image");
			//item.setProfilePic(image);
			//System.out.println(feedObj.getString("Post Image"));;
			//item.setStatus(feedObj.getString("Comments"));
			//System.out.println(feedObj.getString("Comments"));
			item.setuserPic(feedObj.getString("UserImage"));
			System.out.println(feedObj.getString("UserImage"));
			//item.setTimeStamp(feedObj.getString("Timestamp"));
			//System.out.println(feedObj.getString("Timestamp"));
			//item.setRatings(feedObj.getString("Ratings"));
			//System.out.println(feedObj.getString("Ratings"));
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
		
		
		
		
		//final String uid="2";
		
		
		
		
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				String comments=com.getText().toString();
				System.out.println("hello");
				System.out.println(comments);
				System.out.println(ven_name);
			//	System.out.println(uid);
				String email=AndroidFacebookConnectActivity.email;
				
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
				nameValuePairs.add(new BasicNameValuePair("comments",comments));
				nameValuePairs.add(new BasicNameValuePair("ven_name",ven_name));
				nameValuePairs.add(new BasicNameValuePair("email",email));
				try
    	    	{
    			HttpClient httpclient = new DefaultHttpClient();
    		        HttpPost httppost = new HttpPost("http://streettadka.comze.com/ins-comm.php");
    		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
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
    	        Intent inte= getIntent();
    		    finish();
    		    startActivity(inte);
    	        
    	        
			}});
		
		
	}
		/*
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
			JsonObjectRequest jsonReq = new JsonObjectRequest(Method.POST,
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
					}){
				 
			    @Override
			    protected Map<String, String> getParams() 
			    {  
			            Map<String, String>  params = new HashMap<String, String> ();  
			            params.put("ven_name",ven_name);  
			           // params.put("domain", "http://itsalif.info");
			             
			            return params;  
			    }
			 
			};

			// Adding request to volley request queue
			AppController.getInstance().addToRequestQueue(jsonReq);
		}

	}*/
	private void parseJsonFeed(JSONObject response) {
		try {
			JSONArray feedArray = response.getJSONArray("feed");
			//System.out.println("no:"+feedArray.length());
			for (int i = 0; i < feedArray.length(); i++) {
				JSONObject feedObj = (JSONObject) feedArray.get(i);
				
				FeedItem3 item = new FeedItem3();
				//item.setId(feedObj.getInt("User Id"));
				//System.out.println(feedObj.getInt("User Id"));
				item.setName(feedObj.getString("Username"));
				System.out.println(feedObj.getString("Username"));
				item.setComments(feedObj.getString("Comments"));
				System.out.println(feedObj.getString("Comments"));
				// Image might be null sometimes
				//String image = feedObj.isNull("Post Image") ? null : feedObj
				//		.getString("Post Image");
				//item.setProfilePic(image);
				//System.out.println(feedObj.getString("Post Image"));;
				//item.setStatus(feedObj.getString("Comments"));
				//System.out.println(feedObj.getString("Comments"));
				item.setuserPic(feedObj.getString("UserImage"));
				System.out.println(feedObj.getString("UserImage"));
				//item.setTimeStamp(feedObj.getString("Timestamp"));
				//System.out.println(feedObj.getString("Timestamp"));
				//item.setRatings(feedObj.getString("Ratings"));
				//System.out.println(feedObj.getString("Ratings"));
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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.vendor, menu);
		return true;
	}

}
