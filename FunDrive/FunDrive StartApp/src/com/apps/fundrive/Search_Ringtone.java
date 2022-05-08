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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.example.adapter.RingCateItemAdapter;
import com.example.item.ItemRingCategoryItem;
import com.example.util.AlertDialogManager;
import com.example.util.Constant;
import com.example.util.JsonUtils;
import com.google.analytics.tracking.android.EasyTracker;

public class Search_Ringtone extends SherlockActivity{

	ListView lsv_latest;
	List<ItemRingCategoryItem> arrayOfRingcatItem;
	RingCateItemAdapter objAdapterringitemitem;
	AlertDialogManager alert = new AlertDialogManager();
	private ItemRingCategoryItem objAllBean;
	JsonUtils util;
 	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ringcatitem_activity);
	 
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

 		lsv_latest=(ListView)findViewById(R.id.latest_list);
		arrayOfRingcatItem=new ArrayList<ItemRingCategoryItem>();

		if (JsonUtils.isNetworkAvailable(Search_Ringtone.this)) {
			new MyTask().execute(Constant.SEARCH_RINGTONE_URL+Constant.SEARCH.replace(" ", "%20"));
		
		} else {
			showToast("No Network Connection!!!");
			alert.showAlertDialog(Search_Ringtone.this, "Internet Connection Error",
					"Please connect to working Internet connection", false);
		}


		lsv_latest.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				
				objAllBean=arrayOfRingcatItem.get(position);
				Intent intplay=new Intent(getApplicationContext(),SingleRingtone.class);
				Constant.RINGTONE_ITEMID=objAllBean.getRingItemId();
				startActivity(intplay);

			}
		});

	}

	private	class MyTask extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(Search_Ringtone.this);
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
							objItem.setRingImage(objJson.getString(Constant.LATESTRING_RINGIMAGE));
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
		objAdapterringitemitem = new RingCateItemAdapter(Search_Ringtone.this, R.layout.ring_latest_lsv_item,
				arrayOfRingcatItem);
		lsv_latest.setAdapter(objAdapterringitemitem);
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
