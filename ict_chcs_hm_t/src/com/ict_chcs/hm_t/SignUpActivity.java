package com.ict_chcs.hm_t;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ict_chcs.hm_t.Adapter.Utility;
import com.ict_chcs.hm_t.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

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

		findViewById(R.id.btn_su_create).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (((EditText) findViewById(R.id.edt_su_id)).getText().toString().equalsIgnoreCase("")) {
					Utility.msgbox(SignUpActivity.this, "Please insert a user id.");
					return;
				}
				if (((EditText) findViewById(R.id.edt_su_pw)).getText().toString().equalsIgnoreCase("")) {
					Utility.msgbox(SignUpActivity.this, "Please insert a password.");
					return;
				}
				if (((EditText) findViewById(R.id.edt_su_name)).getText().toString().equalsIgnoreCase("")) {
					Utility.msgbox(SignUpActivity.this, "Please insert a name.");
					return;
				}
				if (((EditText) findViewById(R.id.edt_su_age)).getText().toString().equalsIgnoreCase("")) {
					Utility.msgbox(SignUpActivity.this, "Please insert a age.");
					return;
				}
				if (((EditText) findViewById(R.id.edt_su_weight)).getText().toString().equalsIgnoreCase("")) {
					Utility.msgbox(SignUpActivity.this, "Please insert a weight.");
					return;
				}
				if (((EditText) findViewById(R.id.edt_su_rfid)).getText().toString().equalsIgnoreCase("")) {
					Utility.msgbox(SignUpActivity.this, "Please insert a RFID.");
					return;
				}

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

					Utility.msgbox(SignUpActivity.this, "User Created. Please wait ...");

					new Handler() {
						public void handleMessage(Message msg) {
							// Login
							Intent intent = new Intent(SignUpActivity.this, ExStatusActivity.class);
							intent.putExtra("UserID", ((EditText) findViewById(R.id.edt_su_id)).getText().toString());
							intent.putExtra("Password", ((EditText) findViewById(R.id.edt_su_pw)).getText().toString());
							startActivity(intent);
							finish();
						}
					}.sendEmptyMessageDelayed(0, 1000);
					return;
				}

				Utility.msgbox(SignUpActivity.this, "Network or Server disconnected !!");
			}
		});

		findViewById(R.id.btn_su_cancel).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

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

		intent = getIntent();
		if (intent != null) {
			UserID = intent.getStringExtra("UserID");
			Password = intent.getStringExtra("Password");
		}

		new Handler() {
			public void handleMessage(Message msg) {
				// Login
				try {

					if (!(UserID.equalsIgnoreCase("") || Password.equalsIgnoreCase(""))) {
						ArrayList<String> mArrayList;
						StringBuilder mRetJson = new StringBuilder();

						mArrayList = new ArrayList<String>();
						mArrayList.add(UserID);
						mArrayList.add(Password);

						if (Application.getHCSAPI().GetExUser(mArrayList, mRetJson) == true) {

							JSONObject root;
							root = new JSONObject(mRetJson.toString());
							JSONArray results = root.getJSONArray("results");

							if (results.length() > 0) {
								JSONObject resultInfo = results.getJSONObject(0);

								((EditText) findViewById(R.id.edt_su_id)).setText(resultInfo.getString("id"));
								((EditText) findViewById(R.id.edt_su_pw)).setText(resultInfo.getString("password"));
								((EditText) findViewById(R.id.edt_su_name)).setText(resultInfo.getString("name"));
								((EditText) findViewById(R.id.edt_su_age)).setText(resultInfo.getString("age"));
								((EditText) findViewById(R.id.edt_su_weight)).setText(resultInfo.getString("weight"));
								((EditText) findViewById(R.id.edt_su_rfid)).setText(resultInfo.getString("rfid"));
								if (resultInfo.getString("gender").equalsIgnoreCase("MALE")) {
									((RadioGroup) findViewById(R.id.radioGro_su_gender)).check(R.id.radio_su_male);
								} else {
									((RadioGroup) findViewById(R.id.radioGro_su_gender)).check(R.id.radio_su_female);
								}
								return;
							}
						}

						//Utility.msgbox(SignUpActivity.this, "Network or Server disconnected !!");
					}

					((EditText) findViewById(R.id.edt_su_id)).setText(UserID);
					((EditText) findViewById(R.id.edt_su_pw)).setText(Password);

					if (((EditText) findViewById(R.id.edt_su_id)).getText().toString().length() <= 0) {
						((EditText) findViewById(R.id.edt_su_id)).requestFocus();
					} else if (((EditText) findViewById(R.id.edt_su_pw)).getText().toString().length() <= 0) {
						((EditText) findViewById(R.id.edt_su_pw)).requestFocus();
					} else if (((EditText) findViewById(R.id.edt_su_name)).getText().toString().length() <= 0) {
						((EditText) findViewById(R.id.edt_su_name)).requestFocus();
					} else if (((EditText) findViewById(R.id.edt_su_age)).getText().toString().length() <= 0) {
						((EditText) findViewById(R.id.edt_su_age)).requestFocus();
					} else if (((EditText) findViewById(R.id.edt_su_weight)).getText().toString().length() <= 0) {
						((EditText) findViewById(R.id.edt_su_weight)).requestFocus();
					} else if (((EditText) findViewById(R.id.edt_su_rfid)).getText().toString().length() <= 0) {
						((EditText) findViewById(R.id.edt_su_rfid)).requestFocus();
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.sendEmptyMessage(0);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		/*
		 * if (findViewById(R.id.common_include_view_health_info_graph).
		 * getVisibility() == View.VISIBLE) {
		 * findViewById(R.id.common_include_view_health_info_graph).
		 * setVisibility(View.GONE); return; }
		 */
		super.onBackPressed();
	}
}
