package com.apps.fundrive;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.apps.fundrive.R;
import com.example.favorite.RingDatabaseHandler;
import com.example.favorite.RingPojo;
import com.example.imageloader.ImageLoader;
import com.example.item.ItemRingCategoryItem;
import com.example.util.AlertDialogManager;
import com.example.util.Constant;
import com.example.util.DownloadTask;
import com.example.util.JsonUtils;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.startapp.android.publish.StartAppAd;

public class SingleRingtone extends SherlockActivity implements OnClickListener{

	ImageView img_rplay,img_rdown,img_rfav,img_rshare,img_ringfullimg;
	String  ringid,ringcatid,ringcatname,ringname,ringurl,ringdowncount,ruser,rtag,rsize,rstar,rimg;
	int position;
	private MediaPlayer mediaPlayer;
	public RingDatabaseHandler db;
	Dialog dialog;
	String Url,Name,rate_msg;
	AlertDialogManager alert = new AlertDialogManager();
	TextView txt_ringtone,txt_ringcontact,txt_ringnoti,txt_ringalarm,txt_ringname,txt_downcount,
	txt_ruploadname,txt_rsize,txt_rcatename,txt_rtags,txt_star;
	final Context context = this;
	List<ItemRingCategoryItem> arrayOfRingcatItem;
	ItemRingCategoryItem objAllBean; 
	public ImageLoader imageLoader; 
	private AdView mAdView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		StartAppAd.init(this, getString(R.string.startapp_dev_id), getString(R.string.startapp_app_id,false));
		setContentView(R.layout.playringtone);
		StartAppAd.showSlider(this);
		
		mAdView = (AdView) findViewById(R.id.adView);
		mAdView.loadAd(new AdRequest.Builder().build());
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		setTitle("Ringtone");
		img_rdown=(ImageView)findViewById(R.id.image_rdownload);
		img_rshare=(ImageView)findViewById(R.id.image_rshare);
		img_rfav=(ImageView)findViewById(R.id.image_rfavicon);
		img_ringfullimg=(ImageView)findViewById(R.id.image_ringfullimg);
		
		txt_ringname=(TextView)findViewById(R.id.text_ringnametitle);
		txt_downcount=(TextView)findViewById(R.id.text_downcount);
		db=new RingDatabaseHandler(SingleRingtone.this);
		txt_ruploadname=(TextView)findViewById(R.id.text_uploadnamer);
		txt_rcatename=(TextView)findViewById(R.id.text_categoryr);
		txt_rtags=(TextView)findViewById(R.id.text_tagnamer);
		txt_rsize=(TextView)findViewById(R.id.text_sizer);
		txt_star=(TextView)findViewById(R.id.text_rate_cnt);
		arrayOfRingcatItem=new ArrayList<ItemRingCategoryItem>();
		imageLoader=new ImageLoader(getApplicationContext());


		if (JsonUtils.isNetworkAvailable(SingleRingtone.this)) {
			new MyTask().execute(Constant.SINGLE_RINGTONE_URL+Constant.RINGTONE_ITEMID);
		} 

		else {
			showToast("No Network Connection!!!");
			alert.showAlertDialog(getApplicationContext(), "Internet Connection Error",
					"Please connect to working Internet connection", false);

		}
	}


	private	class MyTask extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(SingleRingtone.this);
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
				alert.showAlertDialog(getApplicationContext(), "Server Connection Error",
						"May Server Under Maintaines Or Low Network", false);

			} else {

				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(Constant.LATEST_ARRAY_NAME);
					JSONObject objJson = null;
					for (int i = 0; i < jsonArray.length(); i++) {
						objJson = jsonArray.getJSONObject(i);

						ItemRingCategoryItem objItem = new ItemRingCategoryItem();

						objItem.setRingItemId(objJson.getString(Constant.CATEITEMRING_RINDID));
						objItem.setRingItemCatId(objJson.getString(Constant.CATEITEMRING_RINDCATID));
						objItem.setRingItemCatName(objJson.getString(Constant.CATEITEMRING_CATENAME));
						objItem.setRingItemName(objJson.getString(Constant.CATEITEMRING_RINGNAME));
						objItem.setRingItemUrl(objJson.getString(Constant.CATEITEMRING_RINDURL));
						objItem.setRingItemDownCount(objJson.getString(Constant.CATEITEMRING_RINDDOWNCOUNT));
						objItem.setRingItemUser(objJson.getString(Constant.CATEITEMRING_RINDUSER));
						objItem.setRingItemTag(objJson.getString(Constant.CATEITEMRING_RINDTAG));
						objItem.setRingItemSize(objJson.getString(Constant.CATEITEMRING_RINDSIZE));
						objItem.setRingStar(objJson.getString(Constant.LATESTRING_RINGSTAR));
						objItem.setRingImage(objJson.getString(Constant.CATEITEMRING_RINDIMAGE));
						
						arrayOfRingcatItem.add(objItem);


					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

				setAdapterToListview();
			}

		}
	}

	public void setAdapterToListview() {

		objAllBean=arrayOfRingcatItem.get(0);
		ringid=objAllBean.getRingItemId();
		ringcatid=objAllBean.getRingItemCatId();
		ringcatname=objAllBean.getRingItemCatName();
		ringname=objAllBean.getRingItemName();
		ringurl=objAllBean.getRingItemUrl();
		ringdowncount=objAllBean.getRingItemDownCount();
		ruser=objAllBean.getRingItemUser();
		rtag=objAllBean.getRingItemTag();
		rsize=objAllBean.getRingItemSize();
		rstar=objAllBean.getRingStar();
		rimg=objAllBean.getRingImage();


		txt_ringname.setText(ringname);
		txt_downcount.setText(ringdowncount);
		txt_rcatename.setText(ringcatname);
		txt_ruploadname.setText(ruser);
		txt_rtags.setText(rtag);
		txt_rsize.setText(rsize);
		txt_star.setText(rstar);
		
		

		List<RingPojo> pojolist=db.getFavRow(ringid);
		if(pojolist.size()==0)
		{

			img_rfav.setImageResource(R.drawable.fav_hoverbtnselect);
		}
		else
		{	
			if(pojolist.get(0).getDRingItemId().equals(ringid));
			{
				img_rfav.setImageResource(R.drawable.favlike_hoverbtnselect);
			}

		}

		imageLoader.DisplayImage(Constant.RING_IMAGE_FOLDER_PATH+objAllBean.getRingImage().toString(), img_ringfullimg);

		
		URL url = null;
		try {
			url = new URL(Constant.SERVER_RINGTONE_FOLDER+ringurl);
			Log.e("urll", ""+Constant.SERVER_RINGTONE_FOLDER+ringurl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Url = getFileNameFromUrl(url);
		File filepath = new File(Environment.getExternalStorageDirectory() + Constant.DOWNLOAD_SDCARD_FOLDER_PATH_RINGTONE +Url);
		if (filepath.exists())
		{

			img_rdown.setImageResource(R.drawable.alarm_hoverbtnselect);

		}

		img_rdown.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				File filepath = new File(Environment.getExternalStorageDirectory() + Constant.DOWNLOAD_SDCARD_FOLDER_PATH_RINGTONE +Url);
				if (filepath.exists())
				{
					showCustomDialog();
					//	img_rdown.setImageResource(R.drawable.icon_set_ringtone);

				}
				else
				{

					if (JsonUtils.isNetworkAvailable(SingleRingtone.this)) {
						new MyTaskRDown().execute(Constant.DOWNLOADRING_URL+Constant.RINGCATITEMID);
						//Log.e("urlidd", ""+Constant.DOWNLOADRING_URL+Constant.RINGCATITEMID);
					
					} else {
						showToast("No Network Connection!!!");
						alert.showAlertDialog(SingleRingtone.this, "Internet Connection Error",
								"Please connect to working Internet connection", false);
					}

					new DownloadTask(SingleRingtone.this,ringid).execute(Constant.SERVER_RINGTONE_FOLDER+ringurl);
					img_rdown.setImageResource(R.drawable.alarm_hoverbtnselect);
					AlertDialog.Builder alert=new AlertDialog.Builder(SingleRingtone.this);
					alert.setTitle("Download");

					//MyTaskRDown(SingleRingtone.this);
					alert.setIcon(R.drawable.app_icon);
					alert.setMessage("Please Wait Some Time...Your Download Start Begin In Notification Bar.");
					alert.setPositiveButton("Yes",new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Toast.makeText(SingleRingtone.this, "DownLoad Path:"+Constant.DOWNLOAD_SDCARD_FOLDER_PATH_RINGTONE, Toast.LENGTH_SHORT).show();		

						}
					});

					alert.show();
				}
			}
		});

		img_rshare.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				sendIntent.putExtra(Intent.EXTRA_TEXT," I Would like to share this with you. Here You Can Download This Application from PlayStore "+"https://play.google.com/store/apps/details?id="+getPackageName() );
				sendIntent.setType("text/plain");
				startActivity(sendIntent);

			}
		});

		initView();

		img_rfav.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				List<RingPojo> pojolist=db.getFavRow(ringid);
				if(pojolist.size()==0)
				{

					Toast.makeText(getApplicationContext(), "Add to Favorite", Toast.LENGTH_SHORT).show();
					db.AddtoFavoritering(new RingPojo(ringid,ringcatid,ringcatname,ringname,ringurl,ringdowncount,ruser,rtag,rsize,rstar,rimg));
					img_rfav.setImageResource(R.drawable.favlike_hoverbtnselect);
				}
				else
				{	
					if(pojolist.get(0).getDRingItemId().equals(ringid))
					{

						db.RemoveFav(new RingPojo(ringid));
						Toast.makeText(getApplicationContext(), "Removed From Favorite", Toast.LENGTH_SHORT).show();
						img_rfav.setImageResource(R.drawable.fav_hoverbtnselect);
					}
				}
			}
		});


		txt_star.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(context);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.ratedialog);
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

				final TextView txtrate=(TextView)dialog.findViewById(R.id.textView1);
				Button btnrate=(Button)dialog.findViewById(R.id.button1);
				RatingBar rating=(RatingBar)dialog.findViewById(R.id.ratingBar1);

				rating.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

					@Override
					public void onRatingChanged(RatingBar ratingBar, float rating,
							boolean fromUser) {
						// TODO Auto-generated method stub
						txtrate.setText(String.valueOf(rating));
					}
				});


				btnrate.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Constant.DEVICE_ID=Secure.getString(SingleRingtone.this.getContentResolver(),
								Secure.ANDROID_ID);
						Log.e("Device Id", Constant.DEVICE_ID);

						if(txtrate.getText().toString().equalsIgnoreCase(""))
						{
							showToast("Plase Select Atleast one Star");
						}
						else
						{
							dialog.dismiss();

							if (JsonUtils.isNetworkAvailable(SingleRingtone.this)) {
								new MyTaskRating().execute(Constant.RATE_RINGTONE_URL+Constant.DEVICE_ID+"&rate="+txtrate.getText().toString()+"&ringtone_id="+ringid);
							} else {
								showToast("No Network Connection!!!");
								alert.showAlertDialog(SingleRingtone.this, "Internet Connection Error",
										"Please connect to working Internet connection", false);
							}
						}

					}
				});
				dialog.show();
			}
		});


	}

	public void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}



	private void initView() {
		img_rplay=(ImageView)findViewById(R.id.image_playring);
		img_rplay.setOnClickListener( this);
		mediaPlayer = new MediaPlayer();

	}

	private void primarySeekBarProgressUpdater() {
		if (mediaPlayer.isPlaying()) {
			Runnable notification = new Runnable() {
				public void run() {
					primarySeekBarProgressUpdater();

					int left=mediaPlayer.getCurrentPosition();
					int lefttosecond= (int) ((left / 1000) % 60);
					int leftminute=(int)((left / (1000*60)) % 60);

					String timeleft;
					timeleft=String.valueOf("0"+leftminute+"."+pad(lefttosecond));
					Log.e("left", timeleft);
				}
			};
		}
	}

	//for second0to9
	private Object pad(int hour2) {
		if (hour2 >= 10)
			return String.valueOf(hour2);
		else
			return "0" + String.valueOf(hour2);
	}

	public void onClick(View v) {
		if(v.getId() == R.id.image_playring){
			/** ImageButton onClick event handler. Method which start/pause mediaplayer playing */
			try {

				Uri myUri = Uri.parse(Constant.SERVER_RINGTONE_FOLDER+ringurl);
				mediaPlayer.setDataSource(this,myUri); // setup song from http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
				mediaPlayer.prepare(); // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer. 
			} catch (Exception e) {
				e.printStackTrace();
			}

			if(!mediaPlayer.isPlaying()){
				mediaPlayer.start();
				img_rplay.setImageResource(R.drawable.pause_btn);
			}else {
				mediaPlayer.pause();
				img_rplay.setImageResource(R.drawable.play_btn);
			}

			primarySeekBarProgressUpdater();
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mediaPlayer.stop();
		mediaPlayer.release();
		mediaPlayer=null;

	}

	protected void showCustomDialog() {

		dialog = new Dialog(SingleRingtone.this,
				android.R.style.Theme_Translucent);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCancelable(true);
		dialog.setContentView(R.layout.setdialogactivity);
	//	dialog.setTitle("Set Ringtone");

		txt_ringtone=(TextView)dialog.findViewById(R.id.text_setring);
		txt_ringnoti=(TextView)dialog.findViewById(R.id.text_setringnotifi);
		txt_ringalarm=(TextView)dialog.findViewById(R.id.text_setringalam);

		txt_ringtone.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				//Toast.makeText(getApplicationContext(), "ring", Toast.LENGTH_SHORT).show();

				File f =new File(Environment.getExternalStorageDirectory() + Constant.DOWNLOAD_SDCARD_FOLDER_PATH_RINGTONE +Url);
				ContentResolver mCr = getContentResolver();
				ContentValues values = new ContentValues();
				values.put(MediaStore.MediaColumns.DATA, f.getAbsolutePath());
				values.put(MediaStore.MediaColumns.TITLE, Environment.getExternalStorageDirectory() + Constant.DOWNLOAD_SDCARD_FOLDER_PATH_RINGTONE +Url);
				values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
				values.put(MediaStore.MediaColumns.SIZE, f.length());
				values.put(MediaStore.Audio.Media.ARTIST, R.string.app_name);
				values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
				values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
				values.put(MediaStore.Audio.Media.IS_ALARM, true);
				values.put(MediaStore.Audio.Media.IS_MUSIC, true);

				Uri uri = MediaStore.Audio.Media.getContentUriForPath(f
						.getAbsolutePath());
				getContentResolver().delete(uri, MediaStore.MediaColumns.DATA 
						+ "=\"" + f.getAbsolutePath() + "\"", null);
				Uri newUri = mCr.insert(uri, values);

				try {
					RingtoneManager.setActualDefaultRingtoneUri(SingleRingtone.this,
							RingtoneManager.TYPE_RINGTONE, newUri);
					Settings.System.putString(mCr, Settings.System.RINGTONE,
							newUri.toString());

				} catch (Throwable t) {

				}

				Toast.makeText(getApplicationContext(), "Ring Tone Set", Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});


		txt_ringnoti.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(), "noti", Toast.LENGTH_SHORT).show();

				File f =new File(Environment.getExternalStorageDirectory() + Constant.DOWNLOAD_SDCARD_FOLDER_PATH_RINGTONE +Url);
				ContentResolver mCr = getContentResolver();
				ContentValues values = new ContentValues();
				values.put(MediaStore.MediaColumns.DATA, f.getAbsolutePath());
				values.put(MediaStore.MediaColumns.TITLE, Environment.getExternalStorageDirectory() + Constant.DOWNLOAD_SDCARD_FOLDER_PATH_RINGTONE +Url);
				values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
				values.put(MediaStore.MediaColumns.SIZE, f.length());
				values.put(MediaStore.Audio.Media.ARTIST, R.string.app_name);
				values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
				values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
				values.put(MediaStore.Audio.Media.IS_ALARM, true);
				values.put(MediaStore.Audio.Media.IS_MUSIC, true);

				Uri uri = MediaStore.Audio.Media.getContentUriForPath(f
						.getAbsolutePath());
				getContentResolver().delete(uri, MediaStore.MediaColumns.DATA 
						+ "=\"" + f.getAbsolutePath() + "\"", null);
				Uri newUri = mCr.insert(uri, values);

				try {
					RingtoneManager.setActualDefaultRingtoneUri(SingleRingtone.this,
							RingtoneManager.TYPE_NOTIFICATION, newUri);
					Settings.System.putString(mCr, Settings.System.NOTIFICATION_SOUND,
							newUri.toString());

				} catch (Throwable t) {

				}

				Toast.makeText(getApplicationContext(), "Notification Tone Set", Toast.LENGTH_SHORT).show();
				dialog.dismiss();

			}
		});

		txt_ringalarm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(), "alarm", Toast.LENGTH_SHORT).show();

				File f =new File(Environment.getExternalStorageDirectory() + Constant.DOWNLOAD_SDCARD_FOLDER_PATH_RINGTONE +Url);
				ContentResolver mCr = getContentResolver();
				ContentValues values = new ContentValues();
				values.put(MediaStore.MediaColumns.DATA, f.getAbsolutePath());
				values.put(MediaStore.MediaColumns.TITLE, Environment.getExternalStorageDirectory() + Constant.DOWNLOAD_SDCARD_FOLDER_PATH_RINGTONE +Url);
				values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
				values.put(MediaStore.MediaColumns.SIZE, f.length());
				values.put(MediaStore.Audio.Media.ARTIST, R.string.app_name);
				values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
				values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
				values.put(MediaStore.Audio.Media.IS_ALARM, true);
				values.put(MediaStore.Audio.Media.IS_MUSIC, true);

				Uri uri = MediaStore.Audio.Media.getContentUriForPath(f
						.getAbsolutePath());
				getContentResolver().delete(uri, MediaStore.MediaColumns.DATA 
						+ "=\"" + f.getAbsolutePath() + "\"", null);
				Uri newUri = mCr.insert(uri, values);

				try {
					RingtoneManager.setActualDefaultRingtoneUri(SingleRingtone.this,
							RingtoneManager.TYPE_ALARM, newUri);
					Settings.System.putString(mCr, Settings.System.ALARM_ALERT,
							newUri.toString());

				} catch (Throwable t) {

				}

				Toast.makeText(getApplicationContext(), "Alarm Tone Set", Toast.LENGTH_SHORT).show();
				dialog.dismiss();

			}
		});

		dialog.show();
	}

	public static String getFileNameFromUrl(URL url) {

		String urlString = url.getFile();

		return urlString.substring(urlString.lastIndexOf('/') + 1).split("\\?")[0].split("#")[0];
	}

	private	class MyTaskRDown extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(SingleRingtone.this);
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
				alert.showAlertDialog(getApplicationContext(), "Server Connection Error",
						"May Server Under Maintaines Or Low Network", false);

			} else {

				try {
					Log.e("result", result);
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(Constant.DOWNLOAD_ARRAY);
					JSONObject objJson = null;
					for (int i = 0; i < jsonArray.length(); i++) {
						objJson = jsonArray.getJSONObject(i);

						String success=objJson.getString("download");
						txt_downcount.setText(success);

					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}


	}


	private	class MyTaskRating extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(SingleRingtone.this);
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
				showToast("No data found from web!!!");

			} else {

				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(Constant.RATE_ARRAY_NAME);
					JSONObject objJson = null;
					for (int i = 0; i < jsonArray.length(); i++) {
						objJson = jsonArray.getJSONObject(i);
						rate_msg=objJson.getString(Constant.RATE_MSG);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
				setAdapterToListviewRate();
			}

		}

		public void setAdapterToListviewRate() {

			showToast(rate_msg);

			if(rate_msg.equals("You have already rated"))
			{

			}
			else
			{

				if (JsonUtils.isNetworkAvailable(SingleRingtone.this)) {
					new MyTask().execute(Constant.SINGLE_RINGTONE_URL+Constant.RINGTONE_ITEMID);
				} 

				else {
					showToast("No Network Connection!!!");
					alert.showAlertDialog(getApplicationContext(), "Internet Connection Error",
							"Please connect to working Internet connection", false);

				}
			}

		}
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
