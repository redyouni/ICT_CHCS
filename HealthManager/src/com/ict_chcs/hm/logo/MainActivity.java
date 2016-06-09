package com.ict_chcs.hm.logo;

import com.example.logo.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends Activity {
	Intent intent;
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        Handler handler = new Handler();
	        intent =new Intent(MainActivity.this,LoginActivity.class);
	        handler.postDelayed(new Runnable() {
	            @Override
	            public void run() {
	                startActivity(intent);
	                finish();
	            }
	        }, 2000);

	    }
}
