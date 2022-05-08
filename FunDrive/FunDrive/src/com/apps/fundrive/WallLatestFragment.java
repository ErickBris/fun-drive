package com.apps.fundrive;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;
import com.apps.fundrive.R;
import com.example.adapter.LatestGridAdapter;
import com.example.item.ItemLatest;
import com.example.util.AlertDialogManager;
import com.example.util.Constant;
import com.example.util.JsonUtils;

public class WallLatestFragment extends Fragment {

	GridView grid;
	List<ItemLatest> arrayOfLatestImage;
	LatestGridAdapter objAdapter;
	AlertDialogManager alert = new AlertDialogManager();
	ArrayList<String> allListImage,allListImageCatName,allListImageDCount,allListImageId,
	 			allListwtag,allListwsize,allListuser,allListstar;
	String[] allArrayImage,allArrayImageCatName,allArrayImageDCount,allArrayImageId,
				allArraywtag,allArraywsize,allArraywuser,allArraystar;

	private ItemLatest objAllBean;
	private int columnWidth;
	JsonUtils util;
 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.walllatest_fragment, container, false);
		grid=(GridView)rootView.findViewById(R.id.latest_grid);

 
		arrayOfLatestImage=new ArrayList<ItemLatest>();
		allListImage=new ArrayList<String>();
		allListImageCatName=new ArrayList<String>();
		allListImageDCount=new ArrayList<String>();
		allListImageId=new ArrayList<String>();
		allListuser=new ArrayList<String>();
		allListwtag=new ArrayList<String>();
		allListwsize=new ArrayList<String>();
		allListstar=new ArrayList<String>();

		allArrayImage=new String[allListImage.size()];
		allArrayImageCatName=new String[allListImageCatName.size()];
		allArrayImageDCount=new String[allListImageDCount.size()];
		allArrayImageId=new String[allListImageId.size()];
		allArraywuser=new String[allListuser.size()];
		allArraywtag=new String[allListwtag.size()];
		allArraywsize=new String[allListwsize.size()];
		allArraystar=new String[allListstar.size()];

		util=new JsonUtils(getActivity());
		InitilizeGridLayout();
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				//				objAllBean=arrayOfLatestImage.get(position);
				//				Constant.CATEGORY_ITEMWALLID=objAllBean.getImageWId();
				//				
				//				Log.e("eid", ""+Constant.CATEGORY_ITEMWALLID);

				Intent intslider=new Intent(getActivity(),SingleWallpaper.class);
				intslider.putExtra("POSITION_ID", position);
				intslider.putExtra("IMAGE_ARRAY", allArrayImage);
				intslider.putExtra("IMAGE_CATNAME", allArrayImageCatName);
				intslider.putExtra("IMGDCOUNT", allArrayImageDCount);
				intslider.putExtra("IMGID", allArrayImageId);
				intslider.putExtra("WUSER", allArraywuser);
				intslider.putExtra("WTAG", allArraywtag);
				intslider.putExtra("WSIZE", allArraywsize);
				intslider.putExtra("STAR", allArraystar);
				intslider.putExtra("INWHICH","Latest");
				objAllBean=arrayOfLatestImage.get(position);
				Constant.CATEGORY_ITEMWALLID=objAllBean.getImageWId();
			 	startActivity(intslider);

			}
		});
		if (JsonUtils.isNetworkAvailable(getActivity())) {
			new MyTask().execute(Constant.LATEST_URL);
		}   else {
			showToast("No Network Connection!!!");
			alert.showAlertDialog(getActivity(), "Internet Connection Error",
					"Please connect to working Internet connection", false);

		}
		return rootView;
	}

	private void InitilizeGridLayout() {
		Resources r = getResources();
		float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				Constant.GRID_PADDING, r.getDisplayMetrics());

		columnWidth = (int) ((util.getScreenWidth() - ((Constant.NUM_OF_COLUMNS + 1) * padding)) / Constant.NUM_OF_COLUMNS);

		grid.setNumColumns(Constant.NUM_OF_COLUMNS);
		grid.setColumnWidth(columnWidth);
		grid.setStretchMode(GridView.NO_STRETCH);
		grid.setPadding((int) padding, (int) padding, (int) padding,
				(int) padding);
		grid.setHorizontalSpacing((int) padding);
		grid.setVerticalSpacing((int) padding);
	}
	private	class MyTask extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(getActivity());
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
				alert.showAlertDialog(getActivity(), "Server Connection Error",
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
						arrayOfLatestImage.add(objItem);

					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
				for(int j=0;j<arrayOfLatestImage.size();j++)
				{

					objAllBean=arrayOfLatestImage.get(j);

					allListImage.add(objAllBean.getImageurl());
					allArrayImage=allListImage.toArray(allArrayImage);

					allListImageCatName.add(objAllBean.getCategoryName());
					allArrayImageCatName=allListImageCatName.toArray(allArrayImageCatName);

					allListImageDCount.add(objAllBean.getImageDCount());
					allArrayImageDCount=allListImageDCount.toArray(allArrayImageDCount);

					allListImageId.add(objAllBean.getImageWId());
					allArrayImageId=allListImageId.toArray(allArrayImageId);
					
					allListuser.add(objAllBean.getImageWUser());
					allArraywuser=allListuser.toArray(allArraywuser);
					
					allListwtag.add(objAllBean.getImageWTag());
					allArraywtag=allListwtag.toArray(allArraywtag);
					
					allListwsize.add(objAllBean.getImageWSize());
					allArraywsize=allListwsize.toArray(allArraywsize);
					
					allListstar.add(objAllBean.getImageStar());
					allArraystar=allListstar.toArray(allArraystar);

				}

				setAdapterToListview();
			}

		}
	}

	public void setAdapterToListview() {
		objAdapter = new LatestGridAdapter(getActivity(), R.layout.walllatestgrid_item,
				arrayOfLatestImage,columnWidth);
		grid.setAdapter(objAdapter);
	}

	public void showToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
	}
}
