package com.ict_chcs.hm_t;

import java.util.ArrayList;

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
import android.widget.TextView;

public class SignInActivity extends Activity {

	public static final String TAG = "SignInActivity";

	Handler handler = new Handler();

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);
		Application.getHCSAPI().setContext(this);

		if (Utility.getBuildMode(this) > 0) {
			// DEBUG CODE
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					((TextView) findViewById(R.id.txtView_network_status))
							.setText(Utility.getWifiInfo(SignInActivity.this));
				}
			}).start();

			findViewById(R.id.btn_network_status).setOnClickListener(new View.OnClickListener() {

				@SuppressWarnings("static-access")
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent intent = new Intent(SignInActivity.this, ServerTestActivity.class);
					startActivity(intent);
				}
			});

		}

		findViewById(R.id.btn_si_signin).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				ArrayList<String> mArrayList;
				StringBuilder mRetJson = new StringBuilder();

				mArrayList = new ArrayList<String>();
				mArrayList.add(((EditText) findViewById(R.id.edt_si_id)).getText().toString());
				mArrayList.add(((EditText) findViewById(R.id.edt_si_pw)).getText().toString());

				if (Application.getHCSAPI().GetExUser(mArrayList, mRetJson) == true) {

					// Login
					Intent intent = new Intent(SignInActivity.this, ExStatusActivity.class);
					intent.putExtra("UserID", ((EditText) findViewById(R.id.edt_si_id)).getText().toString());
					intent.putExtra("Password", ((EditText) findViewById(R.id.edt_si_pw)).getText().toString());
					startActivity(intent);

				} else {
					Utility.msgbox(SignInActivity.this, "Please Sign up !!");
				}

			}
		});

		findViewById(R.id.btn_si_signup).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// Sign up
				Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
				intent.putExtra("UserID", ((EditText) findViewById(R.id.edt_si_id)).getText().toString());
				intent.putExtra("Password", ((EditText) findViewById(R.id.edt_si_pw)).getText().toString());
				startActivity(intent);
			}
		});

	}

	@Override
	protected void onNewIntent(Intent intent) {
		Log.d(TAG, "onNewIntent() called.");

		processIntent(intent);

		super.onNewIntent(intent);
	}

	private void processIntent(Intent intent) {
		String from = intent.getStringExtra("from");
		if (from == null) {
			Log.d(TAG, "from is null.");
			return;
		}

		String command = intent.getStringExtra("command");
		String type = intent.getStringExtra("type");
		String data = intent.getStringExtra("data");

		Utility.msgbox(SignInActivity.this, "DATA : " + command + ", " + type + ", " + data);
	}
}
