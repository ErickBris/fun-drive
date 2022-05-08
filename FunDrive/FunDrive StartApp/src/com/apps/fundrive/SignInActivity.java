package com.apps.fundrive;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.pedant.SweetAlert.SweetAlertDialog;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.apps.fundrive.R;
import com.example.util.Constant;
import com.example.util.JsonUtils;
import com.example.util.SessionManager;
import com.google.analytics.tracking.android.EasyTracker;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;

public class SignInActivity extends SherlockActivity implements ValidationListener{
	
	
	@Required(order = 1)
	@Email(order = 2, message = "Please Check and Enter a valid Email Address")
	EditText edtEmail;

	@Required(order = 3)
	@Password(order = 4, message = "Enter a Valid Password")
	@TextRule(order = 5, minLength = 6, message = "Enter a Password Correctly")
	EditText edtPassword;

	private Validator validator; 

	Button btnsignin;

	String strEmail,strPassword,strMessage,strName,strPassengerId,strMacAddress;

	ProgressBar bar;
	LinearLayout layout;

	TextView txtforgot;
	
	CheckBox login_tgb_on_off; 
	
	private static final String PREFS_NAME = "MyPrefsFile";
	private static  String PREF_USERNAME = "username";
	private static String PREF_PASSWORD = "password";
	SessionManager session;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);
 		 
		session = new SessionManager(getApplicationContext());
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
 		setTitle("Please Login");
 		final EditText input = new EditText(SignInActivity.this);
		input.setTextColor(getResources().getColor(R.color.color));
		edtEmail=(EditText)findViewById(R.id.etUserName);
		edtPassword=(EditText)findViewById(R.id.etPass);
		btnsignin=(Button)findViewById(R.id.btnSingIn);
		bar=(ProgressBar)findViewById(R.id.progressBar1);
		layout=(LinearLayout)findViewById(R.id.view);
		txtforgot=(TextView)findViewById(R.id.txt_forgot);
		login_tgb_on_off= (CheckBox) findViewById(R.id.toggle_on_off);
		 
		 if(login_tgb_on_off.getText()!=null)
	        {
	        	SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);   
	       		final String username1 = pref.getString(PREF_USERNAME, null);
	       		final String password1 = pref.getString(PREF_PASSWORD, null);
	       		
	       		if (username1 != null || password1 != null) 
	      		{
	       			edtEmail.setText(username1);
	       			edtPassword.setText(password1);
	      			login_tgb_on_off.setChecked(true);
	      			
	      		}
	        }
	         

		btnsignin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				validator.validate();
			}
		});

		txtforgot.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intforgot=new Intent(SignInActivity.this,ForgotPassActivity.class);
				startActivity(intforgot);
			}
		});


		validator = new Validator(this);
		validator.setValidationListener(this);
		
	}

	@Override
	public void onValidationSucceeded() {
		// TODO Auto-generated method stub
		strEmail=edtEmail.getText().toString();
		strPassword=edtPassword.getText().toString();
		
		if(login_tgb_on_off.isChecked()==true)
		{
			getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
	        .edit()
	        .putString(PREF_USERNAME,strEmail )
	        .putString(PREF_PASSWORD,strPassword)
	        .commit();
		}
		if(login_tgb_on_off.isChecked()==false)
		{
			getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
	        .edit()
	        .putString(PREF_USERNAME,null)
	        .putString(PREF_PASSWORD, null)
	        .commit();
		
		}	
		
		if (JsonUtils.isNetworkAvailable(SignInActivity.this)) {
		 	new MyTaskLogin().execute(Constant.LOGIN_URL+"&email="+strEmail+"&password="+strPassword);

		} else {
			setSweetDialog(SweetAlertDialog.ERROR_TYPE, getString(R.string.conn_msg4), getString(R.string.conn_msg2));
		}
	}

	@Override
	public void onValidationFailed(View failedView, Rule<?> failedRule) {
		// TODO Auto-generated method stub
		String message = failedRule.getFailureMessage();
		if (failedView instanceof EditText) {
			failedView.requestFocus();
			((EditText) failedView).setError(message);
		} else {
			Toast.makeText(this, "Record Not Saved", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	private	class MyTaskLogin extends AsyncTask<String, Void, String> {


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			bar.setVisibility(View.VISIBLE);
			layout.setVisibility(View.INVISIBLE);
		}

		@Override
		protected String doInBackground(String... params) {
			return JsonUtils.getJSONString(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			bar.setVisibility(View.GONE);

			if (null == result || result.length() == 0) {
				setSweetDialog(SweetAlertDialog.ERROR_TYPE, getString(R.string.conn_msg1), getString(R.string.nodata));

			} else {

				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(Constant.USER_LOGIN_ARRAY);
					JSONObject objJson = null;
					for (int i = 0; i < jsonArray.length(); i++) {
						objJson = jsonArray.getJSONObject(i);
						if(objJson.has(Constant.USER_LOGIN_MSG))
						{
							strMessage=objJson.getString(Constant.USER_LOGIN_MSG);
							Constant.GET_SUCCESS_MSG=objJson.getInt(Constant.USER_LOGIN_SUCESS);		
						}
						else
						{
							Constant.GET_SUCCESS_MSG=objJson.getInt(Constant.USER_LOGIN_SUCESS);
							strName=objJson.getString(Constant.USER_LOGIN_NAME);
							strPassengerId=objJson.getString(Constant.USER_LOGIN_ID);
						}
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
				setResult();
			}

		}
	}

	public void setResult() {

		if(Constant.GET_SUCCESS_MSG==0)
		{
			setSweetDialog(SweetAlertDialog.ERROR_TYPE, "Opps.", strMessage);
			layout.setVisibility(View.VISIBLE);
		}
		else
		{
			session.createLoginSession(strName, strEmail,strPassengerId);
			if(Constant.LOGIN_FORM.equals("Upload"))
			{
				Intent up_wall=new Intent(getApplicationContext(),Upload.class);
				startActivity(up_wall);	
				finish();
			}
			else if(Constant.LOGIN_FORM.equals("Main"))
			{
				Intent up_wall=new Intent(getApplicationContext(),WallMainActivity.class);
				startActivity(up_wall);	
				finish();
			}
			else 
			{
				Intent up_wall=new Intent(getApplicationContext(),WallMainActivity.class);
				startActivity(up_wall);	
				finish();
			}
		}
	}
	
	public void setSweetDialog(int code,String title,String message)
	{
		new SweetAlertDialog(this,code)
		.setTitleText(title)
		.setContentText(message)
		.show();
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
