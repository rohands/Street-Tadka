package com.akarsh.streetfoodfinder4;

import info.androidhive.listviewfeed.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ProfileDrawer extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_drawer);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		//drawer creation
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile_drawer, menu);
		return true;
	}

}
