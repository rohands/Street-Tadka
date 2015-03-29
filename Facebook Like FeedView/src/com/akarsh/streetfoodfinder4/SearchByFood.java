package com.akarsh.streetfoodfinder4;

import java.util.List;

//import com.example.actionsearchview.MainActivity;
//import com.example.actionsearchview.NextActivity;

import info.androidhive.listviewfeed.CategoryActivity;
import info.androidhive.listviewfeed.R;
import info.androidhive.listviewfeed.Venactivity;

import android.app.Dialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

public class SearchByFood extends Fragment{
	
	View rootView;
	 private SearchView mSearchView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView= inflater.inflate(R.layout.searchbyfood, container, false);
		//////////////////////////////////////////////////////////////////////////////
		//SETTING CUSTOM TYPE FACE
		Button chaatsb=(Button) rootView.findViewById(R.id.button2);
		Button puffsb=(Button) rootView.findViewById(R.id.button3);
		Button rollsb=(Button) rootView.findViewById(R.id.button4);
		Button dosasb=(Button) rootView.findViewById(R.id.button5);
		Button sweetsb=(Button) rootView.findViewById(R.id.button6);
		Button juicesb=(Button) rootView.findViewById(R.id.button7);
chaatsb.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),CategoryActivity.class);
				intent.putExtra("cat", "Chaats");
				startActivity(intent);
			}});
		puffsb.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),CategoryActivity.class);
				intent.putExtra("cat", "Puffs");
				startActivity(intent);
			}});
		rollsb.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent(getActivity(),CategoryActivity.class);
		intent.putExtra("cat", "Rolls");
		startActivity(intent);
	}});
		dosasb.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent(getActivity(),CategoryActivity.class);
		intent.putExtra("cat", "Dosas");
		startActivity(intent);
	}});
		sweetsb.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent(getActivity(),CategoryActivity.class);
		intent.putExtra("cat", "Sweets");
		startActivity(intent);
	}});
		juicesb.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent(getActivity(),CategoryActivity.class);
		intent.putExtra("cat", "Juices");
		startActivity(intent);
	}});


		
		
		
		//the following statement refreshes the action bar
		setHasOptionsMenu(true);
		/*Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
	            "fonts/web.ttf");
	    chaatsb.setTypeface(tf);
	    puffsb.setTypeface(tf);
	    rollsb.setTypeface(tf);
	    dosasb.setTypeface(tf);
	    sweetsb.setTypeface(tf);
	    juicesb.setTypeface(tf);
	    Typeface tf2 = Typeface.createFromAsset(getActivity().getAssets(),
	            "fonts/GistLight.otf");
		TextView tv=(TextView) rootView.findViewById(R.id.textView1);
		tv.setTypeface(tf2);*/
		/////////////////////////////////////////////////////////////////////////////////
/*CheckBox chaats= (CheckBox) getActivity().findViewById(R.id.chaatsCheckBox);
CheckBox puffs= (CheckBox) getActivity().findViewById(R.id.puffsCheckBox);
CheckBox rolls= (CheckBox) getActivity().findViewById(R.id.rollsCheckBox);
CheckBox dosas= (CheckBox) getActivity().findViewById(R.id.dosasCheckBox);
CheckBox sweets= (CheckBox) getActivity().findViewById(R.id.sweetsCheckBox);*/
		/*final Dialog d= new Dialog(getActivity());
		d.setContentView(R.layout.setfavourites);
		d.setTitle("Categories");
		Button setFav=(Button) rootView.findViewById(R.id.button9);
setFav.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				d.show();
				
			}
		});*/
	ScrollView sc = (ScrollView) rootView.findViewById(R.id.scrollView1);

		return rootView;

	}
	@Override
	public void onCreateOptionsMenu(final Menu menu,final MenuInflater inflater) {
		// TODO Auto-generated method stub
		//MenuInflater inflater1 = getActivity().getMenuInflater();
        inflater.inflate(R.menu.search_by_food_actionbar, menu);
        
        
		super.onCreateOptionsMenu(menu, inflater);
		
		
       // return super.getActivity().onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		 switch (item.getItemId()) {
	        case R.id.action_search:
	        	 System.out.println("I am here!!");
	        	 mSearchView = (SearchView) item.getActionView();
	        	
	 	        setupSearchView(item);
	 	       

	            return true;
	            default:
			return super.onOptionsItemSelected(item);
	        }
		 
		
        
	}
	
	private void setupSearchView(MenuItem searchItem) {

        if (isAlwaysExpanded()) {
            mSearchView.setIconifiedByDefault(false);
        } else {
            searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
                    | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        }

        SearchManager searchManager = (SearchManager)getActivity(). getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();

            SearchableInfo info = searchManager.getSearchableInfo(getActivity().getComponentName());
            for (SearchableInfo inf : searchables) {
                if (inf.getSuggestAuthority() != null
                        && inf.getSuggestAuthority().startsWith("applications")) {
                    info = inf;
                }
            }
            mSearchView.setSearchableInfo(info);
        }

        mSearchView.setOnQueryTextListener((OnQueryTextListener) getActivity());
    }

    public boolean onQueryTextChange(String newText) {
       // mStatusView.setText("Query = " + newText);
    	System.out.println(newText);
        return false;
    }

    public boolean onQueryTextSubmit(String query) {
        //mStatusView.setText("Query = " + query + " : submitted");
        Intent intent=new Intent(getActivity(),Venactivity.class);
        intent.putExtra("data", query);
        intent.putExtra("code", "searchfood");
        startActivity(intent);
        
        return false;
    }

    public boolean onClose() {
      //  mStatusView.setText("Closed!");
        return false;
    }

    protected boolean isAlwaysExpanded() {
        return false;
    }
	
	
	
	/*public void onCheckboxClicked(View view) {
	    // Is the view now checked?
	    boolean checked = ((CheckBox) view).isChecked();
	    
	    // Check which checkbox was clicked
	    switch(view.getId()) {
	        case R.id.chaatsCheckBox:
	            if (checked)
	            {
	           // 	CheckBox chaats= (CheckBox) getActivity().findViewById(R.id.chaatsCheckBox);
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

/*CheckBox chaats= (CheckBox) getActivity().findViewById(R.id.chaatsCheckBox);
CheckBox puffs= (CheckBox) getActivity().findViewById(R.id.puffsCheckBox);
CheckBox rolls= (CheckBox) getActivity().findViewById(R.id.rollsCheckBox);
CheckBox dosas= (CheckBox) getActivity().findViewById(R.id.dosasCheckBox);
CheckBox sweets= (CheckBox) getActivity().findViewById(R.id.sweetsCheckBox);*/
	}
		
	
	

