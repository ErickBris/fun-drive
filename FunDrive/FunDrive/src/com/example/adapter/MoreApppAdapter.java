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
import com.example.item.ItemMoreApp;
import com.example.util.Constant;

public class MoreApppAdapter  extends ArrayAdapter<ItemMoreApp>{

	private Activity activity;
	private List<ItemMoreApp> itemsLatest;
	private ItemMoreApp objLatestBean;
	private int row;
	public ImageLoader imageLoader; 


	public MoreApppAdapter(Activity context, 
			int textViewResourceId, List<ItemMoreApp> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.activity=context;
		this.itemsLatest=objects;		
		this.row=textViewResourceId;
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

		if ((itemsLatest == null) || ((position + 1) > itemsLatest.size()))
			return view;

		objLatestBean = itemsLatest.get(position);


		holder.imgv_latetst=(ImageView)view.findViewById(R.id.image_category);
		holder.txt_appname=(TextView)view.findViewById(R.id.txt_allphotos_categty);
		holder.txt_appname.setText(objLatestBean.getAppName());
		imageLoader.DisplayImage(Constant.SERVER_IMAGE_UPFOLDER_CATEGORY+objLatestBean.getAppImageUrl().toString().replace(" ", "%20"), holder.imgv_latetst);

		return view;

	}

	public class ViewHolder {
		public ImageView imgv_latetst;
		public TextView txt_appname;

	}

}
