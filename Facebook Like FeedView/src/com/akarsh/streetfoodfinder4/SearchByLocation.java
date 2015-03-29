package com.akarsh.streetfoodfinder4;

import info.androidhive.listviewfeed.R;
import info.androidhive.listviewfeed.Venactivity;
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

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class SearchByLocation extends Fragment {

	Button button;
	View rootView;
	private ListView listViewsbl;
	private FeedListAdapter5 listAdaptersbl;
	public static List<FeedItem5> feedItemssbl;
	private int position;
	
	InputStream is=null;
	String result=null;
	String line=null;
	GoogleMap map ;
	TextView loc;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		rootView = inflater.inflate(R.layout.searchbylocation, container, false);
		//the following statement refreshes the action bar
				setHasOptionsMenu(true);
				StrictMode.ThreadPolicy policy = 
		    	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    	StrictMode.setThreadPolicy(policy);
				
		    	map = ((SupportMapFragment) getFragmentManager()
			              .findFragmentById(R.id.map1)).getMap();
		    	
		    	/*
		    	/////////
		    	//object for getting address
		    	////
		    	getAddress a=new getAddress();
		    	String h="no location found";
		    	h=a.addressOf(77.33, 33.33);
		    	Toast.makeText(getActivity().getApplicationContext(), h,
						Toast.LENGTH_LONG).show();
		    	/////////
		    	 
		    	 */

				String h="no location found";
				Double lat=12.934268;
				Double lng=77.534326;
		    	h=Main.addressOf(lat,lng);
		    	  
				listViewsbl = (ListView)rootView.findViewById(R.id.listView1);
				feedItemssbl = new ArrayList<FeedItem5>();
				//final EditText com=(EditText)rootView.findViewById(R.id.textView1);
				listAdaptersbl = new FeedListAdapter5(getActivity(), feedItemssbl);
				listViewsbl.setAdapter(listAdaptersbl);
				//Intent intent=getIntent();
				listViewsbl.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> av, View v, int pos, long id)  {
						// TODO Auto-generated method stub
						
						Intent intent = new Intent(getActivity(), Venactivity.class);
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
			    	Toast.makeText(getActivity().getApplicationContext(), "Invalid IP Address",
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
			    
				/*
		MarkerOptions marker = new MarkerOptions().position(
					new LatLng(lat,lng))
					.title(h).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).draggable(true);
					//.icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarker));
					;
		
			map.addMarker(marker);
			*/	
			CameraPosition cameraPosition = new CameraPosition.Builder()
			.target(new LatLng(lat,lng)).zoom(15).build();

	map.animateCamera(CameraUpdateFactory
			.newCameraPosition(cameraPosition));
			  		

					////////////
					///when user drag the screen , get the lat lng of center screen , get 
					///the address .
					////////////
					map.setOnCameraChangeListener(new OnCameraChangeListener() {
					    @Override
					    public void onCameraChange(CameraPosition cameraPosition) {
					      
					    	System.out.println("lat="+cameraPosition.target.latitude);
					    	System.out.println("lng="+cameraPosition.target.longitude);
					
					    	Double myLat=cameraPosition.target.latitude;
					    	Double myLng=cameraPosition.target.longitude;
					    	Main.addressOf(myLat, myLng);
					    	
					    	
							Toast.makeText(getActivity(), Main.h,
									Toast.LENGTH_SHORT).show();
					    }
					});
					return rootView;
					
	}
	
	@Override
	public void onCreateOptionsMenu(final Menu menu,final MenuInflater inflater) {
		// TODO Auto-generated method stub
		//MenuInflater inflater1 = getActivity().getMenuInflater();
        inflater.inflate(R.menu.search_by_location_actionbar, menu);
		super.onCreateOptionsMenu(menu, inflater);
		
       // return super.getActivity().onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		 switch (item.getItemId()) {
	        case R.id.action_searchl:
	            // search action
	        	System.out.println("yo");
	            return true;
	        case R.id.action_addvendor:
	        	Intent av = new Intent(getActivity(),AddVendor.class);
	        	startActivity(av);
	        	return true;
	        	
	            default:
			return super.onOptionsItemSelected(item);
	        }
		
        
	}
///////////////
////Method for getting address from lat lng
/////////////
	/*
private String getAddress(double lat,double lng)
{
Geocoder geocoder = new Geocoder(this,Locale.getDefault());
String Add = null;
try {
	System.out.println("Trying to get address");
  List<Address> addresses = geocoder.getFromLocation(lat,
          lng, 1);

  if (addresses != null) {
      Address returnedAddress = addresses.get(0);
      StringBuilder strReturnedAddress = new StringBuilder(
              "Address:\n");
      
      for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
          strReturnedAddress
                  .append(returnedAddress.getAddressLine(i)).append(
                          "\n");
      }
      Add=strReturnedAddress.toString();
     System.out.println(Add);
  } else {
      System.out.println("No Address returned!");
  }
} catch (IOException e) {
  // TODO Auto-generated catch block
  e.printStackTrace();
  System.out.println("Canont get Address!");
}
System.out.println("address acquired");
return Add;
}
*/
	}

