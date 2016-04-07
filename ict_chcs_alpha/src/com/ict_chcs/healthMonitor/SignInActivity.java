package com.ict_chcs.healthMonitor;

import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import com.ict_chcs.healthMonitor.Adapter.Php;
import com.ict_chcs.healthMonitor.Adapter.Php.Down;
import com.ict_chcs.healthMonitor.Adapter.Php.Insert;

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

public class SignInActivity extends Activity {

	public Down phpDown;
	public Insert phpInsert;
	public static final String TAG = "SignInActivity";

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
		setContentView(R.layout.activity_signin);

		// phpDown = new Php.Down();

		findViewById(R.id.btn_si_signin).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String txtID = ((EditText) findViewById(R.id.edt_si_id)).getText().toString();
				String txtPW = ((EditText) findViewById(R.id.edt_si_pw)).getText().toString();

				String sendLogin = SERVER_URL + "ict_chcs_getuser.php?" + "id=" + txtID + "&password=" + txtPW;
				// Toast.makeText(SignInActivity.this, sendLogin,
				// Toast.LENGTH_SHORT).show();
				String result = null;

				try {
					result = new Php.Down().execute(sendLogin).get();
					// Toast.makeText(SignInActivity.this, result,
					// Toast.LENGTH_SHORT).show();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ExecutionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {

					JSONObject root = new JSONObject(result);
					String num_results = root.getString("num_results");

					if (Integer.parseInt(num_results) == 1) {

						// Login
						Intent intent = new Intent(SignInActivity.this, ExStatusActivity.class);
						startActivity(intent);
						finish();
					} else {
						msgbox("Please Sign up !!");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		findViewById(R.id.btn_si_signup).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// Sign up
				Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
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

		msgbox("DATA : " + command + ", " + type + ", " + data);
	}
}
