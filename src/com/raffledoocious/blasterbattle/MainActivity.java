package com.raffledoocious.blasterbattle;

import java.util.List;
import java.util.Random;

import com.raffledoocious.battleblaster.R;
import com.raffledoocious.drawing.GameBoard;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.graphics.Point;
import android.view.Menu;

public class MainActivity extends Activity {

	private Handler frame = new Handler();	
	private static final int FRAME_RATE = 20;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Handler h = new Handler();
		
		h.postDelayed(new Runnable() {
			@Override
			public void run(){
				initGfx();
			}
		}, 1000);
	}

	synchronized public void initGfx() {
		GameBoard board = (GameBoard) findViewById(R.id.the_board);
		
		frame.removeCallbacks(frameUpdate);
		frame.postDelayed(frameUpdate, FRAME_RATE);
	}
	
	private Runnable frameUpdate = new Runnable() {
		@Override
		synchronized public void run(){
			frame.removeCallbacks(frameUpdate);
			
			//update the positions of the bullets
			List<Bullet> bullets = ((GameBoard) findViewById(R.id.the_board)).getBullets();
			for (int i = 0; i < bullets.size(); i++){
				Bullet bullet = bullets.get(i);
				bullet.updateBulletLocation();
				bullets.set(i, bullet);
			}
			
			((GameBoard) findViewById(R.id.the_board)).invalidate();
			frame.postDelayed(frameUpdate, FRAME_RATE);
		}
	};

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
