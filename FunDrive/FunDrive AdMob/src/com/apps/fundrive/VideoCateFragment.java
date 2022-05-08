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
import android.widget.ListView;
import android.widget.Toast;
import com.apps.fundrive.R;
import com.example.adapter.VideoCateAdapter;
import com.example.item.ItemVideoCategory;
import com.example.util.AlertDialogManager;
import com.example.util.Constant;
import com.example.util.JsonUtils;

public class VideoCateFragment extends Fragment {

	ListView lsv_ringcate;
	List<ItemVideoCategory> arrayOfringcate;
	VideoCateAdapter objAdapterringcat;
	AlertDialogManager alert = new AlertDialogManager();
	ItemVideoCategory objAllBeanringcatringcat;
	ArrayList<String> allListCatid,allListCatName,allListCatImageUrl;
	String[] allArrayCatid,allArrayCatname,allArrayCatImageurl;
	int textlength = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View rootView = inflater.inflate(R.layout.ringcate_fragement, container, false);
		lsv_ringcate=(ListView)rootView.findViewById(R.id.latest_listcat);
		arrayOfringcate=new ArrayList<ItemVideoCategory>();
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
//				String Catid=objAllBeanringcatringcat.getItemRingCatId();
//				String Catname=objAllBeanringcatringcat.getItemRingCategoryName();
				Constant.VIDEOCATID=objAllBeanringcatringcat.getItemVCId();
				Constant.VIDEOCATNAME=objAllBeanringcatringcat.getItemVCName();
				Log.e("cat_id",""+Constant.VIDEOCATID);
				Log.e("cat_id",""+Constant.VIDEOCATNAME);
 				
				Intent intcatitem=new Intent(getActivity(),VideoCategoryItemActivity.class);
				startActivity(intcatitem);
			}
		});


		if (JsonUtils.isNetworkAvailable(getActivity())) {
			new MyTask().execute(Constant.VIDEO_CATEGORY_URL);
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

						ItemVideoCategory objItem = new ItemVideoCategory();

						objItem.setItemVCId(objJson.getString(Constant.VCATEGORY_CID));
						objItem.setItemVCName(objJson.getString(Constant.VCATEGORY_CNAME));
						objItem.setItemVCImage(objJson.getString(Constant.VCATEGORY_CIMAGE));

						arrayOfringcate.add(objItem);


					}

				} catch (JSONException e) {
					e.printStackTrace();
				}


				for(int j=0;j<arrayOfringcate.size();j++)
				{
					objAllBeanringcatringcat=arrayOfringcate.get(j);

					allListCatid.add(String.valueOf(objAllBeanringcatringcat.getItemVCId()));
					allArrayCatid=allListCatid.toArray(allArrayCatid);

					allListCatName.add(objAllBeanringcatringcat.getItemVCName());
					allArrayCatname=allListCatName.toArray(allArrayCatname);

					allListCatImageUrl.add(objAllBeanringcatringcat.getItemVCImage());
					allArrayCatImageurl=allListCatImageUrl.toArray(allArrayCatImageurl);

				}


				setAdapterToListview();
			}

		}
	}



	public void setAdapterToListview() {
		objAdapterringcat = new VideoCateAdapter(getActivity(), R.layout.ringcate_item,
				arrayOfringcate);
		lsv_ringcate.setAdapter(objAdapterringcat);
	}

	public void showToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
	}
	
	

	//	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	//	    super.onCreateOptionsMenu(menu, inflater);
	//	    inflater.inflate(R.menu.menu_search, menu);
	//	    
	//	    
	//	    final SearchView searchView = (SearchView) menu.findItem(R.id.search)
	//                .getActionView();
	//	    
	//	    final MenuItem searchMenuItem = menu.findItem(R.id.search);
	//	    searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
	//			
	//			@Override
	//			public void onFocusChange(View v, boolean hasFocus) {
	//				// TODO Auto-generated method stub
	//				if(!hasFocus) {
	//					 searchMenuItem.collapseActionView();
	//					searchView.setQuery("", false);
	//	            }
	//			}
	//		});
	//	    
	//	    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
	//		
	//	    	@Override
	//			public boolean onQueryTextChange(String newText) {
	//				// TODO Auto-generated method stub
	//	    		textlength=newText.length();
	//	    		arrayOfringcate.clear();
	//	    		
	//	    		for(int i=0;i<allArrayCatname.length;i++)
	//	    		{
	//	    			if(textlength <= allArrayCatname[i].length())
	//	        		{
	//	    				if(newText.toString().equalsIgnoreCase((String) allArrayCatname[i].subSequence(0, textlength)))
	//	        			{
	//	    					ItemRingCategory objItem = new ItemRingCategory();
	//	    					objItem.setCategoryId(Integer.parseInt(allArrayCatid[i]));
	//	    					objItem.setCategoryName(allArrayCatname[i]);
	//	    					objItem.setCategoryImageurl(allArrayCatImageurl[i]);
	//	    					
	//	    					arrayOfringcate.add(objItem);
	//	        			}
	//	        		}
	//	    		}
	//	    		
	//	    		setAdapterToListview();
	//	    	
	//	    		return false;
	//			}
	//			
	//			@Override
	//			public boolean onQueryTextSubmit(String query) {
	//				// TODO Auto-generated method stub
	//				return false;
	//			}
	//		});


}