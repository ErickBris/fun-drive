package com.apps.fundrive;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.example.adapter.VideoCateItemAdapter;
import com.example.item.ItemVideoCategoryItem;
import com.example.util.AlertDialogManager;
import com.example.util.Constant;
import com.example.util.JsonUtils;
import com.google.analytics.tracking.android.EasyTracker;

public class Search_Video extends SherlockActivity{

	GridView lsv_latest;
	List<ItemVideoCategoryItem> arrayOfRingcatItem;
	VideoCateItemAdapter objAdapterringitemitem;
	AlertDialogManager alert = new AlertDialogManager();
	private ItemVideoCategoryItem objAllBean;
	JsonUtils util;
 	private int mPhotoSize, mPhotoSpacing;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videocatitem_activity);
		
		mPhotoSize = getResources().getDimensionPixelSize(R.dimen.photo_size);
		mPhotoSpacing = getResources().getDimensionPixelSize(R.dimen.photo_spacing);
 	 
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

		lsv_latest=(GridView)findViewById(R.id.vlatest_grid);
		arrayOfRingcatItem=new ArrayList<ItemVideoCategoryItem>();

		if (JsonUtils.isNetworkAvailable(Search_Video.this)) {
			new MyTask().execute(Constant.SEARCH_VIDEO_URL+Constant.SEARCH.replace(" ", "%20"));

		} else {
			showToast("No Network Connection!!!");
			alert.showAlertDialog(Search_Video.this, "Internet Connection Error",
					"Please connect to working Internet connection", false);
		}


		lsv_latest.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub

				objAllBean=arrayOfRingcatItem.get(position);
				String pos=objAllBean.getVItemId();
				Intent intplay=new Intent(getApplicationContext(),VideoSingle.class);
				intplay.putExtra("POSITION", pos);
				Constant.VIDEO_ITEMID=objAllBean.getVItemId();
				startActivity(intplay);

			}
		});

	}

	private	class MyTask extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(Search_Video.this);
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
					if(jsonArray.length()==0)
					{
						showToast("No Result Available");
					}
					else
					{
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
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

				setAdapterToListview();
			}

		}
	}



	public void setAdapterToListview() {
		objAdapterringitemitem = new VideoCateItemAdapter(Search_Video.this, R.layout.video_latest_lsv_item,
				arrayOfRingcatItem);
		lsv_latest.setAdapter(objAdapterringitemitem);
		lsv_latest.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				if (objAdapterringitemitem.getNumColumns() == 0) {
					final int numColumns = (int) Math.floor(lsv_latest.getWidth() / (mPhotoSize + mPhotoSpacing));
					if (numColumns > 0) {
						final int columnWidth = (lsv_latest.getWidth() / numColumns) - mPhotoSpacing;
						objAdapterringitemitem.setNumColumns(numColumns);
						objAdapterringitemitem.setItemHeight(columnWidth);

					}
				}
			}
		});
	}

	public void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
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
