package com.ict_chcs.hm.logo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.logo.R;
import com.ict_chcs.hm.Adapter.Utility;
import com.ict_chcs.hm.logo.Application.MyGlobals;
import com.ict_chcs.hm_t.Application;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ResultCycleActivity extends Activity {
	ImageView btnCycDay, btnCycWeek, btnCycMonth;
	ImageView img_cyc_Back, img_cyc_Menu;
	ImageView img_cyc_Cal, img_cyc_Km, img_cyc_Time;
	TextView txtcycKm, txtcycTime;
	TextView txtViewKm, txtViewCal, txtViewTime;
	BitmapDrawable d_km, d_cal, d_time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cycle_result);

		btnCycDay = (ImageView) findViewById(R.id.btnCycDay);
		btnCycWeek = (ImageView) findViewById(R.id.btnCycWeek);
		btnCycMonth = (ImageView) findViewById(R.id.btnCycMonth);
		img_cyc_Back = (ImageView) findViewById(R.id.cyc_re_back);
		img_cyc_Menu = (ImageView) findViewById(R.id.cyc_re_main);
		img_cyc_Km = (ImageView) findViewById(R.id.imageView_cyc_day2);
		img_cyc_Cal = (ImageView) findViewById(R.id.imageView_cyc_day3);
		img_cyc_Time = (ImageView) findViewById(R.id.imageView_cyc_day4);
		txtcycKm = (TextView) findViewById(R.id.text_cyc_km);
		txtcycTime = (TextView) findViewById(R.id.text_cyc_time);
		txtViewCal = (TextView) findViewById(R.id.textView_cyc_calorie);
		txtViewKm = (TextView) findViewById(R.id.textView_cyc_km);
		txtViewTime = (TextView) findViewById(R.id.textView_cyc_time);

		img_cyc_Km.setImageBitmap(null);
		img_cyc_Cal.setImageBitmap(null);
		img_cyc_Time.setImageBitmap(null);
		int day_cyc_km = 0;
		int day_cyc_cal = 0;
		int day_cyc_time = 0;

		img_cyc_Back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Intent intentSub;
				intentSub = new Intent(ResultCycleActivity.this, CycleActivity.class);
				startActivity(intentSub);
				finish();
			}
		});
		img_cyc_Menu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Intent intentSub;
				intentSub = new Intent(ResultCycleActivity.this, MenuActivity.class);
				startActivity(intentSub);
				finish();
			}
		});

		img_cyc_Cal.setImageResource(R.drawable.drc1);
		img_cyc_Km.setImageResource(R.drawable.drck1);
		img_cyc_Time.setImageResource(R.drawable.drt1);

		btnCycDay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ArrayList<String> mArrayList;
				StringBuilder mRetJson = new StringBuilder();
				mArrayList = new ArrayList<String>();
				String st_time = new String();

				txtcycTime.setText("분");
				txtcycKm.setText("Km");

				int day_cyc_cal = 0;
				int day_cyc_km = 0;
				int day_cyc_time = 0;

				st_time = Utility.getNowDateTime();
				mArrayList.clear();
				mArrayList.add(MyGlobals.getInstance().getmMyUSERID());
				mArrayList.add("BIKE");
				mArrayList.add(st_time);

				if (Application.getHCSAPI().GetExResult(mArrayList, mRetJson) == true) {
					try {
						JSONObject root = new JSONObject(mRetJson.toString());
						JSONArray results = root.getJSONArray("results");

						for (int num = results.length(); num > 0; num--) {
							JSONObject resultInfo = results.getJSONObject(num - 1);

							String ex_time = resultInfo.getString("ex_time").substring(0,
									resultInfo.getString("ex_time").length() - 3);
							day_cyc_time += Integer.parseInt(ex_time);
							day_cyc_km += Integer
									.parseInt(resultInfo.getString("ex_distance").replaceAll("[^0-9]+", ""));
							day_cyc_cal += Integer
									.parseInt(resultInfo.getString("ex_calories").replaceAll("[^0-9]+", ""));

						} // for

					} // try
					catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				/*
				 * Integer.toString(day_cyc_time); Integer.toString(day_cyc_km);
				 * Integer.toString(day_cyc_cal);
				 */
				txtViewKm.setText("" + day_cyc_km);
				txtViewTime.setText("" + day_cyc_time);
				txtViewCal.setText("" + day_cyc_cal);

				if (day_cyc_cal == 0) {
					img_cyc_Cal.setImageResource(R.drawable.drc1);
				} else if (day_cyc_cal > 0 && day_cyc_cal < 101) {
					img_cyc_Cal.setImageResource(R.drawable.drc2);
				} else if (day_cyc_cal >= 101 && day_cyc_cal < 201) {
					img_cyc_Cal.setImageResource(R.drawable.drc3);
				} else if (day_cyc_cal >= 201 && day_cyc_cal < 301) {
					img_cyc_Cal.setImageResource(R.drawable.drc4);
				} else if (day_cyc_cal >= 301 && day_cyc_cal < 401) {
					img_cyc_Cal.setImageResource(R.drawable.drc5);
				} else if (day_cyc_cal >= 401 && day_cyc_cal < 501) {
					img_cyc_Cal.setImageResource(R.drawable.drc6);
				} else if (day_cyc_cal >= 501 && day_cyc_cal < 601) {
					img_cyc_Cal.setImageResource(R.drawable.drc7);
				} else if (day_cyc_cal >= 601 && day_cyc_cal < 701) {
					img_cyc_Cal.setImageResource(R.drawable.drc8);
				} else if (day_cyc_cal >= 701) {
					img_cyc_Cal.setImageResource(R.drawable.drc9);
				} else {
					Toast.makeText(ResultCycleActivity.this, "��¿� �����߽��մ�", Toast.LENGTH_SHORT).show();
				}

				if (day_cyc_km <= 0) {
					img_cyc_Km.setImageResource(R.drawable.drck1);
				} else if (day_cyc_km >= 0 && day_cyc_km < 4) {
					img_cyc_Km.setImageResource(R.drawable.drck2);
				} else if (day_cyc_km >= 4 && day_cyc_km < 6) {
					img_cyc_Km.setImageResource(R.drawable.drck3);
				} else if (day_cyc_km >= 6 && day_cyc_km < 8) {
					img_cyc_Km.setImageResource(R.drawable.drck4);
				} else if (day_cyc_km >= 8 && day_cyc_km < 10) {
					img_cyc_Km.setImageResource(R.drawable.drck5);
				} else if (day_cyc_km >= 10 && day_cyc_km < 12) {
					img_cyc_Km.setImageResource(R.drawable.drck6);
				} else if (day_cyc_km >= 12 && day_cyc_km < 14) {
					img_cyc_Km.setImageResource(R.drawable.drck7);
				} else if (day_cyc_km >= 14 && day_cyc_km < 16) {
					img_cyc_Km.setImageResource(R.drawable.drck8);
				} else if (day_cyc_km >= 16) {
					img_cyc_Km.setImageResource(R.drawable.drck9);
				} else {
					Toast.makeText(ResultCycleActivity.this, "��¿� �����߽��մ�", Toast.LENGTH_SHORT).show();
				}

				if (day_cyc_time <= 0) {
					img_cyc_Time.setImageResource(R.drawable.drt1);
				} else if (day_cyc_time > 0 && day_cyc_time < 31) {
					img_cyc_Time.setImageResource(R.drawable.drt2);
				} else if (day_cyc_time >= 31 && day_cyc_time < 61) {
					img_cyc_Time.setImageResource(R.drawable.drt3);
				} else if (day_cyc_time >= 61 && day_cyc_time < 91) {
					img_cyc_Time.setImageResource(R.drawable.drt4);
				} else if (day_cyc_time >= 91 && day_cyc_time < 121) {
					img_cyc_Time.setImageResource(R.drawable.drt5);
				} else if (day_cyc_time >= 121 && day_cyc_time < 151) {
					img_cyc_Time.setImageResource(R.drawable.drt6);
				} else if (day_cyc_time >= 151 && day_cyc_time < 181) {
					img_cyc_Time.setImageResource(R.drawable.drt7);
				} else if (day_cyc_time >= 181 && day_cyc_time < 211) {
					img_cyc_Time.setImageResource(R.drawable.drt8);
				} else if (day_cyc_time >= 211) {
					img_cyc_Time.setImageResource(R.drawable.drt9);
				} else {
					Toast.makeText(ResultCycleActivity.this, "��¿� �����߽��մ�", Toast.LENGTH_SHORT).show();
				}

			}// onClick
		});
		btnCycWeek.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ArrayList<String> mArrayList;
				StringBuilder mRetJson = new StringBuilder();
				mArrayList = new ArrayList<String>();
				String[] st_time = new String[7];

				txtcycTime.setText("시");
				txtcycKm.setText("Km");

				int day_cyc_cal = 0;
				int day_cyc_km = 0;
				int day_cyc_time = 0;

				for (int i = -7; i < 0; i++) {
					st_time[i + 7] = Utility.get7DaysDateTime(i);
					mArrayList.clear();
					mArrayList.add(MyGlobals.getInstance().getmMyUSERID());
					mArrayList.add("BIKE");
					mArrayList.add(st_time[i + 7]);

					if (Application.getHCSAPI().GetExResult(mArrayList, mRetJson) == true) {
						try {
							JSONObject root = new JSONObject(mRetJson.toString());
							JSONArray results = root.getJSONArray("results");

							for (int num = results.length(); num > 0; num--) {
								JSONObject resultInfo = results.getJSONObject(num - 1);

								String ex_time = resultInfo.getString("ex_time").substring(0,
										resultInfo.getString("ex_time").length() - 3);
								day_cyc_time += Integer.parseInt(ex_time);
								day_cyc_km += Integer
										.parseInt(resultInfo.getString("ex_distance").replaceAll("[^0-9]+", ""));
								day_cyc_cal += Integer
										.parseInt(resultInfo.getString("ex_calories").replaceAll("[^0-9]+", ""));

							}

						} // try
						catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} // for
				txtViewKm.setText("" + day_cyc_km);
				txtViewTime.setText("" + day_cyc_time);
				txtViewCal.setText("" + day_cyc_cal);
				if (day_cyc_cal == 0) {
					img_cyc_Cal.setImageResource(R.drawable.wrc1);
				} else if (day_cyc_cal > 0 && day_cyc_cal < 301) {
					img_cyc_Cal.setImageResource(R.drawable.wrc2);
				} else if (day_cyc_cal >= 301 && day_cyc_cal < 601) {
					img_cyc_Cal.setImageResource(R.drawable.wrc3);
				} else if (day_cyc_cal >= 601 && day_cyc_cal < 901) {
					img_cyc_Cal.setImageResource(R.drawable.wrc4);
				} else if (day_cyc_cal >= 901 && day_cyc_cal < 1201) {
					img_cyc_Cal.setImageResource(R.drawable.wrc5);
				} else if (day_cyc_cal >= 1201 && day_cyc_cal < 1501) {
					img_cyc_Cal.setImageResource(R.drawable.wrc6);
				} else if (day_cyc_cal >= 1501 && day_cyc_cal < 1801) {
					img_cyc_Cal.setImageResource(R.drawable.wrc7);
				} else if (day_cyc_cal >= 1801 && day_cyc_cal < 2101) {
					img_cyc_Cal.setImageResource(R.drawable.wrc8);
				} else if (day_cyc_cal >= 2101) {
					img_cyc_Cal.setImageResource(R.drawable.wrc9);
				} else {
					Toast.makeText(ResultCycleActivity.this, "��¿� �����߽��մ�", Toast.LENGTH_SHORT).show();
				}

				if (day_cyc_km <= 0) {
					img_cyc_Km.setImageResource(R.drawable.wrck1);
				} else if (day_cyc_km > 0 && day_cyc_km < 7) {
					img_cyc_Km.setImageResource(R.drawable.wrck2);
				} else if (day_cyc_km >= 7 && day_cyc_km < 13) {
					img_cyc_Km.setImageResource(R.drawable.wrck3);
				} else if (day_cyc_km >= 13 && day_cyc_km < 19) {
					img_cyc_Km.setImageResource(R.drawable.wrck4);
				} else if (day_cyc_km >= 19 && day_cyc_km < 25) {
					img_cyc_Km.setImageResource(R.drawable.wrck5);
				} else if (day_cyc_km >= 25 && day_cyc_km < 31) {
					img_cyc_Km.setImageResource(R.drawable.wrck6);
				} else if (day_cyc_km >= 31 && day_cyc_km < 37) {
					img_cyc_Km.setImageResource(R.drawable.wrck7);
				} else if (day_cyc_km >= 37 && day_cyc_km < 43) {
					img_cyc_Km.setImageResource(R.drawable.wrck8);
				} else if (day_cyc_km >= 43) {
					img_cyc_Km.setImageResource(R.drawable.wrck9);
				} else {
					Toast.makeText(ResultCycleActivity.this, "��¿� �����߽��մ�", Toast.LENGTH_SHORT).show();
				}
				if (day_cyc_time <= 0) {
					img_cyc_Time.setImageResource(R.drawable.wrt1);
				} else if (day_cyc_time > 0 && day_cyc_time < 121) {
					img_cyc_Time.setImageResource(R.drawable.wrt2);
				} else if (day_cyc_time >= 121 && day_cyc_time < 241) {
					img_cyc_Time.setImageResource(R.drawable.wrt3);
				} else if (day_cyc_time >= 241 && day_cyc_time < 361) {
					img_cyc_Time.setImageResource(R.drawable.wrt4);
				} else if (day_cyc_time >= 361 && day_cyc_time < 481) {
					img_cyc_Time.setImageResource(R.drawable.wrt5);
				} else if (day_cyc_time >= 481 && day_cyc_time < 601) {
					img_cyc_Time.setImageResource(R.drawable.wrt6);
				} else if (day_cyc_time >= 601 && day_cyc_time < 721) {
					img_cyc_Time.setImageResource(R.drawable.wrt7);
				} else if (day_cyc_time >= 721 && day_cyc_time < 841) {
					img_cyc_Time.setImageResource(R.drawable.wrt8);
				} else if (day_cyc_time >= 841) {
					img_cyc_Time.setImageResource(R.drawable.wrt9);
				} else {
					Toast.makeText(ResultCycleActivity.this, "��¿� �����߽��մ�", Toast.LENGTH_SHORT).show();
				}

			}
		});
		btnCycMonth.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ArrayList<String> mArrayList;
				StringBuilder mRetJson = new StringBuilder();
				mArrayList = new ArrayList<String>();
				String[] st_time = new String[30];

				txtcycTime.setText("시");
				txtcycKm.setText("Km");

				int day_cyc_cal = 0;
				int day_cyc_km = 0;
				int day_cyc_time = 0;

				for (int i = -15; i < 0; i++) {
					st_time[i + 15] = Utility.get7DaysDateTime(i);
					mArrayList.clear();
					mArrayList.add(MyGlobals.getInstance().getmMyUSERID());
					mArrayList.add("BIKE");
					mArrayList.add(st_time[i + 15]);

					if (Application.getHCSAPI().GetExResult(mArrayList, mRetJson) == true) {
						try {
							JSONObject root = new JSONObject(mRetJson.toString());
							JSONArray results = root.getJSONArray("results");

							for (int num = results.length(); num > 0; num--) {
								JSONObject resultInfo = results.getJSONObject(num - 1);

								String ex_time = resultInfo.getString("ex_time").substring(0,
										resultInfo.getString("ex_time").length() - 3);
								day_cyc_time += Integer.parseInt(ex_time);
								day_cyc_km += Integer
										.parseInt(resultInfo.getString("ex_distance").replaceAll("[^0-9]+", ""));
								day_cyc_cal += Integer
										.parseInt(resultInfo.getString("ex_calories").replaceAll("[^0-9]+", ""));

							}

						} // try
						catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} // for
				txtViewKm.setText("" + day_cyc_km);
				txtViewTime.setText("" + day_cyc_time);
				txtViewCal.setText("" + day_cyc_cal);

				if (day_cyc_cal <= 0) {
					img_cyc_Cal.setImageResource(R.drawable.mrc1);
				} else if (day_cyc_cal > 0 && day_cyc_cal < 901) {
					img_cyc_Cal.setImageResource(R.drawable.mrc2);
				} else if (day_cyc_cal >= 901 && day_cyc_cal < 1801) {
					img_cyc_Cal.setImageResource(R.drawable.mrc3);
				} else if (day_cyc_cal >= 1801 && day_cyc_cal < 2701) {
					img_cyc_Cal.setImageResource(R.drawable.mrc4);
				} else if (day_cyc_cal >= 2701 && day_cyc_cal < 3601) {
					img_cyc_Cal.setImageResource(R.drawable.mrc5);
				} else if (day_cyc_cal >= 3601 && day_cyc_cal < 4501) {
					img_cyc_Cal.setImageResource(R.drawable.mrc6);
				} else if (day_cyc_cal >= 4501 && day_cyc_cal < 5401) {
					img_cyc_Cal.setImageResource(R.drawable.mrc7);
				} else if (day_cyc_cal >= 5401 && day_cyc_cal < 6301) {
					img_cyc_Cal.setImageResource(R.drawable.mrc8);
				} else if (day_cyc_cal >= 6301) {
					img_cyc_Cal.setImageResource(R.drawable.mrc9);
				} else {
					Toast.makeText(ResultCycleActivity.this, "��¿� �����߽��մ�", Toast.LENGTH_SHORT).show();
				}

				if (day_cyc_km <= 0) {
					img_cyc_Km.setImageResource(R.drawable.mrck1);
				} else if (day_cyc_km >= 1 && day_cyc_km < 41) {
					img_cyc_Km.setImageResource(R.drawable.mrck2);
				} else if (day_cyc_km >= 41 && day_cyc_km < 81) {
					img_cyc_Km.setImageResource(R.drawable.mrck3);
				} else if (day_cyc_km >= 81 && day_cyc_km < 121) {
					img_cyc_Km.setImageResource(R.drawable.mrck4);
				} else if (day_cyc_km >= 121 && day_cyc_km < 161) {
					img_cyc_Km.setImageResource(R.drawable.mrck5);
				} else if (day_cyc_km >= 161 && day_cyc_km < 201) {
					img_cyc_Km.setImageResource(R.drawable.mrck6);
				} else if (day_cyc_km >= 201 && day_cyc_km < 241) {
					img_cyc_Km.setImageResource(R.drawable.mrck7);
				} else if (day_cyc_km >= 241 && day_cyc_km < 281) {
					img_cyc_Km.setImageResource(R.drawable.mrck8);
				} else if (day_cyc_km >= 281) {
					img_cyc_Km.setImageResource(R.drawable.mrck9);
				} else {
					Toast.makeText(ResultCycleActivity.this, "��¿� �����߽��մ�", Toast.LENGTH_SHORT).show();
				}
				if (day_cyc_time <= 0) {
					img_cyc_Time.setImageResource(R.drawable.mrt1);
				} else if (day_cyc_time > 0 && day_cyc_time < 361) {
					img_cyc_Time.setImageResource(R.drawable.mrt2);
				} else if (day_cyc_time >= 361 && day_cyc_time < 721) {
					img_cyc_Time.setImageResource(R.drawable.mrt3);
				} else if (day_cyc_time >= 721 && day_cyc_time < 1081) {
					img_cyc_Time.setImageResource(R.drawable.mrt4);
				} else if (day_cyc_time >= 1081 && day_cyc_time < 1441) {
					img_cyc_Time.setImageResource(R.drawable.mrt5);
				} else if (day_cyc_time >= 1441 && day_cyc_time < 1801) {
					img_cyc_Time.setImageResource(R.drawable.mrt6);
				} else if (day_cyc_time >= 1801 && day_cyc_time < 2161) {
					img_cyc_Time.setImageResource(R.drawable.mrt7);
				} else if (day_cyc_time >= 2161 && day_cyc_time < 2521) {
					img_cyc_Time.setImageResource(R.drawable.mrt8);
				} else if (day_cyc_time >= 2521) {
					img_cyc_Time.setImageResource(R.drawable.mrt9);
				} else {
					Toast.makeText(ResultCycleActivity.this, "��¿� �����߽��մ�", Toast.LENGTH_SHORT).show();
				}
			}// onClick

		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			AlertDialog dialog;
			dialog = new AlertDialog.Builder(this).setMessage("종료하시겠습니까?")
					.setPositiveButton("예", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							moveTaskToBack(true);

							finish();

							android.os.Process.killProcess(android.os.Process.myPid());
						}
					}).setNegativeButton("아니오", new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}
					}).show();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	

	private void getSummaryExerciseInfo(String date) {

		try {
			// day, weekly, monthly
			String curTime = Utility.getNowDateTime();
			String stTime = null;
			String stTime2 = null;

			if (date.equalsIgnoreCase("daily")) {
				stTime = String.format("%s000000", curTime.substring(0, 8));
				stTime2 = String.format("%s235959", curTime.substring(0, 8));
			} else if (date.equalsIgnoreCase("weekly")) {
				stTime2 = curTime;
				Calendar cal = Calendar.getInstance();
				Date toDay = cal.getTime();
				cal.setTime(toDay);
				cal.add(Calendar.DAY_OF_YEAR, -7);
				stTime = new SimpleDateFormat("yyyyMMddHHmmss").format(cal.getTime());
				
				if (Double.parseDouble(stTime) - Double.parseDouble(mStartTime) <= 0) {
					stTime = mStartTime;
				}				
			} else if (date.equalsIgnoreCase("monthly")) {
				stTime = String.format("%s01000000", curTime.substring(0, 6));
				stTime2 = String.format("%s32000000", curTime.substring(0, 6));
			}

			ArrayList<String> mArrayList = new ArrayList<String>();
			StringBuilder mRetJson = new StringBuilder();

			// information of total exercise - start time
			mRetJson.delete(0, mRetJson.length());
			mArrayList.clear();
			mArrayList.add(UserID);
			mArrayList.add("BIKE"); // ex_variety
			mArrayList.add(stTime); // st_time
			mArrayList.add(stTime2); // st_time2
			mArrayList.add("0"); // ex_calories
			mArrayList.add("0"); // ex_distance
			mArrayList.add("0"); // ex_time
			mArrayList.add("0"); // sum
			mArrayList.add("1"); // limit
			mArrayList.add("0"); // desc
			mArrayList.add("0"); // graph

			if (Application.getHCSAPI().GetExResult(mArrayList, mRetJson)) {

				JSONObject root = new JSONObject(mRetJson.toString());
				JSONArray results = root.getJSONArray("results");

				if (results.length() > 0) {
					JSONObject resultInfo = results.getJSONObject(0);

					String st_time = resultInfo.getString("st_time");
					if (st_time != null) {
						String startTime = String.format("%s/%s/%s %s:%s:%s", st_time.substring(0, 4),
								st_time.substring(4, 6), st_time.substring(6, 8), st_time.substring(8, 10),
								st_time.substring(10, 12), st_time.substring(12, 14));

						((TextView) findViewById(R.id.txtView_hs_search_stime_bike)).setText(startTime);
					}
				}
			} else {
				((TextView) findViewById(R.id.txtView_hs_search_stime_bike)).setText("");
			}

			// information of total exercise - end time
			mRetJson.delete(0, mRetJson.length());
			mArrayList.clear();
			mArrayList.add(UserID);
			mArrayList.add("BIKE"); // ex_variety
			mArrayList.add(stTime); // st_time
			mArrayList.add(stTime2); // st_time2
			mArrayList.add("0"); // ex_calories
			mArrayList.add("0"); // ex_distance
			mArrayList.add("0"); // ex_time
			mArrayList.add("0"); // sum
			mArrayList.add("1"); // limit
			mArrayList.add("1"); // desc
			mArrayList.add("0"); // graph

			if (Application.getHCSAPI().GetExResult(mArrayList, mRetJson)) {

				JSONObject root = new JSONObject(mRetJson.toString());
				JSONArray results = root.getJSONArray("results");

				if (results.length() > 0) {
					JSONObject resultInfo = results.getJSONObject(0);

					String en_time = resultInfo.getString("en_time");
					if (en_time != null) {
						String EndTime = String.format("%s/%s/%s %s:%s:%s", en_time.substring(0, 4),
								en_time.substring(4, 6), en_time.substring(6, 8), en_time.substring(8, 10),
								en_time.substring(10, 12), en_time.substring(12, 14));

						((TextView) findViewById(R.id.txtView_hs_search_etime_bike)).setText(EndTime);
					}
				}
			} else {
				((TextView) findViewById(R.id.txtView_hs_search_etime_bike)).setText("");
			}

			if (!(((TextView) findViewById(R.id.txtView_hs_search_stime_bike)).getText().toString().equalsIgnoreCase("")
					|| ((TextView) findViewById(R.id.txtView_hs_search_etime_bike)).getText().toString()
							.equalsIgnoreCase(""))) {

				// information of total exercise - calories
				mRetJson.delete(0, mRetJson.length());
				mArrayList.clear();
				mArrayList.add(UserID);
				mArrayList.add("BIKE"); // ex_variety
				mArrayList.add(stTime); // st_time
				mArrayList.add(stTime2); // st_time2
				mArrayList.add("0"); // ex_calories
				mArrayList.add("0"); // ex_distance
				mArrayList.add("0"); // ex_time
				mArrayList.add("1"); // sum
				mArrayList.add("0"); // limit
				mArrayList.add("0"); // desc
				mArrayList.add("0"); // graph

				if (Application.getHCSAPI().GetExResult(mArrayList, mRetJson)) {

					JSONObject root = new JSONObject(mRetJson.toString());
					JSONArray results = root.getJSONArray("results");

					if (results.length() > 0) {
						JSONObject resultInfo = results.getJSONObject(0);

						String sum_ex_calories = resultInfo.getString("SUM(`ex_calories`)");
						String sum_ex_distance = resultInfo.getString("SUM(`ex_distance`)");
						String sum_ex_time = resultInfo.getString("SUM(`ex_time`)");

						if (sum_ex_calories.equalsIgnoreCase("")) {
							sum_ex_calories = "0";
						}
						if (sum_ex_time.equalsIgnoreCase("")) {
							sum_ex_time = "0";
						}
						if (sum_ex_distance.equalsIgnoreCase("")) {
							sum_ex_distance = "0";
						}

						if (sum_ex_calories.length() > 3) {
							String sum_ex_calories2 = String.format("%d,%d", Integer.parseInt(sum_ex_calories) / 1000,
									Integer.parseInt(sum_ex_calories) % 1000);
							((TextView) findViewById(R.id.txtView_hs_search_calories_bike)).setText(sum_ex_calories2);
						} else {
							((TextView) findViewById(R.id.txtView_hs_search_calories_bike)).setText(sum_ex_calories);
						}
						if (sum_ex_distance.length() > 3) {
							String sum_ex_distance2 = String.format("%d,%d", Integer.parseInt(sum_ex_distance) / 1000,
									Integer.parseInt(sum_ex_distance) % 1000);
							((TextView) findViewById(R.id.txtView_hs_search_distance_bike)).setText(sum_ex_distance2);
						} else {
							((TextView) findViewById(R.id.txtView_hs_search_distance_bike)).setText(sum_ex_distance);
						}

						String sum_ex_time2 = String.format("%02d:%02d:00", Integer.parseInt(sum_ex_time) / 60,
								Integer.parseInt(sum_ex_time) % 60);
						((TextView) findViewById(R.id.txtView_hs_search_time_bike)).setText(sum_ex_time2);
					}
				}
			} else {
				((TextView) findViewById(R.id.txtView_hs_search_calories_bike)).setText("");
				((TextView) findViewById(R.id.txtView_hs_search_distance_bike)).setText("");
				((TextView) findViewById(R.id.txtView_hs_search_time_bike)).setText("");
			}

			// Treadmill
			// information of total exercise - start time
			mRetJson.delete(0, mRetJson.length());
			mArrayList.clear();
			mArrayList.add(UserID);
			mArrayList.add("TREADMILL"); // ex_variety
			mArrayList.add(stTime); // st_time
			mArrayList.add(stTime2); // st_time2
			mArrayList.add("0"); // ex_calories
			mArrayList.add("0"); // ex_distance
			mArrayList.add("0"); // ex_time
			mArrayList.add("0"); // sum
			mArrayList.add("1"); // limit
			mArrayList.add("0"); // desc
			mArrayList.add("0"); // graph

			if (Application.getHCSAPI().GetExResult(mArrayList, mRetJson)) {

				JSONObject root = new JSONObject(mRetJson.toString());
				JSONArray results = root.getJSONArray("results");

				if (results.length() > 0) {
					JSONObject resultInfo = results.getJSONObject(0);

					String st_time = resultInfo.getString("st_time");
					if (st_time != null) {
						String startTime = String.format("%s/%s/%s %s:%s:%s", st_time.substring(0, 4),
								st_time.substring(4, 6), st_time.substring(6, 8), st_time.substring(8, 10),
								st_time.substring(10, 12), st_time.substring(12, 14));

						((TextView) findViewById(R.id.txtView_hs_search_stime_treadmill)).setText(startTime);
					}
				}
			} else {
				((TextView) findViewById(R.id.txtView_hs_search_stime_treadmill)).setText("");
			}

			// information of total exercise - end time
			mRetJson.delete(0, mRetJson.length());
			mArrayList.clear();
			mArrayList.add(UserID);
			mArrayList.add("TREADMILL"); // ex_variety
			mArrayList.add(stTime); // st_time
			mArrayList.add(stTime2); // st_time2
			mArrayList.add("0"); // ex_calories
			mArrayList.add("0"); // ex_distance
			mArrayList.add("0"); // ex_time
			mArrayList.add("0"); // sum
			mArrayList.add("1"); // limit
			mArrayList.add("1"); // desc
			mArrayList.add("0"); // graph

			if (Application.getHCSAPI().GetExResult(mArrayList, mRetJson)) {

				JSONObject root = new JSONObject(mRetJson.toString());
				JSONArray results = root.getJSONArray("results");

				if (results.length() > 0) {
					JSONObject resultInfo = results.getJSONObject(0);

					String en_time = resultInfo.getString("en_time");
					if (en_time != null) {
						String EndTime = String.format("%s/%s/%s %s:%s:%s", en_time.substring(0, 4),
								en_time.substring(4, 6), en_time.substring(6, 8), en_time.substring(8, 10),
								en_time.substring(10, 12), en_time.substring(12, 14));

						((TextView) findViewById(R.id.txtView_hs_search_etime_treadmill)).setText(EndTime);
					}
				}
			} else {
				((TextView) findViewById(R.id.txtView_hs_search_etime_treadmill)).setText("");
			}

			if (!(((TextView) findViewById(R.id.txtView_hs_search_stime_treadmill)).getText().toString()
					.equalsIgnoreCase("")
					|| ((TextView) findViewById(R.id.txtView_hs_search_etime_treadmill)).getText().toString()
							.equalsIgnoreCase(""))) {

				// information of total exercise - calories
				mRetJson.delete(0, mRetJson.length());
				mArrayList.clear();
				mArrayList.add(UserID);
				mArrayList.add("TREADMILL"); // ex_variety
				mArrayList.add(stTime); // st_time
				mArrayList.add(stTime2); // st_time2
				mArrayList.add("0"); // ex_calories
				mArrayList.add("0"); // ex_distance
				mArrayList.add("0"); // ex_time
				mArrayList.add("1"); // sum
				mArrayList.add("0"); // limit
				mArrayList.add("0"); // desc
				mArrayList.add("0"); // graph

				if (Application.getHCSAPI().GetExResult(mArrayList, mRetJson)) {

					JSONObject root = new JSONObject(mRetJson.toString());
					JSONArray results = root.getJSONArray("results");

					if (results.length() > 0) {
						JSONObject resultInfo = results.getJSONObject(0);

						String sum_ex_calories = resultInfo.getString("SUM(`ex_calories`)");
						String sum_ex_distance = resultInfo.getString("SUM(`ex_distance`)");
						String sum_ex_time = resultInfo.getString("SUM(`ex_time`)");

						if (sum_ex_calories.equalsIgnoreCase("")) {
							sum_ex_calories = "0";
						}
						if (sum_ex_time.equalsIgnoreCase("")) {
							sum_ex_time = "0";
						}
						if (sum_ex_distance.equalsIgnoreCase("")) {
							sum_ex_distance = "0";
						}

						if (sum_ex_calories.length() > 3) {
							String sum_ex_calories2 = String.format("%d,%d", Integer.parseInt(sum_ex_calories) / 1000,
									Integer.parseInt(sum_ex_calories) % 1000);
							((TextView) findViewById(R.id.txtView_hs_search_calories_treadmill))
									.setText(sum_ex_calories2);
						} else {
							((TextView) findViewById(R.id.txtView_hs_search_calories_treadmill))
									.setText(sum_ex_calories);
						}
						if (sum_ex_distance.length() > 3) {
							String sum_ex_distance2 = String.format("%d,%d", Integer.parseInt(sum_ex_distance) / 1000,
									Integer.parseInt(sum_ex_distance) % 1000);
							((TextView) findViewById(R.id.txtView_hs_search_distance_treadmill))
									.setText(sum_ex_distance2);
						} else {
							((TextView) findViewById(R.id.txtView_hs_search_distance_treadmill))
									.setText(sum_ex_distance);
						}

						String sum_ex_time2 = String.format("%02d:%02d:00", Integer.parseInt(sum_ex_time) / 60,
								Integer.parseInt(sum_ex_time) % 60);
						((TextView) findViewById(R.id.txtView_hs_search_time_treadmill)).setText(sum_ex_time2);
					}
				}
			} else {
				((TextView) findViewById(R.id.txtView_hs_search_calories_treadmill)).setText("");
				((TextView) findViewById(R.id.txtView_hs_search_distance_treadmill)).setText("");
				((TextView) findViewById(R.id.txtView_hs_search_time_treadmill)).setText("");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
