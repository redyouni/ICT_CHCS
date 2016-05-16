package com.ict_chcs.hm.logo;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

import com.example.logo.R;
import com.ict_chcs.hm.Adapter.Utility;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class LoginActivity extends Activity {
	Button btnLogin, btnH;
	EditText edtId, edtPassword;
	private AnimationDrawable frameAnimation;
	private ImageView view;

	private static final String TAG_RESULTS = "result";
	private static final String TAG_ID = "id";
	private static final String TAG_PASSWORD = "password";
	private static final String TAG_RFID = "RFID";
	private static final String TAG_NAME = "name";
	private static final String TAG_GENDER = "gender";
	private static final String TAG_WEIGHT = "weight";
	private static final String TAG_HEIGHT = "height";

	String myJSON;
	JSONArray Users = null;

	ArrayList<HashMap<String, String>> personList;

	ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		list = (ListView) findViewById(R.id.listView1);
		personList = new ArrayList<HashMap<String, String>>();

		btnLogin = (Button) findViewById(R.id.Login_button);
		btnH = (Button) findViewById(R.id.Login_button1);
		edtId = (EditText) findViewById(R.id.Login_editText);
		edtPassword = (EditText) findViewById(R.id.Login_editText1);

		view = (ImageView) findViewById(R.id.imageAnimation);
		view.setBackgroundResource(R.drawable.frame_animation_list);
		frameAnimation = (AnimationDrawable) view.getBackground();
		
		
		StringBuilder mRetJson = new StringBuilder ();
		if(Application.getHCSAPI().GetServerConnection(mRetJson)) {
			Utility.msgbox(LoginActivity.this, "Connected server !!");
		}
		else {
			Utility.msgbox(LoginActivity.this, "Disconnected server !!");
		}

		btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				//do not use!! 
				//getData("http://192.168.10.168/ict_chcs_get_connection.php");
				
				ArrayList<String> mArrayList;
				StringBuilder mRetJson = new StringBuilder ();

				mArrayList = new ArrayList<String>();
				mArrayList.add(((EditText) findViewById(R.id.Login_editText)).getText().toString());
				mArrayList.add(((EditText) findViewById(R.id.Login_editText1)).getText().toString());
				
				if (Application.getHCSAPI().GetExUser(mArrayList, mRetJson) == true) {

					Intent intent = new Intent(LoginActivity.this, ResultActivity.class);
					startActivity(intent);

				} else {
					Utility.msgbox(LoginActivity.this, "Please Sign up !!");
				}
			}
		});
		btnH.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final Intent intentSub;
				intentSub = new Intent(LoginActivity.this, HActivity.class);
				startActivity(intentSub);
			}
		});
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			frameAnimation.start();
		} else {
			frameAnimation.stop();
		}
	}
}
