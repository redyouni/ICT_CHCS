package com.ict_chcs.healthMonitor;

import java.util.ArrayList;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

public class ServerTestActivity extends Activity {

	public static final String TAG = "ServerTestActivity";

	Handler handler = new Handler();
	Random mRand;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server_test);
		mRand = new Random();
		((TextView) findViewById(R.id.btn_st_result)).setMovementMethod(new ScrollingMovementMethod());

		findViewById(R.id.btn_st_clear).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((TextView) findViewById(R.id.btn_st_result)).setText("");
			}
		});
		((TextView) findViewById(R.id.btn_st_result)).setMovementMethod(new ScrollingMovementMethod());

		findViewById(R.id.btn_st_getconnection).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				StringBuilder mRetJson = new StringBuilder ();
				Application.getHCSAPI().GetServerConnection(mRetJson);

				((TextView) findViewById(R.id.btn_st_result)).setText(mRetJson);

			}
		});
		findViewById(R.id.btn_st_getuser).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				ArrayList<String> mArrayList;
				StringBuilder mRetJson = new StringBuilder ();

				mArrayList = new ArrayList<String>();
				mArrayList.add("0");
				mArrayList.add("0");
				Application.getHCSAPI().GetExUser(mArrayList, mRetJson);

				((TextView) findViewById(R.id.btn_st_result)).setText(mRetJson);

			}
		});
		findViewById(R.id.btn_st_setuser).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				ArrayList<String> mArrayList;
				StringBuilder mRetJson = new StringBuilder ();

				mArrayList = new ArrayList<String>();
				mArrayList.add(getRandom(1000, 1));
				mArrayList.add(getRandom(1000, 1));
				mArrayList.add(getRandom(1000, 1));
				mArrayList.add(getRandom(1000, 1));
				mArrayList.add(getRandom(1000, 1));
				mArrayList.add(getRandom(1000, 1));				
				mArrayList.add(getRandom(1000, 1));

				Application.getHCSAPI().SetExUser(mArrayList, mRetJson);

				((TextView) findViewById(R.id.btn_st_result)).setText(mRetJson);

			}
		});
		findViewById(R.id.btn_st_deluser).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				StringBuilder mRetJson = new StringBuilder ();
				String id = "1";
				Boolean all = true;
				Application.getHCSAPI().DelExUser(id, all, mRetJson);

				((TextView) findViewById(R.id.btn_st_result)).setText(mRetJson);

			}
		});

		findViewById(R.id.btn_st_getexresult).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				ArrayList<String> mArrayList;
				StringBuilder mRetJson = new StringBuilder ();

				mArrayList = new ArrayList<String>();
				mArrayList.add("0");
				mArrayList.add("0");
				mArrayList.add("0");
				Application.getHCSAPI().GetExResult(mArrayList, mRetJson);

				((TextView) findViewById(R.id.btn_st_result)).setText(mRetJson);

			}
		});
		findViewById(R.id.btn_st_setexresult).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				ArrayList<String> mArrayList;
				StringBuilder mRetJson = new StringBuilder ();

				mArrayList = new ArrayList<String>();
				mArrayList.add(getRandom(1000, 1));
				mArrayList.add(getRandom(1000, 1));
				mArrayList.add(getRandom(1000, 1));
				mArrayList.add(getRandom(1000, 1));
				mArrayList.add(getRandom(1000, 1));
				mArrayList.add(getRandom(1000, 1));
				mArrayList.add(getRandom(1000, 1));
				mArrayList.add(getRandom(1000, 1));
				mArrayList.add(getRandom(1000, 1));
				mArrayList.add(getRandom(1000, 1));
				mArrayList.add(getRandom(1000, 1));
				mArrayList.add(getRandom(1000, 1));
				mArrayList.add(getRandom(1000, 1));
				mArrayList.add(getRandom(1000, 1));
				Application.getHCSAPI().SetExResult(mArrayList, mRetJson);

				((TextView) findViewById(R.id.btn_st_result)).setText(mRetJson);

			}
		});
		findViewById(R.id.btn_st_delexresult).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				StringBuilder mRetJson = new StringBuilder ();
				String id = "1";
				Boolean all = true;
				Application.getHCSAPI().DelExResult(id, all, mRetJson);

				((TextView) findViewById(R.id.btn_st_result)).setText(mRetJson);

			}
		});

		findViewById(R.id.btn_st_getexgoalsetting).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				ArrayList<String> mArrayList;
				StringBuilder mRetJson = new StringBuilder ();

				mArrayList = new ArrayList<String>();
				mArrayList.add("0");
				mArrayList.add("0");
				Application.getHCSAPI().GetExGoalSetting(mArrayList, mRetJson);

				((TextView) findViewById(R.id.btn_st_result)).setText(mRetJson);

			}
		});
		findViewById(R.id.btn_st_setexgoalsetting).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				ArrayList<String> mArrayList;
				StringBuilder mRetJson = new StringBuilder ();

				mArrayList = new ArrayList<String>();
				mArrayList.add(getRandom(1000, 1));
				mArrayList.add(getRandom(1000, 1));
				mArrayList.add(getRandom(1000, 1));
				mArrayList.add(getRandom(1000, 1));
				mArrayList.add(getRandom(1000, 1));
				mArrayList.add(getRandom(1000, 1));
				Application.getHCSAPI().SetExGoalSetting(mArrayList, mRetJson);

				((TextView) findViewById(R.id.btn_st_result)).setText(mRetJson);

			}
		});
		findViewById(R.id.btn_st_delexgoalsetting).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				StringBuilder mRetJson = new StringBuilder ();
				String id = "1";
				Boolean all = true;
				Application.getHCSAPI().DelExGoalSetting(id, all, mRetJson);

				((TextView) findViewById(R.id.btn_st_result)).setText(mRetJson);

			}
		});

		findViewById(R.id.btn_st_getexequipment).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				ArrayList<String> mArrayList;
				StringBuilder mRetJson = new StringBuilder ();

				mArrayList = new ArrayList<String>();
				mArrayList.add("0");
				Application.getHCSAPI().GetExEquipment(mArrayList, mRetJson);

				((TextView) findViewById(R.id.btn_st_result)).setText(mRetJson);

			}
		});
		findViewById(R.id.btn_st_setexequipment).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				ArrayList<String> mArrayList;
				StringBuilder mRetJson = new StringBuilder ();

				mArrayList = new ArrayList<String>();
				mArrayList.add(getRandom(1000, 1));
				mArrayList.add(getRandom(1000, 1));

				Application.getHCSAPI().SetExEquipment(mArrayList, mRetJson);

				((TextView) findViewById(R.id.btn_st_result)).setText(mRetJson);

			}
		});
		findViewById(R.id.btn_st_delexequipment).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				StringBuilder mRetJson = new StringBuilder ();
				String id = "1";
				Boolean all = true;
				Application.getHCSAPI().DelExEquipment(id, all, mRetJson);

				((TextView) findViewById(R.id.btn_st_result)).setText(mRetJson);

			}
		});

	}

	public String getRandom(int max, int offset) {
		return String.valueOf(mRand.nextInt(max) + offset);
	}

}
