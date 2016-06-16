package com.ict_chcs.hm.logo;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.logo.R;
import com.ict_chcs.hm.Adapter.Utility;
import com.ict_chcs.hm.logo.Application.MyGlobals;

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

public class ResultRunActivity extends Activity {
   ImageView btnRunDay,btnRunWeek,btnRunMonth;
   ImageView Img_run_Back, Img_run_Menu;
   ImageView imgCal, imgKm, imgTime;
   TextView txtrunKm,txtrunTime;
   TextView txtViewRunKm,txtViewRunCal,txtViewRunTime;
   BitmapDrawable d_km, d_cal, d_time;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_running_result);
   
      btnRunDay = (ImageView) findViewById(R.id.btnRunDay);
      btnRunWeek = (ImageView) findViewById(R.id.btnRunWeek);
      btnRunMonth = (ImageView) findViewById(R.id.btnRunMonth);
      Img_run_Back = (ImageView)findViewById(R.id.run_re_back);
      Img_run_Menu = (ImageView) findViewById(R.id.run_re_main);
	
      imgKm = (ImageView) findViewById(R.id.imageView_run_day2);
	  imgCal = (ImageView) findViewById(R.id.imageView_run_day3);
	  imgTime = (ImageView) findViewById(R.id.imageView_run_day4);
	  txtrunKm = (TextView) findViewById(R.id.text_run_km);
      txtrunTime = (TextView)findViewById(R.id.text_run_time);
      txtViewRunCal = (TextView)findViewById(R.id.textView_run_calorie);
      txtViewRunKm = (TextView)findViewById(R.id.textView_run_ke);
      txtViewRunTime = (TextView)findViewById(R.id.textView_run_time);
	  imgKm.setImageBitmap(null);
	  imgCal.setImageBitmap(null);
	  imgTime.setImageBitmap(null);
	    
	  int day_run_km = 0;
      int day_run_cal = 0;
      int day_run_time = 0;
      
        Img_run_Back.setOnClickListener (new OnClickListener() {
           @Override
           public void onClick(View v) {
            // TODO Auto-generated method stub
            final Intent intentSub;
                      intentSub = new Intent(ResultRunActivity.this, RunningActivity.class);
                      startActivity(intentSub); 
                      finish();
           }
          });
        
        Img_run_Menu.setOnClickListener (new OnClickListener() {
        @Override
        public void onClick(View v) {
         // TODO Auto-generated method stub
         final Intent intentSub;
                   intentSub = new Intent(ResultRunActivity.this, MenuActivity.class);
                   startActivity(intentSub); 
                   finish();
        }
       });
        
            imgCal.setImageResource(R.drawable.drc1);
             imgKm.setImageResource(R.drawable.drrk1);
             imgTime.setImageResource(R.drawable.drt1);
         
        btnRunDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	ArrayList<String> mArrayList;
         	   	StringBuilder mRetJson = new StringBuilder();
         	   	mArrayList = new ArrayList<String>();
         	   	String[] st_time = new String[30];
         	   txtrunTime.setText("분");
               txtrunKm.setText("Km");
         	   	int	day_run_cal = 0;
         	   	int	day_run_km = 0;
        	   	int day_run_time = 0;
        	   	
        		for(int i= -1 ; i<0 ; i++) {
        			st_time[i+1] = Utility.get7DaysDateTime(i);
        			mArrayList.clear();
        			mArrayList.add(MyGlobals.getInstance().getmMyUSERID());
        			mArrayList.add("TREADMILL");
        			mArrayList.add(st_time[i+1]);
        			
        			if(Application.getHCSAPI().GetExResult(mArrayList, mRetJson) == true) {
        				try {
        					JSONObject root = new JSONObject(mRetJson.toString());
        					JSONArray results = root.getJSONArray("results");

        					for (int num = results.length(); num > 0; num--) {
        						JSONObject resultInfo = results.getJSONObject(num - 1);
        						
        						String ex_time = resultInfo.getString("ex_time").substring(0, resultInfo.getString("ex_time").length()-3);
           						day_run_time += Integer.parseInt(ex_time);
        						day_run_km = Integer.parseInt(resultInfo.getString("ex_distance").replaceAll("[^0-9]+", ""));
        						day_run_cal = Integer.parseInt(resultInfo.getString("ex_calories").replaceAll("[^0-9]+", ""));
        			
        					}
        					
        				}//try
        				 catch (JSONException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				 	}
        				}
        		}//for
        		txtViewRunKm.setText(""+day_run_km);
          	   txtViewRunTime.setText(""+day_run_time);
          	   txtViewRunCal.setText(""+day_run_cal);
               if (day_run_cal <= 0) {
                  imgCal.setImageResource(R.drawable.drc1);
              } else if (day_run_cal > 0 && day_run_cal < 101) {
                 imgCal.setImageResource(R.drawable.drc2);
              } else if (day_run_cal >= 101 && day_run_cal < 201) {
                 imgCal.setImageResource(R.drawable.drc3);
              } else if (day_run_cal >= 201 && day_run_cal < 301) {
                 imgCal.setImageResource(R.drawable.drc4);
              } else if (day_run_cal >= 301 && day_run_cal < 401) {
                 imgCal.setImageResource(R.drawable.drc5);
              } else if (day_run_cal >= 401 && day_run_cal < 501) {
                 imgCal.setImageResource(R.drawable.drc6);
              } else if (day_run_cal >= 501 && day_run_cal < 601) {
                 imgCal.setImageResource(R.drawable.drc7);
              } else if (day_run_cal >= 601 && day_run_cal < 701) {
                 imgCal.setImageResource(R.drawable.drc8);
              } else if (day_run_cal >= 701 ) {
                 imgCal.setImageResource(R.drawable.drc9);
              } else {
                 Toast.makeText(ResultRunActivity.this, "��¿� �����߽��մ�", Toast.LENGTH_SHORT).show();
              }
               
               if (day_run_km <= 0) {
                   imgKm.setImageResource(R.drawable.drrk1);
               } else if (day_run_km > 0 && day_run_km < 2) {
                  imgKm.setImageResource(R.drawable.drrk2);
               } else if (day_run_km >= 2 && day_run_km < 3) {
                  imgKm.setImageResource(R.drawable.drrk3);
               } else if (day_run_km >= 3 && day_run_km < 4) {
                  imgKm.setImageResource(R.drawable.drrk4);
               } else if (day_run_km >= 4 && day_run_km < 5) {
                  imgKm.setImageResource(R.drawable.drrk5);
               } else if (day_run_km >= 5 && day_run_km < 6) {
                  imgKm.setImageResource(R.drawable.drrk6);
               } else if (day_run_km >= 6 && day_run_km < 7) {
                  imgKm.setImageResource(R.drawable.drrk7);
               } else if (day_run_km >= 7 && day_run_km < 8) {
                  imgKm.setImageResource(R.drawable.drrk8);
               } else if (day_run_km >= 8) {
                  imgKm.setImageResource(R.drawable.drrk9);
               } else {
                  Toast.makeText(ResultRunActivity.this, "��¿� �����߽��մ�", Toast.LENGTH_SHORT).show();
               }
               if (day_run_time <= 0) {
                   imgTime.setImageResource(R.drawable.drt1);
               } else if (day_run_time > 0 && day_run_time < 31 ) {
                  imgTime.setImageResource(R.drawable.drt2);
               } else if (day_run_time >= 31 && day_run_time < 61 ) {
                  imgTime.setImageResource(R.drawable.drt3);
               }  else if(day_run_time >= 61 && day_run_time < 91 ) {
                  imgTime.setImageResource(R.drawable.drt4);
               } else if (day_run_time >= 91 && day_run_time < 121 ) {
                  imgTime.setImageResource(R.drawable.drt5);
               } else if (day_run_time >= 121 && day_run_time < 151 ) {
                  imgTime.setImageResource(R.drawable.drt6);
               } else if (day_run_time >= 151 && day_run_time < 181 ) {
                  imgTime.setImageResource(R.drawable.drt7);
               } else if (day_run_time >= 181 && day_run_time < 211 ) {
                  imgTime.setImageResource(R.drawable.drt8);
               } else if (day_run_time >= 211) {
                  imgTime.setImageResource(R.drawable.drt9);
               } else {
                  Toast.makeText(ResultRunActivity.this, "��¿� �����߽��մ�", Toast.LENGTH_SHORT).show();
               }
               
            }
        }); // Day
        
        btnRunWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	ArrayList<String> mArrayList;
         	   	StringBuilder mRetJson = new StringBuilder();
         	   	mArrayList = new ArrayList<String>();
         	   	String[] st_time = new String[30];
         	   txtrunTime.setText("시");
               txtrunKm.setText("Km");
         	   	int day_run_cal = 0;
        	   	int day_run_km = 0;
        	   	int day_run_time = 0;
        	   	
        		for(int i= -7 ; i<0 ; i++) {
        			st_time[i+7] = Utility.get7DaysDateTime(i);
        			mArrayList.clear();
        			mArrayList.add(MyGlobals.getInstance().getmMyUSERID());
        			mArrayList.add("TREADMILL");
        			mArrayList.add(st_time[i+7]);
        			
        			if(Application.getHCSAPI().GetExResult(mArrayList, mRetJson) == true) {
        				try {
        					JSONObject root = new JSONObject(mRetJson.toString());
        					JSONArray results = root.getJSONArray("results");

        					for (int num = results.length(); num > 0; num--) {
        						JSONObject resultInfo = results.getJSONObject(num - 1);
        						
        						String ex_time = resultInfo.getString("ex_time").substring(0, resultInfo.getString("ex_time").length()-3);
           						day_run_time += Integer.parseInt(ex_time);
        						day_run_km += Integer.parseInt(resultInfo.getString("ex_distance").replaceAll("[^0-9]+", ""));
        						day_run_cal += Integer.parseInt(resultInfo.getString("ex_calories").replaceAll("[^0-9]+", ""));
        			
        					}
        					
        				}//try
        				 catch (JSONException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				 	}
        				}
        		}//for
        		txtViewRunKm.setText(""+day_run_km);
           	   txtViewRunTime.setText(""+day_run_time);
           	   txtViewRunCal.setText(""+day_run_cal);
                if (day_run_cal <= 0) {
                     imgCal.setImageResource(R.drawable.wrc1);
                 } else if (day_run_cal > 0 && day_run_cal < 301) {
                    imgCal.setImageResource(R.drawable.wrc2);
                 } else if (day_run_cal >= 301 && day_run_cal < 601) {
                    imgCal.setImageResource(R.drawable.wrc3);
                 } else if (day_run_cal >= 601 && day_run_cal < 901) {
                    imgCal.setImageResource(R.drawable.wrc4);
                 } else if (day_run_cal >= 901 && day_run_cal < 1201) {
                    imgCal.setImageResource(R.drawable.wrc5);
                 } else if (day_run_cal >= 1201 && day_run_cal < 1501) {
                    imgCal.setImageResource(R.drawable.wrc6);
                 } else if (day_run_cal >= 1501 && day_run_cal < 1801) {
                    imgCal.setImageResource(R.drawable.wrc7);
                 } else if (day_run_cal >= 1801 && day_run_cal < 2101) {
                    imgCal.setImageResource(R.drawable.wrc8);
                 } else if (day_run_cal >= 2101 ) {
                    imgCal.setImageResource(R.drawable.wrc9);
                 } else {
                    Toast.makeText(ResultRunActivity.this, "��¿� �����߽��մ�", Toast.LENGTH_SHORT).show();
                 }
                  
                  if (day_run_km <= 0) {
                      imgKm.setImageResource(R.drawable.wrrk1);
                  } else if (day_run_km > 0 && day_run_km < 4) {
                     imgKm.setImageResource(R.drawable.wrrk2);
                  } else if (day_run_km >= 4 && day_run_km < 7) {
                     imgKm.setImageResource(R.drawable.wrrk3);
                  } else if (day_run_km >= 7 && day_run_km < 10) {
                     imgKm.setImageResource(R.drawable.wrrk4);
                  } else if (day_run_km >= 10 && day_run_km < 13) {
                     imgKm.setImageResource(R.drawable.wrrk5);
                  } else if (day_run_km >= 13 && day_run_km < 16) {
                     imgKm.setImageResource(R.drawable.wrrk6);
                  } else if (day_run_km >= 16 && day_run_km < 19) {
                     imgKm.setImageResource(R.drawable.wrrk7);
                  } else if (day_run_km >= 19 && day_run_km < 22) {
                     imgKm.setImageResource(R.drawable.wrrk8);
                  } else if (day_run_km >= 22 ) {
                     imgKm.setImageResource(R.drawable.wrrk9);
                  } else {
                     Toast.makeText(ResultRunActivity.this, "��¿� �����߽��մ�", Toast.LENGTH_SHORT).show();
                  }
                  if (day_run_time <= 0 ) {
                      imgTime.setImageResource(R.drawable.wrt1);
                  } else if (day_run_time > 0 && day_run_time < 121 ) {
                     imgTime.setImageResource(R.drawable.wrt2);
                  } else if (day_run_time >= 121 && day_run_time <241) {
                     imgTime.setImageResource(R.drawable.wrt3);
                  }  else if(day_run_time >= 241 && day_run_time <361 ) { 
                     imgTime.setImageResource(R.drawable.wrt4);
                  } else if (day_run_time >= 361 && day_run_time < 481 ) {
                     imgTime.setImageResource(R.drawable.wrt5);
                  } else if (day_run_time >= 481 && day_run_time < 601 ) {
                     imgTime.setImageResource(R.drawable.wrt6);
                  } else if (day_run_time >= 601 && day_run_time < 721 ) {
                     imgTime.setImageResource(R.drawable.wrt7);
                  } else if (day_run_time >= 721 && day_run_time < 841 ) {
                     imgTime.setImageResource(R.drawable.wrt8);
                  } else if (day_run_time >= 841 && day_run_time < 961 ) {
                     imgTime.setImageResource(R.drawable.wrt9);
                  } else {
                     Toast.makeText(ResultRunActivity.this, "��¿� �����߽��մ�", Toast.LENGTH_SHORT).show();
                  }
            }
        });
        btnRunMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	ArrayList<String> mArrayList;
         	   	StringBuilder mRetJson = new StringBuilder();
         	   	mArrayList = new ArrayList<String>();
         	   	String[] st_time = new String[30];
         	   txtrunTime.setText("시");
               txtrunKm.setText("Km");
         	   	int day_run_cal = 0;
        	   	int day_run_km = 0;
        	   	int day_run_time = 0;
        	   	
        		for(int i= -30 ; i<0 ; i++) {
        			st_time[i+30] = Utility.get7DaysDateTime(i);
        			mArrayList.clear();
        			mArrayList.add(MyGlobals.getInstance().getmMyUSERID());
        			mArrayList.add("TREADMILL");
        			mArrayList.add(st_time[i+30]);
        			
        			if(Application.getHCSAPI().GetExResult(mArrayList, mRetJson) == true) {
        				try {
        					JSONObject root = new JSONObject(mRetJson.toString());
        					JSONArray results = root.getJSONArray("results");

        					for (int num = results.length(); num > 0; num--) {
        						JSONObject resultInfo = results.getJSONObject(num - 1);
        						
        						String ex_time = resultInfo.getString("ex_time").substring(0, resultInfo.getString("ex_time").length()-3);
           						day_run_time += Integer.parseInt(ex_time);
        						day_run_km += Integer.parseInt(resultInfo.getString("ex_distance").replaceAll("[^0-9]+", ""));
        						day_run_cal += Integer.parseInt(resultInfo.getString("ex_calories").replaceAll("[^0-9]+", ""));
        			
        					}
        					
        				}//try
        				 catch (JSONException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				 	}
        				}
        		}//for
        		txtViewRunKm.setText(""+day_run_km);
           	   txtViewRunTime.setText(""+day_run_time);
           	   txtViewRunCal.setText(""+day_run_cal);
               if (day_run_cal <= 0) {
                       imgCal.setImageResource(R.drawable.mrc1);
                   } else if (day_run_cal > 0 && day_run_cal < 901) {
                      imgCal.setImageResource(R.drawable.mrc2);
                   } else if (day_run_cal >= 901 && day_run_cal < 1801) {
                      imgCal.setImageResource(R.drawable.mrc3);
                   } else if (day_run_cal >= 1801 && day_run_cal < 2701) {
                      imgCal.setImageResource(R.drawable.mrc4);
                   } else if (day_run_cal >= 2701 && day_run_cal < 3601) {
                      imgCal.setImageResource(R.drawable.mrc5);
                   } else if (day_run_cal >= 3601 && day_run_cal < 4501) {
                      imgCal.setImageResource(R.drawable.mrc6);
                   } else if (day_run_cal >= 4501 && day_run_cal < 5401) {
                      imgCal.setImageResource(R.drawable.mrc7);
                   } else if (day_run_cal >= 5401 && day_run_cal < 6301) {
                      imgCal.setImageResource(R.drawable.mrc8);
                   } else if (day_run_cal >= 6301 && day_run_cal < 7201) {
                      imgCal.setImageResource(R.drawable.mrc9);
                   } else {
                      Toast.makeText(ResultRunActivity.this, "��¿� �����߽��մ�", Toast.LENGTH_SHORT).show();
                   }
                    
                    if (day_run_km <= 0) {
                        imgKm.setImageResource(R.drawable.mrrk1);
                    } else if (day_run_km > 0 && day_run_km < 21) {
                       imgKm.setImageResource(R.drawable.mrrk2);
                    } else if (day_run_km >= 21 && day_run_km < 41) {
                       imgKm.setImageResource(R.drawable.mrrk3);
                    } else if (day_run_km >= 41 && day_run_km < 61) {
                       imgKm.setImageResource(R.drawable.mrrk4);
                    } else if (day_run_km >= 61 && day_run_km < 81) {
                       imgKm.setImageResource(R.drawable.mrrk5);
                    } else if (day_run_km >= 81 && day_run_km < 101) {
                       imgKm.setImageResource(R.drawable.mrrk6);
                    } else if (day_run_km >= 101 && day_run_km < 121) {
                       imgKm.setImageResource(R.drawable.mrrk7);
                    } else if (day_run_km >= 121 && day_run_km < 141) {
                       imgKm.setImageResource(R.drawable.mrrk8);
                    } else if (day_run_km >= 141 ) {
                       imgKm.setImageResource(R.drawable.mrrk9);
                    } else {
                       Toast.makeText(ResultRunActivity.this, "��¿� �����߽��մ�", Toast.LENGTH_SHORT).show();
                    }
                    if (day_run_time <=0) {
                        imgTime.setImageResource(R.drawable.mrt1);
                    } else if (day_run_time > 0 && day_run_time < 361) {
                       imgTime.setImageResource(R.drawable.mrt2);
                    } else if (day_run_time >= 361 && day_run_time <721) {
                       imgTime.setImageResource(R.drawable.mrt3);
                    }  else if(day_run_time >= 721 && day_run_time < 1081 ) { 
                       imgTime.setImageResource(R.drawable.mrt4);
                    } else if (day_run_time >= 1081 && day_run_time < 1441 ) {
                       imgTime.setImageResource(R.drawable.mrt5);
                    } else if (day_run_time >= 1441 && day_run_time < 1801 ) {
                       imgTime.setImageResource(R.drawable.mrt6);
                    } else if (day_run_time >= 1801 && day_run_time < 2161 ) {
                       imgTime.setImageResource(R.drawable.mrt7);
                    } else if (day_run_time >= 2161 && day_run_time < 2521 ) {
                       imgTime.setImageResource(R.drawable.mrt8);
                    } else if (day_run_time >= 2521 ) {
                       imgTime.setImageResource(R.drawable.mrt9);
                    } else {
                       Toast.makeText(ResultRunActivity.this, "��¿� �����߽��մ�", Toast.LENGTH_SHORT).show();
                    }
            }
        });
        
    }   
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
