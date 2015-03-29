package com.akarsh.streetfoodfinder4.adapter;

//import com.akarsh.streetfoodfinder4.AddPost;
import com.akarsh.streetfoodfinder4.NewsFeed;
import com.akarsh.streetfoodfinder4.SearchByFood;
import com.akarsh.streetfoodfinder4.SearchByLocation;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new NewsFeed();
		case 1:
			// Games fragment activity
			return new SearchByFood();
		case 2:
			// Movies fragment activity
			return new SearchByLocation();
		/*case 3:
			// Movies fragment activity
			return new AddPost();*/
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
