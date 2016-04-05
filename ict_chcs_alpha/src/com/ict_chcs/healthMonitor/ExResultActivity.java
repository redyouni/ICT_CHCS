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

public class ExResultActivity extends Activity {

	public phpDown task;
	public phpInsert task_insert;
	public static final String TAG = "MainActivity";

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
		setContentView(R.layout.activity_ex_result);

		task_insert = new phpInsert();

		findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String txtID = ((EditText) findViewById(R.id.edt_id)).getText().toString();
				String txtPW = ((EditText) findViewById(R.id.edt_pw)).getText().toString();
				String txtName = ((EditText) findViewById(R.id.edt_name)).getText().toString();
				String txtAge = ((EditText) findViewById(R.id.edt_age)).getText().toString();
				String txtGender = ((EditText) findViewById(R.id.edt_gender)).getText().toString();
				String txtWeight = ((EditText) findViewById(R.id.edt_weight)).getText().toString();

				String sendLogin = SERVER_URL + "ict_chcs_setuser.php?" + "id=" + txtID + "&password=" + txtPW
						+ "&name=" + txtName + "&age=" + txtAge + "&gender=" + txtGender + "&weight=" + txtWeight;
				Toast.makeText(ExResultActivity.this, sendLogin, Toast.LENGTH_SHORT).show();

				task_insert.execute(sendLogin);
			}
		});

	}

	private void println(String msg) {
		final String output = msg;
		handler.post(new Runnable() {
			public void run() {
				Log.d(TAG, output);
				Toast.makeText(getApplicationContext(), output, Toast.LENGTH_LONG).show();
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

		println("DATA : " + command + ", " + type + ", " + data);
	}

	private class phpDown extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... urls) {
			StringBuilder jsonHtml = new StringBuilder();
			try {
				URL url = new URL(urls[0]);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				if (conn != null) {
					conn.setConnectTimeout(10000);
					conn.setUseCaches(false);
					if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
						BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
						for (;;) {
							// 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
							String line = br.readLine();
							if (line == null)
								break;
							// 저장된 텍스트 라인을 jsonHtml에 붙여넣음
							jsonHtml.append(line + "\n");
						}
						br.close();
					}
					conn.disconnect();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return jsonHtml.toString();

		}

		protected void onPostExecute(String str) {
			String id;

			try {
				JSONObject root = new JSONObject(str);
				JSONArray ja = root.getJSONArray("results"); // get the
																// JSONArray
																// which I made
																// in the php
																// file. the
																// name of
																// JSONArray is
																// "results"

				for (int i = 0; i < ja.length(); i++) {
					JSONObject jo = ja.getJSONObject(i);
					id = jo.getString("id");
					// listitem.add(new ListItem(id));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			// txtView.setText("id : "+listitem.get(0).getData(0));
		}

	}

	private class phpInsert extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... urls) {
			StringBuilder resultText = new StringBuilder();
			try {
				URL url = new URL(urls[0]);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				
				if (conn != null) {
					conn.setConnectTimeout(10000);
					conn.setUseCaches(false);
					
					if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
						BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
						for (;;) {
							// 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
							String line = br.readLine();
							if (line == null)
								break;
							// 저장된 텍스트 라인을 jsonHtml에 붙여넣음
							resultText.append(line);
						}
						br.close();
					} else {
						InputStream is = conn.getErrorStream();
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						byte[] byteBuffer = new byte[1024];
						byte[] byteData = null;
						int nLength = 0;
						while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
							baos.write(byteBuffer, 0, nLength);
						}
						byteData = baos.toByteArray();
						String response = new String(byteData);
						Log.d(TAG, "response = " + response);
					}
					conn.disconnect();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return resultText.toString();

		}

		protected void onPostExecute(String str) {
			if (str.equals("1")) {
				Toast.makeText(getApplicationContext(), "DB Insert Complete.", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(), "DB Insert Failed.", Toast.LENGTH_LONG).show();
			}
		}
	}

}
