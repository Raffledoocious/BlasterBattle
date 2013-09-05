package com.raffledoocious.blasterbattle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.raffledoocious.battleblaster.R;
import com.raffledoocious.manager.SettingsManager;

public class SettingsActivity extends Activity {
	private SeekBar bulletSpeedBar;
	private SeekBar bulletSizeBar;
	private TextView bulletSizeValue;
	private TextView bulletSpeedValue;
	private SettingsManager settingsManager;
	private final int INITIAL_VALUE = 1;
	
	@Override 
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		settingsManager = new SettingsManager(getApplicationContext());
		
		setContentView(R.layout.settings_layout);
		bulletSizeValue = (TextView) findViewById(R.id.bullet_size_value);
		bulletSpeedValue = (TextView) findViewById(R.id.bullet_speed_value);
		bulletSpeedBar = (SeekBar) findViewById(R.id.bullet_speed_bar);
		bulletSpeedBar.setOnSeekBarChangeListener(BulletValueChanged);
		bulletSpeedBar.setProgress(settingsManager.getBulletSpeed() - 1);
		bulletSizeBar = (SeekBar) findViewById(R.id.bullet_size_bar);
		bulletSizeBar.setOnSeekBarChangeListener(BulletValueChanged);
		bulletSizeBar.setProgress(settingsManager.getBulletSize() - 1);
		
		//set the text values in the case where the progress changed is not triggered
		bulletSizeValue.setText(String.valueOf(settingsManager.getBulletSize()));
		bulletSpeedValue.setText(String.valueOf(settingsManager.getBulletSpeed()));
	}
	
	OnSeekBarChangeListener BulletValueChanged = new OnSeekBarChangeListener(){

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

			if (seekBar.getId() == bulletSpeedBar.getId()){
				bulletSpeedValue.setText(String.valueOf(progress + 1));
				settingsManager.saveBulletSpeed(progress + 1);
			}
			else if (seekBar.getId() == bulletSizeBar.getId()){
				bulletSizeValue.setText(String.valueOf(progress + 1));
				settingsManager.saveBulletSize(progress + 1);
			}
			
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
	};
}
