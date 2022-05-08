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
import com.example.adapter.VideoLatestAdapter;
import com.example.item.ItemVideoLatest;
import com.example.util.AlertDialogManager;
import com.example.util.Constant;
import com.example.util.JsonUtils;

public class VideoLatestFragment extends Fragment {

	GridView lsv_latest;
	List<ItemVideoLatest> arrayOfLatestMusic;
	VideoLatestAdapter objAdapterring;
	AlertDialogManager alert = new AlertDialogManager();
	ArrayList<String> allListMusicId,allListMusicCatId,allListMusicCatName,allListMusicUrl,allListMusicName;
	String[] allArrayMusicId,allArrayMusicCatId,allArrayMusicCatName,allArrayMusicurl,allArrayMusicName;
	private ItemVideoLatest objAllBean;
	JsonUtils util;
	int textlength = 0;
	private int mPhotoSize, mPhotoSpacing;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View rootView = inflater.inflate(R.layout.videolatest_fragment, container, false);

		setHasOptionsMenu(true);
		
		mPhotoSize = getResources().getDimensionPixelSize(R.dimen.photo_size);
		mPhotoSpacing = getResources().getDimensionPixelSize(R.dimen.photo_spacing);

		lsv_latest=(GridView)rootView.findViewById(R.id.vlatest_grid);
		arrayOfLatestMusic=new ArrayList<ItemVideoLatest>();


		if (JsonUtils.isNetworkAvailable(getActivity())) {
			new MyTask().execute(Constant.VIDEO_LATEST_URL);
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
 				String pos=objAllBean.getVId();
 
				Intent intplay=new Intent(getActivity(),VideoSingle.class);
				intplay.putExtra("POSITION", pos);
 				Constant.VIDEO_ITEMID=objAllBean.getVId();
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

						ItemVideoLatest objItem = new ItemVideoLatest();

						objItem.setVId(objJson.getString(Constant.VLATEST_VID));
						objItem.setVType(objJson.getString(Constant.VLATEST_VTYPE));
						objItem.setVCatId(objJson.getString(Constant.VLATEST_VCID));
						objItem.setVCatName(objJson.getString(Constant.VLATEST_VCATNAME));
						objItem.setVThumb(objJson.getString(Constant.VLATEST_VTHUMB));
						objItem.setVTag(objJson.getString(Constant.VLATEST_VTAG));
						objItem.setVUrl(objJson.getString(Constant.VLATEST_VURL));
						objItem.setVSize(objJson.getString(Constant.VLATEST_VSIZE));
						objItem.setVUser(objJson.getString(Constant.VLATEST_VUSER));
						objItem.setVRate(objJson.getString(Constant.VLATEST_VRATE));
						objItem.setVPlayIds(objJson.getString(Constant.VLATEST_VVIDEOPLAYID));
						objItem.setVName(objJson.getString(Constant.VLATEST_VVIDEONAME));
						 
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
		objAdapterring = new VideoLatestAdapter(getActivity(), R.layout.video_latest_lsv_item,
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

