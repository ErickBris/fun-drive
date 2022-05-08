package com.apps.fundrive;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.example.favorite.VideoDatabaseHandler;
import com.example.favorite.VideoPojo;
import com.example.imageloader.ImageLoader;
import com.example.item.ItemVideoCategoryItem;
import com.example.play.OpenYouTubePlayerActivity;
import com.example.util.AlertDialogManager;
import com.example.util.Constant;
import com.example.util.JsonUtils;
import com.example.youtube.YoutubePlay;
import com.google.analytics.tracking.android.EasyTracker;
import com.startapp.android.publish.StartAppAd;


public class VideoSingle extends SherlockActivity implements OnClickListener{

	ImageView img_playvideo,img_rfav,img_rshare,img_video;
	String  vid,vtype,vcid,vcname,vthumb,vtag,vurl,vsize,vuser,vrate,vplayid,vname;
	int position;
	public VideoDatabaseHandler db;
	Dialog dialog;
	String Url,Name,rate_msg;
	AlertDialogManager alert = new AlertDialogManager();
	TextView txt_ruploadname,txt_rsize,txt_rcatename,txt_rtags,txt_star,txt_videoname;
	final Context context = this;
	List<ItemVideoCategoryItem> arrayOfRingcatItem;
	ItemVideoCategoryItem objAllBean; 
	public ImageLoader imageLoader; 
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		StartAppAd.init(this, getString(R.string.startapp_dev_id), getString(R.string.startapp_app_id,false));
		setContentView(R.layout.playvideo);
		StartAppAd.showSlider(this);
 		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		setTitle("Video");
		img_rshare=(ImageView)findViewById(R.id.image_rshare);
		img_rfav=(ImageView)findViewById(R.id.image_rfavicon);
		img_playvideo=(ImageView)findViewById(R.id.image_playvideo);
		img_video=(ImageView)findViewById(R.id.image_ringplayicon);

		db=new VideoDatabaseHandler(VideoSingle.this);
		txt_ruploadname=(TextView)findViewById(R.id.text_uploadnamer);
		txt_rcatename=(TextView)findViewById(R.id.text_categoryr);
		txt_rtags=(TextView)findViewById(R.id.text_tagnamer);
		txt_rsize=(TextView)findViewById(R.id.text_sizer);
		txt_star=(TextView)findViewById(R.id.text_rate_cnt);
		txt_videoname=(TextView)findViewById(R.id.text_videoname);
		
		arrayOfRingcatItem=new ArrayList<ItemVideoCategoryItem>();
		imageLoader=new ImageLoader(getApplicationContext());

		if (JsonUtils.isNetworkAvailable(VideoSingle.this)) {
			new MyTask().execute(Constant.VIDEO_SINGLE_URL+Constant.VIDEO_ITEMID);
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

			pDialog = new ProgressDialog(VideoSingle.this);
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

						ItemVideoCategoryItem objItem = new ItemVideoCategoryItem();

						objItem.setVItemId(objJson.getString(Constant.VCITEM_VID));
						objItem.setVItemType(objJson.getString(Constant.VCITEM_VTYPE));
						objItem.setVItemCatId(objJson.getString(Constant.VCITEM_VCID));
						objItem.setVItemCatName(objJson.getString(Constant.VCITEM_VCATNAME));
						objItem.setVItemThumb(objJson.getString(Constant.VCITEM_VTHUMB));
						objItem.setVItemTag(objJson.getString(Constant.VCITEM_VTAG));
						objItem.setVItemUrl(objJson.getString(Constant.VCITEM_VURL));
						objItem.setVItemSize(objJson.getString(Constant.VCITEM_VSIZE));
						objItem.setVItemUser(objJson.getString(Constant.VCITEM_VUSER));
						objItem.setVItemRate(objJson.getString(Constant.VCITEM_VRATE));
						objItem.setVItemPlayIds(objJson.getString(Constant.VCITEM_VVIDEOPLAYID));
						objItem.setVItemName(objJson.getString(Constant.VCITEM_VVIDEONAME));

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
		vid=objAllBean.getVItemId();
		vtype=objAllBean.getVItemType();
		vcid=objAllBean.getVItemCatId();
		vcname=objAllBean.getVItemCatName();
		vthumb=objAllBean.getVItemThumb();
		vtag=objAllBean.getVItemTag();
		vurl=objAllBean.getVItemUrl();
		vsize=objAllBean.getVItemSize();
		vuser=objAllBean.getVItemUser();
		vrate=objAllBean.getVItemRate();
		vplayid=objAllBean.getVItemPlayIds();
		vname=objAllBean.getVItemName();


		txt_rcatename.setText(vcname);
		txt_ruploadname.setText(vuser);
		txt_rtags.setText(vtag);
		txt_rsize.setText(vsize);
		txt_star.setText(vrate);
		txt_videoname.setText(vname);



		List<VideoPojo> pojolist=db.getFavRow(vid);
		if(pojolist.size()==0)
		{

			img_rfav.setImageResource(R.drawable.fav_hoverbtnselect);
		}
		else
		{	
			if(pojolist.get(0).getFVItemId().equals(vid));
			{
				img_rfav.setImageResource(R.drawable.favlike_hoverbtnselect);
			}

		}


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

		if(objAllBean.getVItemPlayIds().equals("000q1w2"))
		{
			imageLoader.DisplayImage(Constant.SERVER_IMAGE_UPFOLDER_THUMB+objAllBean.getVItemThumb().toString(), img_video);
		}
		else
		{
			imageLoader.DisplayImage(Constant.YOUTUBE_IMAGE_FRONT+objAllBean.getVItemPlayIds().toString()+Constant.YOUTUBE_IMAGE_BACK, img_video);
		}

		img_playvideo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				if(vplayid.equals("000q1w2"))
				{
					Intent lVideoIntent = new Intent(null, Uri.parse("file://" + Constant.VIDEO_FOLDER_PATH+vurl), VideoSingle.this, OpenYouTubePlayerActivity.class);
					startActivity(lVideoIntent);
				}
				else if(vtype.equals("youtube"))
				{
					Intent i = new Intent(VideoSingle.this,YoutubePlay.class);
					i.putExtra("id", vplayid);
					startActivity(i);
				}
//				{
//					Intent lVideoIntent = new Intent(null, Uri.parse("ytv://" + vplayid), VideoSingle.this, OpenYouTubePlayerActivity.class);
//					Log.e("video", ""+vplayid);
//					startActivity(lVideoIntent);
//				}


			}
		});

		img_rfav.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				List<VideoPojo> pojolist=db.getFavRow(vid);
				if(pojolist.size()==0)
				{

					Toast.makeText(getApplicationContext(), "Add to Favorite", Toast.LENGTH_SHORT).show();
					db.AddtoFavoritevideo(new VideoPojo(vid,vtype,vcid,vcname,vthumb,vtag,vurl,vsize,vuser,vrate,vplayid,vname));
					img_rfav.setImageResource(R.drawable.favlike_hoverbtnselect);
				}
				else
				{	
					if(pojolist.get(0).getFVItemId().equals(vid))
					{

						db.RemoveFav(new VideoPojo(vid));
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
						Constant.DEVICE_ID=Secure.getString(VideoSingle.this.getContentResolver(),
								Secure.ANDROID_ID);
						Log.e("Device Id", Constant.DEVICE_ID);

						if(txtrate.getText().toString().equalsIgnoreCase(""))
						{
							showToast("Plase Select Atleast one Star");
						}
						else
						{
							dialog.dismiss();

							if (JsonUtils.isNetworkAvailable(VideoSingle.this)) {
								new MyTaskRating().execute(Constant.RATE_VIDEO_URL+Constant.DEVICE_ID+"&rate="+txtrate.getText().toString()+"&video_id="+vid);
							} else {
								showToast("No Network Connection!!!");
								alert.showAlertDialog(VideoSingle.this, "Internet Connection Error",
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



	private	class MyTaskRating extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(VideoSingle.this);
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

				if (JsonUtils.isNetworkAvailable(VideoSingle.this)) {
					new MyTask().execute(Constant.VIDEO_SINGLE_URL+Constant.VIDEO_ITEMID);
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

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}


}
