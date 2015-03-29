package info.androidhive.listviewfeed;

import info.androidhive.listviewfeed.adapter.FeedListAdapter3;
import info.androidhive.listviewfeed.adapter.FeedListAdapter4;
import info.androidhive.listviewfeed.data.FeedItem3;
import info.androidhive.listviewfeed.data.FeedItem4;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class CategoryActivity extends Activity {
	 InputStream is=null;
		String result=null;
		String line=null;
		private ListView listView;
		private FeedListAdapter4 listAdapter;
		public static List<FeedItem4> feedItems;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		StrictMode.ThreadPolicy policy = 
    	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy);
		
		listView = (ListView) findViewById(R.id.listView1);
		feedItems = new ArrayList<FeedItem4>();
		final EditText com=(EditText)findViewById(R.id.textView1);
		listAdapter = new FeedListAdapter4(this, feedItems);
		listView.setAdapter(listAdapter);
		Intent intent=getIntent();
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View v, int pos, long id)  {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(CategoryActivity.this, Venactivity.class);
				intent.putExtra("pos",pos);
				intent.putExtra("code","food");
				startActivity(intent);
			}
            
				// TODO Auto-generated method stub
				
			});
		String cat=intent.getStringExtra("cat");
		System.out.println(cat);
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs.add(new BasicNameValuePair("cat",cat));
		try
		{
			
			
		HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://streettadka.comze.com/ret-food.php");
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
	   
	    try
		{
			int i;
			JSONArray array=new JSONArray(result);
	    //	JSONObject json_data = new JSONObject(result);
			int t=array.length();
			for(i=0;i<t;i++)
		
			{JSONObject feedObj = (JSONObject) array.get(i);
			
			FeedItem4 item = new FeedItem4();
			//item.setId(feedObj.getInt("User Id"));
			//System.out.println(feedObj.getInt("User Id"));
			item.setFood(feedObj.getString("f_name"));
			System.out.println(feedObj.getString("f_name"));
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
			feedItems.add(item);
		}
			listAdapter.notifyDataSetChanged();
			}
	    catch(Exception e)
		{
	    	Log.e("Fail 3", e.toString());
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.category, menu);
		return true;
	}

}
