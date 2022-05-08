package com.example.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class DownloadWallAdapter extends BaseAdapter {

	int imageWidth,columnIndex;
	Cursor cursor;
	Context context;
	public DownloadWallAdapter(Context cn,Cursor cursor,int col,int width) {
		// TODO Auto-generated constructor stub
		this.cursor=cursor;
		this.imageWidth=width;
		this.columnIndex=col;
		this.context=cn;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cursor.getCount();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView i = new ImageView(context);
		// Move cursor to current position
		cursor.moveToPosition(position);
		// Get the current value for the requested column
		int imageID = cursor.getInt(columnIndex);
		// obtain the image URI
		Uri uri = Uri.withAppendedPath( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Integer.toString(imageID) );
		String url = uri.toString();
		// Set the content of the image based on the image URI
		int originalImageId = Integer.parseInt(url.substring(url.lastIndexOf("/") + 1, url.length()));
		Bitmap b = MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(),
				originalImageId, MediaStore.Images.Thumbnails.MINI_KIND, null);
		i.setImageBitmap(b);

		i.setScaleType(ImageView.ScaleType.CENTER_CROP);
		i.setLayoutParams(new GridView.LayoutParams(imageWidth,
				imageWidth));
		return i;
	}

}
