package com.apps.fundrive;

import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.apps.fundrive.R;
import com.example.adapter.RingFavAdapter;
import com.example.favorite.RingDatabaseHandler;
import com.example.favorite.RingPojo;
import com.example.util.Constant;

public class FavRingFragment extends Fragment {

	ListView grid_fav;
	RingDatabaseHandler db;
	RingFavAdapter favo_adapter;
	List<RingPojo> allData;
	RingPojo RingPojoitem;
	int textlength = 0;
	TextView txt_no;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View rootView = inflater.inflate(R.layout.ringfavorite, container, false);
		
		setHasOptionsMenu(true);
 
		 
		db=new RingDatabaseHandler(getActivity());
		grid_fav=(ListView)rootView.findViewById(R.id.latest_listfav);
		txt_no=(TextView)rootView.findViewById(R.id.textView1);

		allData=db.getAllData();
		favo_adapter=new RingFavAdapter(getActivity(),R.layout.ring_latest_lsv_item,allData);
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

				String pos=RingPojoitem.getDRingItemId();
				String rid=RingPojoitem.getDRingItemId();
				String rcatid=RingPojoitem.getDRingItemCatId();
				String rcatname=RingPojoitem.getDRingItemCatName();
				String rname=RingPojoitem.getDRingItemName();
				String rurl=RingPojoitem.getDRingItemUrl();
				String rdowncount=RingPojoitem.getDRingItemDCount();
				String ruser=RingPojoitem.getDRingItemRUser();
				String rtag=RingPojoitem.getDRingItemRTag();
				String rsize=RingPojoitem.getDRingItemRSize();

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
				Constant.RINGTONE_ITEMID=RingPojoitem.getDRingItemId();
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
		favo_adapter=new RingFavAdapter(getActivity(),R.layout.ring_latest_lsv_item,allData);
		grid_fav.setAdapter(favo_adapter);

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

