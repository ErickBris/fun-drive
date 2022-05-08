package com.apps.fundrive;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import com.apps.fundrive.R;
import com.example.adapter.FavoriteAdapter;
import com.example.favorite.DatabaseHandler;
import com.example.favorite.DatabaseHandler.DatabaseManager;
import com.example.favorite.Pojo;
import com.example.util.Constant;
import com.example.util.JsonUtils;

public class FavWallFragment extends Fragment {

	GridView grid_fav;
	DatabaseHandler db;
	private DatabaseManager dbManager;
	FavoriteAdapter adapter;
	ArrayList<String> allListImage,allListImageCatName,allListImageDCount,allListImageId,
	allListwtag,allListwsize,allListuser;
	String[] allArrayImage,allArrayImageCatName,allArrayImageDCount,allArrayImageId,
	allArraywtag,allArraywsize,allArraywuser;
	
	List<Pojo> allData;
	TextView txt_no;
	private int columnWidth;
	JsonUtils util;
	Pojo pojo;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.wallfavorite, container, false);
	 
		grid_fav=(GridView)rootView.findViewById(R.id.favorite_grid);
		txt_no=(TextView)rootView.findViewById(R.id.textView1);
		db=new DatabaseHandler(getActivity());
		dbManager = DatabaseManager.INSTANCE;
		dbManager.init(getActivity());
		util=new JsonUtils(getActivity());
		
		InitilizeGridLayout();
		allData=db.getAllData();
		adapter=new FavoriteAdapter(allData,getActivity(),columnWidth);
		grid_fav.setAdapter(adapter);
		if(allData.size()==0)
		{
			txt_no.setVisibility(View.VISIBLE);
		}
		else
		{
			txt_no.setVisibility(View.INVISIBLE);
		}
		
		grid_fav.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
			
				Intent intslider=new Intent(getActivity(),SingleWallpaper.class);
				intslider.putExtra("POSITION_ID", position);
				intslider.putExtra("IMAGE_ARRAY", allArrayImage);
 				intslider.putExtra("IMAGE_CATNAME", allArrayImageCatName);
 				intslider.putExtra("IMGDCOUNT", allArrayImageDCount);
				intslider.putExtra("IMGID", allArrayImageId);
				intslider.putExtra("WUSER", allArraywuser);
				intslider.putExtra("WTAG", allArraywtag);
				intslider.putExtra("WSIZE", allArraywsize);
				pojo=allData.get(position);
				Constant.CATEGORY_ITEMWALLID=pojo.getImageFId();
				startActivity(intslider);
 				
			}
		});
		return rootView;
		 
	}
	
	private void InitilizeGridLayout() {
		Resources r = getResources();
		float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				Constant.GRID_PADDING, r.getDisplayMetrics());

		columnWidth = (int) ((util.getScreenWidth() - ((Constant.NUM_OF_COLUMNS + 1) * padding)) / Constant.NUM_OF_COLUMNS);

		grid_fav.setNumColumns(Constant.NUM_OF_COLUMNS);
		grid_fav.setColumnWidth(columnWidth);
		grid_fav.setStretchMode(GridView.NO_STRETCH);
		grid_fav.setPadding((int) padding, (int) padding, (int) padding,
				(int) padding);
		grid_fav.setHorizontalSpacing((int) padding);
		grid_fav.setVerticalSpacing((int) padding);
	}
	
	public void onDestroyView() {
		
		//Log.e("OnDestroy", "called");
		if(!dbManager.isDatabaseClosed())
			dbManager.closeDatabase();
		super.onDestroy();
	}
	
	@Override
	public void onResume() {
	super.onResume();
	//Log.e("OnResume", "called");
	//when back key pressed or go one tab to another we update the favorite item so put in resume
	allData=db.getAllData();
	adapter=new FavoriteAdapter(allData,getActivity(),columnWidth);
	grid_fav.setAdapter(adapter);
	if(allData.size()==0)
	{
		txt_no.setVisibility(View.VISIBLE);
	}
	else
	{
		txt_no.setVisibility(View.INVISIBLE);
	}
	allListImage=new ArrayList<String>();
	allListImageCatName=new ArrayList<String>();
	allListImageDCount=new ArrayList<String>();
	allListImageId=new ArrayList<String>();
	allListuser=new ArrayList<String>();
	allListwtag=new ArrayList<String>();
	allListwsize=new ArrayList<String>();

	
	allArrayImage=new String[allListImage.size()];
	allArrayImageCatName=new String[allListImageCatName.size()];
	allArrayImageDCount=new String[allListImageDCount.size()];
	allArrayImageId=new String[allListImageId.size()];
	allArraywuser=new String[allListuser.size()];
	allArraywtag=new String[allListwtag.size()];
	allArraywsize=new String[allListwsize.size()];

	
	 
	for(int j=0;j<allData.size();j++)
	{
		 
		 Pojo objAllBean=allData.get(j);
		
		allListImage.add(objAllBean.getImageurl());
		allArrayImage=allListImage.toArray(allArrayImage);
		
		allListImageCatName.add(objAllBean.getCategoryName());
		allArrayImageCatName=allListImageCatName.toArray(allArrayImageCatName);
		
		allListImageDCount.add(objAllBean.getImageDCount());
		allArrayImageDCount=allListImageDCount.toArray(allArrayImageDCount);

		allListImageId.add(objAllBean.getImageFId());
		allArrayImageId=allListImageId.toArray(allArrayImageId);
		
		allListuser.add(objAllBean.getImageFUser());
		allArraywuser=allListuser.toArray(allArraywuser);
		
		allListwtag.add(objAllBean.getImageFTag());
		allArraywtag=allListwtag.toArray(allArraywtag);
		
		allListwsize.add(objAllBean.getImageFSize());
		allArraywsize=allListwsize.toArray(allArraywsize);
		

		
	}
	if(dbManager == null){
	dbManager = DatabaseManager.INSTANCE;
	dbManager.init(getActivity());
	}else if(dbManager.isDatabaseClosed()){
	dbManager.init(getActivity());
	}
	}

	@Override
	public void onPause() {
	super.onPause();
	//Log.e("OnPaused", "called");
	if(!dbManager.isDatabaseClosed())
	dbManager.closeDatabase();
	}
	 
}
