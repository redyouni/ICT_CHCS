package com.ict_chcs.hm.logo;
import java.util.ArrayList;
import com.example.logo.R;
import com.ict_chcs.hm.Adapter.Utility;
import com.ict_chcs.hm.logo.Application.MyGlobals;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
public class LoginActivity extends Activity {
	Button btnLogin, btnH;
	EditText edtId, edtPassword;
	CheckBox Auto_LogIn;
	SharedPreferences setting;
	SharedPreferences.Editor editor;
	private AnimationDrawable frameAnimation;
	private ImageView view;
	private final long	FINSH_INTERVAL_TIME    = 2000;
	private long		backPressedTime        = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		btnLogin = (Button) findViewById(R.id.Login_button);
		btnH = (Button) findViewById(R.id.Login_button1);
		edtId = (EditText) findViewById(R.id.Login_editText);
		edtPassword = (EditText) findViewById(R.id.Login_editText1);
		view = (ImageView) findViewById(R.id.imageAnimation);
		view.setBackgroundResource(R.drawable.frame_animation_list);
		frameAnimation = (AnimationDrawable) view.getBackground();
		edtId.setPrivateImeOptions("defaultInputmode=english;");
		// �ڵ� �α��� �E��
				Auto_LogIn = (CheckBox) findViewById(R.id.chbAutoLogin);
				setting = getSharedPreferences("setting", 0);
				editor= setting.edit();
				
				 if(setting.getBoolean("Auto_Login_enabled", false)){
			         edtId.setText(setting.getString("ID", ""));
			         edtPassword.setText(setting.getString("PW", ""));
			         Auto_LogIn.setChecked(true);
			      }
				 
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
				ArrayList<String> mArrayList;
				StringBuilder mRetJson = new StringBuilder ();
				
				mArrayList = new ArrayList<String>();
				mArrayList.add(edtId.getText().toString());
				mArrayList.add(edtPassword.getText().toString());
				
				if (!edtId.getText().toString().equals("") && !edtPassword.getText().toString().equals("")
						&& Application.getHCSAPI().GetExUser(mArrayList, mRetJson) == true) {
					
					MyGlobals.getInstance().setmMyUSERID(edtId.getText().toString());
					MyGlobals.getInstance().setmMyUSERPW(edtPassword.getText().toString());	
					Utility.msgbox(getApplicationContext(), "로그인 완료");
					Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
					startActivity(intent);
				} else {
					Utility.msgbox(LoginActivity.this, "아이디 혹은 비밀번호가 맞지 않습니다 !!");
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
		
		// �ڵ� �α��� ����
			 Auto_LogIn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		         @Override
		         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		            // TODO Auto-generated method stub
		            if(isChecked){
		               String ID = edtId.getText().toString();
		               String PW = edtPassword.getText().toString();
		               
		               editor.putString("ID", ID);
		               editor.putString("PW", PW);
		               editor.putBoolean("Auto_Login_enabled", true);
		               editor.commit();
		            }else{
		               editor.clear();
		               editor.commit();
		            }
		         }
	      });
			 
	}//onCreate
	 // �ڷΰ��� ����
	 @Override 
	      public void onBackPressed() {
	      long tempTime        = System.currentTimeMillis();
	          long intervalTime    = tempTime - backPressedTime;
	   
	          if ( 0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime ) {
	              super.onBackPressed(); 
	          } 
	          else { 
	              backPressedTime = tempTime; 
	              Toast.makeText(getApplicationContext(),"'뒤로'버튼을 한번더 누르시면 종료됩니다.",Toast.LENGTH_SHORT).show(); 
	          } 
	      }//onBackPressed
	 
	 @Override
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
	    
	//onBackPressed
		 
		 
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

