package com.ict_chcs.hm.logo;

import java.util.ArrayList;
import com.example.logo.R;
import com.ict_chcs.hm.Adapter.Utility;
import com.ict_chcs.hm.logo.Application.MyGlobals;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class HActivity extends Activity {
	EditText edtRFID, edtID, edtPW, edtPW2, edtName, edtAge, edtWeight;
	String Gender;
	Button btnOk, btnIDCheck;
	RadioButton	rdbMan, rdbWoman;
	RadioGroup rdgGender;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_h);
		
		edtRFID = (EditText) findViewById(R.id.editRFID);
		edtID = (EditText) findViewById(R.id.editID);
		edtPW = (EditText) findViewById(R.id.editPW);
		edtPW2 = (EditText) findViewById(R.id.editPW2);
		edtName = (EditText) findViewById(R.id.editName);
		edtAge = (EditText)findViewById(R.id.editOld);
		edtWeight = (EditText)findViewById(R.id.editWeight);
		rdbMan = (RadioButton)findViewById(R.id.rdbHMan);
		rdbWoman = (RadioButton)findViewById(R.id.rdbHWoman);
		rdgGender = (RadioGroup)findViewById(R.id.rdgHGender);
		btnOk =(Button)findViewById(R.id.h_button1);
		btnIDCheck = (Button)findViewById(R.id.btnHCheck);
		edtName.requestFocus();
		edtName.setPrivateImeOptions("defaultInputmode=english;");
		edtID.setPrivateImeOptions("defaultInputmode=english;");
		final ArrayList<String> mArrayList;
		final StringBuilder mRetJson = new StringBuilder ();

		mArrayList = new ArrayList<String>();
		
		rdbMan.setChecked(true);
		Gender="MALE";
		rdgGender.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				
				if(checkedId == R.id.rdbHMan) Gender = "MALE";
				else	Gender = "FEMALE";
			}
		});
		
		
		// �ߺ� ���̵� �˻�
		btnIDCheck.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mArrayList.clear();
				mArrayList.add(edtID.getText().toString());
				if(!edtID.getText().toString().equals("") && Application.getHCSAPI().GetExUser(mArrayList, mRetJson) == false) {
					Utility.msgbox(getApplicationContext(), "아이디 사용 가능 합니다");
				}else	Utility.msgbox(getApplicationContext(), "아이디를 확인해주세요");
				
			}
		});
		
		btnOk.setOnClickListener(new View.OnClickListener() {
	         @Override
	         public void onClick(View v) {
	        	 
	        		
					mArrayList.add(edtID.getText().toString());
					mArrayList.add(edtPW.getText().toString());
					mArrayList.add(edtName.getText().toString());
					mArrayList.add(Gender);
					mArrayList.add(edtAge.getText().toString());
					mArrayList.add(edtWeight.getText().toString());
					mArrayList.add(edtRFID.getText().toString());
					
					if (!edtID.getText().toString().equals("") && !edtPW.getText().toString().equals("") &&
							!edtPW2.getText().toString().equals("") && !edtName.getText().toString().equals("") &&
							!Gender.equals("") && !edtAge.getText().toString().equals("") &&
							!edtWeight.getText().toString().equals("") && !edtRFID.getText().toString().equals("") && 
							Application.getHCSAPI().GetExUser(mArrayList, mRetJson) == false) {
						
						Application.getHCSAPI().SetExUser(mArrayList, mRetJson);
						Utility.msgbox(HActivity.this, "Sign Up Successed !!");
						Intent intentSub = new Intent(HActivity.this, LoginActivity.class);
			            startActivity(intentSub);

					} else {
						Utility.msgbox(HActivity.this, "이미 등록된 회원이거나 회원양식을 다 채워주세요.");
					}

	             
	         }//onClick
	     });
	}//onCreate

}
