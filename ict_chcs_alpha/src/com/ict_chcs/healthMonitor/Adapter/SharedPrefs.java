package com.ict_chcs.healthMonitor.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefs {
	private SharedPreferences appSharedPrefs;
	private Editor prefsEditor;

	public SharedPrefs(Context context)	{
	    this.appSharedPrefs = context.getSharedPreferences("lexcobikePref", Activity.MODE_PRIVATE);
	    this.prefsEditor = appSharedPrefs.edit();
	}

	public String getLanguageStatus(String key) {
	    return appSharedPrefs.getString(key, "null");
	}

	public void saveLanguageStatus(String key , String Value) {
	    prefsEditor.putString(key, Value);
	    prefsEditor.commit();
	}
	
	public String getLanguageValue(String key) {
	    return appSharedPrefs.getString(key, "null");
	}

	public void saveLanguageValue(String key , String Value) {
	    prefsEditor.putString(key, Value);
	    prefsEditor.commit();
	}
	
	public String getResolutionValue(String key) {
	    return appSharedPrefs.getString(key, "null");
	}

	public void saveResolutionValue(String key , String Value) {
	    prefsEditor.putString(key, Value);
	    prefsEditor.commit();
	}
	
	public String getProductValue(String key) {
	    return appSharedPrefs.getString(key, "null");
	}

	public void saveProductValue(String key , String Value) {
	    prefsEditor.putString(key, Value);
	    prefsEditor.commit();
	}

	public int getPrefsValue(String key) {
	    return appSharedPrefs.getInt(key, 1);
	}

	public void savePrefsValue(String key , int Value) {
	    prefsEditor.putInt(key, Value);
	    prefsEditor.commit();
	}
	
	public String getPrefsStringValue(String key) {
	    return appSharedPrefs.getString(key, "");
	}

	public void savePrefsStringValue(String key , String Value) {
	    prefsEditor.putString(key, Value);
	    prefsEditor.commit();
	}
	
	//값 가져오기
	public String getPassPrefsValue(String key) {
	    return appSharedPrefs.getString(key, "0000");
	}
	
	//값 수정하기
	public void savePassPrefsValue(String key , String Value) {
	    prefsEditor.putString(key, Value);
	    prefsEditor.commit();
	}
}
