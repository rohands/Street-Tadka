package com.akarsh.streetfoodfinder4;

import info.androidhive.listviewfeed.MypostActivity;
import info.androidhive.listviewfeed.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.akarsh.streetfoodfinder4.adapter.TabsPagerAdapter;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.FacebookError;
//import com.akarsh.streetfoodfinder4.adapter.NavDrawerListAdapter;
//import android.app.Dialog;
/*import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;*/
//import com.example.drawer1.MainActivity;

@SuppressLint("NewApi")
public class Main extends FragmentActivity implements
		ActionBar.TabListener,OnItemClickListener{
public static String h=null;
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	// Tab titles
	private String[] tabs = { "News Feed", "Search By Food", "Search By Location"/*,"Add Post"*/ };
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	private DrawerLayout drawerLayout;
	private ListView listView;
	private String[] planets;
	private int position;
	private ActionBarDrawerToggle drawerListener;
	public static Context con;
	//////////////////////////////////////////////////////////////////////

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		con=this;
		TextView name=(TextView) findViewById(R.id.textView1);
		name.setText(AndroidFacebookConnectActivity.name);
		TextView email=(TextView) findViewById(R.id.textView2);
		email.setText(AndroidFacebookConnectActivity.email);
		ImageView i=(ImageView) findViewById(R.id.imageView1);
		i.setImageBitmap(AndroidFacebookConnectActivity.bitmap);
		//////////////////////////////////////////////////////////////////////////////////////
		//SETTING FORNTS 
		//1)for profile
		Typeface mFont = Typeface.createFromAsset(getAssets(), "fonts/web.ttf");
		name.setTypeface(mFont);
		email.setTypeface(mFont);
		
		 h="no location found";
    	h=addressOf(12.934268,77.534326 );
    	
		//////////////////////////////////////////////////////////////////////////////////////
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		planets=getResources().getStringArray(R.array.planets);
		listView=(ListView) findViewById(R.id.drawerList);
		
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, planets));
		listView.setOnItemClickListener(this);
		drawerListener=new ActionBarDrawerToggle(this, drawerLayout, R.drawable.icon, R.string.open, R.string.close){
			
			@Override
			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerOpened(drawerView);
				//Toast.makeText(Main.this, "Drawer opened", Toast.LENGTH_LONG).show();
			}
			@Override
			public void onDrawerClosed(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerClosed(drawerView);
				//Toast.makeText(Main.this, "Drawer cloased", Toast.LENGTH_LONG).show();
			}
		};
			
			
		
		drawerLayout.setDrawerListener(drawerListener);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		
		/*final Dialog d= new Dialog(this);
		d.setContentView(R.layout.setfavourites);
		d.setTitle("Categories");
		Button setFav=(Button) findViewById(R.id.button9);
		setFav.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				d.show();
				
			}
		});
		CheckBox chaats= (CheckBox) findViewById(R.id.chaatsCheckBox);
		CheckBox puffs= (CheckBox) findViewById(R.id.puffsCheckBox);
		CheckBox rolls= (CheckBox) findViewById(R.id.rollsCheckBox);
		CheckBox dosas= (CheckBox) findViewById(R.id.dosasCheckBox);
		CheckBox sweets= (CheckBox) findViewById(R.id.sweetsCheckBox);
		
		
	}
	public void onCheckboxClicked(View view) {
	    // Is the view now checked?
	    boolean checked = ((CheckBox) view).isChecked();
	    
	    // Check which checkbox was clicked
	    switch(view.getId()) {
	        case R.id.chaatsCheckBox:
	            if (checked)
	            {
	            	CheckBox chaats= (CheckBox) findViewById(R.id.chaatsCheckBox);
	            }
	            else
	            {
	            	
	            }
	                
	            break;
	        case R.id.puffsCheckBox:
	        	if (checked)
	            {
	            	
	            }
	            else
	            {
	            	
	            }
	        	break;
	        case R.id.rollsCheckBox:
	            if (checked)
	            {
	            	
	            }
	            else
	            {
	            	
	            }
	                
	            break;
	        case R.id.dosasCheckBox:
	            if (checked)
	            {
	            	
	            }
	            else
	            {
	            	
	            }
	                
	            break;
	        case R.id.sweetsCheckBox:
	            if (checked)
	            {
	            	
	            }
	            else
	            {
	            	
	            }
	                
	            break;
	    }*/

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);		

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
			/////////////////////////////////////////////////////////////////


		
///////////////////////////////////////////////////////////////////////////////////////
		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		}
		
		Toast.makeText(this, h,
				Toast.LENGTH_LONG).show();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}
	
	////////////////////////////////////////////////////////////////////////////////////
	//Drawer
	@Override
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
	//}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		drawerListener.onConfigurationChanged(newConfig);
	}
   
/*	private static String APP_ID = "1446551692261358";
	private Facebook facebook = new Facebook(APP_ID);
	private AsyncFacebookRunner mAsyncRunner;
	String FILENAME = "AndroidSSO_data";
	private SharedPreferences mPrefs;*/
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//Toast.makeText(this, "it worked", Toast.LENGTH_LONG).show();	
position=arg2;
if(position==0)
{
	final Dialog d= new Dialog(this);
	d.setContentView(R.layout.setfavourites);
	d.setTitle("Categories");
	d.show();
}
else if(position==1)
{
	Intent sfl = new Intent(Main.this,SetFavouriteLocation.class);
	startActivity(sfl);
}
else if(position==2)
{
	Intent mp = new Intent(Main.this,MypostActivity.class);
	startActivity(mp);
}
else if(position==3)
{     
     System.out.println("in logout");
    
    	    AndroidFacebookConnectActivity.mAsyncRunner.logout(this, new RequestListener() {
    	        @Override
    	        public void onComplete(String response, Object state) {
    	            Log.d("Logout from Facebook", response);
    	            if (Boolean.parseBoolean(response) == true) {
    	                // User successfully Logged out
    	            }
    	        }
    	 
    	        @Override
    	        public void onIOException(IOException e, Object state) {
    	        }
    	 
    	        @Override
    	        public void onFileNotFoundException(FileNotFoundException e,
    	                Object state) {
    	        }
    	 
    	        @Override
    	        public void onMalformedURLException(MalformedURLException e,
    	                Object state) {
    	        }
    	 
    	        @Override
    	        public void onFacebookError(FacebookError e, Object state) {
    	        }
    	    });
    	
	
	Intent mp1 = new Intent(Main.this,AndroidFacebookConnectActivity.class);
	startActivity(mp1);
}
else if(position==4)
{
	Intent wh = new Intent(Main.this,WhatsHot.class);
	startActivity(wh);
}

//getActionBar().setTitle(planets[position]);
	}
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		drawerListener.syncState();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////
//Action bar customization
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		/*getMenuInflater().inflate(R.menu.main, menu);
		return true;*/
		/*MenuInflater inflater1 = getMenuInflater();
        inflater1.inflate(R.menu.activity_main_actions, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));*/
		
        return super.onCreateOptionsMenu(menu);
	}
	
	public static String addressOf(double lat,double lng)
	{
	Geocoder geocoder = new Geocoder(con,Locale.getDefault());
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
	System.out.println("*******************************************"+Add);
	h=Add;
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
	
public void tost()
{
	Toast.makeText(this, h,
			Toast.LENGTH_SHORT).show();	
}


}
