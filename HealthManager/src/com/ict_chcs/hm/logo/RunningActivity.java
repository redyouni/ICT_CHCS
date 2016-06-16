package com.ict_chcs.hm.logo;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.logo.R;
import com.ict_chcs.hm.logo.Application.MyGlobals;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
public class RunningActivity extends Activity {
	ImageView imgBack1, imgResult;
	TextView txtStartTime, txtEndTime, txtExTime, txtSpeed, txtDistance, txtHeartRate, txtCalories;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_running);
		
		  imgBack1 = (ImageView)findViewById(R.id.run_back);
		  imgResult = (ImageView)findViewById(R.id.run_result);
		  txtCalories = (TextView)findViewById(R.id.textView_run_Calories);
		  txtEndTime = (TextView)findViewById(R.id.textView_run_End);
		  txtExTime = (TextView)findViewById(R.id.textView_run_Time);
		  txtHeartRate = (TextView)findViewById(R.id.textView_run_Heart);
		  txtDistance = (TextView)findViewById(R.id.textView_run_Distance);
		  txtSpeed = (TextView)findViewById(R.id.textView_run_Speed);
		  txtStartTime = (TextView)findViewById(R.id.textView_run_Start);
		
		  String startTime, endTime, exTime, speed, distance, heartRate, calories;
		  ArrayList<String> mArrayList;
		  StringBuilder mRetJson = new StringBuilder ();
		  mArrayList = new ArrayList<String>();
  		  mArrayList.add(MyGlobals.getInstance().getmMyUSERID());
		  mArrayList.add("TREADMILL");
		  
		  if(Application.getHCSAPI().GetExLatestResult(mArrayList, mRetJson) == true ) {
				try {
					JSONObject root = new JSONObject(mRetJson.toString());
					JSONArray results = root.getJSONArray("results");

					for (int num = results.length(); num > 0; num--) {
						JSONObject resultInfo = results.getJSONObject(num - 1);
						
						startTime = resultInfo.getString("st_time");
						endTime = resultInfo.getString("en_time");
						exTime = resultInfo.getString("ex_time");
						speed = resultInfo.getString("ex_speed");
						distance = resultInfo.getString("ex_distance");
						heartRate = resultInfo.getString("ex_heartplus");
						calories = resultInfo.getString("ex_calories");
						
						txtCalories.setText(calories);
						txtEndTime.setText(endTime);
						txtExTime.setText(exTime);
						txtHeartRate.setText(heartRate);
						txtDistance.setText(distance);
						txtSpeed.setText(speed);
						txtStartTime.setText(startTime);
			
					}
					
				}//try
				 catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				 	}
				}
		  
		  imgBack1.setOnClickListener(new OnClickListener() {
		   @Override
		   public void onClick(View v) {
		    Application.getHCSAPI();
			// TODO Auto-generated method stub
			   Intent intentSub = new Intent(RunningActivity.this,MenuActivity.class);
		       startActivity(intentSub);
		       finish();
		   }
		  });
		  
		  imgResult.setOnClickListener(new OnClickListener() {
		   @Override
		   public void onClick(View v) {
		    // TODO Auto-generated method stub
		    final Intent intentSub;
		              intentSub = new Intent(RunningActivity.this,ResultRunActivity.class);
		              startActivity(intentSub); 
		              finish();
		   }
		  });
	}//onCreate
    public boolean onKeyDown(int keyCode,KeyEvent event)
    {
      if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
      {
       AlertDialog dialog;
       dialog = new AlertDialog.Builder(this).setMessage("종료하시겠습니까?")
           .setPositiveButton("예", new DialogInterface.OnClickListener() {   
              public void onClick(DialogInterface dialog, int which) {
               // TODO Auto-generated method stub
             	 moveTaskToBack(true); 

            	  finish(); 

            	  android.os.Process.killProcess(android.os.Process.myPid());
              }
             })
              .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            
            public void onClick(DialogInterface dialog, int which) {
             // TODO Auto-generated method stub
             dialog.cancel();
            }
           }) 
           .show();    
       return true;
      }
      return super.onKeyDown(keyCode, event);
    }
    
   }

