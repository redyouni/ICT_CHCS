package com.ict_chcs.healthMonitor;

import com.ict_chcs.healthMonitor.Adapter.SharedPrefs;
import com.ict_chcs.healthMonitor.Adapter.Utility;
import com.ict_chcs.healthMonitor.JSONRPC.RPCAPI;

import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.util.Log;

public class Application extends android.app.Application {

	public final static String ICT_CHCS_EX_VERSION = "IctChcsEx.Version";
	public final static String ICT_CHCS_EX_LANGUAGE = "IctChcsEx.Language";
	public final static String ICT_CHCS_EX_SERVER = "IctChcsEx.Server";

	private SharedPrefs mSharePref = null;
	private Boolean mIsRun = false;
	private RPCAPI mCJAPI = null;

	@Override
	public void onCreate() {
		super.onCreate();
		
		Log.d("Application", "onCreate");
		
		mSharePref = new SharedPrefs(this);		
		
		//SET VERSION
		try {
			Log.d("Application", "versionName : " + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
			Log.d("Application", "VERSION() : " + getSharedPrefs_STRING(ICT_CHCS_EX_VERSION));
			
			if(getSharedPrefs_STRING(ICT_CHCS_EX_VERSION).equalsIgnoreCase(getPackageManager().getPackageInfo(getPackageName(), 0).versionName) == false) {
				setSharedPrefs_STRING(ICT_CHCS_EX_VERSION, getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
			}
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		mCJAPI = new RPCAPI(this);
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
	
	public RPCAPI getCJAPI() {
		return mCJAPI;
    }
}
