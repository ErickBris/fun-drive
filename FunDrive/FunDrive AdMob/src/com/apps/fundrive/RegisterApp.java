package com.apps.fundrive;
 
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import com.example.util.Constant;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
 
 
public class RegisterApp extends AsyncTask<Void, Void, String> {
 
 private static final String TAG = "GCMRelated";
 Context ctx;
 GoogleCloudMessaging gcm;
 String SENDER_ID = Constant.GCM_SENDER_ID;
 String regid = null; 
 private int appVersion;
 public RegisterApp(Context ctx, GoogleCloudMessaging gcm, int appVersion){
  this.ctx = ctx;
  this.gcm = gcm;
  this.appVersion = appVersion;
 }
  
  
 @Override
 protected void onPreExecute() {
  super.onPreExecute();
 }
 
 
 @Override
 protected String doInBackground(Void... arg0) {
  String msg = "";
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(ctx);
            }
            regid = gcm.register(SENDER_ID);
            msg = "Device registered, registration ID=" + regid;
 
            // You should send the registration ID to your server over HTTP,
            // so it can use GCM/HTTP or CCS to send messages to your app.
            // The request to your server should be authenticated if your app
            // is using accounts.
            sendRegistrationIdToBackend();
 
            // For this demo: we don't need to send it because the device
            // will send upstream messages to a server that echo back the
            // message using the 'from' address in the message.
 
            // Persist the regID - no need to register again.
            storeRegistrationId(ctx, regid);
        } catch (IOException ex) {
            msg = "Error :" + ex.getMessage();
            // If there is an error, don't just keep trying to register.
            // Require the user to click a button again, or perform
            // exponential back-off.
        }
        return msg;
 }
 
 private void storeRegistrationId(Context ctx, String regid) {
  final SharedPreferences prefs = ctx.getSharedPreferences(WallMainActivity.class.getSimpleName(),
             Context.MODE_PRIVATE);
     Log.i(TAG, "Saving regId on app version " + appVersion);
     SharedPreferences.Editor editor = prefs.edit();
     editor.putString("registration_id", regid);
     editor.putInt("appVersion", appVersion);
     editor.commit();
   
 }
 
 
 private void sendRegistrationIdToBackend() {
  URI url = null;
  try {
   url = new URI(Constant.APP_GCM_URL + regid);
  } catch (URISyntaxException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  } 
  HttpClient httpclient = new DefaultHttpClient();
  HttpGet request = new HttpGet();
  request.setURI(url);
  try {
   httpclient.execute(request);
  } catch (ClientProtocolException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  } catch (IOException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
 }
 
 
 @Override
 protected void onPostExecute(String result) {
  super.onPostExecute(result);
  Log.i(TAG, "Registration Completed. Now you can see the notifications");
  Log.v(TAG, result);
 }
 
}