package com.ict_chcs.hm.logo;

import com.example.logo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class HActivity extends Activity {
	Button btnOk;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_h);
		btnOk =(Button)findViewById(R.id.h_button1);
		btnOk.setOnClickListener(new View.OnClickListener() {
	         @Override
	         public void onClick(View v) {

	             Intent intentSub = new Intent(HActivity.this, LoginActivity.class);
	             startActivity(intentSub);
	         }
	     });
	}

}
