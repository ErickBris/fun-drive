package com.apps.fundrive;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.apps.fundrive.R;
import com.example.util.Constant;
import com.example.util.ZoomableImageView;
import com.google.analytics.tracking.android.EasyTracker;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class PinchZoom extends SherlockActivity {

	String[] mZoomImages,mZoomCatName;
	int position;
	public ImageLoader imageLoader; 
	DisplayImageOptions options;
	String Images;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pinchzoom);
		getSupportActionBar().hide();

		options = new DisplayImageOptions.Builder()
		.showImageForEmptyUri(R.drawable.app_icon)
		.showImageOnFail(R.drawable.app_icon)
		.resetViewBeforeLoading(true)
		.cacheOnDisc(true)
		.imageScaleType(ImageScaleType.EXACTLY)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.considerExifParams(true)
		.displayer(new FadeInBitmapDisplayer(300))
		.build();
		ZoomableImageView zoom=(ZoomableImageView)findViewById(R.id.IMAGEID);

		Intent i=getIntent();
 
 		Images=i.getStringExtra("ZOOM_IMAGE_URL");

		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
		ImageLoader.getInstance().displayImage(Constant.SERVER_IMAGE_UPFOLDER_CATEGORY+Images.replace(" ", "%20"), zoom, options, new SimpleImageLoadingListener());
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
