package com.ict_chcs.hm_t;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.ict_chcs.hm_t.Adapter.Utility;
import com.ict_chcs.hm_t.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
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
		Application.getHCSAPI().setContext(this);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server_test);
		
		mRand = new Random();
		((TextView) findViewById(R.id.txtView_st_result)).setMovementMethod(new ScrollingMovementMethod());

		findViewById(R.id.btn_st_clear).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((TextView) findViewById(R.id.txtView_st_result)).setText("");
				((EditText) findViewById(R.id.edTxt_st_setuser)).setText("");
				((EditText) findViewById(R.id.edTxt_st_setpasswd)).setText("");

			}
		});
		((TextView) findViewById(R.id.txtView_st_result)).setMovementMethod(new ScrollingMovementMethod());

		findViewById(R.id.btn_st_getconnection).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((TextView) findViewById(R.id.txtView_st_result)).setText("\n   Waiting.............");

				new Handler() {
					public void handleMessage(Message msg) {
						((TextView) findViewById(R.id.txtView_st_result)).setText(Utility.getWifiInfo(ServerTestActivity.this));

						StringBuilder mRetJson = new StringBuilder();
						Application.getHCSAPI().GetServerConnection(mRetJson);
						((TextView) findViewById(R.id.txtView_st_result)).append("\n\n");

						((TextView) findViewById(R.id.txtView_st_result)).append(mRetJson);
					}
				}.sendEmptyMessageDelayed(0, 100);
			}
		});
		findViewById(R.id.btn_st_getuser).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				((TextView) findViewById(R.id.txtView_st_result)).setText("\n   Waiting.............");

				new Handler() {
					public void handleMessage(Message msg) {
						ArrayList<String> mArrayList;
						StringBuilder mRetJson = new StringBuilder();

						mArrayList = new ArrayList<String>();

						if (((EditText) findViewById(R.id.edTxt_st_setuser)).getText().length() > 0
								&& ((EditText) findViewById(R.id.edTxt_st_setpasswd)).getText().length() > 0) {
							mArrayList.add(((EditText) findViewById(R.id.edTxt_st_setuser)).getText().toString());
							mArrayList.add(((EditText) findViewById(R.id.edTxt_st_setpasswd)).getText().toString());
						} else {
							mArrayList.add("0");
							mArrayList.add("0");
						}
						Application.getHCSAPI().GetExUser(mArrayList, mRetJson);

						((TextView) findViewById(R.id.txtView_st_result)).setText(mRetJson);
					}
				}.sendEmptyMessageDelayed(0, 100);

			}
		});
		findViewById(R.id.btn_st_setuser).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				((TextView) findViewById(R.id.txtView_st_result)).setText("\n   Waiting.............");

				new Handler() {
					public void handleMessage(Message msg) {
						ArrayList<String> mArrayList;
						StringBuilder mRetJson = new StringBuilder();

						mArrayList = new ArrayList<String>();
						if (((EditText) findViewById(R.id.edTxt_st_setuser)).getText().length() > 0
								&& ((EditText) findViewById(R.id.edTxt_st_setpasswd)).getText().length() > 0) {
							mArrayList.add(((EditText) findViewById(R.id.edTxt_st_setuser)).getText().toString());
							mArrayList.add(((EditText) findViewById(R.id.edTxt_st_setpasswd)).getText().toString());
						} else {
							mArrayList.add(getRandom(2, 1)); // id
							mArrayList.add(getRandom(2, 1)); // password
						}
						mArrayList.add(getRandom(2, 1)); // name

						mArrayList.add(((Integer.parseInt(getRandom(1000, 1)) % 2) != 0 ? "MALE" : "FEMALE")); // gender
						mArrayList.add(getRandom(100, 1)); // age
						mArrayList.add(getRandom(100, 1)); // weight
						mArrayList.add(getRandom(1000, 1)); // rfid

						Application.getHCSAPI().SetExUser(mArrayList, mRetJson);

						((TextView) findViewById(R.id.txtView_st_result)).setText(mRetJson);
					}
				}.sendEmptyMessageDelayed(0, 100);
			}
		});
		findViewById(R.id.btn_st_deluser).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				((TextView) findViewById(R.id.txtView_st_result)).setText("\n   Waiting.............");

				new Handler() {
					public void handleMessage(Message msg) {
						StringBuilder mRetJson = new StringBuilder();
						String id = "1";
						Boolean all = true;
						if (((EditText) findViewById(R.id.edTxt_st_setuser)).getText().length() > 0) {
							id = ((EditText) findViewById(R.id.edTxt_st_setuser)).getText().toString();
							all = false;
						}

						Application.getHCSAPI().DelExUser(id, all, mRetJson);

						((TextView) findViewById(R.id.txtView_st_result)).setText(mRetJson);
					}
				}.sendEmptyMessageDelayed(0, 100);
			}
		});

		findViewById(R.id.btn_st_getexresult).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				((TextView) findViewById(R.id.txtView_st_result)).setText("\n   Waiting.............");

				new Handler() {
					public void handleMessage(Message msg) {
						ArrayList<String> mArrayList;
						StringBuilder mRetJson = new StringBuilder();

						mArrayList = new ArrayList<String>();
						if (((EditText) findViewById(R.id.edTxt_st_setuser)).getText().length() > 0) {
							mArrayList.add(((EditText) findViewById(R.id.edTxt_st_setuser)).getText().toString());
						} else {
							mArrayList.add("0");
						}
						mArrayList.add("0");
						mArrayList.add("0");
						Application.getHCSAPI().GetExResult(mArrayList, mRetJson);

						((TextView) findViewById(R.id.txtView_st_result)).setText(mRetJson);
					}
				}.sendEmptyMessageDelayed(0, 100);
			}
		});
		findViewById(R.id.btn_st_setexresult).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				((TextView) findViewById(R.id.txtView_st_result)).setText("\n   Waiting.............");

				new Handler() {
					public void handleMessage(Message msg) {
						ArrayList<String> mArrayList;
						StringBuilder mRetJson = new StringBuilder();

						mArrayList = new ArrayList<String>();

						if (((EditText) findViewById(R.id.edTxt_st_setuser)).getText().length() > 0) {
							mArrayList.add(((EditText) findViewById(R.id.edTxt_st_setuser)).getText().toString());
						} else {
							mArrayList.add(getRandom(2, 1)); // id
						}
						mArrayList.add(((Integer.parseInt(getRandom(1000, 1)) % 2) != 0 ? "BIKE" : "TREADMILL")); // gender

						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
						Date curDate = new Date();
						String st_time = sdf.format(curDate);
						mArrayList.add(st_time); // st_time

						Calendar cal = Calendar.getInstance();
						cal.setTime(curDate);

						int min = Integer.parseInt(getRandom(60, 1));
						cal.add(Calendar.MINUTE, min);

						int sec = Integer.parseInt(getRandom(60, 1));
						cal.add(Calendar.SECOND, sec);

						String en_time = sdf.format(cal.getTime());
						mArrayList.add(en_time); // en_time

						String ex_time = String.format("%02d:%02d", min, sec);
						mArrayList.add(ex_time); // ex_time

						mArrayList.add(getRandom(100, 1)); // ex_heartplus
						mArrayList.add(getRandom(50, 1)); // ex_carories
						mArrayList.add(getRandom(50, 1)); // ex_distance
						mArrayList.add(getRandom(10, 1)); // ex_speed
						mArrayList.add(getRandom(10, 1)); // ex_incline
						mArrayList.add(getRandom(10, 1)); // ex_level
						mArrayList.add(getRandom(10, 1)); // ex_rpm
						mArrayList.add(getRandom(10, 1)); // ex_watts
						mArrayList.add(getRandom(10, 1)); // ex_mets
						Application.getHCSAPI().SetExResult(mArrayList, mRetJson);

						((TextView) findViewById(R.id.txtView_st_result)).setText(mRetJson);
					}
				}.sendEmptyMessageDelayed(0, 100);
			}
		});
		findViewById(R.id.btn_st_delexresult).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				((TextView) findViewById(R.id.txtView_st_result)).setText("\n   Waiting.............");

				new Handler() {
					public void handleMessage(Message msg) {
						StringBuilder mRetJson = new StringBuilder();
						String id = "1";
						Boolean all = true;

						if (((EditText) findViewById(R.id.edTxt_st_setuser)).getText().length() > 0) {
							id = ((EditText) findViewById(R.id.edTxt_st_setuser)).getText().toString();
							all = false;
						}

						Application.getHCSAPI().DelExResult(id, all, mRetJson);

						((TextView) findViewById(R.id.txtView_st_result)).setText(mRetJson);
					}
				}.sendEmptyMessageDelayed(0, 100);
			}
		});

		findViewById(R.id.btn_st_getexgoalsetting).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				((TextView) findViewById(R.id.txtView_st_result)).setText("\n   Waiting.............");

				new Handler() {
					public void handleMessage(Message msg) {
						ArrayList<String> mArrayList;
						StringBuilder mRetJson = new StringBuilder();

						mArrayList = new ArrayList<String>();
						if (((EditText) findViewById(R.id.edTxt_st_setuser)).getText().length() > 0) {
							mArrayList.add(((EditText) findViewById(R.id.edTxt_st_setuser)).getText().toString());
						} else {
							mArrayList.add("0");
						}
						mArrayList.add("0");
						Application.getHCSAPI().GetExGoalSetting(mArrayList, mRetJson);

						((TextView) findViewById(R.id.txtView_st_result)).setText(mRetJson);
					}
				}.sendEmptyMessageDelayed(0, 100);
			}
		});
		findViewById(R.id.btn_st_setexgoalsetting).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				((TextView) findViewById(R.id.txtView_st_result)).setText("\n   Waiting.............");

				new Handler() {
					public void handleMessage(Message msg) {
						ArrayList<String> mArrayList;
						StringBuilder mRetJson = new StringBuilder();

						mArrayList = new ArrayList<String>();
						if (((EditText) findViewById(R.id.edTxt_st_setuser)).getText().length() > 0) {
							mArrayList.add(((EditText) findViewById(R.id.edTxt_st_setuser)).getText().toString());
						} else {
							mArrayList.add(getRandom(1000, 1)); // id
						}
						mArrayList.add(getRandom(2, 1)); // ex_variety
						mArrayList.add(getRandom(100, 1)); // ex_heartplus
						mArrayList.add(getRandom(50, 1)); // ex_calories
						mArrayList.add(getRandom(50, 1)); // ex_distance
						mArrayList.add(getRandom(10, 1)); // ex_speed
						Application.getHCSAPI().SetExGoalSetting(mArrayList, mRetJson);

						((TextView) findViewById(R.id.txtView_st_result)).setText(mRetJson);
					}
				}.sendEmptyMessageDelayed(0, 100);
			}
		});
		findViewById(R.id.btn_st_delexgoalsetting).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				((TextView) findViewById(R.id.txtView_st_result)).setText("\n   Waiting.............");

				new Handler() {
					public void handleMessage(Message msg) {
						StringBuilder mRetJson = new StringBuilder();
						String id = "1";
						Boolean all = true;

						if (((EditText) findViewById(R.id.edTxt_st_setuser)).getText().length() > 0) {
							id = ((EditText) findViewById(R.id.edTxt_st_setuser)).getText().toString();
							all = false;
						}

						Application.getHCSAPI().DelExGoalSetting(id, all, mRetJson);

						((TextView) findViewById(R.id.txtView_st_result)).setText(mRetJson);
					}
				}.sendEmptyMessageDelayed(0, 100);
			}
		});

		findViewById(R.id.btn_st_getexequipment).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				((TextView) findViewById(R.id.txtView_st_result)).setText("\n   Waiting.............");

				new Handler() {
					public void handleMessage(Message msg) {
						ArrayList<String> mArrayList;
						StringBuilder mRetJson = new StringBuilder();

						mArrayList = new ArrayList<String>();
						mArrayList.add("0");
						Application.getHCSAPI().GetExEquipment(mArrayList, mRetJson);

						((TextView) findViewById(R.id.txtView_st_result)).setText(mRetJson);
					}
				}.sendEmptyMessageDelayed(0, 100);
			}
		});
		findViewById(R.id.btn_st_setexequipment).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				((TextView) findViewById(R.id.txtView_st_result)).setText("\n   Waiting.............");

				new Handler() {
					public void handleMessage(Message msg) {
						ArrayList<String> mArrayList;
						StringBuilder mRetJson = new StringBuilder();

						mArrayList = new ArrayList<String>();
						mArrayList.add(getRandom(10, 1));
						mArrayList.add(getRandom(10, 1));

						Application.getHCSAPI().SetExEquipment(mArrayList, mRetJson);

						((TextView) findViewById(R.id.txtView_st_result)).setText(mRetJson);
					}
				}.sendEmptyMessageDelayed(0, 100);
			}
		});
		findViewById(R.id.btn_st_delexequipment).setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				((TextView) findViewById(R.id.txtView_st_result)).setText("\n   Waiting.............");

				new Handler() {
					public void handleMessage(Message msg) {
						StringBuilder mRetJson = new StringBuilder();
						String id = "1";
						Boolean all = true;
						Application.getHCSAPI().DelExEquipment(id, all, mRetJson);

						((TextView) findViewById(R.id.txtView_st_result)).setText(mRetJson);
					}
				}.sendEmptyMessageDelayed(0, 100);
			}
		});

	}

	public String getRandom(int max, int offset) {
		return String.valueOf(mRand.nextInt(max) + offset);
	}

}
