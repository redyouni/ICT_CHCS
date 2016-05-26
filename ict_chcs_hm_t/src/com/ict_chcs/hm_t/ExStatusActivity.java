package com.ict_chcs.hm_t;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ict_chcs.healthMonitor.R;
import com.ict_chcs.hm_t.Adapter.Utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ExStatusActivity extends Activity {

	public static final String TAG = "ExStatusActivity";

	String UserID = null;
	String Password = null;
	Intent intent = null;
	Handler handler = new Handler();
	ArrayList<healthInfoList> ArrayHealthInfo = new ArrayList<healthInfoList>();

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

		if (Utility.getBuildMode(this) > 0) {
			// DEBUG CODE
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					((TextView) findViewById(R.id.txtView_network_status))
							.setText(Utility.getWifiInfo(ExStatusActivity.this));
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

		intent = getIntent();
		if (intent != null) {
			UserID = intent.getStringExtra("UserID");
			Password = intent.getStringExtra("Password");
		}

		new Handler() {
			public void handleMessage(Message msg) {

				try {
					if (UserID != null) {
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
										((ImageView) findViewById(R.id.img_es_face))
												.setImageResource(R.drawable.female);
									}
								}
							}
						}

						// Health information
						mRetJson.delete(0, mRetJson.length());
						mArrayList.clear();
						mArrayList.add(UserID);
						mArrayList.add("0");
						mArrayList.add("0");
						ArrayHealthInfo.clear();

						if (Application.getHCSAPI().GetExResult(mArrayList, mRetJson)) {

							JSONObject root = new JSONObject(mRetJson.toString());
							JSONArray results = root.getJSONArray("results");

							for (int num = results.length(); num > 0; num--) {
								JSONObject resultInfo = results.getJSONObject(num - 1);
								healthInfoList mHealthInfoList = new healthInfoList();

								String ex_variety = resultInfo.getString("ex_variety");
								String ex_time = resultInfo.getString("ex_time");
								String ex_calories = resultInfo.getString("ex_calories");
								String ex_heartplus = resultInfo.getString("ex_heartplus");
								String ex_distance = resultInfo.getString("ex_distance");

								if (ex_variety != null && ex_time != null && ex_calories != null && ex_heartplus != null
										&& ex_distance != null) {
									if (ex_variety.equalsIgnoreCase("TREADMILL")) {
										mHealthInfoList.ex_variety = R.drawable.treadmill_64x64;
									} else if (ex_variety.equalsIgnoreCase("BIKE")) {
										mHealthInfoList.ex_variety = R.drawable.bike1_64x64;
									}
									mHealthInfoList.ex_time = ex_time;
									mHealthInfoList.ex_calories = ex_calories;
									mHealthInfoList.ex_heartplus = ex_heartplus;
									mHealthInfoList.ex_distance = ex_distance;
									ArrayHealthInfo.add(mHealthInfoList);
								}
							}
						}
						// adapter
						MyAdapter adapter = new MyAdapter(getApplicationContext(), R.layout.view_health_info_list,
								ArrayHealthInfo);

						// adapterView - ListView, GridView
						((ListView) findViewById(R.id.listViewHealthInfo)).setAdapter(adapter);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

			};
		}.sendEmptyMessageDelayed(0, 1000);

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

class MyAdapter extends BaseAdapter {
	Context context;
	int layout;
	ArrayList<healthInfoList> al;
	LayoutInflater inf;

	public MyAdapter(Context context, int layout, ArrayList<healthInfoList> al) {
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
		if (convertView == null)
			convertView = inf.inflate(layout, null);

		ImageView iv = (ImageView) convertView.findViewById(R.id.imgExVariety);
		TextView tv1 = (TextView) convertView.findViewById(R.id.txtViewExtime);
		TextView tv2 = (TextView) convertView.findViewById(R.id.txtViewCalories);
		TextView tv3 = (TextView) convertView.findViewById(R.id.txtViewheartplus);
		TextView tv4 = (TextView) convertView.findViewById(R.id.txtViewDistance);

		healthInfoList s = al.get(position);
		tv1.setText(s.ex_time);
		tv2.setText(s.ex_calories);
		tv3.setText(s.ex_heartplus);
		tv4.setText(s.ex_distance);
		iv.setImageResource(s.ex_variety);
		return convertView;
	}
}

class healthInfoList {
	String ex_time = null;
	String ex_calories = null;
	String ex_heartplus = null;
	String ex_distance = null;
	int ex_variety;

	public healthInfoList(int img, String msg1, String msg2, String msg3, String msg4) {
		this.ex_variety = img;
		this.ex_time = msg1;
		this.ex_calories = msg2;
		this.ex_heartplus = msg3;
		this.ex_distance = msg4;
	}

	public healthInfoList() {
	}
}
