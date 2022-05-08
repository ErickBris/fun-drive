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
import com.example.imageloader.ImageLoader;
import com.example.item.ItemRingLatest;
import com.example.util.Constant;

public class RingLatestAdapter extends ArrayAdapter<ItemRingLatest> {

	private Activity activity;
	private List<ItemRingLatest> itemsLatestring;
	private ItemRingLatest objLatestBeanring;
	private int row;
	public ImageLoader imageLoader; 

	public RingLatestAdapter(Activity act, int resource, List<ItemRingLatest> arrayList) {
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

 		holder.textringname.setText(objLatestBeanring.getRingName().toString());
		holder.txtringcatname.setText(objLatestBeanring.getRingCatName().toString());
		holder.txtringdown.setText(objLatestBeanring.getRingLDownCount().toString());
		imageLoader.DisplayImage(Constant.RING_IMAGE_FOLDER_PATH+objLatestBeanring.getRingImage(),holder.img_ringimg);


		return view;

	}

	public class ViewHolder {

		public TextView textringname,txtringcatname,txtringdown;
		public ImageView img_ringimg;

	}

}
