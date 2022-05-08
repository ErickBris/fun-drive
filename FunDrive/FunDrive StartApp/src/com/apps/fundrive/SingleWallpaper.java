package com.apps.fundrive;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.example.favorite.DatabaseHandler;
import com.example.favorite.Pojo;
import com.example.item.ItemCategory;
import com.example.util.AlertDialogManager;
import com.example.util.Constant;
import com.example.util.JsonUtils;
import com.google.analytics.tracking.android.EasyTracker;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.startapp.android.publish.StartAppAd;

public class SingleWallpaper extends SherlockActivity {

	public DatabaseHandler db;
	ImageView vp_imageview,img_fav,img_download,img_share;
	ImageView imageView;
	ProgressBar spinner;
	TextView txt_wcount,txt_uploadname,txt_size,txt_catename,txt_tags,txt_star;
	List<ItemCategory> arrayOfCategoryImage;
	ItemCategory objAllBean;
	String  Image_catName,Image_Url,Image_DCount,Image_Id,Image_User,Image_Tag,Image_Size,rate_msg,Image_Star;
	DisplayImageOptions options;
	AlertDialogManager alert = new AlertDialogManager();
	final Context context = this;
 	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		StartAppAd.init(this, getString(R.string.startapp_dev_id), getString(R.string.startapp_app_id,false));
		setContentView(R.layout.singlewallpaper);
		StartAppAd.showSlider(this);
 		
		arrayOfCategoryImage=new ArrayList<ItemCategory>();
		img_download=(ImageView)findViewById(R.id.image_downl);
		img_fav=(ImageView)findViewById(R.id.image_favadd);
		img_share=(ImageView)findViewById(R.id.image_share);
		txt_uploadname=(TextView)findViewById(R.id.text_uploadname);
		txt_catename=(TextView)findViewById(R.id.text_category);
		txt_tags=(TextView)findViewById(R.id.text_tagname);
		txt_size=(TextView)findViewById(R.id.text_size);
		txt_star=(TextView)findViewById(R.id.text_rate_cnt);
		txt_wcount=(TextView)findViewById(R.id.text_wallcountd);
		imageView=(ImageView)findViewById(R.id.image);
		spinner = (ProgressBar)findViewById(R.id.loading);
		db = new DatabaseHandler(this);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		setTitle("Wallpaper");
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


		if (JsonUtils.isNetworkAvailable(SingleWallpaper.this)) {
			new MyTask().execute(Constant.SINGLE_WALLPAPER_URL+Constant.CATEGORY_ITEMWALLID);
		} 

		else {
			showToast("No Network Connection!!!");
			alert.showAlertDialog(getApplicationContext(), "Internet Connection Error",
					"Please connect to working Internet connection", false);

		}




		//Share 
		img_share.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				(new ShareTask(SingleWallpaper.this)).execute(Constant.SERVER_IMAGE_UPFOLDER_CATEGORY+Image_Url.replace(" ", "%20"));

			}
		});
		
		//Image Download
		
		img_download.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				(new SaveTask(SingleWallpaper.this)).execute(Constant.SERVER_IMAGE_UPFOLDER_CATEGORY+Image_Url.replace(" ", "%20"));

			}
		});
		
		//Image Rate
		
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
						  Constant.DEVICE_ID=Secure.getString(SingleWallpaper.this.getContentResolver(),
					                Secure.ANDROID_ID);
						Log.e("Device Id", Constant.DEVICE_ID);

						if(txtrate.getText().toString().equalsIgnoreCase(""))
						{
							showToast("Plase Select Atleast one Star");
						}
						else
						{
							dialog.dismiss();

							if (JsonUtils.isNetworkAvailable(SingleWallpaper.this)) {
								new MyTaskRating().execute(Constant.RATE_WALLPAPER_URL+Constant.DEVICE_ID+"&rate="+txtrate.getText().toString()+"&wallpaper_id="+Image_Id);
							} else {
								showToast("No Network Connection!!!");
								alert.showAlertDialog(SingleWallpaper.this, "Internet Connection Error",
										"Please connect to working Internet connection", false);
							}
						}

					}
				});
				dialog.show();
			}
		});
		
		
		img_fav.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				List<Pojo> pojolist=db.getFavRow(Image_Url);
				if(pojolist.size()==0)
				{
					AddtoFav();//if size is zero i.e means that record not in database show add to favorite 
				}
				else
				{	
					if(pojolist.get(0).getImageurl().equals(Image_Url))
					{
						RemoveFav();
					}

				}
			}
		});

	}
	
	private	class MyTaskRating extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(SingleWallpaper.this);
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
				if (JsonUtils.isNetworkAvailable(SingleWallpaper.this)) {
					new MyTask().execute(Constant.SINGLE_WALLPAPER_URL+Constant.CATEGORY_ITEMWALLID);
				} 

				else {
					showToast("No Network Connection!!!");
					alert.showAlertDialog(getApplicationContext(), "Internet Connection Error",
							"Please connect to working Internet connection", false);

				}

			}

		}
	}

	private	class MyTask extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(SingleWallpaper.this);
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
				SingleWallpaper.this.finish();
			} else {

				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(Constant.CATEGORY_ITEM_ARRAY);
					JSONObject objJson = null;
					for (int i = 0; i < jsonArray.length(); i++) {
						objJson = jsonArray.getJSONObject(i);

						ItemCategory objItem = new ItemCategory();
						objItem.setItemCategoryName(objJson.getString(Constant.CATEGORY_ITEM_CATNAME));
						objItem.setItemImageurl(objJson.getString(Constant.CATEGORY_ITEM_IMAGEURL));
						objItem.setItemCatId(objJson.getString(Constant.CATEGORY_ITEM_CATID));
						objItem.setItemDCount(objJson.getString(Constant.CATEGORY_ITEM_WDCOUNT));
						objItem.setItemWallId(objJson.getString(Constant.CATEGORY_ITEM_ID));
						objItem.setItemWallUser(objJson.getString(Constant.CATEGORY_ITEM_USER));
						objItem.setItemWallTag(objJson.getString(Constant.CATEGORY_ITEM_TAG));
						objItem.setItemWallSize(objJson.getString(Constant.CATEGORY_ITEM_SIZE));
						objItem.setImageStar(objJson.getString(Constant.CATEGORY_ITEM_STAR));
						arrayOfCategoryImage.add(objItem);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				setAdapterToListview();
			}

		}
	}

	public void setAdapterToListview() {

		objAllBean=arrayOfCategoryImage.get(0);

		Image_DCount=objAllBean.getItemDCount();
		Image_User=objAllBean.getItemWallUser();
		Image_catName=objAllBean.getItemCategoryName();
		Image_Tag=objAllBean.getItemWallTag();
		Image_Size=objAllBean.getItemWallSize();
		Image_Star=objAllBean.getImageStar();
		Image_Id=objAllBean.getItemWallId();
		Image_Url=objAllBean.getItemImageurl();


		txt_wcount.setText(Image_DCount);
		txt_uploadname.setText(Image_User);
		txt_catename.setText(Image_catName);
		txt_tags.setText(Image_Tag);
		txt_size.setText(Image_Size);
		txt_star.setText(Image_Star);

		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
		ImageLoader.getInstance().displayImage(Constant.SERVER_IMAGE_UPFOLDER_CATEGORY+Image_Url.replace(" ", "%20"), imageView, options, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				spinner.setVisibility(View.VISIBLE);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				String message = null;
				switch (failReason.getType()) {
				case IO_ERROR:
					message = "Input/Output error";
					break;
				case DECODING_ERROR:
					message = "Image can't be decoded";
					break;
				case NETWORK_DENIED:
					message = "Downloads are denied";
					break;
				case OUT_OF_MEMORY:
					message = "Out Of Memory error";
					break;
				case UNKNOWN:
					message = "Unknown error";
					break;
				}
				Toast.makeText(SingleWallpaper.this, message, Toast.LENGTH_SHORT).show();

				spinner.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				spinner.setVisibility(View.GONE);
			}
		});
		
		List<Pojo> pojolist=db.getFavRow(Image_Url);
		if(pojolist.size()==0)
		{
			img_fav.setImageResource(R.drawable.fav_hoverbtnselect);
		}
		else
		{	
			if(pojolist.get(0).getImageurl().equals(Image_Url))
			{
				img_fav.setImageResource(R.drawable.favlike_hoverbtnselect);
			}

		}


	}

	public void showToast(String msg) {
		Toast.makeText(SingleWallpaper.this, msg, Toast.LENGTH_LONG).show();
	}

	public class ShareTask extends AsyncTask<String , String , String>
	{
		private Context context;
		private ProgressDialog pDialog;
		String image_url;
		URL myFileUrl;
		String myFileUrl1;
		Bitmap bmImg = null;
		File file ;

		public ShareTask(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

			super.onPreExecute();

			pDialog = new ProgressDialog(context);
			pDialog.setMessage("Please Wait ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub

			try {  

				myFileUrl = new URL(args[0]);
				//myFileUrl1 = args[0];

				HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();   
				conn.setDoInput(true);   
				conn.connect();     
				InputStream is = conn.getInputStream();
				bmImg = BitmapFactory.decodeStream(is); 
			}
			catch (IOException e)
			{       
				e.printStackTrace();  
			}
			try {       

				String path = myFileUrl.getPath();
				String idStr = path.substring(path.lastIndexOf('/') + 1);
				File filepath = Environment.getExternalStorageDirectory();
				File dir = new File (filepath.getAbsolutePath() + "/HD Wallpaper/");
				dir.mkdirs();
				String fileName = idStr;
				file = new File(dir, fileName);
				FileOutputStream fos = new FileOutputStream(file);
				bmImg.compress(CompressFormat.JPEG, 75, fos);   
				fos.flush();    
				fos.close();    

			}
			catch (Exception e)
			{
				e.printStackTrace();  
			}
			return null;   
		}


		@Override
		protected void onPostExecute(String args) {
			// TODO Auto-generated method stub

			Intent share = new Intent(Intent.ACTION_SEND);
			share.setType("image/jpeg");
			share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file.getAbsolutePath()));
			startActivity(Intent.createChooser(share, "Share Image"));
			pDialog.dismiss();
		}
	}

	//Save Wallpaper

	public class SaveTask extends AsyncTask<String , String , String>
	{
		private Context context;
		private ProgressDialog pDialog;
		String image_url;
		URL myFileUrl;
		String myFileUrl1;
		Bitmap bmImg = null;
		File file ;

		public SaveTask(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

			super.onPreExecute();

			pDialog = new ProgressDialog(context);
			pDialog.setMessage("Downloading Image ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			String as[] = null;
			try {  

				myFileUrl = new URL(args[0]);
				//myFileUrl1 = args[0];

				HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();   
				conn.setDoInput(true);   
				conn.connect();     
				InputStream is = conn.getInputStream();
				bmImg = BitmapFactory.decodeStream(is); 
			}
			catch (IOException e)
			{       
				e.printStackTrace();  
			}
			try {       

				String path = myFileUrl.getPath();
				String idStr = path.substring(path.lastIndexOf('/') + 1);
				File filepath = Environment.getExternalStorageDirectory();
				File dir = new File (filepath.getAbsolutePath() + Constant.DOWNLOAD_SDCARD_FOLDER_PATH_WALLPAPER);
				dir.mkdirs();
				String fileName = "Image_"+Image_Id+"_"+idStr;
				file = new File(dir, fileName);
				FileOutputStream fos = new FileOutputStream(file);
				bmImg.compress(CompressFormat.JPEG, 75, fos);   
				fos.flush();    
				fos.close();   
				as = new String[1];
				as[0] = file.toString();

				MediaScannerConnection.scanFile(SingleWallpaper.this, as, null, new android.media.MediaScannerConnection.OnScanCompletedListener() {
					public void onScanCompleted(String s1, Uri uri)
					{
					}

				});

			}
			catch (Exception e)
			{
				e.printStackTrace();  
			}
			return null;   
		}


		@Override
		protected void onPostExecute(String args) {
			// TODO Auto-generated method stub
			Toast.makeText(SingleWallpaper.this, "Image Saved Succesfully "+"Path:"+Constant.DOWNLOAD_SDCARD_FOLDER_PATH_WALLPAPER, Toast.LENGTH_SHORT).show();
			pDialog.dismiss();

			if (JsonUtils.isNetworkAvailable(SingleWallpaper.this)) {
				new MyTaskWDown().execute(Constant.DOWNLOADWALL_URL+Image_Id);
			} else {
				showToast("No Network Connection!!!");
				alert.showAlertDialog(SingleWallpaper.this, "Internet Connection Error",
						"Please connect to working Internet connection", false);
			}
		}
	}

	private	class MyTaskWDown extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(SingleWallpaper.this);
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
						txt_wcount.setText(success);

					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}


	}
	
	public void AddtoFav()
	{
		db.AddtoFavorite(new Pojo(Image_catName, Image_Url,Image_DCount,Image_Id,Image_User,Image_Tag,Image_Size));
		Toast.makeText(getApplicationContext(), "Added to Favorite", Toast.LENGTH_SHORT).show();
		img_fav.setImageResource(R.drawable.favlike_hoverbtnselect);
	}

	//remove from favorite
	public void RemoveFav()
	{
		db.RemoveFav(new Pojo(Image_Url));
		Toast.makeText(getApplicationContext(), "Removed from Favorite", Toast.LENGTH_SHORT).show();
		img_fav.setImageResource(R.drawable.fav_hoverbtnselect);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.photo_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{       
		switch (menuItem.getItemId()) 
		{
		case android.R.id.home: 
			onBackPressed();
			return true;

		case R.id.menu_overflow:
			//just override click 
			return true;

		case R.id.menu_setaswallaper:
			Intent intwall=new Intent(getApplicationContext(),SetAsWallpaperActivity.class);
			intwall.putExtra("WALLPAPER_IMAGE_URL", Image_Url);
			startActivity(intwall);

			return true;

		case R.id.menu_zoom:
			Intent intzoom=new Intent(getApplicationContext(),PinchZoom.class);
			intzoom.putExtra("ZOOM_IMAGE_URL", Image_Url);
			startActivity(intzoom);

			return true;

		default:
			return super.onOptionsItemSelected(menuItem);
		}

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
