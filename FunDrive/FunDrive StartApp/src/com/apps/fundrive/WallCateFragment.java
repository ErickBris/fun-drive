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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.apps.fundrive.R;
import com.example.adapter.AllPhotosListAdapter;
import com.example.item.ItemAllPhotos;
import com.example.util.AlertDialogManager;
import com.example.util.Constant;
import com.example.util.JsonUtils;

public class WallCateFragment extends Fragment {

	ListView lsv_allphotos;
	List<ItemAllPhotos> arrayOfAllphotos;
	AllPhotosListAdapter objAdapter;
	AlertDialogManager alert = new AlertDialogManager();
	private ItemAllPhotos objAllBean;
	int page=1;
	int TOTAL_LIST_ITEMS;
	private Button btnLoadMore;
	public int NUM_ITEMS_PAGE;
	private int noOfBtns;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.wallcate_fragment, container, false);
		lsv_allphotos=(ListView)rootView.findViewById(R.id.lsv_allphotos);
		arrayOfAllphotos=new ArrayList<ItemAllPhotos>();

		NUM_ITEMS_PAGE=Integer.parseInt(getString(R.string.numofitemcat));
		btnLoadMore = new Button(getActivity());
		btnLoadMore.setText("Load More");

		lsv_allphotos.addFooterView(btnLoadMore, null, false);
		lsv_allphotos.setFooterDividersEnabled(false);


		lsv_allphotos.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				objAllBean=arrayOfAllphotos.get(position);
				String Catid=objAllBean.getCategoryId();
				Constant.CATEGORY_ID=objAllBean.getCategoryId();
				Log.e("cat_id",""+Catid);

				Constant.CATEGORY_TITLE=objAllBean.getCategoryName();

				Intent intcat=new Intent(getActivity(),WallCategoryItemActivity.class);
				startActivity(intcat);


			}
		});


		if (JsonUtils.isNetworkAvailable(getActivity())) {
			new MyTask().execute(Constant.CATEGORY_URL+page);
		} else {


			showToast("No Network Connection!!!");
			alert.showAlertDialog(getActivity(), "Internet Connection Error",
					"Please connect to working Internet connection", false);
		}

		btnLoadMore.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(page >= noOfBtns)
				{
					showToast("NO MORE CATEGORY");
					btnLoadMore.setVisibility(Button.GONE);
				}
				else
				{	
					page=page+1;
					new MyTask().execute(Constant.CATEGORY_URL+page);	
				}
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
				showToast("No data found from web!!!");

			} else {

				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(Constant.CATEGORY_ARRAY_NAME);
					JSONObject objJson = null;
					for (int i = 0; i < jsonArray.length(); i++) {
						objJson = jsonArray.getJSONObject(i);

						ItemAllPhotos objItem = new ItemAllPhotos();
						objItem.setCategoryName(objJson.getString(Constant.CATEGORY_NAME));
						objItem.setCategoryId(objJson.getString(Constant.CATEGORY_CID));
						objItem.setCategoryImage(objJson.getString(Constant.CATEGORY_IMAGE_URL));
						TOTAL_LIST_ITEMS=Integer.parseInt(objJson.getString(Constant.CATEGORY_NUM));
						arrayOfAllphotos.add(objItem);


					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

				int val = TOTAL_LIST_ITEMS%NUM_ITEMS_PAGE;
				val = val==0?0:1;
				noOfBtns=TOTAL_LIST_ITEMS/NUM_ITEMS_PAGE+val;
				setAdapterToListview();
			}

		}
	}



	public void setAdapterToListview() {
		objAdapter = new AllPhotosListAdapter(getActivity(), R.layout.wallcate_item,
				arrayOfAllphotos);
		lsv_allphotos.setAdapter(objAdapter);
		if(page==1)
		{
			lsv_allphotos.setSelection(0);	
		}
		else
		{
			lsv_allphotos.setSelection(NUM_ITEMS_PAGE*page);
		}
		if(page >= noOfBtns)
		{
			btnLoadMore.setVisibility(View.GONE);
		}

	}

	public void showToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
	}
}
