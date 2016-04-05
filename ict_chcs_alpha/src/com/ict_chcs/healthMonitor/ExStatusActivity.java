package com.ict_chcs.healthMonitor;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ict_chcs.ex_server_test.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ExStatusActivity extends Activity {

	public static final String TAG = "ExStatusActivity";

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
		setContentView(R.layout.activity_ex_status);

		findViewById(R.id.btn_es_commit).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				/*
				 * String txtID = ((EditText)
				 * findViewById(R.id.edt_id)).getText().toString(); String txtPW
				 * = ((EditText)
				 * findViewById(R.id.edt_pw)).getText().toString(); String
				 * txtName = ((EditText)
				 * findViewById(R.id.edt_name)).getText().toString(); String
				 * txtAge = ((EditText)
				 * findViewById(R.id.edt_age)).getText().toString(); String
				 * txtGender = ((EditText)
				 * findViewById(R.id.edt_gender)).getText().toString(); String
				 * txtWeight = ((EditText)
				 * findViewById(R.id.edt_weight)).getText().toString();
				 * 
				 * String sendLogin = SERVER_URL + "ict_chcs_setuser.php?" +
				 * "id=" + txtID + "&password=" + txtPW + "&name=" + txtName +
				 * "&age=" + txtAge + "&gender=" + txtGender + "&weight=" +
				 * txtWeight; Toast.makeText(ExStatusActivity.this, sendLogin,
				 * Toast.LENGTH_SHORT).show();
				 * 
				 * task_insert.execute(sendLogin);
				 */

				msgbox("commit!!");
			}
		});

		findViewById(R.id.btn_es_cancel).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				/*
				 * String txtID = ((EditText)
				 * findViewById(R.id.edt_id)).getText().toString(); String txtPW
				 * = ((EditText)
				 * findViewById(R.id.edt_pw)).getText().toString(); String
				 * txtName = ((EditText)
				 * findViewById(R.id.edt_name)).getText().toString(); String
				 * txtAge = ((EditText)
				 * findViewById(R.id.edt_age)).getText().toString(); String
				 * txtGender = ((EditText)
				 * findViewById(R.id.edt_gender)).getText().toString(); String
				 * txtWeight = ((EditText)
				 * findViewById(R.id.edt_weight)).getText().toString();
				 * 
				 * String sendLogin = SERVER_URL + "ict_chcs_setuser.php?" +
				 * "id=" + txtID + "&password=" + txtPW + "&name=" + txtName +
				 * "&age=" + txtAge + "&gender=" + txtGender + "&weight=" +
				 * txtWeight; Toast.makeText(ExStatusActivity.this, sendLogin,
				 * Toast.LENGTH_SHORT).show();
				 * 
				 * task_insert.execute(sendLogin);
				 */

				//msgbox("cancel!!");
				
				// Login
				Intent intent = new Intent(ExStatusActivity.this, SignInActivity.class);
				startActivity(intent);
				finish();
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
