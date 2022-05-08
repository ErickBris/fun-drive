package com.apps.fundrive;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.example.adapter.DrawerAdapter;
import com.example.item.Item_Drawer;
import com.example.util.AlertDialogManager;
import com.example.util.Constant;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class VideoMainActivity extends  SherlockFragmentActivity implements ActionBar.TabListener {

	private String[] tabs = { "RECENT", "CATEGORIES","MOST POPULAR"};
	private VideoTabsPagerAdapter mAdapter;
	private ViewPager viewPager;
	ActionBar.Tab tab;
	 
	List<Item_Drawer> arraydrawer;
	AlertDialogManager alert = new AlertDialogManager();
	final Context context = this;
	DrawerAdapter adapter;
	private AdView mAdView;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
   		setContentView(R.layout.ringactivity_main);
   		
 		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
 		getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
 		setTitle("Videos");
		viewPager = (ViewPager) findViewById(R.id.pager);
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mAdapter = new VideoTabsPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(mAdapter);

		mAdView = (AdView) findViewById(R.id.adView);
		mAdView.loadAd(new AdRequest.Builder().build());
		 
		//tab added in action bar
		for(String tab_name:tabs)
		{
			tab = getSupportActionBar().newTab();
			tab.setText(tab_name);
			tab.setTabListener(this);
			getSupportActionBar().addTab(tab);
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				getSupportActionBar().setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction transaction) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction transaction) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction transaction) {
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		  MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
	    
	    final SearchView searchView = (SearchView) menu.findItem(R.id.search)
              .getActionView();
	    
	    final MenuItem searchMenuItem = menu.findItem(R.id.search);
	    searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus) {
					 searchMenuItem.collapseActionView();
					searchView.setQuery("", false);
	            }
			}
		});
	    
	    
	    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
	        @Override
	        public boolean onQueryTextChange(String newText) {
	
	        	return false;
	        }

	        @Override
	        public boolean onQueryTextSubmit(String query) {
	            // Do something
	        	Intent intsearch=new Intent(VideoMainActivity.this,Search_Video.class);
	        	Constant.SEARCH=query;
	        	startActivity(intsearch);
	            return true;
	        }
	    });
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{       
		switch (menuItem.getItemId()) 
		{
		case android.R.id.home: 
			onBackPressed();
			break;

		default:
			return super.onOptionsItemSelected(menuItem);
		}
		return true;
	}
 
   
}
