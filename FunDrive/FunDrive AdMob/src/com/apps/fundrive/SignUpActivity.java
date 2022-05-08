package com.apps.fundrive;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.pedant.SweetAlert.SweetAlertDialog.OnSweetClickListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.apps.fundrive.R;
import com.example.util.Constant;
import com.example.util.JsonUtils;
import com.google.analytics.tracking.android.EasyTracker;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NumberRule;
import com.mobsandgeeks.saripaar.annotation.NumberRule.NumberType;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;

public class SignUpActivity extends SherlockActivity implements ValidationListener{
	
	@Required(order = 1)
	@TextRule(order = 2, minLength = 5, maxLength = 35, trim = true, message = "Enter Valid Full Name")
	EditText edtFullName;

	@Required(order = 3)
	@Email(order = 4, message = "Please Check and Enter a valid Email Address")
	EditText edtEmail;

	@Required(order = 5)
	@Password(order = 6, message = "Enter a Valid Password")
	@TextRule(order = 7, minLength = 6, message = "Enter a Password Correctly")
	EditText edtPassword;
	
	 @Required(order = 8)
	 @ConfirmPassword(order = 9, message = "Repeat a Password Correctly")
	 EditText edtPasswordRepeat;

	@Required(order = 10)
	@NumberRule(order = 11, message = "Enter Phone Number in Numeric", type = NumberType.LONG)
	@TextRule(order = 12, message = "Enter valid Phone Number", minLength = 10, maxLength = 14)
	EditText edtContactNo;

	private Validator validator;

	Button btnsignup;

	String strFullname,strId,strEmail,strPassword,strContact,strMessage,strMacAddress;

	ProgressBar bar;
	ScrollView scrollview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

		edtFullName=(EditText)findViewById(R.id.etFullName);
		edtEmail=(EditText)findViewById(R.id.etEmail);
		edtPassword=(EditText)findViewById(R.id.etPass);
 		btnsignup=(Button)findViewById(R.id.btnSingUp);
		bar=(ProgressBar)findViewById(R.id.progressBar1);
		scrollview=(ScrollView)findViewById(R.id.scrollView1);
		setTitle("Sign Up");
		final EditText input = new EditText(SignUpActivity.this);
		input.setTextColor(getResources().getColor(R.color.color));
		
		btnsignup.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				validator.validateAsync();
			}
		});

		validator = new Validator(this);
		validator.setValidationListener(this);
	}

	@Override
	public void onValidationSucceeded() {
		// TODO Auto-generated method stub
		strFullname=edtFullName.getText().toString().replace(" ", "%20");
		strEmail=edtEmail.getText().toString();
		strPassword=edtPassword.getText().toString();
 
		
		if (JsonUtils.isNetworkAvailable(SignUpActivity.this)) {
			new MyTaskRegister().execute(Constant.REGISTER_URL+"&fullname="+strFullname+"&email="+strEmail+"&password="+strPassword);
			Log.e("reg", ""+Constant.REGISTER_URL+"&fullname="+strFullname+"&email="+strEmail+"&password="+strPassword);
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
	
	private	class MyTaskRegister extends AsyncTask<String, Void, String> {


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			bar.setVisibility(View.VISIBLE);
			scrollview.setVisibility(View.INVISIBLE);
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
				showToast(getString(R.string.nodata));
			} else {

				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(Constant.USER_REG_ARRAY);
					JSONObject objJson = null;
					for (int i = 0; i < jsonArray.length(); i++) {
						objJson = jsonArray.getJSONObject(i);
						strMessage=objJson.getString(Constant.USER_REG_MSG);
						Constant.GET_SUCCESS_MSG=objJson.getInt(Constant.USER_REG_SUCESS);
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
			scrollview.setVisibility(View.VISIBLE);
			edtEmail.setText("");
			edtEmail.requestFocus();
		}
		else
		{
			new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.SUCCESS_TYPE)
			.setTitleText("Good job")
			.setContentText(strMessage)
			.setConfirmClickListener(new OnSweetClickListener() {

				@Override
				public void onClick(SweetAlertDialog sweetAlertDialog) {
					// TODO Auto-generated method stub
					sweetAlertDialog.dismiss();
					Intent up_wall=new Intent(getApplicationContext(),SignInActivity.class);
					startActivity(up_wall);
					finish();
					
				}
			})
			.show();
		}
	}
	
	public void showToast(String msg) {
		Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_LONG).show();
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
