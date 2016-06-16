package com.ict_chcs.hm.logo;

import java.util.ArrayList;
import com.example.logo.R;
import com.ict_chcs.hm.Adapter.HCSAPI;
import com.ict_chcs.hm.logo.CycleActivity;
import com.ict_chcs.hm.logo.RunningActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle; 
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MenuActivity extends Activity {
 ImageView imgRunning,imgCycle,imgChange;
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_menu);
  
  imgRunning = (ImageView)findViewById(R.id.running);
  imgCycle = (ImageView)findViewById(R.id.cycle);
  imgChange = (ImageView)findViewById(R.id.changeInformation);
  
  final ArrayList<String> mArrayList = new ArrayList<String>();
  final StringBuilder mRetJson = new StringBuilder ();

  imgRunning.setOnClickListener(new OnClickListener() {
   @Override
   public void onClick(View v) {
    Application.getHCSAPI();
	// TODO Auto-generated method stub
	   HCSAPI.GetExResult(mArrayList, mRetJson);
	   Intent intentSub = new Intent(MenuActivity.this, RunningActivity.class);
       startActivity(intentSub);
       finish();
   }
  });
  imgCycle.setOnClickListener(new OnClickListener() {
   
   @Override
   public void onClick(View v) {
    // TODO Auto-generated method stub
    final Intent intentSub;
              intentSub = new Intent(MenuActivity.this, CycleActivity.class);
              startActivity(intentSub);
              finish();
              
   }
  });
  
  //회원정보 수정
  imgChange.setOnClickListener(new OnClickListener() {
   @Override
   public void onClick(View v) {
    // TODO Auto-generated method stub
    final Intent intentSub;
              intentSub = new Intent(MenuActivity.this, ChangeActivity.class);
              startActivity(intentSub); 
   }
  });
  
 } //onCreate


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
