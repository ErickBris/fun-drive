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
import com.example.item.ItemRingCategoryItem;
import com.example.util.Constant;

public class RingCateItemAdapter extends ArrayAdapter<ItemRingCategoryItem> {

	private Activity activity;
	private List<ItemRingCategoryItem> itemsLatestring;
	private ItemRingCategoryItem objLatestBeanring;
	private int row;
	public ImageLoader imageLoader; 

	public RingCateItemAdapter(Activity act, int resource, List<ItemRingCategoryItem> arrayList) {
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

		holder.textringname.setText(objLatestBeanring.getRingItemName().toString());
		holder.txtringcatname.setText(objLatestBeanring.getRingItemCatName().toString());
		holder.txtringdown.setText(objLatestBeanring.getRingItemDownCount().toString());
		imageLoader.DisplayImage(Constant.RING_IMAGE_FOLDER_PATH+objLatestBeanring.getRingImage(),holder.img_ringimg);


		return view;

	}

	public class ViewHolder {

		public TextView textringname,txtringcatname,txtringdown;
		public ImageView img_ringimg;
		 

	}

}
