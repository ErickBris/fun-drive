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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import com.apps.fundrive.R;
import com.example.adapter.RingLatestAdapter;
import com.example.item.ItemRingLatest;
import com.example.util.AlertDialogManager;
import com.example.util.Constant;
import com.example.util.JsonUtils;

public class RingMostPopularFragment extends Fragment {

	ListView lsv_latest;
	List<ItemRingLatest> arrayOfLatestMusic;
	RingLatestAdapter objAdapterring;
	AlertDialogManager alert = new AlertDialogManager();
	ArrayList<String> allListMusicId,allListMusicCatId,allListMusicCatName,allListMusicUrl,allListMusicName;
	String[] allArrayMusicId,allArrayMusicCatId,allArrayMusicCatName,allArrayMusicurl,allArrayMusicName;
	private ItemRingLatest objAllBean;
	JsonUtils util;
	int textlength = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View rootView = inflater.inflate(R.layout.ringlatest_fragement, container, false);

		setHasOptionsMenu(true);

		lsv_latest=(ListView)rootView.findViewById(R.id.latest_list);
		arrayOfLatestMusic=new ArrayList<ItemRingLatest>();


		if (JsonUtils.isNetworkAvailable(getActivity())) {
			new MyTask().execute(Constant.MOST_POPULAR_RING_URL);
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
				String pos=objAllBean.getRingId();
				String rid=objAllBean.getRingId();
				String rcatid=objAllBean.getRingCatId();
				String rcatname=objAllBean.getRingCatName();
				String rname=objAllBean.getRingName();
				String rurl=objAllBean.getRingUrl();
				String rdowncount=objAllBean.getRingLDownCount();
				String ruser=objAllBean.getRingLUser();
				String rtag=objAllBean.getRingLTag();
				String rsize=objAllBean.getRingLSize();
				String rstar=objAllBean.getRingStar();
				Constant.RINGCATITEMID=objAllBean.getRingId();

				Intent intplay=new Intent(getActivity(),SingleRingtone.class);
				intplay.putExtra("POSITION", pos);
				intplay.putExtra("RID", rid);
				intplay.putExtra("RCATID", rcatid);
				intplay.putExtra("RCATNAME", rcatname);
				intplay.putExtra("RRINGNAME", rname);
				intplay.putExtra("RRINGURL", rurl);
				intplay.putExtra("RDOWNRING", rdowncount);
				intplay.putExtra("RUSER", ruser);
				intplay.putExtra("RTAG", rtag);
				intplay.putExtra("RSIZE", rsize);
				intplay.putExtra("RSTAR", rstar);
				Constant.RINGTONE_ITEMID=objAllBean.getRingId();
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

						ItemRingLatest objItem = new ItemRingLatest();

						objItem.setRingId(objJson.getString(Constant.LATESTRING_RINGID));
						objItem.setRingCatId(objJson.getString(Constant.LATESTRING_RINGCATID));
						objItem.setRingCatName(objJson.getString(Constant.LATESTRING_RINGCATENAME));
						objItem.setRingName(objJson.getString(Constant.LATESTRING_RINGNAME));
						objItem.setRingUrl(objJson.getString(Constant.LATESTRING_RINGURL));
						objItem.setRingLDownCount(objJson.getString(Constant.LATESTRING_RINGDOWNCOUNT));
						objItem.setRingLUser(objJson.getString(Constant.LATESTRING_RINGUSER));
						objItem.setRingLTag(objJson.getString(Constant.LATESTRING_RINGTAG));
						objItem.setRingLSize(objJson.getString(Constant.LATESTRING_RINGSIZE));
						objItem.setRingStar(objJson.getString(Constant.LATESTRING_RINGSTAR));
						objItem.setRingImage(objJson.getString(Constant.LATESTRING_RINGIMAGE));

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
		objAdapterring = new RingLatestAdapter(getActivity(), R.layout.ring_latest_lsv_item,
				arrayOfLatestMusic);
		lsv_latest.setAdapter(objAdapterring);
	}

	public void showToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
	}

	
}

