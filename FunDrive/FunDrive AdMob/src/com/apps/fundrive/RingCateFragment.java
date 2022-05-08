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

import com.example.adapter.RingCateAdapter;
import com.example.item.ItemRingCategory;
import com.example.util.AlertDialogManager;
import com.example.util.Constant;
import com.example.util.JsonUtils;

public class RingCateFragment extends Fragment {

	ListView lsv_ringcate;
	List<ItemRingCategory> arrayOfringcate;
	RingCateAdapter objAdapterringcat;
	AlertDialogManager alert = new AlertDialogManager();
	ItemRingCategory objAllBeanringcatringcat;
	ArrayList<String> allListCatid,allListCatName,allListCatImageUrl;
	String[] allArrayCatid,allArrayCatname,allArrayCatImageurl;
	int textlength = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View rootView = inflater.inflate(R.layout.ringcate_fragement, container, false);
		lsv_ringcate=(ListView)rootView.findViewById(R.id.latest_listcat);
		arrayOfringcate=new ArrayList<ItemRingCategory>();
		setHasOptionsMenu(true);

		allListCatid=new ArrayList<String>();
		allListCatImageUrl=new ArrayList<String>();
		allListCatName=new ArrayList<String>();

		allArrayCatid=new String[allListCatid.size()];
		allArrayCatname=new String[allListCatName.size()];
		allArrayCatImageurl=new String[allListCatImageUrl.size()];

 		lsv_ringcate.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				objAllBeanringcatringcat=arrayOfringcate.get(position);
 
				Constant.RINGCATID=objAllBeanringcatringcat.getItemRingCatId();
				Constant.RINGCATNAME=objAllBeanringcatringcat.getItemRingCategoryName();
			 
 				
				Intent intcatitem=new Intent(getActivity(),RingCategoryItemActivity.class);
				startActivity(intcatitem);
			}
		});


		if (JsonUtils.isNetworkAvailable(getActivity())) {
			new MyTask().execute(Constant.RINGCATEGORY_URL);
		} else {
			showToast("No Network Connection!!!");
			alert.showAlertDialog(getActivity(), "Internet Connection Error",
					"Please connect to working Internet connection", false);
		}
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
				showToast("No data found from web!!!");

			} else {

				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(Constant.CATEGORY_ARRAY_NAME);
					JSONObject objJson = null;
					for (int i = 0; i < jsonArray.length(); i++) {
						objJson = jsonArray.getJSONObject(i);

						ItemRingCategory objItem = new ItemRingCategory();

						objItem.setItemRingCatId(objJson.getString(Constant.CATERING_CATID));
						objItem.setItemRingCategoryName(objJson.getString(Constant.CATERING_CATENAME));
						objItem.setItemRingImageurl(objJson.getString(Constant.CATERING_CATEIMG));

						arrayOfringcate.add(objItem);


					}

				} catch (JSONException e) {
					e.printStackTrace();
				}


				for(int j=0;j<arrayOfringcate.size();j++)
				{
					objAllBeanringcatringcat=arrayOfringcate.get(j);

					allListCatid.add(String.valueOf(objAllBeanringcatringcat.getItemRingCatId()));
					allArrayCatid=allListCatid.toArray(allArrayCatid);

					allListCatName.add(objAllBeanringcatringcat.getItemRingCategoryName());
					allArrayCatname=allListCatName.toArray(allArrayCatname);

					allListCatImageUrl.add(objAllBeanringcatringcat.getItemRingImageurl());
					allArrayCatImageurl=allListCatImageUrl.toArray(allArrayCatImageurl);

				}


				setAdapterToListview();
			}

		}
	}



	public void setAdapterToListview() {
		objAdapterringcat = new RingCateAdapter(getActivity(), R.layout.ringcate_item,
				arrayOfringcate);
		lsv_ringcate.setAdapter(objAdapterringcat);
	}

	public void showToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
	}
	
	

	 


}