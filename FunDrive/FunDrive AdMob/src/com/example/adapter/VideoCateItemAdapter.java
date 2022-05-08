package com.example.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apps.fundrive.R;
import com.example.imageloader.ImageLoader;
import com.example.item.ItemVideoCategoryItem;
import com.example.util.Constant;

public class VideoCateItemAdapter extends ArrayAdapter<ItemVideoCategoryItem> {

	private Activity activity;
	private List<ItemVideoCategoryItem> itemsLatestring;
	private ItemVideoCategoryItem objLatestBeanring;
	private int row;
	public ImageLoader imageLoader; 
	private int mItemHeight = 0;
	private int mNumColumns = 0;
	private RelativeLayout.LayoutParams mImageViewLayoutParams;

	public VideoCateItemAdapter(Activity act, int resource, List<ItemVideoCategoryItem> arrayList) {
		super(act, resource, arrayList);
		this.activity = act;
		this.row = resource;
		this.itemsLatestring = arrayList;
		imageLoader=new ImageLoader(activity);
		mImageViewLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
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


		holder.textvname=(TextView)view.findViewById(R.id.text_subcate_imgtitle);
 		holder.imgv_latetst=(ImageView)view.findViewById(R.id.img_subcategory);
		holder.textvname.setText(objLatestBeanring.getVItemName().toString());
		holder.imgv_latetst.setScaleType(ImageView.ScaleType.CENTER_CROP);
		holder.imgv_latetst.setLayoutParams(mImageViewLayoutParams);
	 
		// Check the height matches our calculated column width
		if (holder.imgv_latetst.getLayoutParams().height != mItemHeight) {
			holder.imgv_latetst.setLayoutParams(mImageViewLayoutParams);
		}
		if(objLatestBeanring.getVItemPlayIds().equals("000q1w2"))
		{
			imageLoader.DisplayImage(Constant.SERVER_IMAGE_UPFOLDER_THUMB+objLatestBeanring.getVItemThumb().toString(), holder.imgv_latetst);
		}
		else
		{
			imageLoader.DisplayImage(Constant.YOUTUBE_IMAGE_FRONT+objLatestBeanring.getVItemPlayIds().toString()+Constant.YOUTUBE_IMAGE_BACK, holder.imgv_latetst);
		}


		return view;

	}

	public class ViewHolder {

		public TextView textvname;
		public ImageView imgv_latetst;

 	}
	// set numcols
			public void setNumColumns(int numColumns) {
				mNumColumns = numColumns;
			}

			public int getNumColumns() {
				return mNumColumns;
			}

			// set photo item height
			public void setItemHeight(int height) {
				if (height == mItemHeight) {
					return;
				}
				mItemHeight = height;
				mImageViewLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, mItemHeight);
				notifyDataSetChanged();
			}

		}

	 