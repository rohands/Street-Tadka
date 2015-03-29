package com.akarsh.streetfoodfinder4;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import info.androidhive.listviewfeed.R;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SetFavouriteLocation extends Activity implements OnItemClickListener {
	private DrawerLayout drawerLayout;
	private ListView listView;
	AutoCompleteTextView textView=null;		
	InputStream is=null;
	String result=null;
	String line=null;
	private String[] planets;
	private int position;
	private ActionBarDrawerToggle drawerListener;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setfavouritelocation);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout1);
		planets=getResources().getStringArray(R.array.planets);
		listView=(ListView) findViewById(R.id.listView3);
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, planets));
		listView.setOnItemClickListener(this);
		drawerListener=new ActionBarDrawerToggle(this, drawerLayout, R.drawable.icon, R.string.open, R.string.close){
			
			@Override
			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerOpened(drawerView);
				Toast.makeText(SetFavouriteLocation.this, "Drawer opened", Toast.LENGTH_LONG).show();
			}
			@Override
			public void onDrawerClosed(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerClosed(drawerView);
				Toast.makeText(SetFavouriteLocation.this, "Drawer cloased", Toast.LENGTH_LONG).show();
			}
		};
			
			
		
		drawerLayout.setDrawerListener(drawerListener);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		//////////////////////////////////////////////////
		//auto complete
		
		
		StrictMode.ThreadPolicy policy = 
    	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy);
	
	//View rootView = inflater.inflate(R.layout.searchbyfood, container, false);
	
	textView=(AutoCompleteTextView) findViewById(R.id.abc2);
	
	try
	{
	HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://streettadka.comze.com/ret-loc.php");
        //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpclient.execute(httppost); 
        HttpEntity entity = response.getEntity();
        is = entity.getContent();
        Log.e("pass 1", "connection success ");
}
    catch(Exception e)
{
    	Log.e("Fail 1", e.toString());
    	//Toast.makeText(getApplicationContext(), "Invalid IP Address",
		//Toast.LENGTH_LONG).show();
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
		String[] locs=new String[t];
		//System.out.println(t);
		for(i=0;i<array.length();i++)
	
		{JSONObject json_data =array.getJSONObject(i);
		locs[i]=(json_data.getString("l_name"));
		System.out.println(locs[i]);};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, locs);
		 textView.setThreshold(1);
         
         //Set adapter to AutoCompleteTextView
          textView.setAdapter(adapter);
       /*   textView.setOnItemSelectedListener(this);
          textView.setOnItemClickListener(this);*/
		
    	//name=(json_data.getString("c_name"));
    	//System.out.println(name);
    	//res.setText(name);}
	//Toast.makeText(getBaseContext(), "Name : "+name,
		//Toast.LENGTH_SHORT).show();
	}
    catch(Exception e)
	{
    	Log.e("Fail 3", e.toString());
	}
	
	
	////////////////////////////////////////////////////
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(drawerListener.onOptionsItemSelected(item))
		{
			return true;
		}
		
	/*	  // Take appropriate action for each action item click
		 
       switch (item.getItemId()) {
        case R.id.action_search:
            // search action
            return true;
            default:*/
		return super.onOptionsItemSelected(item);
        }
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		drawerListener.onConfigurationChanged(newConfig);
	}
	
}
