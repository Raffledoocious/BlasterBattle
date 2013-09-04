package com.raffledoocious.manager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SettingsManager {

	public static final String PREFS_NAME="GameSettings";
	private static final String BULLET_SPEED="bullet_speed";
	private static final String BULLET_SIZE="bullet_size";
	private SharedPreferences _sharedPrefs;
	private Editor _prefsEditor;
	
	public SettingsManager(Context context){
		this._sharedPrefs = context.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
		this._prefsEditor = _sharedPrefs.edit();
	}

	public int getBulletSize(){
		return _sharedPrefs.getInt(BULLET_SIZE, 1);
	}
	
	public int getBulletSpeed(){
		return _sharedPrefs.getInt(BULLET_SPEED, 1);	
	}
	
	public void saveBulletSize(int bulletSize){
		_prefsEditor.putInt(BULLET_SIZE, bulletSize);
	}
	
	public void saveBulletSpeed(int bulletSpeed){
		_prefsEditor.putInt(BULLET_SPEED, bulletSpeed);
	}

}
