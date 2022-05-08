package com.example.adapter;

import java.io.File;
import java.util.ArrayList;

import com.apps.fundrive.R;
import com.example.item.Song;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class SongAdapter extends BaseAdapter {

	Context mContext;
	int selectedPosition;
	private LayoutInflater songInf;
	private ArrayList songs;
	private Editor editor;
	
	public SongAdapter(Context context, ArrayList arraylist)
	{
		selectedPosition = 0;
		mContext = context;
		songs = arraylist;
		songInf = LayoutInflater.from(context);
		selectedPosition = mContext.getSharedPreferences("selectpref", 0).getInt("pos", 0);
		editor =mContext.getSharedPreferences("durationpref", 0).edit();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return songs.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getSelected()
	{
		return ((Song)songs.get(selectedPosition)).getPath();
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		final ViewHolder holder;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.song, null);

			holder = new ViewHolder();

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.name=(TextView)view.findViewById(R.id.song_title);
		holder.artist=(TextView)view.findViewById(R.id.song_artist);
		holder.time=(TextView)view.findViewById(R.id.song_duration);
		holder.img_play=(LinearLayout)view.findViewById(R.id.playmusic_btn);
		holder.rb=(RadioButton)view.findViewById(R.id.radiobutton);
		final Song currSong = (Song)songs.get(position);
		holder.name.setText(currSong.getTitle());
		holder.artist.setText(currSong.getArtist());
		long l = Long.parseLong(currSong.getDuration());
		String obj1 = String.valueOf((l % 60000L) / 1000L);
		String obj2 = String.valueOf(l / 60000L);
		boolean flag;
		if (obj1.length() == 1)
		{
			holder.time.setText((new StringBuilder("0")).append(((String) (obj2))).append(":0").append(((String) (obj1))).toString());
		} else
		{
			holder.time.setText((new StringBuilder("0")).append(((String) (obj2))).append(":").append(((String) (obj1))).toString());
		}
	//	view.setTag(Integer.valueOf(position));

		if (position == selectedPosition)
		{
			flag = true;
		} else
		{
			flag = false;
		}

		holder.rb.setChecked(flag);
		holder.rb.setTag(Integer.valueOf(position));
		holder.rb.setOnClickListener(new View.OnClickListener() {
			
			 
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectedPosition = ((Integer)v.getTag()).intValue();
                editor.putInt("pos", selectedPosition);
                editor.commit();
                notifyDataSetChanged();
			}
		});
		
		holder.img_play.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				File view1 = new File(currSong.getPath());
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setDataAndType(Uri.fromFile(view1), "audio/*");
                mContext.startActivity(intent);
			}
		});


		return view;
	}

	public class ViewHolder {

		public TextView name,artist,time;
		LinearLayout img_play;
		RadioButton rb;


	}

}
