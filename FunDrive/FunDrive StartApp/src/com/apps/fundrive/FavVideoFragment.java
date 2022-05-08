package com.apps.fundrive;

import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import com.apps.fundrive.R;
import com.example.adapter.VideoFavAdapter;
import com.example.favorite.VideoDatabaseHandler;
import com.example.favorite.VideoPojo;
import com.example.util.Constant;

public class FavVideoFragment extends Fragment {

	GridView grid_fav;
	VideoDatabaseHandler db;
	VideoFavAdapter favo_adapter;
	List<VideoPojo> allData;
	VideoPojo RingPojoitem;
	int textlength = 0;
	TextView txt_no;
	private int mPhotoSize, mPhotoSpacing;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View rootView = inflater.inflate(R.layout.videofav_activity, container, false);
		
		setHasOptionsMenu(true);
 
		mPhotoSize = getResources().getDimensionPixelSize(R.dimen.photo_size);
		mPhotoSpacing = getResources().getDimensionPixelSize(R.dimen.photo_spacing);
 		
		db=new VideoDatabaseHandler(getActivity());
		grid_fav=(GridView)rootView.findViewById(R.id.vlatest_grid);
		txt_no=(TextView)rootView.findViewById(R.id.textView1);

		allData=db.getAllData();
		favo_adapter=new VideoFavAdapter(getActivity(),R.layout.video_latest_lsv_item,allData);
		grid_fav.setAdapter(favo_adapter);

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
				RingPojoitem=allData.get(position);

				String pos=RingPojoitem.getFVItemId();
  
				Intent intplay=new Intent(getActivity(),VideoSingle.class);
				intplay.putExtra("POSITION", pos);
 				Constant.VIDEO_ITEMID=RingPojoitem.getFVItemId();
			 
				startActivity(intplay);

			}
		});
		return rootView;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//Log.e("Name", "Called");
		allData=db.getAllData();
		favo_adapter=new VideoFavAdapter(getActivity(),R.layout.video_latest_lsv_item,allData);
		grid_fav.setAdapter(favo_adapter);
		
		grid_fav.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				if (favo_adapter.getNumColumns() == 0) {
					final int numColumns = (int) Math.floor(grid_fav.getWidth() / (mPhotoSize + mPhotoSpacing));
					if (numColumns > 0) {
						final int columnWidth = (grid_fav.getWidth() / numColumns) - mPhotoSpacing;
						favo_adapter.setNumColumns(numColumns);
						favo_adapter.setItemHeight(columnWidth);

					}
				}
			}
		});

		if(allData.size()==0)
		{
			txt_no.setVisibility(View.VISIBLE);

		}
		else
		{
			txt_no.setVisibility(View.INVISIBLE);

		}
	}
}

