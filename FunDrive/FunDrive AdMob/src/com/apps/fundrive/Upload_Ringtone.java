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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.example.adapter.SongAdapter;
import com.example.item.Song;
import com.example.util.AlertDialogManager;
import com.example.util.Constant;
import com.example.util.JsonUtils;
import com.example.util.SessionManager;
import com.google.analytics.tracking.android.EasyTracker;

public class Upload_Ringtone extends SherlockActivity{
	
	AlertDialogManager alert = new AlertDialogManager();
	Spinner spincategory;
	ArrayList<String> category,categoryid;
	EditText edttagname,edttitle;
	Button btn_submit,btn_rimgchoose;
	private ProgressDialog dialog = null;
	private int serverResponseCode = 0;
	HttpURLConnection conn  ;
	String NewImagePath,SizeImage,NewImagePathR;
	File files[];
	ListView listview;
	private ArrayList myList;
	SongAdapter adapter;
	SessionManager session;
	HashMap<String, String> user;
	ImageView imgrshow;
	private static final int SELECT_PICTURE = 1;
	private String selectedImagePathRing;
	boolean isupload=false;
	boolean isuploadimage=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_ringtone);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		setTitle("Upload Ringtone");
		category=new ArrayList<String>();
		categoryid=new ArrayList<String>();
		spincategory=(Spinner)findViewById(R.id.spinner_selectcate);
		edttagname=(EditText)findViewById(R.id.editText2);
		edttitle=(EditText)findViewById(R.id.editText1);
		btn_submit=(Button)findViewById(R.id.button_submit);
		listview = (ListView)findViewById(R.id.listView1);
		imgrshow=(ImageView)findViewById(R.id.imageView_chooserimgshow);
		btn_rimgchoose=(Button)findViewById(R.id.button_chooseringimg);
		final EditText input = new EditText(Upload_Ringtone.this);
		input.setTextColor(getResources().getColor(R.color.color));
		session = new SessionManager(getApplicationContext());
		user = session.getUserDetails();
		Constant.LOGIN_ID=user.get(SessionManager.KEY_PASSENGER_ID);
		
		myList = new ArrayList();
		String	obj = MimeTypeMap.getSingleton().getMimeTypeFromExtension("mp3");
		Cursor 	mCursor = getContentResolver().query(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, "mime_type=?", new String[] {
				obj
		}, null);

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
				Log.e("path :", (new StringBuilder()).append(s3).toString());
				myList.add(new Song(l1, s, s1, s2, s3));
			} while (mCursor.moveToNext());
		} else
		{
			Toast.makeText(this, "No mp3 file found", Toast.LENGTH_SHORT).show();
		}
		adapter = new SongAdapter(this, myList);
		listview.setAdapter(adapter);
		
		listview.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                case MotionEvent.ACTION_DOWN:
                    // Disallow ScrollView to intercept touch events.
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    break;

                case MotionEvent.ACTION_UP:
                    // Allow ScrollView to intercept touch events.
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });
		
		btn_rimgchoose.setOnClickListener(new OnClickListener() {

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
		
 
		
		if (JsonUtils.isNetworkAvailable(Upload_Ringtone.this)) {
			new MyTask().execute(Constant.RINGCATEGORY_URL);
		} else {
			showToast(getString(R.string.conn_msg1));
			alert.showAlertDialog(Upload_Ringtone.this, getString(R.string.conn_msg2),
					getString(R.string.conn_msg3), false);
		}
		
		
		btn_submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				dialog = ProgressDialog.show(Upload_Ringtone.this, "", "Uploading file...", true);

				new Thread(new Runnable() {
					public void run() {

						uploadFileR(selectedImagePathRing);
						isupload=true;

					}
				}).start();  
			}
		});

		
	}
	
	public int uploadFile(String sourceFileUri) {
		final String upLoadServerUri = Constant.UPLOAD_RINTONEFILE_URL;

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
	
		NewImagePath=sourceFile.getName();

		as = new String[1];
		as[0] = sourceFile.toString();

		MediaScannerConnection.scanFile(Upload_Ringtone.this, as, null, new android.media.MediaScannerConnection.OnScanCompletedListener() {
			public void onScanCompleted(String s1, Uri uri)
			{
			}

		});



		if (!sourceFile.isFile()) {
			Log.e("uploadFile", "Source File Does not exist");
			dialog.dismiss();
			return 0;
		}
		try { // open a URL connection to the Servlet

			FileInputStream fileInputStream = new FileInputStream(sourceFile);
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
			conn.setRequestProperty("uploaded_file", sourceFile.getAbsolutePath()); 

			dos = new DataOutputStream(conn.getOutputStream());

			dos.writeBytes(twoHyphens + boundary + lineEnd); 
			//********filename first is php base filename
			dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""+ sourceFile.getAbsolutePath() + "\"" + lineEnd);
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
						Toast.makeText(Upload_Ringtone.this, "File Upload Complete.", Toast.LENGTH_SHORT).show();
						int pos=spincategory.getSelectedItemPosition();
						String Categoryid=categoryid.get(pos).toString();
						String Tag=edttagname.getText().toString().replace(" ", "%20");
						String Name=edttitle.getText().toString().replace(" ", "%20");
						
						if (JsonUtils.isNetworkAvailable(Upload_Ringtone.this)) {
							new MyTaskPost().execute(Constant.ADD_RINGTONE_URL+Constant.LOGIN_ID+"&ringtone_path="+Constant.SERVER_RINGTONE_FOLDER+NewImagePath.replace(" ", "%20")+"&tag="+Tag+"&size="+SizeImage.replace(" ", "%20")+"&cid="+Categoryid+"&ringtone_name="+Name+"&image="+Constant.RING_IMAGE_FOLDER_PATH+NewImagePathR.replace(" ", "%20"));
							//Log.e("ringpath", ""+Constant.ADD_RINGTONE_URL+Constant.LOGIN_ID+"&ringtone_path="+Constant.SERVER_RINGTONE_FOLDER+NewImagePath.replace(" ", "%20")+"&tag="+Tag+"&size="+SizeImage.replace(" ", "%20")+"&cid="+Categoryid+"&ringtone_name="+Name+"&image="+Constant.RING_IMAGE_FOLDER_PATH+NewImagePathR.replace(" ", "%20"));
						} else {
							showToast(getString(R.string.conn_msg1));
							alert.showAlertDialog(Upload_Ringtone.this, getString(R.string.conn_msg2),
									getString(R.string.conn_msg3), false);
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
			Toast.makeText(Upload_Ringtone.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
			Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
		} catch (Exception e) {
			dialog.dismiss();  
			e.printStackTrace();
			Toast.makeText(Upload_Ringtone.this, "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
	
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				Uri selectedImageUri = data.getData();
				selectedImagePathRing = getPathR(selectedImageUri);
				System.out.println("Image Path : " + selectedImagePathRing);
				imgrshow.setVisibility(View.VISIBLE);
				imgrshow.setImageURI(selectedImageUri);
			}
		}
	}


	public String getPathR(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	public int uploadFileR(String sourceFileUri) {
		final String upLoadServerUri = Constant.UPLOAD_RINTONEIMG_URL;

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
 
		NewImagePathR=sourceFile.getName();

 


		if (!sourceFile.isFile()) {
			Log.e("uploadFile", "Source File Does not exist");
			dialog.dismiss();
			return 0;
		}
		try { // open a URL connection to the Servlet

			FileInputStream fileInputStream = new FileInputStream(sourceFile);
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
			conn.setRequestProperty("image", sourceFile.getAbsolutePath()); 

			dos = new DataOutputStream(conn.getOutputStream());

			dos.writeBytes(twoHyphens + boundary + lineEnd); 
			//********filename first is php base filename
			dos.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\""+ sourceFile.getAbsolutePath() + "\"" + lineEnd);
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
						Toast.makeText(Upload_Ringtone.this, "Image Upload Complete.", Toast.LENGTH_SHORT).show();
						
						final String item=adapter.getSelected();
						dialog = ProgressDialog.show(Upload_Ringtone.this, "", "Uploading file...", true);

						new Thread(new Runnable() {
							public void run() {
								uploadFile(item);
								isuploadimage=true;
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
			dialog.dismiss();  
			ex.printStackTrace();
			Toast.makeText(Upload_Ringtone.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
			Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
		} catch (Exception e) {
			dialog.dismiss();  
			e.printStackTrace();
			Toast.makeText(Upload_Ringtone.this, "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
			Log.e("Upload file to server Exception", "Exception : " + e.getMessage(), e);  
		}
		dialog.dismiss();       
		return serverResponseCode;  
	} 

	public int genr() {
		Random r = new Random( System.currentTimeMillis() );
		return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
	}
	public  String formatFileSizer(long size) {
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
	
	private	class MyTask extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(Upload_Ringtone.this);
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
				alert.showAlertDialog(Upload_Ringtone.this, getString(R.string.conn_msg4),
						getString(R.string.conn_msg5), false);

			} else {

				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(Constant.CATEGORY_ARRAY_NAME);
					JSONObject objJson = null;
					for (int i = 0; i < jsonArray.length(); i++) {
						objJson = jsonArray.getJSONObject(i);

						category.add(objJson.getString(Constant.CATERING_CATENAME));
						categoryid.add(objJson.getString(String.valueOf(Constant.CATERING_CATID)));
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
		Toast.makeText(Upload_Ringtone.this, msg, Toast.LENGTH_LONG).show();
	}
	
	private	class MyTaskPost extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(Upload_Ringtone.this);
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
				alert.showAlertDialog(Upload_Ringtone.this, getString(R.string.conn_msg4),
						getString(R.string.conn_msg5), false);

			} else {

				Toast.makeText(getApplicationContext(), "Successfully Ringtone Added", Toast.LENGTH_SHORT).show();
				Intent intentuploadr=new Intent(getApplicationContext(),MainActivitySlider.class);
				intentuploadr.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intentuploadr);
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
