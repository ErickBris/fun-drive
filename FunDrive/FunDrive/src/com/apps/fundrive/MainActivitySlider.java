package com.apps.fundrive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.apps.fundrive.R;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.BaseSliderView.OnSliderClickListener;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.adapter.DrawerAdapter;
import com.example.imageloader.ImageLoader;
import com.example.item.ItemLatest;
import com.example.item.ItemRingLatest;
import com.example.item.ItemSlider;
import com.example.item.ItemVideoLatest;
import com.example.item.Item_Drawer;
import com.example.util.AlertDialogManager;
import com.example.util.Constant;
import com.example.util.JsonUtils;
import com.example.util.SessionManager;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.startapp.android.publish.Ad;
import com.startapp.android.publish.AdEventListener;
import com.startapp.android.publish.StartAppAd;

public class MainActivitySlider extends SherlockActivity implements OnSliderClickListener{

	private SliderLayout mDemoSlider;
	List<ItemSlider> arrayofSlider;
	ItemSlider itemSlider;
	List<ItemLatest> arrayofWallpaper;
	ItemLatest itemWallpaper;
	List<ItemRingLatest> arrayOfRingtone;
	ItemRingLatest itemRingtone;
	List<ItemVideoLatest> arrayOfVideo;
	ItemVideoLatest itemVideo;
	AlertDialogManager alert = new AlertDialogManager();
	LinearLayout linearContent,linearRingtone,linearVideo;
	ImageLoader imageLoader;
	private int mPhotoSize;
	DrawerLayout mDrawerLayout;
	ListView mDrawerList,lsvlatest;
	ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	List<Item_Drawer> arraydrawer;
	DrawerAdapter adapter;

	SessionManager session;
	HashMap<String, String> user;
	String strMessage;
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private static final String TAG = "GCMRelated";
	GoogleCloudMessaging gcm;
	AtomicInteger msgId = new AtomicInteger();
	String regid;
	LinearLayout laywall,layring,layvideo;
	private AdView mAdView;
	private StartAppAd startAppAd = new StartAppAd(this); 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		StartAppAd.init(this, getString(R.string.startapp_dev_id), getString(R.string.startapp_app_id,false));
		setContentView(R.layout.activity_main_slide);
		getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));


		startAppAd.loadAd(new AdEventListener() {

			@Override
			public void onReceiveAd(Ad arg0) {
				// TODO Auto-generated method stub
				startAppAd.showAd(); // show the ad
				startAppAd.loadAd(); // load the next ad
			}

			@Override
			public void onFailedToReceiveAd(Ad arg0) {
				// TODO Auto-generated method stub

			}
		});

		mPhotoSize = getResources().getDimensionPixelSize(R.dimen.photo_size);
		mDemoSlider = (SliderLayout)findViewById(R.id.slider);

		Constant.DEVICE_ID=Secure.getString(this.getContentResolver(),
				Secure.ANDROID_ID);

		mAdView = (AdView) findViewById(R.id.adView);
		mAdView.loadAd(new AdRequest.Builder().build());
 
		arrayofSlider=new ArrayList<ItemSlider>();
		arrayofWallpaper=new ArrayList<ItemLatest>();
		arrayOfRingtone=new ArrayList<ItemRingLatest>();
		arrayOfVideo=new ArrayList<ItemVideoLatest>();

		linearContent=(LinearLayout)findViewById(R.id.rel_c_content);
		linearRingtone=(LinearLayout)findViewById(R.id.rel_c_content_ringtone);
		linearVideo=(LinearLayout)findViewById(R.id.rel_c_content_video);
		imageLoader=new ImageLoader(MainActivitySlider.this);

		laywall=(LinearLayout)findViewById(R.id.linear_title_latest);
		layring=(LinearLayout)findViewById(R.id.linear_title_ringtone);
		layvideo=(LinearLayout)findViewById(R.id.linear_title_video);

		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		arraydrawer=new ArrayList<Item_Drawer>();

		laywall.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentlatestwall=new Intent(getApplicationContext(),WallMainActivity.class);
				startActivity(intentlatestwall);
			}
		});

		layring.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentlatestring=new Intent(getApplicationContext(),RingMainActivity.class);
				startActivity(intentlatestring);
			}
		});

		layvideo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentlatestvideo=new Intent(getApplicationContext(),VideoMainActivity.class);
				startActivity(intentlatestvideo);
			}
		});

		Item_Drawer recentAlbum = new Item_Drawer(1,"Wallpapers",R.drawable.drawerwall_hoverbtnselect);
		Item_Drawer recentAlbum2 = new Item_Drawer(2,"Ringtones",R.drawable.drawerring_hoverbtnselect);
		Item_Drawer recentAlbum3 = new Item_Drawer(3,"Videos",R.drawable.drawervideo_hoverbtnselect);
		Item_Drawer recentAlbum4 = new Item_Drawer(4,"Upload",R.drawable.drawerupload_hoverbtnselect);
		Item_Drawer recentAlbum5 = new Item_Drawer(5,"Feedback",R.drawable.drawerfeedback_hoverbtnselect);
		Item_Drawer recentAlbum6 = new Item_Drawer(6,"About Us",R.drawable.drawerabout_hoverbtnselect);
		Item_Drawer recentAlbum7;


		if(isLogin())
		{
			recentAlbum7 = new Item_Drawer(7,"Logout",R.drawable.drawerlogout_hoverbtnselect);	
		}
		else
		{
			recentAlbum7 = new Item_Drawer(7,"Login",R.drawable.drawerlogin_hoverbtnselect);
		}


		arraydrawer.add(0, recentAlbum);
		arraydrawer.add(1, recentAlbum2);
		arraydrawer.add(2, recentAlbum3);
		arraydrawer.add(3, recentAlbum4);
		arraydrawer.add(4,recentAlbum5);
		arraydrawer.add(5,recentAlbum6);
		arraydrawer.add(6,recentAlbum7);



		adapter = new DrawerAdapter(MainActivitySlider.this, R.layout.drawer_lsv_item,
				arraydrawer);
		mDrawerList.setAdapter(adapter);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
				mDrawerLayout, /* DrawerLayout object */
				R.drawable.menu_navicon, /* nav drawer image to replace 'Up' caret */
				R.string.drawer_open, /* "open drawer" description for accessibility */
				R.string.drawer_close /* "close drawer" description for accessibility */
				) {
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
				// creates call to
				// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				drawerView.bringToFront();
				// creates call to
				// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		//		// enable ActionBar app icon to behave as action to toggle nav drawer


		if (checkPlayServices()) {
			gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
			regid = getRegistrationId(getApplicationContext());
			if (regid.isEmpty()) {
				new RegisterApp(getApplicationContext(), gcm, getAppVersion(getApplicationContext())).execute();
			}else{
				Log.i(TAG, "Device already Registered");
			}
		} else {
			Log.i(TAG, "No valid Google Play Services APK found.");
		}

		if (JsonUtils.isNetworkAvailable(MainActivitySlider.this)) {
			new MyTaskFeatured().execute(Constant.SLIDER_URL);
		}   else {
			showToast("No Network Connection!!!");
			alert.showAlertDialog(MainActivitySlider.this, "Internet Connection Error",
					"Please connect to working Internet connection", false);

		}


	}

	// The click listener for ListView in the navigation drawer
	private class DrawerItemClickListener implements
	ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			//Toast.makeText(Category_Activity.this, ""+position, Toast.LENGTH_SHORT).show();
			if(position==0)
			{

				Intent intentwall=new Intent(getApplicationContext(),WallMainActivity.class);
				mDrawerLayout.closeDrawer(mDrawerList);
				startActivity(intentwall);
			}
			else if(position==1)
			{
				Intent intentring=new Intent(getApplicationContext(),RingMainActivity.class);
				mDrawerLayout.closeDrawer(mDrawerList);
				startActivity(intentring);
			}
			else if(position==2)
			{
				Intent intentvideo=new Intent(getApplicationContext(),VideoMainActivity.class);
				mDrawerLayout.closeDrawer(mDrawerList);
				startActivity(intentvideo);
			}

			else if(position==3)
			{
				if(isLogin())
				{
					Intent intentupload=new Intent(getApplicationContext(),Upload.class);
					mDrawerLayout.closeDrawer(mDrawerList);
					startActivity(intentupload);
				}
				else
				{
					Toast.makeText(MainActivitySlider.this, "If you want to upload something you need to Login First.", Toast.LENGTH_SHORT).show();
					Intent up_wall=new Intent(getApplicationContext(),AuthonticationActivity.class);
					mDrawerLayout.closeDrawer(mDrawerList);
					startActivity(up_wall);	
					Constant.LOGIN_FORM="Upload";
				}
			}
			else if(position==4)
			{
				Intent intentfeedback=new Intent(getApplicationContext(),Activity_Feedback.class);
				mDrawerLayout.closeDrawer(mDrawerList);
				startActivity(intentfeedback);
			}
			else if(position==5)
			{
				Intent intentabout=new Intent(getApplicationContext(),AboutActivity.class);
				mDrawerLayout.closeDrawer(mDrawerList);
				startActivity(intentabout);
			}
			else if(position==6)
			{
				if(isLogin())
				{
					Logout();	
				}
				else
				{
					Intent up_wall=new Intent(getApplicationContext(),AuthonticationActivity.class);
					mDrawerLayout.closeDrawer(mDrawerList);
					startActivity(up_wall);	
					Constant.LOGIN_FORM="Main";

				}
			}


		}

	}
	public	class MyTaskFeatured extends AsyncTask<String, Void, String> {


		@Override
		protected void onPreExecute() {
			super.onPreExecute();


		}

		@Override
		protected String doInBackground(String... params) {
			return JsonUtils.getJSONString(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);



			if (null == result || result.length() == 0) {
				showToast(getString(R.string.nodata));

			} else {

				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(Constant.SLIDER_ARRAY);
					JSONObject objJson = null;
					for (int i = 0; i < jsonArray.length(); i++) {
						objJson = jsonArray.getJSONObject(i);

						ItemSlider objItem = new ItemSlider();
						objItem.setName(objJson.getString(Constant.SLIDER_NAME));
						objItem.setImage(objJson.getString(Constant.SLIDER_IMAGE));
						objItem.setLink(objJson.getString(Constant.SLIDER_LINK));
						arrayofSlider.add(objItem);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

				setAdapterToFeatured();
			}

		}
	}


	public void setAdapterToFeatured() {

		for(int i=0;i<arrayofSlider.size();i++)
		{
			itemSlider=arrayofSlider.get(i);
			TextSliderView textSliderView = new TextSliderView(MainActivitySlider.this);
			textSliderView.description(itemSlider.getName());
			textSliderView.image(Constant.SERVER_IMAGE_UPFOLDER_THUMB+itemSlider.getImage().toString());
			textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
			textSliderView.getBundle().putString("extra", itemSlider.getLink());
			textSliderView.setOnSliderClickListener(this);
			mDemoSlider.addSlider(textSliderView);
		}

		//mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
		mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
		mDemoSlider.setCustomAnimation(new DescriptionAnimation());
		// mDemoSlider.setDuration(4000);

		if (JsonUtils.isNetworkAvailable(MainActivitySlider.this)) {
			new MyWallpaper().execute(Constant.F_WALLPAPER_URL);
		}   else {
			showToast("No Network Connection!!!");
			alert.showAlertDialog(MainActivitySlider.this, "Internet Connection Error",
					"Please connect to working Internet connection", false);

		}

	}

	public void showToast(String msg) {
		Toast.makeText(MainActivitySlider.this, msg, Toast.LENGTH_LONG).show();
	}


	private	class MyWallpaper extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(MainActivitySlider.this);
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			return JsonUtils.getJSONString(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}

			if (null == result || result.length() == 0) {
				showToast("Server Connection Error");
				alert.showAlertDialog(MainActivitySlider.this, "Server Connection Error",
						"May Server Under Maintaines Or Low Network", false);

			} else {

				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(Constant.LATEST_ARRAY_NAME);
					JSONObject objJson = null;
					for (int i = 0; i < jsonArray.length(); i++) {
						objJson = jsonArray.getJSONObject(i);

						ItemLatest objItem = new ItemLatest();

						objItem.setCategoryName(objJson.getString(Constant.LATEST_IMAGE_CATEGORY_NAME));
						objItem.setImageurl(objJson.getString(Constant.LATEST_IMAGE_URL));
						objItem.setImageDCount(objJson.getString(Constant.LATEST_IMAGE_WDCOUNTL));
						objItem.setImageWId(objJson.getString(Constant.LATEST_IMAGE_WID));
						objItem.setImageWUser(objJson.getString(Constant.LATEST_IMAGE_WUSER));
						objItem.setImageWTag(objJson.getString(Constant.LATEST_IMAGE_WTAG));
						objItem.setImageWSize(objJson.getString(Constant.LATEST_IMAGE_WSIZE));
						objItem.setImageStar(objJson.getString(Constant.LATEST_IMAGE_STAR));
						arrayofWallpaper.add(objItem);

					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
				Wallpaper();
				if (JsonUtils.isNetworkAvailable(MainActivitySlider.this)) {
					new MyRingTone().execute(Constant.F_RINGTONE_URL);
				}   else {
					showToast("No Network Connection!!!");
					alert.showAlertDialog(MainActivitySlider.this, "Internet Connection Error",
							"Please connect to working Internet connection", false);

				}
			}

		}
	}

	public void Wallpaper()
	{
		linearContent.removeAllViews();
		int i=0;
		do
		{
			if(i>=arrayofWallpaper.size())
			{
				return;
			}

			View view = getLayoutInflater().inflate(R.layout.latest_grid_item, null);
			final ImageView imageView = (ImageView)view.findViewById(R.id.img_subcategory);
			imageView.setId(i);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setLayoutParams(new LinearLayout.LayoutParams(mPhotoSize,
					mPhotoSize));
			linearContent.addView(view);
			itemWallpaper=arrayofWallpaper.get(i);
			imageLoader.DisplayImage(Constant.SERVER_IMAGE_UPFOLDER_THUMB+itemWallpaper.getImageurl().toString(), imageView);



			imageView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intslider=new Intent(MainActivitySlider.this,SingleWallpaper.class);
					itemWallpaper=arrayofWallpaper.get(imageView.getId());
					Constant.CATEGORY_ITEMWALLID=itemWallpaper.getImageWId();
					startActivity(intslider);
				}
			});
			i++;
		}while(true);


	}

	public void RingTone()
	{
		linearRingtone.removeAllViews();
		int i=0;
		do
		{
			if(i>=arrayOfRingtone.size())
			{
				return;
			}

			View view = getLayoutInflater().inflate(R.layout.latest_grid_item, null);
			final ImageView imageView = (ImageView)view.findViewById(R.id.img_subcategory);
			imageView.setId(i);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setLayoutParams(new LinearLayout.LayoutParams(mPhotoSize,
					mPhotoSize));
			linearRingtone.addView(view);
			itemRingtone=arrayOfRingtone.get(i);
			imageLoader.DisplayImage(Constant.RING_IMAGE_FOLDER_PATH+itemRingtone.getRingImage().toString(), imageView);



			imageView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intslider=new Intent(MainActivitySlider.this,SingleRingtone.class);
					itemRingtone=arrayOfRingtone.get(imageView.getId());
					Constant.RINGTONE_ITEMID=itemRingtone.getRingId();
					startActivity(intslider);
				}
			});
			i++;
		}while(true);
	}

	public void Videos()
	{
		linearVideo.removeAllViews();
		int i=0;
		do
		{
			if(i>=arrayOfVideo.size())
			{
				return;
			}

			View view = getLayoutInflater().inflate(R.layout.latest_grid_item, null);
			final ImageView imageView = (ImageView)view.findViewById(R.id.img_subcategory);
			imageView.setId(i);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setLayoutParams(new LinearLayout.LayoutParams(mPhotoSize,
					mPhotoSize));
			linearVideo.addView(view);
			itemVideo=arrayOfVideo.get(i);

			if(itemVideo.getVPlayIds().equals("000q1w2"))
			{
				imageLoader.DisplayImage(Constant.SERVER_IMAGE_UPFOLDER_THUMB+itemVideo.getVThumb().toString(), imageView);
			}
			else
			{
				imageLoader.DisplayImage(Constant.YOUTUBE_IMAGE_FRONT+itemVideo.getVPlayIds().toString()+Constant.YOUTUBE_IMAGE_BACK, imageView);
			}

			imageView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intslider=new Intent(MainActivitySlider.this,VideoSingle.class);
					itemVideo=arrayOfVideo.get(imageView.getId());
					Constant.VIDEO_ITEMID=itemVideo.getVId();
					startActivity(intslider);
				}
			});
			i++;
		}while(true);
	}

	private	class MyRingTone extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(MainActivitySlider.this);
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			return JsonUtils.getJSONString(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}

			if (null == result || result.length() == 0) {
				showToast("Server Connection Error");

			} else {

				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(Constant.LATEST_ARRAY_NAME);
					JSONObject objJson = null;
					for (int i = 0; i < jsonArray.length(); i++) {
						objJson = jsonArray.getJSONObject(i);

						ItemRingLatest objItem = new ItemRingLatest();

						objItem.setRingId(objJson.getString(Constant.LATESTRING_RINGID));
						objItem.setRingCatId(objJson.getString(Constant.LATESTRING_RINGCATID));
						objItem.setRingCatName(objJson.getString(Constant.LATESTRING_RINGCATENAME));
						objItem.setRingName(objJson.getString(Constant.LATESTRING_RINGNAME));
						objItem.setRingUrl(objJson.getString(Constant.LATESTRING_RINGURL));
						objItem.setRingLDownCount(objJson.getString(Constant.LATESTRING_RINGDOWNCOUNT));
						objItem.setRingLUser(objJson.getString(Constant.LATESTRING_RINGUSER));
						objItem.setRingLTag(objJson.getString(Constant.LATESTRING_RINGTAG));
						objItem.setRingLSize(objJson.getString(Constant.LATESTRING_RINGSIZE));
						objItem.setRingStar(objJson.getString(Constant.LATESTRING_RINGSTAR));
						objItem.setRingImage(objJson.getString(Constant.LATESTRING_RINGIMAGE));
						arrayOfRingtone.add(objItem);


					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

				RingTone();
				if (JsonUtils.isNetworkAvailable(MainActivitySlider.this)) {
					new MyVideo().execute(Constant.F_VIDEO_URL);
				}   else {
					showToast("No Network Connection!!!");
				}
			}

		}
	}

	private	class MyVideo extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(MainActivitySlider.this);
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			return JsonUtils.getJSONString(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}

			if (null == result || result.length() == 0) {
				showToast("Server Connection Error");

			} else {

				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(Constant.LATEST_ARRAY_NAME);
					JSONObject objJson = null;
					for (int i = 0; i < jsonArray.length(); i++) {
						objJson = jsonArray.getJSONObject(i);

						ItemVideoLatest objItem = new ItemVideoLatest();

						objItem.setVId(objJson.getString(Constant.VLATEST_VID));
						objItem.setVType(objJson.getString(Constant.VLATEST_VTYPE));
						objItem.setVCatId(objJson.getString(Constant.VLATEST_VCID));
						objItem.setVCatName(objJson.getString(Constant.VLATEST_VCATNAME));
						objItem.setVThumb(objJson.getString(Constant.VLATEST_VTHUMB));
						objItem.setVTag(objJson.getString(Constant.VLATEST_VTAG));
						objItem.setVUrl(objJson.getString(Constant.VLATEST_VURL));
						objItem.setVSize(objJson.getString(Constant.VLATEST_VSIZE));
						objItem.setVUser(objJson.getString(Constant.VLATEST_VUSER));
						objItem.setVRate(objJson.getString(Constant.VLATEST_VRATE));
						objItem.setVPlayIds(objJson.getString(Constant.VLATEST_VVIDEOPLAYID));
						objItem.setVName(objJson.getString(Constant.VLATEST_VVIDEONAME));

						arrayOfVideo.add(objItem);


					}

				} catch (JSONException e) {
					e.printStackTrace();
				}


				Videos();
			}

		}
	}


	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}



	public void checkLogin(){
		// Check login status
		SessionManager sessionManager=new SessionManager(getApplicationContext());
		if(!sessionManager.isLoggedIn()){
			// user is not logged in redirect him to Login Activity
			Intent i = new Intent(getApplicationContext(), AuthonticationActivity.class);
			// Closing all the Activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			// Staring Login Activity
			startActivity(i);
			finish();
		}

	}

	public boolean isLogin() {

		SessionManager sessionManager=new SessionManager(getApplicationContext());
		return sessionManager.isLoggedIn();
	}

	/**
	 * Check the device to make sure it has the Google Play Services APK. If
	 * it doesn't, display a dialog that allows users to download the APK from
	 * the Google Play Store or enable it in the device's system settings.
	 */

	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i(TAG, "This device is not supported.");
				finish();
			}
			return false;
		}
		return true;
	}

	/**
	 * Gets the current registration ID for application on GCM service.
	 * <p>
	 * If result is empty, the app needs to register.
	 *
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i(TAG, "Registration not found.");
			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(getApplicationContext());
		if (registeredVersion != currentVersion) {
			Log.i(TAG, "App version changed.");
			return "";
		}
		return registrationId;
	}

	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGCMPreferences(Context context) {
		// This sample app persists the registration ID in shared preferences, but
		// how you store the regID in your app is up to you.
		return getSharedPreferences(WallMainActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);
	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}


	}
	public void Logout() {

		SharedPreferences pref = getSharedPreferences(SessionManager.PREF_NAME, SessionManager.PRIVATE_MODE);
		Editor	editor = pref.edit();
		editor.clear();
		editor.commit();

		// After logout redirect user to Loing Activity
		Intent i = new Intent(getApplicationContext(), AuthonticationActivity.class);
		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		// Add new Flag to start new Activity
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		// Staring Login Activity
		startActivity(i);

		finish();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.slide_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{       
		switch (menuItem.getItemId()) 
		{
		case android.R.id.home: 
			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}
			return true;

		case R.id.menu_overflow:
			//just override click 
			return true;

		case R.id.menu_slidefav:
			Intent intmfav=new Intent(getApplicationContext(),FavMainActivity.class);
			startActivity(intmfav);

			return true;

		case R.id.menu_slidedown:
			Intent intmdown=new Intent(getApplicationContext(),DownloadMainActivity.class);
			startActivity(intmdown);

			return true;

		default:
			return super.onOptionsItemSelected(menuItem);
		}

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// Toast.makeText(appContext, "BAck", Toast.LENGTH_LONG).show();
			AlertDialog.Builder alert = new AlertDialog.Builder(
					MainActivitySlider.this);
			alert.setTitle(R.string.app_name);
			alert.setIcon(R.drawable.app_icon);
			alert.setMessage("Are You Sure You Want To Quit?");

			alert.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {

					//you may open Interstitial Ads here

					finish();
				}



			});

			alert.setNegativeButton("Rate App",
					new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

					final String appName = getPackageName();//your application package name i.e play store application url
					try {
						startActivity(new Intent(Intent.ACTION_VIEW,
								Uri.parse("market://details?id="
										+ appName)));
					} catch (android.content.ActivityNotFoundException anfe) {
						startActivity(new Intent(
								Intent.ACTION_VIEW,
								Uri.parse("http://play.google.com/store/apps/details?id="
										+ appName)));
					}

				}
			});
			alert.show();
			return true;
		}

		return super.onKeyDown(keyCode, event);

	}

	@Override
	protected void onResume() {
		super.onResume();
		startAppAd.onResume();
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

	@Override
	public void onSliderClick(BaseSliderView slider) {
		// TODO Auto-generated method stub
		startActivity(new Intent(
			     Intent.ACTION_VIEW,
			     Uri.parse(slider.getBundle().getString("extra"))));
	}

}
