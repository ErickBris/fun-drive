package com.example.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class DownloadTask extends AsyncTask<String, Integer, Void>{
	private NotificationHelper mNotificationHelper;

	//private static final String PEFERENCE_FILE = "preference";
	//private static final String ISDOWNLOADED = "isdownloaded";
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	Context context;
	String a;
	String as[];
	String rid;
	public DownloadTask(Context context, String ringid){
		this.context = context;
		//Create the notification object from NotificationHelper class
		mNotificationHelper = new NotificationHelper(context);
		this.rid=ringid;
	}

	protected void onPreExecute(){
		//Create the notification in the statusbar
		mNotificationHelper.createNotification();
	}

	@Override
	protected Void doInBackground(String... aurl) {
		//This is where we would do the actual download stuff
		//for now I'm just going to loop for 10 seconds
		// publishing progress every second

		int count;

		try {


			URL url = new URL(aurl[0]);
			URLConnection conexion = url.openConnection();
			conexion.connect();

			File root = android.os.Environment.getExternalStorageDirectory();

			int lenghtOfFile = conexion.getContentLength();
			Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

			InputStream input = new BufferedInputStream(url.openStream());
			File direct = new File(Environment.getExternalStorageDirectory() +Constant.DOWNLOAD_SDCARD_FOLDER_PATH_RINGTONE);
			direct.mkdirs();
			if(!direct.exists())
			{
				direct.mkdirs();
			}
			//OutputStream output = new FileOutputStream("/sdcard/foldername/temp.zip");

			a = root.getAbsolutePath() + Constant.DOWNLOAD_SDCARD_FOLDER_PATH_RINGTONE + aurl[0].substring(aurl[0].lastIndexOf('/')+1);
			OutputStream output = new FileOutputStream(a);
			byte data[] = new byte[2048];

			long total = 0;

			while ((count = input.read(data)) != -1) {
				total += count;
				//publishProgress(""+(int)((total*100)/lenghtOfFile));
				Log.d("%Percentage%",""+(int)((total*100)/lenghtOfFile));
				onProgressUpdate((int)((total*100)/lenghtOfFile));
				output.write(data, 0, count);
			}

			output.flush();
			output.close();
			input.close();
		} catch (Exception e) {}

		as = new String[1];
		as[0] = new File(a).toString();

		MediaScannerConnection.scanFile(context, as, null, new android.media.MediaScannerConnection.OnScanCompletedListener() {
			public void onScanCompleted(String s1, Uri uri)
			{
			}

		});
		return null;
	}
	protected void onProgressUpdate(Integer... progress) {
		//This method runs on the UI thread, it receives progress updates
		//from the background thread and publishes them to the status bar
		mNotificationHelper.progressUpdate(progress[0]);
	}
	protected void onPostExecute(Void result)    {
		//The task is complete, tell the status bar about it
		mNotificationHelper.completed();
	}
}
