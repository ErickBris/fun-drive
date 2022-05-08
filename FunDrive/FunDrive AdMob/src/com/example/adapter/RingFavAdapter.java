package com.example.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.fundrive.R;
import com.example.favorite.RingPojo;
import com.example.imageloader.ImageLoader;
import com.example.util.Constant;

public class RingFavAdapter extends ArrayAdapter<RingPojo> {

	private Activity activity;
	private List<RingPojo> itemsLatestring;
	private RingPojo objLatestBeanring;
	private int row;
	public ImageLoader imageLoader; 

	public RingFavAdapter(Activity act, int resource, List<RingPojo> arrayList) {
		super(act, resource, arrayList);
		this.activity = act;
		this.row = resource;
		this.itemsLatestring = arrayList;
		imageLoader=new ImageLoader(activity);
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(row, null);

			holder = new ViewHolder();
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		if ((itemsLatestring == null) || ((position + 1) > itemsLatestring.size()))
			return view;

		objLatestBeanring = itemsLatestring.get(position);


		holder.textringname=(TextView)view.findViewById(R.id.text_ringname);
		holder.txtringcatname=(TextView)view.findViewById(R.id.text_ringcatname);
		holder.txtringdown=(TextView)view.findViewById(R.id.text_ringlistdown);
		holder.img_ringimg=(ImageView)view.findViewById(R.id.img_default);

		holder.textringname.setText(objLatestBeanring.getDRingItemName().toString());
		holder.txtringcatname.setText(objLatestBeanring.getDRingItemCatName().toString());
		holder.txtringdown.setText(objLatestBeanring.getDRingItemDCount().toString());
 		imageLoader.DisplayImage(Constant.RING_IMAGE_FOLDER_PATH+objLatestBeanring.getDRingItemRImag(),holder.img_ringimg);


		return view;

	}

	public class ViewHolder {

		public TextView textringname,txtringcatname,txtringdown;
		public ImageView img_ringimg;


	}

}
