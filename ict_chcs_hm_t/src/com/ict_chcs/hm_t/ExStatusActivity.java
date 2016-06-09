package com.ict_chcs.hm_t;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ict_chcs.hm_t.Adapter.CustomAniDrawable;
import com.ict_chcs.hm_t.Adapter.Graph;
import com.ict_chcs.hm_t.Adapter.Utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class ExStatusActivity extends Activity {

	public static final String TAG = "ExStatusActivity";

	String mCurTime = null;
	String UserID = null;
	String Password = null;
	String mStartTime = null;
	String mEndTime = null;
	Intent intent = null;
	Handler handler = new Handler();
	public ArrayList<healthInfoList> ArrayHealthInfo = null;
	public ImageView mLoadingView = null;
	public AnimationDrawable mFrameAnimation = null;
	public HealthInfoAdapter mHealthInfoAdapter = null;
	public UiHandler mUiHandler = null;
	public CustomAniDrawable mAni1 = null;
	public Graph mGraph = null;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ex_status);
		Application.getHCSAPI().setContext(this);
		mGraph = new Graph();

		if (Utility.getBuildMode(this) > 0) {
			// DEBUG CODE
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					((TextView) findViewById(R.id.txtView_network_status))
							.setText(Utility.getWifiInfo(ExStatusActivity.this));

					StringBuilder mRetJson = new StringBuilder();
					if (Application.getHCSAPI().GetServerConnection(mRetJson) == false) {
						((TextView) findViewById(R.id.txtView_network_status)).append("\nServer disconnected.");
					}
				}
			}).start();

			findViewById(R.id.btn_network_status).setOnClickListener(new View.OnClickListener() {

				@SuppressWarnings("static-access")
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent intent = new Intent(ExStatusActivity.this, ServerTestActivity.class);
					startActivity(intent);
				}
			});
		}

		mUiHandler = new UiHandler();

		mLoadingView = (ImageView) findViewById(R.id.imgView_es_loading);
		mFrameAnimation = (AnimationDrawable) mLoadingView.getBackground();

		ArrayHealthInfo = new ArrayList<healthInfoList>();
		mHealthInfoAdapter = new HealthInfoAdapter(ExStatusActivity.this, R.layout.view_health_info_list,
				ArrayHealthInfo);

		// adapterView - ListView, GridView
		((ListView) findViewById(R.id.listViewHealthInfo)).setAdapter(mHealthInfoAdapter);

		// adapter
		((ListView) findViewById(R.id.listViewHealthInfo))
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long l_position) {

						healthInfoList s = (healthInfoList) parent.getAdapter().getItem(position);

						Log.d("ExStatusActivity", "s.ex_time = " + s.ex_time);
						Log.d("ExStatusActivity", "s.ex_calories = " + s.ex_calories);
						Log.d("ExStatusActivity", "s.ex_heartplus = " + s.ex_heartplus);
						Log.d("ExStatusActivity", "s.ex_distance = " + s.ex_distance);
						Log.d("ExStatusActivity", "s.st_time = " + s.st_time);

						ArrayList<String> mArrayList;
						StringBuilder mRetJson = new StringBuilder();

						mArrayList = new ArrayList<String>();
						if (((TextView) findViewById(R.id.txtView_es_id)).getText().length() > 0) {
							mArrayList.add(UserID);
							if (s.ex_variety == R.drawable.treadmill_64x64)
								mArrayList.add("TREADMILL");
							else
								mArrayList.add("BIKE");

							String StartTime = String.format("%s%s%s%s%s%s", s.st_time.substring(0, 4),
									s.st_time.substring(5, 7), s.st_time.substring(8, 10), s.st_time.substring(11, 13),
									s.st_time.substring(14, 16), s.st_time.substring(17, 19));

							mArrayList.add(StartTime);
							mArrayList.add("0"); // st_time2
							mArrayList.add("0"); // ex_calories
							mArrayList.add("0"); // ex_distance
							mArrayList.add("0"); // ex_time
							mArrayList.add("0"); // sum
							mArrayList.add("0"); // limit
							mArrayList.add("0"); // desc
							mArrayList.add("0"); // graph

							if (Application.getHCSAPI().GetExResult(mArrayList, mRetJson)) {

								JSONObject root;
								try {
									root = new JSONObject(mRetJson.toString());

									JSONArray results = root.getJSONArray("results");

									for (int num = results.length(); num > 0; num--) {
										JSONObject resultInfo = results.getJSONObject(num - 1);

										if (resultInfo.getString("ex_variety").equalsIgnoreCase("TREADMILL")) {
											findViewById(R.id.common_include_health_info_treadmill)
													.setVisibility(View.VISIBLE);
											findViewById(R.id.listViewHealthInfo).setVisibility(View.GONE);
											((ImageView) findViewById(R.id.imgView_hit_ExVariety))
													.setImageResource(R.drawable.treadmill_32x32);

											String stTime = resultInfo.getString("st_time");
											String startTime = String.format("%s/%s/%s %s:%s:%s",
													stTime.substring(0, 4), stTime.substring(4, 6),
													stTime.substring(6, 8), stTime.substring(8, 10),
													stTime.substring(10, 12), stTime.substring(12, 14));
											((TextView) findViewById(R.id.txtView_hit_StartTime)).setText(startTime);
											String enTime = resultInfo.getString("en_time");
											String endTime = String.format("%s/%s/%s %s:%s:%s", enTime.substring(0, 4),
													enTime.substring(4, 6), enTime.substring(6, 8),
													enTime.substring(8, 10), enTime.substring(10, 12),
													enTime.substring(12, 14));
											((TextView) findViewById(R.id.txtView_hit_EndTime)).setText(endTime);
											((TextView) findViewById(R.id.txtView_hit_Running))
													.setText(resultInfo.getString("ex_time"));
											((TextView) findViewById(R.id.txtView_hit_Calories))
													.setText(resultInfo.getString("ex_calories"));
											((TextView) findViewById(R.id.txtView_hit_heartplus))
													.setText(resultInfo.getString("ex_heartplus"));
											((TextView) findViewById(R.id.txtView_hit_Distance))
													.setText(resultInfo.getString("ex_distance"));
											((TextView) findViewById(R.id.txtView_hit_Speed))
													.setText(resultInfo.getString("ex_speed"));

										} else if (resultInfo.getString("ex_variety").equalsIgnoreCase("BIKE")) {
											findViewById(R.id.common_include_health_info_bike)
													.setVisibility(View.VISIBLE);
											findViewById(R.id.listViewHealthInfo).setVisibility(View.GONE);
											((ImageView) findViewById(R.id.imgView_hib_ExVariety))
													.setImageResource(R.drawable.bike1_32x32);

											String stTime = resultInfo.getString("st_time");
											String start = String.format("%s/%s/%s %s:%s:%s", stTime.substring(0, 4),
													stTime.substring(4, 6), stTime.substring(6, 8),
													stTime.substring(8, 10), stTime.substring(10, 12),
													stTime.substring(12, 14));
											((TextView) findViewById(R.id.txtView_hib_StartTime)).setText(start);
											String enTime = resultInfo.getString("en_time");
											String end = String.format("%s/%s/%s %s:%s:%s", enTime.substring(0, 4),
													enTime.substring(4, 6), enTime.substring(6, 8),
													enTime.substring(8, 10), enTime.substring(10, 12),
													enTime.substring(12, 14));
											((TextView) findViewById(R.id.txtView_hib_EndTime)).setText(end);
											((TextView) findViewById(R.id.txtView_hib_Running))
													.setText(resultInfo.getString("ex_time"));

											((TextView) findViewById(R.id.txtView_hib_Calories))
													.setText(resultInfo.getString("ex_calories"));
											((TextView) findViewById(R.id.txtView_hib_heartplus))
													.setText(resultInfo.getString("ex_heartplus"));
											((TextView) findViewById(R.id.txtView_hib_Speed))
													.setText(resultInfo.getString("ex_speed"));

											((TextView) findViewById(R.id.txtView_hib_Level))
													.setText(resultInfo.getString("ex_level"));
											((TextView) findViewById(R.id.txtView_hib_Rpm))
													.setText(resultInfo.getString("ex_rpm"));
											((TextView) findViewById(R.id.txtView_hib_Mets))
													.setText(resultInfo.getString("ex_mets"));

										} else {
											((TextView) findViewById(R.id.txtView_hib_EndTime)).setText("");
											((TextView) findViewById(R.id.txtView_hib_Running)).setText("");

											((TextView) findViewById(R.id.txtView_hib_Calories)).setText("");
											((TextView) findViewById(R.id.txtView_hib_heartplus)).setText("");
											((TextView) findViewById(R.id.txtView_hib_Speed)).setText("");

											((TextView) findViewById(R.id.txtView_hib_Level)).setText("");
											((TextView) findViewById(R.id.txtView_hib_Rpm)).setText("");
											((TextView) findViewById(R.id.txtView_hib_Mets)).setText("");
										}
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
				});

		findViewById(R.id.imgView_hit_Close).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (findViewById(R.id.common_include_health_info_treadmill) != null) {
					findViewById(R.id.common_include_health_info_treadmill).setVisibility(View.GONE);
					findViewById(R.id.listViewHealthInfo).setVisibility(View.VISIBLE);
				}
			}
		});
		findViewById(R.id.imgView_hib_Close).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (findViewById(R.id.common_include_health_info_bike) != null) {
					findViewById(R.id.common_include_health_info_bike).setVisibility(View.GONE);
					findViewById(R.id.listViewHealthInfo).setVisibility(View.VISIBLE);
				}
			}
		});

		findViewById(R.id.img_es_left_arrow).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				mUiHandler.sendEmptyMessage(mUiHandler.MSG_GET_LISTVIEW_HEALTH_BACK);
			}
		});

		findViewById(R.id.img_es_right_arrow).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				mUiHandler.sendEmptyMessage(mUiHandler.MSG_GET_LISTVIEW_HEALTH_NEXT);
			}
		});

		findViewById(R.id.btn_es_health_monthly).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				((TextView) findViewById(R.id.btn_es_health_monthly)).setSelected(true);
				((TextView) findViewById(R.id.btn_es_health_weekly)).setSelected(false);
				((TextView) findViewById(R.id.btn_es_health_daily)).setSelected(false);

				mUiHandler.sendEmptyMessage(mUiHandler.MSG_GET_SUMMARY_HEALTH_MONTHLY);
			}
		});

		findViewById(R.id.btn_es_health_weekly).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((TextView) findViewById(R.id.btn_es_health_monthly)).setSelected(false);
				((TextView) findViewById(R.id.btn_es_health_weekly)).setSelected(true);
				((TextView) findViewById(R.id.btn_es_health_daily)).setSelected(false);

				mUiHandler.sendEmptyMessage(mUiHandler.MSG_GET_SUMMARY_HEALTH_WEEKLY);
			}
		});

		findViewById(R.id.btn_es_health_daily).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((TextView) findViewById(R.id.btn_es_health_monthly)).setSelected(false);
				((TextView) findViewById(R.id.btn_es_health_weekly)).setSelected(false);
				((TextView) findViewById(R.id.btn_es_health_daily)).setSelected(true);

				mUiHandler.sendEmptyMessage(mUiHandler.MSG_GET_SUMMARY_HEALTH_DAILY);
			}
		});

		findViewById(R.id.img_es_health_total_graph).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// Utility.msgbox(ExStatusActivity.this, "Total Graph");
				mUiHandler.sendEmptyMessage(mUiHandler.MSG_GET_SUMMARY_HEALTH_GRAPH);
			}
		});

		findViewById(R.id.img_es_health_monthly_graph).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// Utility.msgbox(ExStatusActivity.this, "Monthly Graph");
				// findViewById(R.id.common_include_view_health_info_graph).setVisibility(View.VISIBLE);
			}
		});

		intent = getIntent();
		if (intent != null) {
			UserID = intent.getStringExtra("UserID");
			Password = intent.getStringExtra("Password");
		}

		// UserID = null;
		// Password = null;

		if (UserID != null) {
			((TextView) findViewById(R.id.btn_es_health_monthly)).setSelected(true);

			// mUiHandler.sendEmptyMessage(mUiHandler.MSG_LOADING);
			mUiHandler.sendEmptyMessage(mUiHandler.MSG_GET_USER);
			mUiHandler.sendEmptyMessage(mUiHandler.MSG_GET_LISTVIEW_HEALTH);
			mUiHandler.sendEmptyMessage(mUiHandler.MSG_GET_SUMMARY_HEALTH_TOTAL);
			mUiHandler.sendEmptyMessage(mUiHandler.MSG_GET_SUMMARY_HEALTH_MONTHLY);
		} else {
			Utility.msgbox(this, "Invalid User !!");
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		super.onResume();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		if (findViewById(R.id.common_include_view_health_info_graph).getVisibility() == View.VISIBLE) {
			findViewById(R.id.common_include_view_health_info_graph).setVisibility(View.GONE);
			return;
		}
		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub

		super.onDestroy();
	}

	private void getUserInfo(String UserID, String Password) {
		try {
			ArrayList<String> mArrayList = new ArrayList<String>();
			StringBuilder mRetJson = new StringBuilder();

			// User information
			mArrayList.add(UserID);
			mArrayList.add(Password);

			if (Application.getHCSAPI().GetExUser(mArrayList, mRetJson)) {
				JSONObject root = new JSONObject(mRetJson.toString());
				JSONArray results = root.getJSONArray("results");

				for (int num = results.length(); num > 0; num--) {
					JSONObject resultInfo = results.getJSONObject(num - 1);
					healthInfoList mHealthInfoList = new healthInfoList();

					String id = resultInfo.getString("id");
					String password = resultInfo.getString("password");
					String name = resultInfo.getString("name");
					String gender = resultInfo.getString("gender");
					String weight = resultInfo.getString("weight");
					String rfid = resultInfo.getString("rfid");

					if (id != null && password != null && name != null && gender != null && weight != null
							&& rfid != null) {
						((TextView) findViewById(R.id.txtView_es_id)).setText(id);
						((TextView) findViewById(R.id.txtView_es_passwd)).setText(password);
						((TextView) findViewById(R.id.txtView_es_name)).setText(name);
						((TextView) findViewById(R.id.txtView_es_weight)).setText(weight);
						((TextView) findViewById(R.id.txtView_es_rfid)).setText(rfid);
						((TextView) findViewById(R.id.txtView_es_gender)).setText(gender);

						if (gender.equalsIgnoreCase("MALE")) {
							((ImageView) findViewById(R.id.img_es_face)).setImageResource(R.drawable.male);
						} else {
							((ImageView) findViewById(R.id.img_es_face)).setImageResource(R.drawable.female);
						}
					}
				}
			} else {
				((TextView) findViewById(R.id.txtView_es_id)).setText("");
				((TextView) findViewById(R.id.txtView_es_passwd)).setText("");
				((TextView) findViewById(R.id.txtView_es_name)).setText("");
				((TextView) findViewById(R.id.txtView_es_weight)).setText("");
				((TextView) findViewById(R.id.txtView_es_rfid)).setText("");
				((TextView) findViewById(R.id.txtView_es_gender)).setText("");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void getListViewMonthlyExerciseInfo(String msg) {
		try {
			// Health information of monthly
			ArrayList<String> mArrayList = new ArrayList<String>();
			StringBuilder mRetJson = new StringBuilder();
			String stTime = null;
			String stTime2 = null;

			if (msg.equalsIgnoreCase("BACK")) {
				if (Integer.parseInt(mCurTime.substring(4, 6)) - 1 <= 0) {
					stTime = String.format("%04d1201000000", Integer.parseInt(mCurTime.substring(0, 4)) - 1);
					stTime2 = String.format("%04d1232000000", Integer.parseInt(mCurTime.substring(0, 4)) - 1);

				} else {
					stTime = String.format("%s%02d01000000", mCurTime.substring(0, 4),
							Integer.parseInt(mCurTime.substring(4, 6)) - 1);
					stTime2 = String.format("%s%02d32000000", mCurTime.substring(0, 4),
							Integer.parseInt(mCurTime.substring(4, 6)) - 1);
				}
				mCurTime = stTime;
				findViewById(R.id.img_es_right_arrow).setVisibility(View.VISIBLE);
			} else if (msg.equalsIgnoreCase("NEXT")) {
				if (Integer.parseInt(mCurTime.substring(4, 6)) + 1 >= 13) {
					stTime = String.format("%04d0101000000", Integer.parseInt(mCurTime.substring(0, 4)) + 1);
					stTime2 = String.format("%04d0132000000", Integer.parseInt(mCurTime.substring(0, 4)) + 1);
				} else {
					stTime = String.format("%s%02d01000000", mCurTime.substring(0, 4),
							Integer.parseInt(mCurTime.substring(4, 6)) + 1);
					stTime2 = String.format("%s%02d32000000", mCurTime.substring(0, 4),
							Integer.parseInt(mCurTime.substring(4, 6)) + 1);
				}
				mCurTime = stTime;

				if (mCurTime.substring(0, 6).equalsIgnoreCase(Utility.getNowDateTime().substring(0, 6))) {
					findViewById(R.id.img_es_right_arrow).setVisibility(View.INVISIBLE);
				}
			} else {
				mCurTime = Utility.getNowDateTime();
				stTime = String.format("%s01000000", mCurTime.substring(0, 6));
				stTime2 = String.format("%s32000000", mCurTime.substring(0, 6));
			}

			mRetJson.delete(0, mRetJson.length());
			mArrayList.clear();
			mArrayList.add(UserID);
			mArrayList.add("0"); // ex_variety
			mArrayList.add(stTime); // st_time
			mArrayList.add(stTime2); // st_time2
			mArrayList.add("0"); // ex_calories
			mArrayList.add("0"); // ex_distance
			mArrayList.add("0"); // ex_time
			mArrayList.add("0"); // sum
			mArrayList.add("0"); // limit
			mArrayList.add("0"); // desc
			mArrayList.add("0"); // graph
			mHealthInfoAdapter.clear();
			// mHealthInfoAdapter.notifyDataSetChanged();

			if (Application.getHCSAPI().GetExResult(mArrayList, mRetJson)) {

				JSONObject root = new JSONObject(mRetJson.toString());
				JSONArray results = root.getJSONArray("results");

				((TextView) findViewById(R.id.txtView_hs_health_list_num)).setText(String.format("%s/%s : %d",
						mCurTime.substring(0, 4), mCurTime.substring(4, 6), results.length()));
				for (int num = results.length(); num > 0; num--) {
					JSONObject resultInfo = results.getJSONObject(num - 1);
					healthInfoList mHealthInfoList = new healthInfoList();

					String ex_variety = resultInfo.getString("ex_variety");
					String st_time = resultInfo.getString("st_time");
					String ex_time = resultInfo.getString("ex_time");
					String ex_calories = resultInfo.getString("ex_calories");
					String ex_heartplus = resultInfo.getString("ex_heartplus");
					String ex_distance = resultInfo.getString("ex_distance");

					if (ex_variety != null && st_time != null && ex_time != null && ex_calories != null
							&& ex_heartplus != null && ex_distance != null) {
						if (ex_variety.equalsIgnoreCase("TREADMILL")) {
							mHealthInfoList.ex_variety = R.drawable.treadmill_64x64;
						} else if (ex_variety.equalsIgnoreCase("BIKE")) {
							mHealthInfoList.ex_variety = R.drawable.bike1_64x64;
						}
						String startTime = String.format("%s/%s/%s %s:%s:%s", st_time.substring(0, 4),
								st_time.substring(4, 6), st_time.substring(6, 8), st_time.substring(8, 10),
								st_time.substring(10, 12), st_time.substring(12, 14));

						mHealthInfoList.st_time = startTime;
						mHealthInfoList.ex_time = ex_time;
						mHealthInfoList.ex_calories = ex_calories;
						mHealthInfoList.ex_heartplus = ex_heartplus;
						mHealthInfoList.ex_distance = ex_distance;
						mHealthInfoAdapter.add(mHealthInfoList);
					}
				}
			} else {
				((TextView) findViewById(R.id.txtView_hs_health_list_num))
						.setText(String.format("%s/%s : 0", mCurTime.substring(0, 4), mCurTime.substring(4, 6)));
			}

			Log.d((new Exception()).getStackTrace()[0].getMethodName(), "Time : " + Utility.getNowDateTime());
			mHealthInfoAdapter.notifyDataSetChanged();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void getSummaryTotalExerciseInfo() {
		try {
			// total
			ArrayList<String> mArrayList = new ArrayList<String>();
			StringBuilder mRetJson = new StringBuilder();
			// Health information of monthly
			String curTime = Utility.getNowDateTime();
			String stTime = String.format("20160101000000");
			String stTime2 = String.format("%s32000000", curTime.substring(0, 6));

			// information of total exercise - start time
			mRetJson.delete(0, mRetJson.length());
			mArrayList.clear();
			mArrayList.add(UserID);
			mArrayList.add("0"); // ex_variety
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

					mStartTime = resultInfo.getString("st_time");
					if (mStartTime != null) {
						String startTime = String.format("%s/%s/%s %s:%s:%s", mStartTime.substring(0, 4),
								mStartTime.substring(4, 6), mStartTime.substring(6, 8), mStartTime.substring(8, 10),
								mStartTime.substring(10, 12), mStartTime.substring(12, 14));

						((TextView) findViewById(R.id.txtView_hs_total_stime)).setText(startTime);
					}
				}
			} else {
				((TextView) findViewById(R.id.txtView_hs_total_stime)).setText("");
			}

			// information of total exercise - end time
			mRetJson.delete(0, mRetJson.length());
			mArrayList.clear();
			mArrayList.add(UserID);
			mArrayList.add("0"); // ex_variety
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

					mEndTime = resultInfo.getString("en_time");
					if (mEndTime != null) {
						String EndTime = String.format("%s/%s/%s %s:%s:%s", mEndTime.substring(0, 4),
								mEndTime.substring(4, 6), mEndTime.substring(6, 8), mEndTime.substring(8, 10),
								mEndTime.substring(10, 12), mEndTime.substring(12, 14));

						((TextView) findViewById(R.id.txtView_hs_total_etime)).setText(EndTime);
					}
				}
			} else {
				((TextView) findViewById(R.id.txtView_hs_total_etime)).setText("");
			}

			if (!(((TextView) findViewById(R.id.txtView_hs_total_stime)).getText().toString().equalsIgnoreCase("")
					|| ((TextView) findViewById(R.id.txtView_hs_total_etime)).getText().toString()
							.equalsIgnoreCase(""))) {
				// information of total exercise - calories
				mRetJson.delete(0, mRetJson.length());
				mArrayList.clear();
				mArrayList.add(UserID);
				mArrayList.add("0"); // ex_variety
				mArrayList.add(stTime); // st_time
				mArrayList.add(stTime2); // st_time2
				mArrayList.add("1"); // ex_calories
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
						String sum_ex_time = resultInfo.getString("SUM(`ex_time`)");
						String sum_ex_distance = resultInfo.getString("SUM(`ex_distance`)");

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
							((TextView) findViewById(R.id.txtView_hs_total_calories)).setText(sum_ex_calories2);
						} else {
							((TextView) findViewById(R.id.txtView_hs_total_calories)).setText(sum_ex_calories);
						}

						if (sum_ex_distance.length() > 3) {
							String sum_ex_distance2 = String.format("%d,%d", Integer.parseInt(sum_ex_distance) / 1000,
									Integer.parseInt(sum_ex_distance) % 1000);
							((TextView) findViewById(R.id.txtView_hs_total_distance)).setText(sum_ex_distance2);
						} else {
							((TextView) findViewById(R.id.txtView_hs_total_distance)).setText(sum_ex_distance);
						}

						String sum_ex_time2 = String.format("%02d:%02d:00", Integer.parseInt(sum_ex_time) / 60,
								Integer.parseInt(sum_ex_time) % 60);
						((TextView) findViewById(R.id.txtView_hs_total_time)).setText(sum_ex_time2);

						return;
					}
				}
			}

			((TextView) findViewById(R.id.txtView_hs_total_calories)).setText("");
			((TextView) findViewById(R.id.txtView_hs_total_distance)).setText("");
			((TextView) findViewById(R.id.txtView_hs_total_time)).setText("");
		} catch (Exception e) {
			// TODO: handle exception
		}
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

	private void getSummaryExerciseInfoGraph() {
		try {
			Boolean queryStop = false;
			String queryStartDate = null;
			String queryEndDate = null;
			ArrayList<String> mArrayList = new ArrayList<String>();
			StringBuilder mRetJson = new StringBuilder();

			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			Calendar cal = Calendar.getInstance();

			Date beginDate = formatter.parse(mStartTime);
			Date endDate = formatter.parse(mEndTime);
			long diff = endDate.getTime() - beginDate.getTime();
			long diffDays = diff / (24 * 60 * 60 * 1000);
			int x_axis = (int) diffDays / 10;

			if (x_axis <= 0)
				x_axis = 1;

			float[] bike_ex_calories = new float[10];
			float[] Treadmill_ex_calories = new float[10];
			float[] bikeTreadmill_ex_calories = new float[10];

			float[] bike_ex_heartplus = new float[10];
			float[] Treadmill_ex_heartplus = new float[10];
			float[] bikeTreadmill_ex_heartplus = new float[10];

			float[] bike_ex_time = new float[10];
			float[] Treadmill_ex_time = new float[10];
			float[] bikeTreadmill_ex_time = new float[10];

			float[] bike_ex_distance = new float[10];
			float[] Treadmill_ex_distance = new float[10];
			float[] bikeTreadmill_ex_distance = new float[10];

			int xNum = 10; // fix
			for (int i = 1; i <= x_axis; i++) {

				if (queryStartDate == null && queryEndDate == null) {
					queryEndDate = mEndTime;

					cal.setTime(endDate);
					cal.add(Calendar.DAY_OF_YEAR, -10);
					queryStartDate = new SimpleDateFormat("yyyyMMddHHmmss").format(cal.getTime());
				} else {
					queryEndDate = queryStartDate;
					endDate = formatter.parse(queryEndDate);

					cal.setTime(endDate);
					cal.add(Calendar.DAY_OF_YEAR, -10);
					queryStartDate = new SimpleDateFormat("yyyyMMddHHmmss").format(cal.getTime());
				}

				if ((Double.parseDouble(queryStartDate) - Double.parseDouble(mStartTime) <= 0) || (i == 10)
						|| (x_axis == i)) {
					queryStartDate = mStartTime;
					queryStop = true;
				}

				mRetJson.delete(0, mRetJson.length());
				mArrayList.clear();
				mArrayList.add(UserID);
				mArrayList.add("0"); // ex_variety
				mArrayList.add(queryStartDate); // st_time
				mArrayList.add(queryEndDate); // st_time2
				mArrayList.add("0"); // ex_calories
				mArrayList.add("0"); // ex_distance
				mArrayList.add("0"); // ex_time
				mArrayList.add("0"); // sum
				mArrayList.add("0"); // limit
				mArrayList.add("0"); // desc
				mArrayList.add("1"); // graph

				if (Application.getHCSAPI().GetExResult(mArrayList, mRetJson)) {

					JSONObject root = new JSONObject(mRetJson.toString());
					JSONArray results = root.getJSONArray("results");
					float ex_c = 0;
					float ex_h = 0;
					float ex_t = 0;
					float ex_d = 0;

					if (results.length() > 0) {
						for (int num = 0; num < results.length(); num++) {
							JSONObject resultInfo = results.getJSONObject(num);

							String ex_calories = resultInfo.getString("SUM(`ex_calories`)");
							String ex_heartplus = resultInfo.getString("SUM(`ex_heartplus`)");
							String ex_time = resultInfo.getString("SUM(`ex_time`)");
							String ex_distance = resultInfo.getString("SUM(`ex_distance`)");

							if (ex_calories.equalsIgnoreCase("")) {
								ex_calories = "0";
							}
							if (ex_heartplus.equalsIgnoreCase("")) {
								ex_heartplus = "0";
							}
							if (ex_time.equalsIgnoreCase("")) {
								ex_time = "0";
							}
							if (ex_distance.equalsIgnoreCase("")) {
								ex_distance = "0";
							}

							ex_c = Float.parseFloat(ex_calories);
							ex_h = Float.parseFloat(ex_heartplus);
							ex_t = Float.parseFloat(ex_time);
							ex_d = Float.parseFloat(ex_distance);

							if (num == 0) {
								--xNum;

								if (ex_c <= mGraph.maxValue) {
									Treadmill_ex_calories[xNum] = ex_c;
								} else {
									Treadmill_ex_calories[xNum] = mGraph.maxValue;
								}
								if (ex_h <= mGraph.maxValue) {
									Treadmill_ex_heartplus[xNum] = ex_h;
								} else {
									Treadmill_ex_heartplus[xNum] = mGraph.maxValue;
								}
								if (ex_t <= mGraph.maxValue) {
									Treadmill_ex_time[xNum] = ex_t;
								} else {
									Treadmill_ex_time[xNum] = mGraph.maxValue;
								}
								if (ex_d <= mGraph.maxValue) {
									Treadmill_ex_distance[xNum] = ex_d;
								} else {
									Treadmill_ex_distance[xNum] = mGraph.maxValue;
								}
							} else {
								if (ex_c <= mGraph.maxValue) {
									bike_ex_calories[xNum] = ex_c;
								} else {
									bike_ex_calories[xNum] = mGraph.maxValue;
								}
								if (ex_h <= mGraph.maxValue) {
									bike_ex_heartplus[xNum] = ex_h;
								} else {
									bike_ex_heartplus[xNum] = mGraph.maxValue;
								}
								if (ex_t <= mGraph.maxValue) {
									bike_ex_time[xNum] = ex_t;
								} else {
									bike_ex_time[xNum] = mGraph.maxValue;
								}
								if (ex_d <= mGraph.maxValue) {
									bike_ex_distance[xNum] = ex_d;
								} else {
									bike_ex_distance[xNum] = mGraph.maxValue;
								}
							}
						}

						if ((Treadmill_ex_calories[xNum] + bike_ex_calories[xNum]) <= mGraph.maxValue) {
							bikeTreadmill_ex_calories[xNum] = Treadmill_ex_calories[xNum] + bike_ex_calories[xNum];
						} else {
							bikeTreadmill_ex_calories[xNum] = mGraph.maxValue;
						}
						if ((Treadmill_ex_heartplus[xNum] + bike_ex_heartplus[xNum]) <= mGraph.maxValue) {
							bikeTreadmill_ex_heartplus[xNum] = Treadmill_ex_heartplus[xNum] + bike_ex_heartplus[xNum];
						} else {
							bikeTreadmill_ex_heartplus[xNum] = mGraph.maxValue;
						}
						if ((Treadmill_ex_time[xNum] + bike_ex_time[xNum]) <= mGraph.maxValue) {
							bikeTreadmill_ex_time[xNum] = Treadmill_ex_time[xNum] + bike_ex_time[xNum];
						} else {
							bikeTreadmill_ex_time[xNum] = mGraph.maxValue;
						}
						if ((Treadmill_ex_distance[xNum] + bike_ex_distance[xNum]) <= mGraph.maxValue) {
							bikeTreadmill_ex_distance[xNum] = Treadmill_ex_distance[xNum] + bike_ex_distance[xNum];
						} else {
							bikeTreadmill_ex_distance[xNum] = mGraph.maxValue;
						}

					}
				}
				
				if(queryStop) {
					break;
				}
			}

			if (findViewById(R.id.common_include_view_health_info_graph).getVisibility() == View.GONE) {
				findViewById(R.id.common_include_view_health_info_graph).setVisibility(View.VISIBLE);
			}

			// graph
			mGraph.setLineGraph("CALORIES", ExStatusActivity.this, Treadmill_ex_calories, bike_ex_calories,
					bikeTreadmill_ex_calories, (ViewGroup) findViewById(R.id.layoutLineGraphView_exCalories));
			mGraph.setLineGraph("HEARTPULSE", ExStatusActivity.this, Treadmill_ex_heartplus, bike_ex_heartplus,
					bikeTreadmill_ex_heartplus, (ViewGroup) findViewById(R.id.layoutLineGraphView_exHeartPulse));
			mGraph.setLineGraph("TIME", ExStatusActivity.this, Treadmill_ex_time, bike_ex_time, bikeTreadmill_ex_time,
					(ViewGroup) findViewById(R.id.layoutLineGraphView_exTime));
			mGraph.setLineGraph("DISTANCE", ExStatusActivity.this, Treadmill_ex_distance, bike_ex_distance,
					bikeTreadmill_ex_distance, (ViewGroup) findViewById(R.id.layoutLineGraphView_exDistance));

		} catch (Exception e) {
			// TODO: handle exception
			Log.e("Error", "error");
		}

	}

	private class UiHandler extends Handler {

		public static final int MSG_LOADING = 1;
		public static final int MSG_GET_USER = 2;
		public static final int MSG_GET_LISTVIEW_HEALTH = 3;
		public static final int MSG_GET_LISTVIEW_HEALTH_BACK = 4;
		public static final int MSG_GET_LISTVIEW_HEALTH_NEXT = 5;
		public static final int MSG_GET_SUMMARY_HEALTH_TOTAL = 6;
		public static final int MSG_GET_SUMMARY_HEALTH_MONTHLY = 7;
		public static final int MSG_GET_SUMMARY_HEALTH_WEEKLY = 8;
		public static final int MSG_GET_SUMMARY_HEALTH_DAILY = 9;
		public static final int MSG_GET_SUMMARY_HEALTH_GRAPH = 10;

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_LOADING:
				if (findViewById(R.id.common_include_loading_ani).getVisibility() == View.GONE) {
					findViewById(R.id.common_include_loading_ani).setVisibility(View.VISIBLE);
					mFrameAnimation.start();

					/*
					 * mAni1 = new CustomAniDrawable(); BitmapDrawable frame1 =
					 * new BitmapDrawable(); Resources resource =
					 * getApplicationContext().getResources();
					 * 
					 * frame1 = (BitmapDrawable)
					 * resource.getDrawable(R.drawable.loading_0);
					 * mAni1.addFrame(frame1, 300); frame1 = (BitmapDrawable)
					 * resource.getDrawable(R.drawable.loading_1);
					 * mAni1.addFrame(frame1, 300); frame1 = (BitmapDrawable)
					 * resource.getDrawable(R.drawable.loading_2);
					 * mAni1.addFrame(frame1, 300); frame1 = (BitmapDrawable)
					 * resource.getDrawable(R.drawable.loading_3);
					 * mAni1.addFrame(frame1, 300); frame1 = (BitmapDrawable)
					 * resource.getDrawable(R.drawable.loading_4);
					 * mAni1.addFrame(frame1, 300); frame1 = (BitmapDrawable)
					 * resource.getDrawable(R.drawable.loading_5);
					 * mAni1.addFrame(frame1, 300);
					 * 
					 * mAni1.setOneShot(false);
					 * findViewById(R.id.imgView_es_loading).
					 * setBackgroundDrawable(mAni1); mAni1.start();
					 * mAni1.setDuration(300);
					 */

				} else {
					// mAni1.stop();

					mFrameAnimation.stop();
					findViewById(R.id.common_include_loading_ani).setVisibility(View.GONE);
				}
				break;

			case MSG_GET_USER:
				getUserInfo(UserID, Password);
				break;

			case MSG_GET_LISTVIEW_HEALTH:
				getListViewMonthlyExerciseInfo("");
				break;

			case MSG_GET_LISTVIEW_HEALTH_BACK:
				getListViewMonthlyExerciseInfo("BACK");
				break;

			case MSG_GET_LISTVIEW_HEALTH_NEXT:
				getListViewMonthlyExerciseInfo("NEXT");
				break;

			case MSG_GET_SUMMARY_HEALTH_TOTAL:
				getSummaryTotalExerciseInfo();
				break;

			case MSG_GET_SUMMARY_HEALTH_MONTHLY:
				getSummaryExerciseInfo("monthly");
				break;

			case MSG_GET_SUMMARY_HEALTH_WEEKLY:
				getSummaryExerciseInfo("weekly");
				break;

			case MSG_GET_SUMMARY_HEALTH_DAILY:
				getSummaryExerciseInfo("daily");
				break;

			case MSG_GET_SUMMARY_HEALTH_GRAPH:
				getSummaryExerciseInfoGraph();
				break;

			}
		}
	}
}

class HealthInfoAdapter extends BaseAdapter {
	Context context;
	int layout;
	ArrayList<healthInfoList> al;
	LayoutInflater inf;

	public HealthInfoAdapter(Context context, int layout, ArrayList<healthInfoList> al) {
		this.context = context;
		this.layout = layout;
		this.al = al;
		this.inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return al.size();
	}

	@Override
	public Object getItem(int position) {
		return al.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = this.inf.inflate(this.layout, parent, false);

			holder = new ViewHolder();
			holder.iv = (ImageView) convertView.findViewById(R.id.imgView_hi_listExVariety);
			holder.tv1 = (TextView) convertView.findViewById(R.id.txtView_hi_listExtime);
			holder.tv2 = (TextView) convertView.findViewById(R.id.txtView_hi_listCalories);
			holder.tv3 = (TextView) convertView.findViewById(R.id.txtView_hi_listHeartplus);
			holder.tv4 = (TextView) convertView.findViewById(R.id.txtView_hi_listDistance);
			holder.tv5 = (TextView) convertView.findViewById(R.id.txtView_hi_listStTime);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		convertView.setTag(holder);

		if ((position % 2) > 0) {
			holder.tv1.setBackgroundResource(R.color.lightcyan);
			holder.tv2.setBackgroundResource(R.color.lightcyan);
			holder.tv3.setBackgroundResource(R.color.lightcyan);
			holder.tv4.setBackgroundResource(R.color.lightcyan);
			holder.tv5.setBackgroundResource(R.color.lightcyan);
		} else {
			holder.tv1.setBackgroundResource(R.color.lightyellow);
			holder.tv2.setBackgroundResource(R.color.lightyellow);
			holder.tv3.setBackgroundResource(R.color.lightyellow);
			holder.tv4.setBackgroundResource(R.color.lightyellow);
			holder.tv5.setBackgroundResource(R.color.lightyellow);
		}

		healthInfoList s = al.get(position);
		holder.tv1.setText(s.ex_time);
		holder.tv2.setText(s.ex_calories);
		holder.tv3.setText(s.ex_heartplus);
		holder.tv4.setText(s.ex_distance);
		holder.tv5.setText(s.st_time);
		holder.iv.setImageResource(s.ex_variety);

		// Log.d((new Exception()).getStackTrace()[0].getMethodName(), "Time
		// : "
		// + Utility.getNowDateTime() + " position :" + position);

		return convertView;
	}

	public void add(healthInfoList hl) {
		this.al.add(hl);
	}

	public void remove(int position) {
		this.al.remove(position);
	}

	public void clear() {
		this.al.clear();
	}
}

class healthInfoList {
	String st_time = null;
	String ex_time = null;
	String ex_calories = null;
	String ex_heartplus = null;
	String ex_distance = null;
	int ex_variety;

	/*
	 * public healthInfoList(int img, String msg1, String msg2, String msg3,
	 * String msg4, String msg5) { this.ex_variety = img; this.st_time = msg1;
	 * this.ex_time = msg2; this.ex_calories = msg3; this.ex_heartplus = msg4;
	 * this.ex_distance = msg5; }
	 */

	public healthInfoList() {
	}
}

class ViewHolder {
	ImageView iv;
	TextView tv1;
	TextView tv2;
	TextView tv3;
	TextView tv4;
	TextView tv5;
}
