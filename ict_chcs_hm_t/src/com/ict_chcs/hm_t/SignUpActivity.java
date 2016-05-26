package com.ict_chcs.hm_t;

import java.util.ArrayList;

import com.ict_chcs.healthMonitor.R;
import com.ict_chcs.hm_t.Adapter.Utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class SignUpActivity extends Activity {

	public static final String TAG = "SignUpActivity";

	Handler handler = new Handler();
	String gender = "MALE";
	String UserID = null;
	String Password = null;
	Intent intent = null;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		Application.getHCSAPI().setContext(this);
		
		if (Utility.getBuildMode(this) > 0) {
			// DEBUG CODE
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					((TextView) findViewById(R.id.txtView_network_status))
							.setText(Utility.getWifiInfo(SignUpActivity.this));
				}
			}).start();
		}
		
		intent = getIntent();
		if (intent != null) {
			UserID = intent.getStringExtra("UserID");
			Password = intent.getStringExtra("Password");
			((EditText) findViewById(R.id.edt_su_id)).setText(UserID);
			((EditText) findViewById(R.id.edt_su_pw)).setText(Password);
		}

		if (((EditText) findViewById(R.id.edt_su_id)).getText().toString().length() <= 0) {
			((EditText) findViewById(R.id.edt_su_id)).requestFocus();
		}
		else if (((EditText) findViewById(R.id.edt_su_pw)).getText().toString().length() <= 0) {
			((EditText) findViewById(R.id.edt_su_pw)).requestFocus();
		}
		else {
			((EditText) findViewById(R.id.edt_su_name)).requestFocus();
		}
		
		findViewById(R.id.btn_su_create).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				ArrayList<String> mArrayList;
				StringBuilder mRetJson = new StringBuilder();

				mArrayList = new ArrayList<String>();

				mArrayList.add(((EditText) findViewById(R.id.edt_su_id)).getText().toString());
				mArrayList.add(((EditText) findViewById(R.id.edt_su_pw)).getText().toString());
				mArrayList.add(((EditText) findViewById(R.id.edt_su_name)).getText().toString());
				mArrayList.add(gender);
				mArrayList.add(((EditText) findViewById(R.id.edt_su_age)).getText().toString());
				mArrayList.add(((EditText) findViewById(R.id.edt_su_weight)).getText().toString());
				mArrayList.add(((EditText) findViewById(R.id.edt_su_rfid)).getText().toString());

				if (Application.getHCSAPI().SetExUser(mArrayList, mRetJson) == true) {

					// Login
					intent = new Intent(SignUpActivity.this, ExStatusActivity.class);
					startActivity(intent);
					finish();
				} else {
					msgbox("Network or Server disconnected !!");
				}
			}
		});

		findViewById(R.id.btn_su_cancel).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				/*
				 * ((EditText) findViewById(R.id.edt_su_id)).setText("");
				 * ((EditText) findViewById(R.id.edt_su_pw)).setText("");
				 * ((EditText) findViewById(R.id.edt_su_name)).setText("");
				 * ((EditText) findViewById(R.id.edt_su_age)).setText("");
				 * ((RadioGroup)
				 * findViewById(R.id.radioGro_su_gender)).check(R.id.
				 * radio_su_male); ((EditText)
				 * findViewById(R.id.edt_su_weight)).setText("");
				 * msgbox("Canceled.");
				 */

				Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
				startActivity(intent);
				finish();
			}
		});

		((RadioGroup) findViewById(R.id.radioGro_su_gender)).setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.radio_su_male:
					gender = "MALE";
					break;
				case R.id.radio_su_female:
					gender = "FEMALE";
					break;
				}
			}
		});

	}

	private void msgbox(String msg) {
		final String output = msg;
		handler.post(new Runnable() {
			public void run() {
				Log.d(TAG, output);
				Toast.makeText(getApplicationContext(), output, Toast.LENGTH_LONG).show();
			}
		});
	}

}
