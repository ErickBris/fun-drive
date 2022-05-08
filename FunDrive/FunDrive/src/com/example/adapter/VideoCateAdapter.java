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
import com.example.item.ItemVideoCategory;
import com.example.util.Constant;

public class VideoCateAdapter extends ArrayAdapter<ItemVideoCategory> {

	private Activity activity;
	private List<ItemVideoCategory> itemsringcat;
	private ItemVideoCategory objAllBeanringcat;
	private int row;
	ImageLoader imageloader; 

	public VideoCateAdapter(Activity act, int resource, List<ItemVideoCategory> arrayList) {
		super(act, resource, arrayList);
		this.activity = act;
		this.row = resource;
		this.itemsringcat = arrayList;
		imageloader=new ImageLoader(activity);

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

		if ((itemsringcat == null) || ((position + 1) > itemsringcat.size()))
			return view;

		objAllBeanringcat = itemsringcat.get(position);
		holder.txtracat=(TextView)view.findViewById(R.id.text_ringcategty);
		holder.img_racat=(ImageView)view.findViewById(R.id.image_ringcategory);
		holder.txtracat.setText(objAllBeanringcat.getItemVCName().toString());
		imageloader.DisplayImage(Constant.SERVER_IMAGE_UPFOLDER_THUMB+objAllBeanringcat.getItemVCImage(),holder.img_racat);
		return view;

	}

	public class ViewHolder {

		public TextView txtracat;
		public ImageView img_racat;
	}

}
