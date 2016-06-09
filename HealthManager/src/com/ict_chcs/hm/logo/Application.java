package com.ict_chcs.hm.logo;
import com.ict_chcs.hm.Adapter.HCSAPI;
import com.ict_chcs.hm.Adapter.SharedPrefs;

import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.util.Log;
public class Application extends android.app.Application {
	public final static String ICT_CHCS_HM_VERSION = "IctChcsHm.Version";
	public final static String ICT_CHCS_HM_LANGUAGE = "IctChcsHm.Language";
	public final static String ICT_CHCS_HM_SERVER = "IctChcsHm.Server";
	private SharedPrefs mSharePref = null;
	private Boolean mIsRun = false;
	private static HCSAPI mHCSAPI = null;
	@Override
	public void onCreate() {
		super.onCreate();
		
		Log.d("Application", "onCreate");
		
		mSharePref = new SharedPrefs(this);		
		
		//SET VERSION
		try {
			Log.d("Application", "versionName : " + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
			Log.d("Application", "VERSION() : " + getSharedPrefs_STRING(ICT_CHCS_HM_VERSION));
			
			if(getSharedPrefs_STRING(ICT_CHCS_HM_VERSION).equalsIgnoreCase(getPackageManager().getPackageInfo(getPackageName(), 0).versionName) == false) {
				setSharedPrefs_STRING(ICT_CHCS_HM_VERSION, getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
			}
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		mHCSAPI = new HCSAPI();
	}
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
	
	@Override
    public void onTerminate() {
        super.onTerminate();
        
        mSharePref = null;
    }
	
	public void setSharedPrefs_STRING(String id, String value) {
		mSharePref.savePrefsStringValue(id, value);
    }
 	
	public String getSharedPrefs_STRING(String id) {
		return mSharePref.getPrefsStringValue(id);
    }
	
	public void setRunStatus(Boolean value) {
		mIsRun = value;
    }
 	
	public Boolean getRunStatus() {
		return mIsRun;
    }
	
	public static HCSAPI getHCSAPI() {
		return mHCSAPI;
    }
	
	// USER ID 전역 변수 클래스
	public static class MyGlobals{
		private String userID, userPW;
		
		private MyGlobals() {
			
		}
		
		// 유저 아이디 함수
		public String getmMyUSERID()
		 {
			return userID;
		 }
		public void setmMyUSERID(String USERID)
		 {
			this.userID = USERID;
		 }
		
		// 유저 패스워드 함수
		public String getmMyUSERPW()
		 {
			return userPW;
		 }
		public void setmMyUSERPW(String USERPW)
		 {
			this.userPW = USERPW;
		 }
		
		private volatile static MyGlobals instance = null;
		public static MyGlobals getInstance(){
		if(instance==null){
		synchronized (MyGlobals.class){
		if(instance==null){
		instance = new MyGlobals();
				}
		 	}
		 }
			return instance;
		 }//getInstance
	}//MyGlobals
}

