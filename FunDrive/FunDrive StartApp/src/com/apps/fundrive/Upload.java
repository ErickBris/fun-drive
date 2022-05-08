package com.apps.fundrive;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.analytics.tracking.android.EasyTracker;
import com.startapp.android.publish.StartAppAd;

public class Upload extends SherlockActivity{

	Button btn_wallpaper,btn_ring,btn_video;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		StartAppAd.init(this, getString(R.string.startapp_dev_id), getString(R.string.startapp_app_id,false));
		setContentView(R.layout.activity_upload);
		StartAppAd.showSlider(this);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		setTitle("Upload Screen");
		btn_wallpaper=(Button)findViewById(R.id.btnImageUpload);
		btn_ring=(Button)findViewById(R.id.btnMp3Upload);
		btn_video=(Button)findViewById(R.id.btnVideoUpload);
 
		btn_wallpaper.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intbw=new Intent(Upload.this,Upload_Wallpaper.class);
				startActivity(intbw);

			}
		});

		btn_ring.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intbw=new Intent(Upload.this,Upload_Ringtone.class);
				startActivity(intbw);
			}
		});
		
		btn_video.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intvup=new Intent(Upload.this,Upload_Video.class);
				startActivity(intvup);
			}
		});

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
