package com.apps.fundrive;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class DownloadMainActivity extends  SherlockFragmentActivity implements ActionBar.TabListener {

	private String[] tabs = { "RINGTONE", "WALLPAPER"};
	private DownalodTabsPagerAdapter mAdapter;
	private ViewPager viewPager;
	ActionBar.Tab tab;
	 
	private AdView mAdView;
	 

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
  		setContentView(R.layout.ringactivity_main);
  		
 		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
 		getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
 		setTitle("My Download");
		viewPager = (ViewPager) findViewById(R.id.pager);
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mAdapter = new DownalodTabsPagerAdapter(getSupportFragmentManager());
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
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}
   
}
