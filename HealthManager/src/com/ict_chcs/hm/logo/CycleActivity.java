package com.ict_chcs.hm.logo;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.logo.R;
import com.ict_chcs.hm.Adapter.Utility;
import com.ict_chcs.hm.logo.Application.MyGlobals;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
public class CycleActivity extends Activity {
	ImageView imgCy_back, imgCy_result;
	TextView txtStartTime, txtEndTime, txtExTime, txtSpeed, txtLevel, txtHeartRate, txtCalories;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cycle);
		
		imgCy_back = (ImageView)findViewById(R.id.cyc_back);
		imgCy_result = (ImageView)findViewById(R.id.cyc_result);
		txtCalories = (TextView)findViewById(R.id.textView_clc_Calories);
		txtEndTime = (TextView)findViewById(R.id.textView_clc_End);
		txtExTime = (TextView)findViewById(R.id.textView_clc_Time);
		txtHeartRate = (TextView)findViewById(R.id.textView_clc_Heart);
		txtLevel = (TextView)findViewById(R.id.textView_clc_Level);
		txtSpeed = (TextView)findViewById(R.id.textView_clc_Speed);
		txtStartTime = (TextView)findViewById(R.id.textView_clc_Start);
		
		String startTime, endTime, exTime, speed, level, heartRate, calories;
		ArrayList<String> mArrayList;
		StringBuilder mRetJson = new StringBuilder ();
		mArrayList = new ArrayList<String>();
		mArrayList.add(MyGlobals.getInstance().getmMyUSERID());
		mArrayList.add("BIKE");
				
		if(Application.getHCSAPI().GetExLatestResult(mArrayList, mRetJson) == true) {
			try {
				JSONObject root = new JSONObject(mRetJson.toString());
				JSONArray results = root.getJSONArray("results");

				for (int num = results.length(); num > 0; num--) {
					JSONObject resultInfo = results.getJSONObject(num - 1);
					
					startTime = resultInfo.getString("st_time");
					endTime = resultInfo.getString("en_time");
					exTime = resultInfo.getString("ex_time");
					speed = resultInfo.getString("ex_speed");
					level = resultInfo.getString("ex_level");
					heartRate = resultInfo.getString("ex_heartplus");
					calories = resultInfo.getString("ex_calories");
					
					txtCalories.setText(calories);
					txtEndTime.setText(endTime);
					txtExTime.setText(exTime);
					txtHeartRate.setText(heartRate);
					txtLevel.setText(level);
					txtSpeed.setText(speed);
					txtStartTime.setText(startTime);
		
				}
				
			}//try
			 catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 	}
		}
			
		  // 메인 메뉴로 돌아가기
		  imgCy_back.setOnClickListener(new OnClickListener() {
		   @Override
		   public void onClick(View v) {
		    Application.getHCSAPI();
			// TODO Auto-generated method stub
			   Intent intentSub = new Intent(CycleActivity.this,MenuActivity.class);
		       startActivity(intentSub);
		   }
		  });
		  
		  // 싸이클 운동 정보
		  imgCy_result.setOnClickListener(new OnClickListener() {
		   @Override
		   public void onClick(View v) {
		    // TODO Auto-generated method stub
		    final Intent intentSub;
		              intentSub = new Intent(CycleActivity.this,ResultCycleActivity.class);
		              startActivity(intentSub); 
		   }
		  });
	}//onCreate
}