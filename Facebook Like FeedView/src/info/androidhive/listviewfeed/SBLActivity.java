package info.androidhive.listviewfeed;

import info.androidhive.listviewfeed.adapter.FeedListAdapter5;
import info.androidhive.listviewfeed.data.FeedItem5;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class SBLActivity extends Activity {
	private ListView listViewsbl;
	private FeedListAdapter5 listAdaptersbl;
	public static List<FeedItem5> feedItemssbl;
	
	InputStream is=null;
	String result=null;
	String line=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchbylocation);
		StrictMode.ThreadPolicy policy = 
    	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy);
		
		listViewsbl = (ListView)findViewById(R.id.listView1);
		feedItemssbl = new ArrayList<FeedItem5>();
		//final EditText com=(EditText)rootView.findViewById(R.id.textView1);
		listAdaptersbl = new FeedListAdapter5(this, feedItemssbl);
		listViewsbl.setAdapter(listAdaptersbl);
		//Intent intent=getIntent();
		listViewsbl.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View v, int pos, long id)  {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(SBLActivity.this, Venactivity.class);
				intent.putExtra("pos",pos);
				intent.putExtra("code","location");
				startActivity(intent);
			}
            
				// TODO Auto-generated method stub
				
			});
		//String cat=intent.getStringExtra("cat");
		//System.out.println(cat);
		//ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		//nameValuePairs.add(new BasicNameValuePair("cat",cat));
		try
		{
			
			
		HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://streettadka.comze.com/ret-loc.php");
	       // httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
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
			
			FeedItem5 item = new FeedItem5();
			//item.setId(feedObj.getInt("User Id"));
			//System.out.println(feedObj.getInt("User Id"));
			item.setLoc(feedObj.getString("l_name"));
			System.out.println(feedObj.getString("l_name"));
			/////item.setComments(feedObj.getString("Comments"));
			//System.out.println(feedObj.getString("Comments"));
			// Image might be null sometimes
			//String image = feedObj.isNull("Post Image") ? null : feedObj
			//		.getString("Post Image");
			//item.setProfilePic(image);
			//System.out.println(feedObj.getString("Post Image"));;
			//item.setStatus(feedObj.getString("Comments"));
			//System.out.println(feedObj.getString("Comments"));
			//item.setuserPic(feedObj.getString("UserImage"));
			//System.out.println(feedObj.getString("UserImage"));
			//item.setTimeStamp(feedObj.getString("Timestamp"));
			//System.out.println(feedObj.getString("Timestamp"));
			//item.setRatings(feedObj.getString("Ratings"));
			//System.out.println(feedObj.getString("Ratings"));
			// url might be null sometimes
			//String feedUrl = feedObj.isNull("Vendor Name") ? null : feedObj
			//		.getString("Vendor Name");
			//item.setUrl(feedUrl);
			//System.out.println(feedObj.getString("Vendor Name"));				
			feedItemssbl.add(item);
		}
			listAdaptersbl.notifyDataSetChanged();
			}
	    catch(Exception e)
		{
	    	Log.e("Fail 3", e.toString());
		}		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sbl, menu);
		return true;
	}

}
