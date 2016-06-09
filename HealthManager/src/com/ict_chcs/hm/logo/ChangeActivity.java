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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ChangeActivity extends Activity {
	EditText edtName, edtPw, edtAge, edtWeight, edtRFID;
	RadioButton rdbMan, rdbWoman;
	RadioGroup rdgGender;
	Button btnOK;
	String id, password, name, age, gender, weight, rfid;;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change);
		
		edtName = (EditText)findViewById(R.id.edit_ch_Name);
		edtPw = (EditText)findViewById(R.id.edit_ch_PW);
		edtAge = (EditText)findViewById(R.id.edit_ch_Old);
		edtWeight = (EditText)findViewById(R.id.edit_ch_Weight);
		edtRFID = (EditText)findViewById(R.id.editText_ch_Card);
		rdbMan = (RadioButton)findViewById(R.id.rdbChMan);
		rdbWoman = (RadioButton)findViewById(R.id.rdbChWoman);
		rdgGender = (RadioGroup)findViewById(R.id.rdgChGender);
		btnOK = (Button)findViewById(R.id.btnChOK);
		
		final ArrayList<String> mArrayList;
		final StringBuilder mRetJson = new StringBuilder ();
		mArrayList = new ArrayList<String>();
		mArrayList.add(MyGlobals.getInstance().getmMyUSERID());
		mArrayList.add(MyGlobals.getInstance().getmMyUSERPW());
		
		if(Application.getHCSAPI().GetExUser(mArrayList, mRetJson)) {
			try {
				JSONObject root = new JSONObject(mRetJson.toString());
				JSONArray results = root.getJSONArray("results");

				for (int num = results.length(); num > 0; num--) {
					JSONObject resultInfo = results.getJSONObject(num - 1);
					
					 id = resultInfo.getString("id");
					 password = resultInfo.getString("password");
					 name = resultInfo.getString("name");
					 age = resultInfo.getString("age");
					 gender = resultInfo.getString("gender");
					 weight = resultInfo.getString("weight");
					 rfid = resultInfo.getString("rfid");
					
					
					
					edtName.setText(name);
					edtAge.setText(age);
					edtWeight.setText(weight);
					edtRFID.setText(rfid);
					
					if(gender.equalsIgnoreCase("MALE")) rdgGender.check(R.id.rdbChMan);
					else rdgGender.check(R.id.rdbChWoman);
					
					
				}
				
			}//try
			 catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 	}
			}
		
		btnOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					mArrayList.clear();
					mArrayList.add(MyGlobals.getInstance().getmMyUSERID());
					mArrayList.add(edtPw.getText().toString());
					mArrayList.add(edtName.getText().toString());
					mArrayList.add(gender);
					mArrayList.add(edtAge.getText().toString());
					mArrayList.add(edtWeight.getText().toString());
					mArrayList.add(edtRFID.getText().toString());
					
					Application.getHCSAPI().SetExUser(mArrayList, mRetJson);
					Utility.msgbox(getApplicationContext(), "정보 수정 완료");
					Intent intent = new Intent(ChangeActivity.this, MenuActivity.class);
					startActivity(intent);
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					Utility.msgbox(getApplicationContext(), "정보 수정 실패");
				}
				
			}
		
		});
		
		rdgGender.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.rdbChMan:
					gender = "MALE";
					break;
				case R.id.rdbChWoman:
					gender = "FEMALE";
					break;
				}
			}
		});
			
	}//onCreate
	
}
