package com.apps.fundrive;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.apps.fundrive.R;
import com.example.imageloader.ImageLoader;
import com.example.item.ItemAbout;
import com.example.util.AlertDialogManager;
import com.example.util.Constant;
import com.example.util.JsonUtils;
import com.google.analytics.tracking.android.EasyTracker;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends SherlockActivity {
	
	ImageView imglogo;
	TextView txtappname,txtcomemail,txtcomsite;
	WebView webcomdes;
	public ImageLoader imageLoader; 
	JsonUtils util;
	List<ItemAbout> listabout;
	AlertDialogManager alert = new AlertDialogManager();
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		setTitle("About Us");
		 
		imglogo=(ImageView)findViewById(R.id.image_comlogo);
		txtappname=(TextView)findViewById(R.id.text_appname);
		txtcomemail=(TextView)findViewById(R.id.text_comemail);
		txtcomsite=(TextView)findViewById(R.id.text_comwebsite);
		webcomdes=(WebView)findViewById(R.id.webView_comdes);
		webcomdes.getSettings().setDefaultTextEncodingName("UTF-8");
		listabout=new ArrayList<ItemAbout>();


		imageLoader=new ImageLoader(getApplicationContext());

		util=new JsonUtils(getApplicationContext());


		if (JsonUtils.isNetworkAvailable(AboutActivity.this)) {
			new MyTask().execute(Constant.COMPANY_DETAILS_URL);
		} else {
			showToast("No Network Connection!!!");
			alert.showAlertDialog(AboutActivity.this, "Internet Connection Error",
					"Please connect to working Internet connection", false);
		}

	}
	private	class MyTask extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(AboutActivity.this);
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
				alert.showAlertDialog(AboutActivity.this, "Server Connection Error",
						"May Server Under Maintaines Or Low Network", false);

			} else {

				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(Constant.CATEGORY_ARRAY_NAME);
					JSONObject objJson = null;
					for (int i = 0; i < jsonArray.length(); i++) {
						objJson = jsonArray.getJSONObject(i);

						ItemAbout objItem = new ItemAbout();


						objItem.setAppName(objJson.getString(Constant.COMPANY_DETAILS_APPNAME));
						objItem.setComEmail(objJson.getString(Constant.COMPANY_DETAILS_COMMAIL));
						objItem.setComWebsite(objJson.getString(Constant.COMPANY_DETAILS_COMSITE));
						objItem.setComDes(objJson.getString(Constant.COMPANY_DETAILS_COMDES));
						objItem.setComLogo(objJson.getString(Constant.COMPANY_DETAILS_COMLOGO));

						listabout.add(objItem);

					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
 
				setAdapterToListview();
			}

		}
	}
 
	public void setAdapterToListview() {

		ItemAbout about=listabout.get(0);
		txtappname.setText(about.getAppName());
		txtcomemail.setText(about.getComEmail());
		txtcomsite.setText(about.getComWebsite());
	 

		String mimeType = "text/html";
		String encoding = "utf-8";
		String htmlText = about.getComDes();
		

		String text = "<html><head>"
				+ "<style type=\"text/css\">body{color: #4d4d4d;}"
				+ "</style></head>"
				+ "<body>"  
				+  htmlText
				+ "</body></html>";

		webcomdes.loadData(text, mimeType, encoding);
		webcomdes.setBackgroundColor(Color.parseColor(getString(R.color.background_clr)));

		imageLoader.DisplayImage(Constant.SERVER_IMAGE_UPFOLDER_CATEGORY+about.getComLogo().toString(), imglogo);
	}

	public void showToast(String msg) {
		Toast.makeText(AboutActivity.this, msg, Toast.LENGTH_LONG).show();
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
