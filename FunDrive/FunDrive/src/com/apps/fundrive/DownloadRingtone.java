package com.apps.fundrive;

import java.io.File;
import java.util.ArrayList;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import com.apps.fundrive.R;
import com.example.adapter.DownloadRingAdapter;
import com.example.item.Song;
import com.example.util.Constant;

public class DownloadRingtone extends Fragment{
	
	
	DownloadRingAdapter adapter;
	File files[];
	ListView listview;
	private ArrayList<Song> myList;
	Cursor 	mCursor;
	ArrayList<String> FileName;
	String [] filePath;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.ringfavorite, container, false);
		listview = (ListView)rootView.findViewById(R.id.latest_listfav);
		FileName=new ArrayList<String>();
		filePath=new String[FileName.size()];
		getFileScan();
		myList = new ArrayList<Song>();
		
		 	mCursor = getActivity().getContentResolver().query( MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				new String[]{MediaStore.Audio.Media._ID,MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DURATION,MediaStore.Audio.Media.DATA}, 
		        MediaStore.Audio.Media.DATA + " like ? ",
		        new String[] {"%"+Environment.getExternalStorageDirectory() + Constant.DOWNLOAD_SDCARD_FOLDER_PATH_RINGTONE +"%"},  
		        null);

		if (mCursor != null && mCursor.moveToFirst())
		{
			int i = mCursor.getColumnIndex("title");
			int j = mCursor.getColumnIndex("_id");
			int k = mCursor.getColumnIndex("artist");
			int l = mCursor.getColumnIndex("duration");
			int i1 = mCursor.getColumnIndexOrThrow("_data");
			do
			{
				long l1 = mCursor.getLong(j);
				String s = mCursor.getString(i);
				String s1 = mCursor.getString(k);
				String s2 = mCursor.getString(l);
				String s3 = mCursor.getString(i1);
				myList.add(new Song(l1, s, s1, s2, s3));
			} while (mCursor.moveToNext());
		} else
		{
			Toast.makeText(getActivity(), "No mp3 file found", Toast.LENGTH_SHORT).show();
		}
		adapter = new DownloadRingAdapter(getActivity(), myList);
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Song s=myList.get(position);
				Log.e("Name", s.getTitle());
			}
		});
		
		return rootView;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mCursor.close();
	}
	public void getFileScan()
	{
		File sdCardRoot = Environment.getExternalStorageDirectory();
		File yourDir = new File(sdCardRoot, Constant.DOWNLOAD_SDCARD_FOLDER_PATH_RINGTONE);
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
