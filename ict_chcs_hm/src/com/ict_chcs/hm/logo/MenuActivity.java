package com.ict_chcs.hm.logo;

import com.example.logo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MenuActivity extends Activity {
 ImageView imgPlaying,imgResult,imgChange;
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_menu);
  
  imgPlaying = (ImageView)findViewById(R.id.menuView1);
  imgResult = (ImageView)findViewById(R.id.menuView2);
  imgChange = (ImageView)findViewById(R.id.imenuView3);
  
  imgPlaying.setOnClickListener(new OnClickListener() {
   
   @Override
   public void onClick(View v) {
    // TODO Auto-generated method stub
    Intent intentSub = new Intent(MenuActivity.this, PlayingActivity.class);
              startActivity(intentSub);
   }
  });
  imgResult.setOnClickListener(new OnClickListener() {
   
   @Override
   public void onClick(View v) {
    // TODO Auto-generated method stub
    final Intent intentSub;
              intentSub = new Intent(MenuActivity.this, ResultActivity.class);
              startActivity(intentSub);    
   }
  });
  imgChange.setOnClickListener(new OnClickListener() {
   
   @Override
   public void onClick(View v) {
    // TODO Auto-generated method stub
    final Intent intentSub;
              intentSub = new Intent(MenuActivity.this, ChangeActivity.class);
              startActivity(intentSub); 
   }
  });
  
 } 


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
