package com.raffledoocious.blasterbattle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.raffledoocious.battleblaster.R;

public class SettingsActivity extends Activity {
	private SeekBar bulletSpeedBar;
	private SeekBar bulletSizeBar;
	private TextView bulletSizeValue;
	private TextView bulletSpeedValue;
	
	@Override 
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.settings_layout);
		bulletSpeedBar = (SeekBar) findViewById(R.id.bullet_speed_bar);
		bulletSpeedBar.setOnSeekBarChangeListener(BulletValueChanged);
		bulletSizeBar = (SeekBar) findViewById(R.id.bullet_size_bar);
		bulletSizeBar.setOnSeekBarChangeListener(BulletValueChanged);
		bulletSizeValue = (TextView) findViewById(R.id.bullet_size_value);
		bulletSpeedValue = (TextView) findViewById(R.id.bullet_speed_value);
	}
	
	OnSeekBarChangeListener BulletValueChanged = new OnSeekBarChangeListener(){

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			int current = progress;
			if (seekBar.getId() == bulletSpeedBar.getId()){
				bulletSpeedValue.setText(String.valueOf(current));
			}
			else if (seekBar.getId() == bulletSizeBar.getId()){
				bulletSizeValue.setText(String.valueOf(current));
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
