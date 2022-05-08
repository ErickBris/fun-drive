package com.apps.fundrive;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;
import com.apps.fundrive.R;
import com.example.adapter.VideoCateItemAdapter;
import com.example.item.ItemVideoCategoryItem;
import com.example.util.AlertDialogManager;
import com.example.util.Constant;
import com.example.util.JsonUtils;

public class VideoMostPopularFragment extends Fragment {

	GridView lsv_latest;
	List<ItemVideoCategoryItem> arrayOfLatestMusic;
	VideoCateItemAdapter objAdapterring;
	AlertDialogManager alert = new AlertDialogManager();
 	private ItemVideoCategoryItem objAllBean;
	JsonUtils util;
	int textlength = 0;
	private int mPhotoSize, mPhotoSpacing;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View rootView = inflater.inflate(R.layout.videocatitem_activity, container, false);

		setHasOptionsMenu(true);
		
		mPhotoSize = getResources().getDimensionPixelSize(R.dimen.photo_size);
		mPhotoSpacing = getResources().getDimensionPixelSize(R.dimen.photo_spacing);

		lsv_latest=(GridView)rootView.findViewById(R.id.vlatest_grid);
		arrayOfLatestMusic=new ArrayList<ItemVideoCategoryItem>();


		if (JsonUtils.isNetworkAvailable(getActivity())) {
			new MyTask().execute(Constant.MOST_POPULAR_VIDEO_URL);
		} else {
			showToast("No Network Connection!!!");
			alert.showAlertDialog(getActivity(), "Internet Connection Error",
					"Please connect to working Internet connection", false);
		}


		lsv_latest.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub

				objAllBean=arrayOfLatestMusic.get(position);
				String pos=objAllBean.getVItemId();
			  
				Intent intplay=new Intent(getActivity(),VideoSingle.class);
				intplay.putExtra("POSITION", pos);
 				Constant.VIDEO_ITEMID=objAllBean.getVItemId();
				startActivity(intplay);

			}
		});

		return rootView;
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

						arrayOfLatestMusic.add(objItem);


					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			 
				setAdapterToListview();
			}

		}
	}
 
	public void setAdapterToListview() {
		objAdapterring = new VideoCateItemAdapter(getActivity(), R.layout.video_latest_lsv_item,
				arrayOfLatestMusic);
		lsv_latest.setAdapter(objAdapterring);
		lsv_latest.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				if (objAdapterring.getNumColumns() == 0) {
					final int numColumns = (int) Math.floor(lsv_latest.getWidth() / (mPhotoSize + mPhotoSpacing));
					if (numColumns > 0) {
						final int columnWidth = (lsv_latest.getWidth() / numColumns) - mPhotoSpacing;
						objAdapterring.setNumColumns(numColumns);
						objAdapterring.setItemHeight(columnWidth);

					}
				}
			}
		});
	}

	public void showToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
	}

	 
}

