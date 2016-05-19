package com.ict_chcs.st;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import com.ict_chcs.healthMonitor.R;
import com.ict_chcs.st.Adapter.HCSAPI;

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
import android.widget.Toast;

public class SignUpActivity extends Activity {

	public static final String TAG = "SignUpActivity";

	public static String SERVER_URL = "http://192.168.0.168/";

	Handler handler = new Handler();

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);

		// task_insert = new phpInsert();

		findViewById(R.id.btn_su_create).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				ArrayList<String> mArrayList;
				StringBuilder mRetJson = new StringBuilder ();

				mArrayList = new ArrayList<String>();

				mArrayList.add(((EditText) findViewById(R.id.edt_su_id)).getText().toString());
				mArrayList.add(((EditText) findViewById(R.id.edt_su_pw)).getText().toString());
				mArrayList.add(((EditText) findViewById(R.id.edt_su_name)).getText().toString());
				mArrayList.add(((EditText) findViewById(R.id.edt_su_age)).getText().toString());
				mArrayList.add(((EditText) findViewById(R.id.edt_su_gender)).getText().toString());
				mArrayList.add(((EditText) findViewById(R.id.edt_su_weight)).getText().toString());

				if (Application.getHCSAPI().GetExUser(mArrayList, mRetJson) == true) {

						// Login
					Intent intent = new Intent(SignUpActivity.this, ExStatusActivity.class);
					startActivity(intent);
					finish();
				} else {
					msgbox("Please Sign up !!");
				}
			}
		});

		findViewById(R.id.btn_su_cancel).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				((EditText) findViewById(R.id.edt_su_id)).setText("");
				((EditText) findViewById(R.id.edt_su_pw)).setText("");
				((EditText) findViewById(R.id.edt_su_name)).setText("");
				((EditText) findViewById(R.id.edt_su_age)).setText("");
				((EditText) findViewById(R.id.edt_su_gender)).setText("");
				((EditText) findViewById(R.id.edt_su_weight)).setText("");

				msgbox("Canceled.");
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
