package com.apps.fundrive;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.example.adapter.CategoryItemGridAdapter;
import com.example.item.ItemCategory;
import com.example.util.AlertDialogManager;
import com.example.util.Constant;
import com.example.util.GridViewWithHeaderAndFooter;
import com.example.util.JsonUtils;
import com.google.analytics.tracking.android.EasyTracker;

public class Search_Wallpaper extends SherlockActivity {

	GridViewWithHeaderAndFooter grid_cat_item;
	List<ItemCategory> arrayOfCategoryImage;
	CategoryItemGridAdapter objAdapter;
	AlertDialogManager alert = new AlertDialogManager();
	private int columnWidth;
	JsonUtils util;
	ItemCategory objAllbean;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wallcategrid_item);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
 
		grid_cat_item=(GridViewWithHeaderAndFooter)findViewById(R.id.category_grid);
		arrayOfCategoryImage=new ArrayList<ItemCategory>();

		util=new JsonUtils(getApplicationContext());
		InitilizeGridLayout();

		grid_cat_item.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub

				Intent intslider=new Intent(getApplicationContext(),SingleWallpaper.class);
				objAllbean=arrayOfCategoryImage.get(position);
				Constant.CATEGORY_ITEMWALLID=objAllbean.getItemWallId();
				startActivity(intslider);

			}
		});

		if (JsonUtils.isNetworkAvailable(Search_Wallpaper.this)) {
			new MyTask().execute(Constant.SEARCH_WALLPAPER_URL+Constant.SEARCH.replace(" ", "%20"));
		} 

		else {
			showToast("No Network Connection!!!");
			alert.showAlertDialog(getApplicationContext(), "Internet Connection Error",
					"Please connect to working Internet connection", false);

		}
	}
	private void InitilizeGridLayout() {
		Resources r = getResources();
		float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				Constant.GRID_PADDING, r.getDisplayMetrics());

		columnWidth = (int) ((util.getScreenWidth() - ((Constant.NUM_OF_COLUMNS + 1) * padding)) / Constant.NUM_OF_COLUMNS);

		grid_cat_item.setNumColumns(Constant.NUM_OF_COLUMNS);
		grid_cat_item.setColumnWidth(columnWidth);
		grid_cat_item.setStretchMode(GridView.NO_STRETCH);
		grid_cat_item.setPadding((int) padding, (int) padding, (int) padding,
				(int) padding);
		grid_cat_item.setHorizontalSpacing((int) padding);
		grid_cat_item.setVerticalSpacing((int) padding);
	}

	private	class MyTask extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(Search_Wallpaper.this);
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
				Search_Wallpaper.this.finish();
			} else {

				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(Constant.CATEGORY_ITEM_ARRAY);
					JSONObject objJson = null;
					
					if(jsonArray.length()==0)
					{
						showToast("No Result Available");
					}
					else
					{
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
					}
					

				} catch (JSONException e) {
					e.printStackTrace();
				}

				setAdapterToListview();
			}

		}
	}

	public void setAdapterToListview() {
		objAdapter = new CategoryItemGridAdapter(Search_Wallpaper.this, R.layout.walllatestgrid_item,
				arrayOfCategoryImage,columnWidth);
		grid_cat_item.setAdapter(objAdapter);
	}

	public void showToast(String msg) {
		Toast.makeText(Search_Wallpaper.this, msg, Toast.LENGTH_LONG).show();
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
