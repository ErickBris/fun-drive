package com.apps.fundrive;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;
import com.apps.fundrive.R;
import com.example.adapter.DownloadWallAdapter;
import com.example.util.Constant;
import com.example.util.JsonUtils;

public class DownloadWallpaper extends Fragment {

	GridView grid;
	private int columnWidth;
	JsonUtils util;
	Cursor cursor;
	ArrayList<String> TitleName,FileName;
	String [] filePath;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.walllatest_fragment, container, false);
		grid=(GridView)rootView.findViewById(R.id.latest_grid);
		util=new JsonUtils(getActivity());
		TitleName=new ArrayList<String>();
		FileName=new ArrayList<String>();
		filePath=new String[FileName.size()];
		InitilizeGridLayout();
		getFileScan();
		String[] projection = {MediaStore.Images.Media._ID,MediaStore.Images.Media.TITLE};
		// Create the cursor pointing to the SDCard
		cursor = getActivity().getContentResolver().query( MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				projection, 
				MediaStore.Images.Media.DATA + " like ? ",
				new String[] {"%"+Environment.getExternalStorageDirectory() + Constant.DOWNLOAD_SDCARD_FOLDER_PATH_WALLPAPER+"%"},  
				null);
		// Get the column index of the image ID
		if(cursor!= null)
		{
			int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
			grid.setAdapter(new DownloadWallAdapter(getActivity(),cursor,columnIndex,columnWidth));

			if(cursor.moveToFirst())
			{
				int i = cursor.getColumnIndex("title");
				do
				{
					String Title= cursor.getString(i);
					TitleName.add(Title);
				}while(cursor.moveToNext());
			}
		}
		else
		{
			Toast.makeText(getActivity(), "No Wallpaper found", Toast.LENGTH_LONG).show();
		}


		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String Name=TitleName.get(position);

				StringTokenizer str=new StringTokenizer(Name, "_");
				String im=str.nextToken();
				String id1=str.nextToken();
				Log.i("Title",im);
				Intent intslider=new Intent(getActivity(),SingleWallpaper.class);
				Constant.CATEGORY_ITEMWALLID=id1;
				startActivity(intslider);

			}
		});

		return rootView;
	}


	private void InitilizeGridLayout() {
		Resources r = getResources();
		float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				Constant.GRID_PADDING, r.getDisplayMetrics());

		columnWidth = (int) ((util.getScreenWidth() - ((Constant.NUM_OF_COLUMNS + 1) * padding)) / Constant.NUM_OF_COLUMNS);

		grid.setNumColumns(Constant.NUM_OF_COLUMNS);
		grid.setColumnWidth(columnWidth);
		grid.setStretchMode(GridView.NO_STRETCH);
		grid.setPadding((int) padding, (int) padding, (int) padding,
				(int) padding);
		grid.setHorizontalSpacing((int) padding);
		grid.setVerticalSpacing((int) padding);
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		cursor.close();
	}

	public void getFileScan()
	{
		File sdCardRoot = Environment.getExternalStorageDirectory();
		File yourDir = new File(sdCardRoot, Constant.DOWNLOAD_SDCARD_FOLDER_PATH_WALLPAPER);
		if(yourDir.exists())
		{
			for (File f : yourDir.listFiles()) {
				if (f.isFile())
				{
					String name=f.getAbsolutePath();
					Log.e("Name", name);
					FileName.add(name);
					filePath=FileName.toArray(filePath);
				}
			}

			MediaScannerConnection.scanFile(getActivity(),
					filePath,
					null,
					new MediaScannerConnection.OnScanCompletedListener() {
				public void onScanCompleted(String path, Uri uri) {
					// scanned path and uri
				}
			});			
		}


	}
}
