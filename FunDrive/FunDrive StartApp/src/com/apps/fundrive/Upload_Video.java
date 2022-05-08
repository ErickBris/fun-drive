package com.apps.fundrive;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.apps.fundrive.R;
import com.example.util.AlertDialogManager;
import com.example.util.Constant;
import com.example.util.JsonUtils;
import com.example.util.SessionManager;
import com.google.analytics.tracking.android.EasyTracker;

public class Upload_Video extends SherlockActivity {

	AlertDialogManager alert = new AlertDialogManager();
	Spinner spincategory,spinvideotype;
	ArrayList<String> category,categoryid;
	EditText edttagname,edtvideoname,edtyturl;
	Button btnimgchoose,btn_submit,btnvideochoose;
	LinearLayout lay_videochoose,lay_videoupload,lay_imagechoose,lay_imageupload,lay_ytsec;
	ImageView imgshow;
	private static final int SELECT_PICTURE = 1,SELECT_VIDEO = 2;
	private String selectedImagePath,selectedImagePathVideo;
	byte[] byteImage1 = null;
	private ProgressDialog dialog = null;
	private ProgressDialog dialogvi = null;
	private int serverResponseCode = 0;
	HttpURLConnection conn  ;
	String NewImagePath,NewImagePathV,SizeImage,SizeImageV;
	SessionManager session;
	HashMap<String, String> user;
	boolean isuploadvideo=false;
	boolean isuploadvideoimage=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_video);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		setTitle("Upload Video");
		category=new ArrayList<String>();
		categoryid=new ArrayList<String>();
		spincategory=(Spinner)findViewById(R.id.spinner_selectcate);
		spinvideotype=(Spinner)findViewById(R.id.spinner_selectvideotype);
		edttagname=(EditText)findViewById(R.id.editText_addplacename);
		btnimgchoose=(Button)findViewById(R.id.button_chooseimg);
		imgshow=(ImageView)findViewById(R.id.imageView_chooseimgshow);
		btn_submit=(Button)findViewById(R.id.button_submit);
		edtvideoname=(EditText)findViewById(R.id.editText_addvideoname);
		edtyturl=(EditText)findViewById(R.id.editText_addyturl);
		btnvideochoose=(Button)findViewById(R.id.button_choosevideo);
		lay_videochoose=(LinearLayout)findViewById(R.id.lay_videochoose);
		lay_videoupload=(LinearLayout)findViewById(R.id.lay_videoupload);
		lay_imagechoose=(LinearLayout)findViewById(R.id.lay_chooseimage);
		lay_imageupload=(LinearLayout)findViewById(R.id.lay_imageupload);
		lay_ytsec=(LinearLayout)findViewById(R.id.lay_ytsec);

		final EditText input = new EditText(Upload_Video.this);
		input.setTextColor(getResources().getColor(R.color.color));

		session = new SessionManager(getApplicationContext());
		user = session.getUserDetails();
		Constant.LOGIN_ID=user.get(SessionManager.KEY_PASSENGER_ID);
		//Data Comes in Spinner
		if (JsonUtils.isNetworkAvailable(Upload_Video.this)) {
			new MyTask().execute(Constant.VIDEO_CATEGORYSPIN_URL);
		} else {
			showToast(getString(R.string.conn_msg1));
			alert.showAlertDialog(Upload_Video.this, getString(R.string.conn_msg2),
					getString(R.string.conn_msg3), false);
		}

		ArrayAdapter<CharSequence> spin=ArrayAdapter.createFromResource(this, R.array.videotype,R.layout.uploadtype_spiner);
		spin.setDropDownViewResource(R.layout.uploadtype_spiner);
		spinvideotype.setAdapter(spin);

		spinvideotype.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(spinvideotype.getSelectedItem().toString().equals("Youtube"))
				{
					lay_ytsec.setVisibility(View.VISIBLE);
					lay_videochoose.setVisibility(View.GONE);
					lay_videoupload.setVisibility(View.GONE);
					lay_imagechoose.setVisibility(View.GONE);
					lay_imageupload.setVisibility(View.GONE);
				}
				else if(spinvideotype.getSelectedItem().toString().equals("Upload From Mobile"))
				{

					lay_ytsec.setVisibility(View.GONE);
					lay_videochoose.setVisibility(View.VISIBLE);
					lay_videoupload.setVisibility(View.VISIBLE);
					lay_imagechoose.setVisibility(View.VISIBLE);
					lay_imageupload.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});


		btnimgchoose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(
						Intent.createChooser(intent, "Select Picture"),
						SELECT_PICTURE);

			}
		});



		btnvideochoose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setType("video/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(
						Intent.createChooser(intent, "Select Picture"),
						SELECT_VIDEO);
			}
		});


		btn_submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				//				Toast.makeText(Upload_Video.this,
				//                        "On Button Click : " + 
				//                        "\n" + String.valueOf(spinvideotype.getSelectedItem()) ,
				//                        Toast.LENGTH_LONG).show();
				
				int pos=spincategory.getSelectedItemPosition();
				String Categoryid=categoryid.get(pos).toString();
				String Tag=edttagname.getText().toString().replace(" ", "%20");
				String VideoName=edtvideoname.getText().toString().replace(" ", "%20");
				String YTVUrl=edtyturl.getText().toString().replace(" ", "%20");
				if(spinvideotype.getSelectedItem().toString().equals("Youtube"))
				{
					if(edtyturl.getText().toString().length() == 0 )
					{
						Toast.makeText(getApplicationContext(), "Add Youtube Url", Toast.LENGTH_SHORT).show();
					}
					else
					{
						if (JsonUtils.isNetworkAvailable(Upload_Video.this)) {
							new MyTaskPost().execute(Constant.ADD_VIDEO_URL+Constant.LOGIN_ID+"&upload_type=youtube"+"&image_path="+"&tag="+Tag+"&video_name="+VideoName+"&youtube_url="+YTVUrl.replace(" ", "%20")+"&video_size="+"&cat_id="+Categoryid);
							
						} else {
							showToast(getString(R.string.conn_msg1));
							alert.showAlertDialog(Upload_Video.this, getString(R.string.conn_msg2),
									getString(R.string.conn_msg3), false);
						} 
					}
				}
				else
				{
					dialogvi = ProgressDialog.show(Upload_Video.this, "", "Uploading file...", true);

					new Thread(new Runnable() {
						public void run() {

							uploadFileVideo(selectedImagePathVideo);
							isuploadvideo=true;

						}
					}).start();
				}

				


			}
		});



	}


	private	class MyTask extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(Upload_Video.this);
			pDialog.setMessage(getString(R.string.loading));
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			return JsonUtils.getJSONString(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}

			if (null == result || result.length() == 0) {
				showToast(getString(R.string.conn_msg4));
				alert.showAlertDialog(Upload_Video.this, getString(R.string.conn_msg4),
						getString(R.string.conn_msg5), false);

			} else {

				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(Constant.CATEGORY_ARRAY_NAME);
					JSONObject objJson = null;
					for (int i = 0; i < jsonArray.length(); i++) {
						objJson = jsonArray.getJSONObject(i);

						category.add(objJson.getString(Constant.VCATEGORY_CNAME));
						categoryid.add(objJson.getString(String.valueOf(Constant.VCATEGORY_CID)));
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

				setAdapterToListview();
			}

		}
	}
	public void setAdapterToListview() {

		ArrayAdapter<String> City = new ArrayAdapter<String>(this,R.layout.addspiner_item, category);
		spincategory.setAdapter(City);


	}

	public void showToast(String msg) {
		Toast.makeText(Upload_Video.this, msg, Toast.LENGTH_LONG).show();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				Uri selectedImageUri = data.getData();
				selectedImagePath = getPath(selectedImageUri);
				System.out.println("Image Path : " + selectedImagePath);
				imgshow.setVisibility(View.VISIBLE);
				imgshow.setImageURI(selectedImageUri);
			}
			if (requestCode == SELECT_VIDEO) {
				Uri selectedImageUri = data.getData();
				selectedImagePathVideo = getPath(selectedImageUri);
				Log.e("vIDEO Path : " , selectedImagePathVideo);
			}
		}
	}


	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	public int uploadFile(String sourceFileUri) {
		final String upLoadServerUri = Constant.UPLOAD_VIDEOTHUMB_URL;

		DataOutputStream dos = null;  
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024; 
		File sourceFile = new File(sourceFileUri);
		Log.e("File Name", sourceFile.getName());
		Log.e("File Path", sourceFile.getParent());
		Log.e("File Size", ""+sourceFile.length());
		SizeImage=formatFileSize(sourceFile.length());
		String as[] = null;
		File to = new File(sourceFile.getParent()+"/",gen()+"_"+ sourceFile.getName());
		if(sourceFile.exists())
			sourceFile.renameTo(to);
		Log.e("File Name_New", to.getName());
		NewImagePath=to.getName();

		as = new String[1];
		as[0] = to.toString();

		MediaScannerConnection.scanFile(Upload_Video.this, as, null, new android.media.MediaScannerConnection.OnScanCompletedListener() {
			public void onScanCompleted(String s1, Uri uri)
			{
			}

		});



		if (!to.isFile()) {
			Log.e("uploadFile", "Source File Does not exist");
			dialog.dismiss();
			return 0;
		}
		try { // open a URL connection to the Servlet

			FileInputStream fileInputStream = new FileInputStream(to);
			URL url = new URL(upLoadServerUri);
			conn = (HttpURLConnection) url.openConnection(); // Open a HTTP  connection to  the URL
			conn.setDoInput(true); // Allow Inputs
			conn.setDoOutput(true); // Allow Outputs
			conn.setUseCaches(false); // Don't use a Cached Copy
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("ENCTYPE", "multipart/form-data");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			//*********filename first is php base filename
			conn.setRequestProperty("uploaded_file", to.getAbsolutePath()); 

			dos = new DataOutputStream(conn.getOutputStream());

			dos.writeBytes(twoHyphens + boundary + lineEnd); 
			//********filename first is php base filename
			dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""+ to.getAbsolutePath() + "\"" + lineEnd);
			dos.writeBytes(lineEnd);

			bytesAvailable = fileInputStream.available(); // create a buffer of  maximum size

			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];

			// read file and write it into form...
			bytesRead = fileInputStream.read(buffer, 0, bufferSize);  

			while (bytesRead > 0) {
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);               
			}

			// send multipart form data necesssary after file data...
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			// Responses from the server (code and message)
			serverResponseCode = conn.getResponseCode();
			String serverResponseMessage = conn.getResponseMessage();

			Log.e("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
			if(serverResponseCode == 200){
				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(Upload_Video.this, "File Upload Complete.", Toast.LENGTH_SHORT).show();

						int pos=spincategory.getSelectedItemPosition();
						String Categoryid=categoryid.get(pos).toString();
						String Tag=edttagname.getText().toString().replace(" ", "%20");
						String VideoName=edtvideoname.getText().toString().replace(" ", "%20");
						String YTVUrl=edtyturl.getText().toString().replace(" ", "%20");
						if(spinvideotype.getSelectedItem().toString().equals("Youtube"))
						{
							if(edtyturl.getText().toString().length() == 0 )
							{
								Toast.makeText(getApplicationContext(), "Add Youtube Url", Toast.LENGTH_SHORT).show();
							}
							else
							{
								if (JsonUtils.isNetworkAvailable(Upload_Video.this)) {
									new MyTaskPost().execute(Constant.ADD_VIDEO_URL+Constant.LOGIN_ID+"&upload_type=youtube"+"&image_path="+"&tag="+Tag+"&video_name="+VideoName+"&youtube_url="+YTVUrl.replace(" ", "%20")+"&video_size="+"&cat_id="+Categoryid);
									
								} else {
									showToast(getString(R.string.conn_msg1));
									alert.showAlertDialog(Upload_Video.this, getString(R.string.conn_msg2),
											getString(R.string.conn_msg3), false);
								} 
							}
						}
						else if(spinvideotype.getSelectedItem().toString().equals("Upload From Mobile"))
						{

							if (JsonUtils.isNetworkAvailable(Upload_Video.this)) {
								new MyTaskPost().execute(Constant.ADD_VIDEO_URL+Constant.LOGIN_ID+"&upload_type=Local_Upload"+"&image_path="+Constant.SERVER_IMAGE_UPFOLDER_CATEGORY+NewImagePath+"&tag="+Tag+"&video_name="+VideoName+"&video_path="+Constant.VIDEO_FOLDER_PATH+NewImagePathV+"&video_size="+SizeImageV.replace(" ", "%20")+"&cat_id="+Categoryid);
								
							} else {
								showToast(getString(R.string.conn_msg1));
								alert.showAlertDialog(Upload_Video.this, getString(R.string.conn_msg2),
										getString(R.string.conn_msg3), false);
							}


						}
					}
				});                
			}    

			//close the streams //
			fileInputStream.close();
			dos.flush();
			dos.close();

		} catch (MalformedURLException ex) {  
			dialog.dismiss();  
			ex.printStackTrace();
			Toast.makeText(Upload_Video.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
			Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
		} catch (Exception e) {
			dialog.dismiss();  
			e.printStackTrace();
			Toast.makeText(Upload_Video.this, "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
			Log.e("Upload file to server Exception", "Exception : " + e.getMessage(), e);  
		}
		dialog.dismiss();       
		return serverResponseCode;  
	} 

	public int gen() {
		Random r = new Random( System.currentTimeMillis() );
		return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
	}
	public  String formatFileSize(long size) {
		String hrSize = null;

		double b = size;
		double k = size/1024.0;
		double m = ((size/1024.0)/1024.0);
		double g = (((size/1024.0)/1024.0)/1024.0);
		double t = ((((size/1024.0)/1024.0)/1024.0)/1024.0);

		DecimalFormat dec = new DecimalFormat("0.00");

		if ( t>1 ) {
			hrSize = dec.format(t).concat(" TB");
		} else if ( g>1 ) {
			hrSize = dec.format(g).concat(" GB");
		} else if ( m>1 ) {
			hrSize = dec.format(m).concat(" MB");
		} else if ( k>1 ) {
			hrSize = dec.format(k).concat(" KB");
		} else {
			hrSize = dec.format(b).concat(" Bytes");
		}

		return hrSize;
	}


	public String getPathVideo(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	public int uploadFileVideo(String sourceFileUri) {
		final String upLoadServerUrivideo = Constant.UPLOAD_VIDEOFILE_URL;

		DataOutputStream dos = null;  
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024; 
		File sourceFile = new File(sourceFileUri);
		Log.e("File Name", sourceFile.getName());
		Log.e("File Path", sourceFile.getParent());
		Log.e("File Size", ""+sourceFile.length());
		SizeImageV=formatFileSize(sourceFile.length());
		NewImagePathV=sourceFile.getName();

		if (!sourceFile.isFile()) {
			Log.e("uploadFile", "Source File Does not exist");
			dialogvi.dismiss();
			return 0;
		}
		try { // open a URL connection to the Servlet

			FileInputStream fileInputStream = new FileInputStream(sourceFile);
			URL url = new URL(upLoadServerUrivideo);
			conn = (HttpURLConnection) url.openConnection(); // Open a HTTP  connection to  the URL
			conn.setDoInput(true); // Allow Inputs
			conn.setDoOutput(true); // Allow Outputs
			conn.setUseCaches(false); // Don't use a Cached Copy
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("ENCTYPE", "multipart/form-data");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			//*********filename first is php base filename
			conn.setRequestProperty("video", sourceFile.getAbsolutePath()); 

			dos = new DataOutputStream(conn.getOutputStream());

			dos.writeBytes(twoHyphens + boundary + lineEnd); 
			//********filename first is php base filename
			dos.writeBytes("Content-Disposition: form-data; name=\"video\";filename=\""+ sourceFile.getAbsolutePath() + "\"" + lineEnd);
			dos.writeBytes(lineEnd);

			bytesAvailable = fileInputStream.available(); // create a buffer of  maximum size

			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];

			// read file and write it into form...
			bytesRead = fileInputStream.read(buffer, 0, bufferSize);  

			while (bytesRead > 0) {
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);               
			}

			// send multipart form data necesssary after file data...
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			// Responses from the server (code and message)
			serverResponseCode = conn.getResponseCode();
			String serverResponseMessage = conn.getResponseMessage();

			Log.e("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
			if(serverResponseCode == 200){
				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(Upload_Video.this, "File Upload Complete.", Toast.LENGTH_SHORT).show();

						dialog = ProgressDialog.show(Upload_Video.this, "", "Uploading file...", true);

						new Thread(new Runnable() {
							public void run() {

								uploadFile(selectedImagePath);
								isuploadvideoimage=true;

							}
						}).start();  
					}
				});                
			}    

			//close the streams //
			fileInputStream.close();
			dos.flush();
			dos.close();

		} catch (MalformedURLException ex) {  
			dialogvi.dismiss();  
			ex.printStackTrace();
			Toast.makeText(Upload_Video.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
			Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
		} catch (Exception e) {
			dialogvi.dismiss();  
			e.printStackTrace();
			Toast.makeText(Upload_Video.this, "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
			Log.e("Upload file to server Exception", "Exception : " + e.getMessage(), e);  
		}
		dialogvi.dismiss();       
		return serverResponseCode;  
	} 

	public int genvideo() {
		Random r = new Random( System.currentTimeMillis() );
		return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
	}
	public  String formatFileSizevideo(long size) {
		String hrSize = null;

		double b = size;
		double k = size/1024.0;
		double m = ((size/1024.0)/1024.0);
		double g = (((size/1024.0)/1024.0)/1024.0);
		double t = ((((size/1024.0)/1024.0)/1024.0)/1024.0);

		DecimalFormat dec = new DecimalFormat("0.00");

		if ( t>1 ) {
			hrSize = dec.format(t).concat(" TB");
		} else if ( g>1 ) {
			hrSize = dec.format(g).concat(" GB");
		} else if ( m>1 ) {
			hrSize = dec.format(m).concat(" MB");
		} else if ( k>1 ) {
			hrSize = dec.format(k).concat(" KB");
		} else {
			hrSize = dec.format(b).concat(" Bytes");
		}

		return hrSize;
	}


	private	class MyTaskPost extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(Upload_Video.this);
			pDialog.setMessage(getString(R.string.loading));
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			return JsonUtils.getJSONString(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}

			if (null == result || result.length() == 0) {
				showToast(getString(R.string.conn_msg4));
				alert.showAlertDialog(Upload_Video.this, getString(R.string.conn_msg4),
						getString(R.string.conn_msg5), false);

			} else {

				Toast.makeText(getApplicationContext(), "Successfully Video Added", Toast.LENGTH_SHORT).show();
				Intent intentuploadv=new Intent(getApplicationContext(),MainActivitySlider.class);
				intentuploadv.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intentuploadv);
			}

		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{       
		switch (menuItem.getItemId()) 
		{
		case android.R.id.home: 
			onBackPressed();
			break;

		default:
			return super.onOptionsItemSelected(menuItem);
		}
		return true;
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}



}
