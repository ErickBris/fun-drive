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
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.apps.fundrive.R;
import com.example.adapter.CategoryItemGridAdapter;
import com.example.item.ItemCategory;
import com.example.util.AlertDialogManager;
import com.example.util.Constant;
import com.example.util.GridViewWithHeaderAndFooter;
import com.example.util.JsonUtils;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.startapp.android.publish.StartAppAd;

public class WallCategoryItemActivity extends SherlockActivity {

	GridViewWithHeaderAndFooter grid_cat_item;
	List<ItemCategory> arrayOfCategoryImage;
	CategoryItemGridAdapter objAdapter;
	AlertDialogManager alert = new AlertDialogManager();
	ArrayList<String> allListImage,allListImageCatName,allListItemId,allListImageWId,allListImageDCount,
	allListwtag,allListwsize,allListuser,allListstar;
	String[] allArrayImage,allArrayImageCatName,allArrayItemId,allArrayImageWId,allArrayImageDCount,
	allArraywtag,allArraywsize,allArraywuser,allArraystar;
	private int columnWidth;
	JsonUtils util;
	private Button btnLoadMore;
	int page=1;
	int TOTAL_LIST_ITEMS;
	public int NUM_ITEMS_PAGE;
	private int noOfBtns;
	ItemCategory objAllbean;
	
	private AdView mAdView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		StartAppAd.init(this, getString(R.string.startapp_dev_id), getString(R.string.startapp_app_id,false));
		setContentView(R.layout.wallcategrid_item);
		StartAppAd.showSlider(this);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		setTitle(Constant.CATEGORY_TITLE);
		
		mAdView = (AdView)findViewById(R.id.adView);
		mAdView.loadAd(new AdRequest.Builder().build());
		
		btnLoadMore = new Button(WallCategoryItemActivity.this);
		btnLoadMore.setText("Load More");
		grid_cat_item=(GridViewWithHeaderAndFooter)findViewById(R.id.category_grid);
		arrayOfCategoryImage=new ArrayList<ItemCategory>();
		grid_cat_item.addFooterView(btnLoadMore);
		NUM_ITEMS_PAGE=Integer.parseInt(getString(R.string.numofitemimage));
		allListImage=new ArrayList<String>();
		allListImageCatName=new ArrayList<String>();
		allListItemId=new ArrayList<String>();
		allListImageDCount=new ArrayList<String>();
		allListImageWId=new ArrayList<String>();
		allListuser=new ArrayList<String>();
		allListwtag=new ArrayList<String>();
		allListwsize=new ArrayList<String>();
		allListstar=new ArrayList<String>();

		allArrayImage=new String[allListImage.size()];
		allArrayImageCatName=new String[allListImageCatName.size()];
		allArrayItemId=new String[allListItemId.size()];
		allArrayImageDCount=new String[allListImageDCount.size()];
		allArrayImageWId=new String[allListImageWId.size()];
		allArraywuser=new String[allListuser.size()];
		allArraywtag=new String[allListwtag.size()];
		allArraywsize=new String[allListwsize.size()];
		allArraystar=new String[allListstar.size()];

		util=new JsonUtils(getApplicationContext());
		InitilizeGridLayout();

		grid_cat_item.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub

				Intent intslider=new Intent(getApplicationContext(),SingleWallpaper.class);
				intslider.putExtra("POSITION_ID", position);
				intslider.putExtra("IMAGE_ARRAY", allArrayImage);
				intslider.putExtra("IMAGE_CATNAME", allArrayImageCatName);
				intslider.putExtra("ITEMID", allArrayItemId);
				intslider.putExtra("IMGDCOUNT", allArrayImageDCount);
				intslider.putExtra("IMGID", allArrayImageWId);
				intslider.putExtra("WUSER", allArraywuser);
				intslider.putExtra("WTAG", allArraywtag);
				intslider.putExtra("WSIZE", allArraywsize);
				intslider.putExtra("STAR", allArraystar);
				intslider.putExtra("INWHICH","Category");
				objAllbean=arrayOfCategoryImage.get(position);
				Constant.CATEGORY_ITEMWALLID=objAllbean.getItemWallId();
				startActivity(intslider);

			}
		});

		if (JsonUtils.isNetworkAvailable(WallCategoryItemActivity.this)) {
			new MyTask().execute(Constant.CATEGORY_ITEM_URL+Constant.CATEGORY_ID+"&page="+page);
		} 

		else {
			showToast("No Network Connection!!!");
			alert.showAlertDialog(getApplicationContext(), "Internet Connection Error",
					"Please connect to working Internet connection", false);

		}

		btnLoadMore.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(page >= noOfBtns)
				{
					showToast("NO MORE WallPaper");
					btnLoadMore.setVisibility(Button.GONE);
				}
				else
				{	
					page=page+1;
					allListImage.clear();
					allListImageCatName.clear();
					allListImageDCount.clear();
					allListImageWId.clear();
					allListItemId.clear();
					allListstar.clear();
					allListuser.clear();
					allListwsize.clear();
					allListwtag.clear();
					new MyTask().execute(Constant.CATEGORY_ITEM_URL+Constant.CATEGORY_ID+"&page="+page);	
				}
			}
		});

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

			pDialog = new ProgressDialog(WallCategoryItemActivity.this);
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
				WallCategoryItemActivity.this.finish();
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
						TOTAL_LIST_ITEMS=Integer.parseInt(objJson.getString(Constant.CATEGORY_NUM));
						arrayOfCategoryImage.add(objItem);


					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

				for(int j=0;j<arrayOfCategoryImage.size();j++)
				{

					ItemCategory objCategoryBean=arrayOfCategoryImage.get(j);

					allListImage.add(objCategoryBean.getItemImageurl());
					allArrayImage=allListImage.toArray(allArrayImage);

					allListImageCatName.add(objCategoryBean.getItemCategoryName());
					allArrayImageCatName=allListImageCatName.toArray(allArrayImageCatName);

					allListItemId.add(objCategoryBean.getItemCatId());
					allArrayItemId=allListItemId.toArray(allArrayItemId);

					allListImageDCount.add(objCategoryBean.getItemDCount());
					allArrayImageDCount=allListImageDCount.toArray(allArrayImageDCount);

					allListImageWId.add(objCategoryBean.getItemWallId());
					allArrayImageWId=allListImageWId.toArray(allArrayImageWId);

					allListuser.add(objCategoryBean.getItemWallUser());
					allArraywuser=allListuser.toArray(allArraywuser);

					allListwtag.add(objCategoryBean.getItemWallTag());
					allArraywtag=allListwtag.toArray(allArraywtag);

					allListwsize.add(objCategoryBean.getItemWallSize());
					allArraywsize=allListwsize.toArray(allArraywsize);

					allListstar.add(objCategoryBean.getImageStar());
					allArraystar=allListstar.toArray(allArraystar);

				}
				int val = TOTAL_LIST_ITEMS%NUM_ITEMS_PAGE;
				val = val==0?0:1;
				noOfBtns=TOTAL_LIST_ITEMS/NUM_ITEMS_PAGE+val;
				setAdapterToListview();
			}

		}
	}

	public void setAdapterToListview() {
		objAdapter = new CategoryItemGridAdapter(WallCategoryItemActivity.this, R.layout.walllatestgrid_item,
				arrayOfCategoryImage,columnWidth);
		grid_cat_item.setAdapter(objAdapter);
		if(page==1)
		{
			grid_cat_item.setSelection(0);
		}
		else
		{
			int v = grid_cat_item.getCount(); 
			grid_cat_item.setSelection(v-NUM_ITEMS_PAGE);			  

		}

		if(page >= noOfBtns)
		{
			btnLoadMore.setVisibility(View.GONE);
		}



	}

	public void showToast(String msg) {
		Toast.makeText(WallCategoryItemActivity.this, msg, Toast.LENGTH_LONG).show();
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
