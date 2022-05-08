package com.apps.fundrive;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import cn.pedant.SweetAlert.SweetAlertDialog;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.apps.fundrive.R;
import com.example.util.Constant;
import com.example.util.JsonUtils;
import com.google.analytics.tracking.android.EasyTracker;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;

public class Activity_Feedback extends SherlockActivity implements ValidationListener{

	@Required(order = 1)
	@TextRule(order = 2, minLength = 3, message = "Enter a Name Correctly")
	EditText edtName;

	@Required(order = 3)
	@Email(order = 4, message = "Please Check and Enter a valid Email Address")
	EditText edtEmail;

	@Required(order = 5)
	@TextRule(order = 6, minLength = 3, message = "Enter a Message Correctly")
	EditText edtMessage;
	Button btnFeedback;

	private Validator validator; 
	ProgressBar bar;
	LinearLayout layout;
	
	String strName,strEmail,strMsg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		setTitle("FeedBack");
 		
		edtName=(EditText)findViewById(R.id.etName);
		edtEmail=(EditText)findViewById(R.id.etEmail);
		edtMessage=(EditText)findViewById(R.id.etMsg);
		btnFeedback=(Button)findViewById(R.id.btnFeedback);
		bar=(ProgressBar)findViewById(R.id.progressBar1);
		layout=(LinearLayout)findViewById(R.id.view);

		final EditText input = new EditText(Activity_Feedback.this);
		input.setTextColor(getResources().getColor(R.color.color));
		btnFeedback.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				validator.validate();
			}
		});

		validator = new Validator(this);
		validator.setValidationListener(this);


	}

	@Override
	public void onValidationSucceeded() {
		// TODO Auto-generated method stub

		strEmail=edtEmail.getText().toString();
		strName=edtName.getText().toString();
		strMsg=edtMessage.getText().toString();
		
		if (JsonUtils.isNetworkAvailable(Activity_Feedback.this)) {
		 	new MyTaskFeedBack().execute(Constant.FEEDBACK_URL+strName+"&email="+strEmail+"&message="+strMsg);

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

	private	class MyTaskFeedBack extends AsyncTask<String, Void, String> {


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
						Constant.GET_SUCCESS_MSG=objJson.getInt(Constant.FEEDBACK_MSG);		

					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
				setResult();
			}

		}
	}

	public void setResult() {
		layout.setVisibility(View.VISIBLE);
		edtName.setText("");
		edtMessage.setText("");
		edtEmail.setText("");
		Toast.makeText(Activity_Feedback.this, "Your Feedback Sent Successfully.", Toast.LENGTH_SHORT).show();
		
		
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
