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
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class ResultCycleActivity extends Activity {
	ImageView btnCycDay,btnCycWeek,btnCycMonth;
	ImageView img_cyc_Back, img_cyc_Menu;
	   ImageView img_cyc_Cal, img_cyc_Km, img_cyc_Time;
	   ImageView img_cyc_day,img_cyc_week,img_cyc_month;
	   BitmapDrawable d_km, d_cal, d_time;
	   int day_cyc_km ;
	   int day_cyc_cal ;
	   int day_cyc_time;   
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cycle_result);
		
		btnCycDay = (ImageView) findViewById(R.id.btnCycDay);
		btnCycWeek = (ImageView) findViewById(R.id.btnCycWeek);
		btnCycMonth = (ImageView) findViewById(R.id.btnCycMonth);
		img_cyc_Back = (ImageView)findViewById(R.id.cyc_re_back);
		img_cyc_Menu = (ImageView)findViewById(R.id.cyc_re_main);
		img_cyc_day = (ImageView)findViewById(R.id.imageView_cyc_day1);
        img_cyc_Km = (ImageView) findViewById(R.id.imageView_cyc_day2);
        img_cyc_Cal = (ImageView) findViewById(R.id.imageView_cyc_day3);
        img_cyc_Time = (ImageView) findViewById(R.id.imageView_cyc_day4);
        img_cyc_Km.setImageBitmap(null);
        img_cyc_Cal.setImageBitmap(null);
        img_cyc_Time.setImageBitmap(null);
       
		img_cyc_Back.setOnClickListener (new OnClickListener() {
        	   @Override
        	   public void onClick(View v) {
        	    // TODO Auto-generated method stub
        	    final Intent intentSub;
        	              intentSub = new Intent(ResultCycleActivity.this, CycleActivity.class);
        	              startActivity(intentSub); 
        	   }
        	  });
		img_cyc_Menu.setOnClickListener (new OnClickListener() {
     	   @Override
     	   public void onClick(View v) {
     	    // TODO Auto-generated method stub
     	    final Intent intentSub;
     	              intentSub = new Intent(ResultCycleActivity.this, MenuActivity.class);
     	              startActivity(intentSub); 
     	   }
     	  });
     
		btnCycDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	ArrayList<String> mArrayList;
         	   	StringBuilder mRetJson = new StringBuilder();
         	   	mArrayList = new ArrayList<String>();
         	   	String[] st_time = new String[1];
       
        		for(int i= -1 ; i<0 ; i++) {
        			st_time[i+1] = Utility.get7DaysDateTime(i);
        			mArrayList.clear();
        			mArrayList.add(MyGlobals.getInstance().getmMyUSERID());
        			mArrayList.add("BIKE");
        			mArrayList.add(st_time[i+1]);
        			
        			if(Application.getHCSAPI().GetExResult(mArrayList, mRetJson) == true) {
        				try {
        					JSONObject root = new JSONObject(mRetJson.toString());
        					JSONArray results = root.getJSONArray("results");
        					
        					for (int num = results.length(); num > 0; num--) {
        						JSONObject resultInfo = results.getJSONObject(num - 1);
        						
        						day_cyc_time += Integer.parseInt(resultInfo.getString("ex_time"));
        						day_cyc_km += Integer.parseInt(resultInfo.getString("ex_distance"));
        						day_cyc_cal += Integer.parseInt(resultInfo.getString("ex_calories"));
        			
        					}
        					
        				}//try
        				 catch (JSONException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				 	}
        				}else Utility.msgbox(getApplicationContext(), "하루동안 운동을 하지 않았습니다.");
        		}//for
        		
            	img_cyc_day.setImageResource(R.drawable.day);
            	 if (day_cyc_cal == 0) {
                     img_cyc_Cal.setImageResource(R.drawable.drc1);
                 } else if (day_cyc_cal> 0 && day_cyc_cal<101) {
                    img_cyc_Cal.setImageResource(R.drawable.drc2);
                 } else if (day_cyc_cal>= 101 && day_cyc_cal<201) {
                    img_cyc_Cal.setImageResource(R.drawable.drc3);
                 } else if (day_cyc_cal>= 201 && day_cyc_cal<301) {
                    img_cyc_Cal.setImageResource(R.drawable.drc4);
                 } else if (day_cyc_cal>= 301 && day_cyc_cal<401) {
                    img_cyc_Cal.setImageResource(R.drawable.drc5);
                 } else if (day_cyc_cal>= 401 && day_cyc_cal<501) {
                    img_cyc_Cal.setImageResource(R.drawable.drc6);
                 } else if (day_cyc_cal>= 501 && day_cyc_cal<601) {
                    img_cyc_Cal.setImageResource(R.drawable.drc7);
                 } else if (day_cyc_cal>= 601 && day_cyc_cal<701) {
                    img_cyc_Cal.setImageResource(R.drawable.drc8);
                 } else if (day_cyc_cal>= 701 && day_cyc_cal<801) {
                    img_cyc_Cal.setImageResource(R.drawable.drc9);
                 } else {
                    Toast.makeText(ResultCycleActivity.this, "출력에 실패했습닙다", Toast.LENGTH_SHORT).show();
                 }
                  
                  if (day_cyc_km == 0) {
                      img_cyc_Cal.setImageResource(R.drawable.drck1);
                  } else if (day_cyc_km == 1) {
                     img_cyc_Cal.setImageResource(R.drawable.drck2);
                  } else if (day_cyc_km == 2) {
                     img_cyc_Cal.setImageResource(R.drawable.drck3);
                  } else if (day_cyc_km == 3) {
                     img_cyc_Cal.setImageResource(R.drawable.drck4);
                  } else if (day_cyc_km == 4) {
                     img_cyc_Cal.setImageResource(R.drawable.drck5);
                  } else if (day_cyc_km == 5) {
                     img_cyc_Cal.setImageResource(R.drawable.drck6);
                  } else if (day_cyc_km == 6) {
                     img_cyc_Cal.setImageResource(R.drawable.drck7);
                  } else if (day_cyc_km == 7) {
                     img_cyc_Cal.setImageResource(R.drawable.drck8);
                  } else if (day_cyc_km == 8) {
                     img_cyc_Cal.setImageResource(R.drawable.drck9);
                  } else {
                     Toast.makeText(ResultCycleActivity.this, "출력에 실패했습닙다", Toast.LENGTH_SHORT).show();
                  }
                  if (day_cyc_time ==0) {
                      img_cyc_Cal.setImageResource(R.drawable.drt1);
                  } else if (day_cyc_time >0 && day_cyc_time< 31 ) {
                      img_cyc_Cal.setImageResource(R.drawable.drt2);
                  } else if (day_cyc_time >=31 && day_cyc_time< 61 ) {
                      img_cyc_Cal.setImageResource(R.drawable.drt3);
                  }  else if(day_cyc_time >=61 && day_cyc_time< 91 ) {
                      img_cyc_Cal.setImageResource(R.drawable.drt4);
                  } else if (day_cyc_time >=91 && day_cyc_time< 121 ) {
                      img_cyc_Cal.setImageResource(R.drawable.drt5);
                  } else if (day_cyc_time >=121 && day_cyc_time< 151 ) {
                      img_cyc_Cal.setImageResource(R.drawable.drt6);
                  } else if (day_cyc_time >=151 && day_cyc_time< 181 ) {
                      img_cyc_Cal.setImageResource(R.drawable.drt7);
                  } else if (day_cyc_time >=181 && day_cyc_time< 211 ) {
                      img_cyc_Cal.setImageResource(R.drawable.drt8);
                  } else if (day_cyc_time >=211 && day_cyc_time< 241 ) {
                      img_cyc_Cal.setImageResource(R.drawable.drt9);
                  } else {
                     Toast.makeText(ResultCycleActivity.this, "출력에 실패했습닙다", Toast.LENGTH_SHORT).show();
                  }
                  
               
            }
        });
		btnCycWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	ArrayList<String> mArrayList;
         	   	StringBuilder mRetJson = new StringBuilder();
         	   	mArrayList = new ArrayList<String>();
         	   	String[] st_time = new String[7];
            	
        		for(int i= -7 ; i<0 ; i++) {
        			st_time[i+7] = Utility.get7DaysDateTime(i);
        			mArrayList.clear();
        			mArrayList.add(MyGlobals.getInstance().getmMyUSERID());
        			mArrayList.add("BIKE");
        			mArrayList.add(st_time[i+7]);
        			
        			if(Application.getHCSAPI().GetExResult(mArrayList, mRetJson)) {
        				try {
        					JSONObject root = new JSONObject(mRetJson.toString());
        					JSONArray results = root.getJSONArray("results");

        					for (int num = results.length(); num > 0; num--) {
        						JSONObject resultInfo = results.getJSONObject(num - 1);
        						
        						day_cyc_time += Integer.parseInt(resultInfo.getString("ex_time"));
        						day_cyc_km += Integer.parseInt(resultInfo.getString("ex_distance"));
        						day_cyc_cal += Integer.parseInt(resultInfo.getString("ex_calories"));
        			
        					}
        					
        				}//try
        				 catch (JSONException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				 	}
        				}else Utility.msgbox(getApplicationContext(), "일주일동안 운동을 하지 않았습니다.");
        		}//for
        		
            	img_cyc_day.setImageResource(R.drawable.week);
            	 if (day_cyc_cal == 0) {
                     img_cyc_Cal.setImageResource(R.drawable.wrc1);
                 } else if (day_cyc_cal> 0 && day_cyc_cal<301) {
                    img_cyc_Cal.setImageResource(R.drawable.wrc2);
                 } else if (day_cyc_cal>= 301 && day_cyc_cal<601) {
                    img_cyc_Cal.setImageResource(R.drawable.wrc3);
                 } else if (day_cyc_cal>= 601 && day_cyc_cal<901) {
                    img_cyc_Cal.setImageResource(R.drawable.wrc4);
                 } else if (day_cyc_cal>= 901 && day_cyc_cal<1201) {
                    img_cyc_Cal.setImageResource(R.drawable.wrc5);
                 } else if (day_cyc_cal>= 1201 && day_cyc_cal<1501) {
                    img_cyc_Cal.setImageResource(R.drawable.wrc6);
                 } else if (day_cyc_cal>= 1501 && day_cyc_cal<1801) {
                    img_cyc_Cal.setImageResource(R.drawable.wrc7);
                 } else if (day_cyc_cal>= 1801 && day_cyc_cal<2101) {
                    img_cyc_Cal.setImageResource(R.drawable.wrc8);
                 } else if (day_cyc_cal>= 2101 && day_cyc_cal<2401) {
                    img_cyc_Cal.setImageResource(R.drawable.wrc9);
                 } else {
                    Toast.makeText(ResultCycleActivity.this, "출력에 실패했습닙다", Toast.LENGTH_SHORT).show();
                 }
                  
                  if (day_cyc_km == 0) {
                      img_cyc_Cal.setImageResource(R.drawable.wrck1);
                  } else if (day_cyc_km >= 1 && day_cyc_km < 7) {
                     img_cyc_Cal.setImageResource(R.drawable.wrck2);
                  } else if (day_cyc_km >= 7 && day_cyc_km < 13) {
                     img_cyc_Cal.setImageResource(R.drawable.wrck3);
                  } else if (day_cyc_km >= 13 && day_cyc_km < 19) {
                     img_cyc_Cal.setImageResource(R.drawable.wrck4);
                  } else if (day_cyc_km >= 19 && day_cyc_km < 25) {
                     img_cyc_Cal.setImageResource(R.drawable.wrck5);
                  } else if (day_cyc_km >= 25 && day_cyc_km < 31) {
                     img_cyc_Cal.setImageResource(R.drawable.wrck6);
                  } else if (day_cyc_km >= 31 && day_cyc_km < 37) {
                     img_cyc_Cal.setImageResource(R.drawable.wrck7);
                  } else if (day_cyc_km >= 37 && day_cyc_km < 43) {
                     img_cyc_Cal.setImageResource(R.drawable.wrck8);
                  } else if (day_cyc_km >= 43 && day_cyc_km < 49) {
                     img_cyc_Cal.setImageResource(R.drawable.wrck9);
                  } else {
                     Toast.makeText(ResultCycleActivity.this, "출력에 실패했습닙다", Toast.LENGTH_SHORT).show();
                  }
                  if (day_cyc_time ==0) {
                      img_cyc_Cal.setImageResource(R.drawable.wrt1);
                  } else if (day_cyc_time >0 && day_cyc_time< 121 ) {
                      img_cyc_Cal.setImageResource(R.drawable.wrt2);
                  } else if (day_cyc_time >=121 && day_cyc_time<241) {
                      img_cyc_Cal.setImageResource(R.drawable.wrt3);
                  }  else if(day_cyc_time >=241 && day_cyc_time<361 ) { 
                      img_cyc_Cal.setImageResource(R.drawable.wrt4);
                  } else if (day_cyc_time >=361 && day_cyc_time<481 ) {
                      img_cyc_Cal.setImageResource(R.drawable.wrt5);
                  } else if (day_cyc_time >=481 && day_cyc_time< 601 ) {
                      img_cyc_Cal.setImageResource(R.drawable.wrt6);
                  } else if (day_cyc_time >=601 && day_cyc_time< 721 ) {
                      img_cyc_Cal.setImageResource(R.drawable.wrt7);
                  } else if (day_cyc_time >=721 && day_cyc_time< 841 ) {
                      img_cyc_Cal.setImageResource(R.drawable.wrt8);
                  } else if (day_cyc_time >=841 && day_cyc_time< 961 ) {
                      img_cyc_Cal.setImageResource(R.drawable.wrt9);
                  } else {
                     Toast.makeText(ResultCycleActivity.this, "출력에 실패했습닙다", Toast.LENGTH_SHORT).show();
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
            	
        		for(int i= -30 ; i<0 ; i++) {
        			st_time[i+30] = Utility.get7DaysDateTime(i);
        			mArrayList.clear();
        			mArrayList.add(MyGlobals.getInstance().getmMyUSERID());
        			mArrayList.add("BIKE");
        			mArrayList.add(st_time[i+30]);
        			
        			if(Application.getHCSAPI().GetExResult(mArrayList, mRetJson)) {
        				try {
        					JSONObject root = new JSONObject(mRetJson.toString());
        					JSONArray results = root.getJSONArray("results");

        					for (int num = results.length(); num > 0; num--) {
        						JSONObject resultInfo = results.getJSONObject(num - 1);
        						
        						day_cyc_time += Integer.parseInt(resultInfo.getString("ex_time"));
        						day_cyc_km += Integer.parseInt(resultInfo.getString("ex_distance"));
        						day_cyc_cal += Integer.parseInt(resultInfo.getString("ex_calories"));
        			
        					}
        					
        				}//try
        				 catch (JSONException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				 	}
        				}else Utility.msgbox(getApplicationContext(), "한달동안 운동을 하지 않았습니다.");
        		}//for
        		
            	img_cyc_day.setImageResource(R.drawable.month);
            	if (day_cyc_cal == 0) {
                    img_cyc_Cal.setImageResource(R.drawable.mrc1);
                } else if (day_cyc_cal> 0 && day_cyc_cal<901) {
                   img_cyc_Cal.setImageResource(R.drawable.mrc2);
                } else if (day_cyc_cal>= 901 && day_cyc_cal<1801) {
                   img_cyc_Cal.setImageResource(R.drawable.mrc3);
                } else if (day_cyc_cal>= 1801 && day_cyc_cal<2701) {
                   img_cyc_Cal.setImageResource(R.drawable.mrc4);
                } else if (day_cyc_cal>= 2701 && day_cyc_cal<3601) {
                   img_cyc_Cal.setImageResource(R.drawable.mrc5);
                } else if (day_cyc_cal>= 3601 && day_cyc_cal<4501) {
                   img_cyc_Cal.setImageResource(R.drawable.mrc6);
                } else if (day_cyc_cal>= 4501 && day_cyc_cal<5401) {
                   img_cyc_Cal.setImageResource(R.drawable.mrc7);
                } else if (day_cyc_cal>= 5401 && day_cyc_cal<6301) {
                   img_cyc_Cal.setImageResource(R.drawable.mrc8);
                } else if (day_cyc_cal>= 6301 && day_cyc_cal<7201) {
                   img_cyc_Cal.setImageResource(R.drawable.mrc9);
                } else {
                   Toast.makeText(ResultCycleActivity.this, "출력에 실패했습닙다", Toast.LENGTH_SHORT).show();
                }
                 
                 if (day_cyc_km == 0) {
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
                 } else if (day_cyc_km >= 281 && day_cyc_km < 321) {
                 	img_cyc_Km.setImageResource(R.drawable.mrck9);
                 } else {
                    Toast.makeText(ResultCycleActivity.this, "출력에 실패했습닙다", Toast.LENGTH_SHORT).show();
                 }
                 if (day_cyc_time ==0) {
                     img_cyc_Time.setImageResource(R.drawable.mrt1);
                 } else if (day_cyc_time >0 && day_cyc_time< 361) {
                 	img_cyc_Time.setImageResource(R.drawable.mrt2);
                 } else if (day_cyc_time >=361 && day_cyc_time<721) {
                 	img_cyc_Time.setImageResource(R.drawable.mrt3);
                 }  else if(day_cyc_time >=721 && day_cyc_time<1081 ) { 
                 	img_cyc_Time.setImageResource(R.drawable.mrt4);
                 } else if (day_cyc_time >=1081 && day_cyc_time<1441 ) {
                 	img_cyc_Time.setImageResource(R.drawable.mrt5);
                 } else if (day_cyc_time >=1441 && day_cyc_time< 1801 ) {
                 	img_cyc_Time.setImageResource(R.drawable.mrt6);
                 } else if (day_cyc_time >=1801 && day_cyc_time< 2161 ) {
                 	img_cyc_Time.setImageResource(R.drawable.mrt7);
                 } else if (day_cyc_time >=2161 && day_cyc_time< 2521 ) {
                 	img_cyc_Time.setImageResource(R.drawable.mrt8);
                 } else if (day_cyc_time >=2521 && day_cyc_time< 2881 ) {
                 	img_cyc_Time.setImageResource(R.drawable.mrt9);
                 } else {
                    Toast.makeText(ResultCycleActivity.this, "출력에 실패했습닙다", Toast.LENGTH_SHORT).show();
                 }
            }
        });
    }   
}

