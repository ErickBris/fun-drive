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
import com.example.item.Item_Drawer;

public class DrawerAdapter extends ArrayAdapter<Item_Drawer> {
	
	private Activity activity;
	private List<Item_Drawer> itemsAllphotos;
	private Item_Drawer objAllBean;
	private int row;
 	
	public DrawerAdapter(Activity act, int resource, List<Item_Drawer> arrayList) {
		super(act, resource, arrayList);
		this.activity = act;
		this.row = resource;
		this.itemsAllphotos = arrayList;
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

		if ((itemsAllphotos == null) || ((position + 1) > itemsAllphotos.size()))
			return view;

		objAllBean = itemsAllphotos.get(position);
		
		holder.txt=(TextView)view.findViewById(R.id.drawer_text_menu);
		holder.img=(ImageView)view.findViewById(R.id.image_drawericon);
		holder.txt.setText(objAllBean.getCategoryName().toString());
		holder.img.setImageResource(objAllBean.getCategoryImage());
		
		return view;
		
	}

	public class ViewHolder {
	 
		public TextView txt;
		public ImageView img;
		 
	}

}
