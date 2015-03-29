package com.akarsh.streetfoodfinder4;

//import com.example.autocomplete.R;

import info.androidhive.listviewfeed.R;

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

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
 
public class SearchResultsActivity extends Activity implements  OnItemClickListener, OnItemSelectedListener {
 
	AutoCompleteTextView textView=null;
	 InputStream is=null;
		String result=null;
		String line=null;
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
       
		StrictMode.ThreadPolicy policy = 
    	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy);
	
        // get the action bar
        ActionBar actionBar = getActionBar();
 
        // Enabling Back navigation on Action Bar icon
        actionBar.setDisplayHomeAsUpEnabled(true);
 
       // txtQuery = (TextView) findViewById(R.id.txtQuery);
        textView=(AutoCompleteTextView) findViewById(R.id.txtQuery);
        handleIntent(getIntent());
    }
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
            long arg3) {
        // TODO Auto-generated method stub
        //Log.d("AutocompleteContacts", "onItemSelected() position " + position);
    }
 
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
         
        //InputMethodManager imm = (InputMethodManager) getSystemService(
            //    INPUT_METHOD_SERVICE);
          //  imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
 
    }
 
  
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
         
        // Show Alert       
        Toast.makeText(getBaseContext(), "Position:"+arg2+" Month:"+arg0.getItemAtPosition(arg2),
                Toast.LENGTH_LONG).show();
         
        Log.d("AutocompleteContacts", "Position:"+arg2+" Month:"+arg0.getItemAtPosition(arg2));
         
    }
     
    protected void onResume() {
        super.onResume();
    }
  
    protected void onDestroy() {
        super.onDestroy();
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }
 
    /**
     * Handling intent data
     */
    private void handleIntent(Intent intent) {
       // if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
           // String query = intent.getStringExtra(SearchManager.QUERY);
            
            /**
             * Use this query to display search results like 
             * 1. Getting the data from SQLite and showing in listview 
             * 2. Making webrequest and displaying the data 
             * For now we just display the query only
             */
            try
        	{
        	HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://streettadka.comze.com/ret-food.php");
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
        		String[] foods=new String[t];
        		//System.out.println(t);
        		for(i=0;i<array.length();i++)
        	
        		{JSONObject json_data =array.getJSONObject(i);
        		foods[i]=(json_data.getString("f_name"));
        		System.out.println(foods[i]);};
        		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, foods);
        		textView.setThreshold(1);
                 
                 //Set adapter to AutoCompleteTextView
                  textView.setAdapter(adapter);
                  textView.setOnItemSelectedListener(this);
                  textView.setOnItemClickListener(this);
        		
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
            
            
            
            //txtQuery.setText("Search Query: " + query);
 
        }
 
    
    
    /*public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_by_food_actionbar, menu);
 
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
 
        return super.onCreateOptionsMenu(menu);
    }*/
}