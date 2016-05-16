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
}
