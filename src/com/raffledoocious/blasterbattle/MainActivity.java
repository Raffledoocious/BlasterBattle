package com.raffledoocious.blasterbattle;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.raffledoocious.battleblaster.R;
import com.raffledoocious.drawing.AnimationBoard;


public class MainActivity extends Activity {
	private static final int FRAME_RATE = 20;	
	private Handler frame = new Handler();
	private long startTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.main_layout);
		
		startTime = System.currentTimeMillis();
		
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			@Override
			public void run(){
				initGfx();
			}
		}, 1000);
	}
	
	synchronized public void initGfx() {		
		frame.removeCallbacks(frameUpdate);
		frame.postDelayed(frameUpdate, FRAME_RATE);
	}
	
	private Runnable frameUpdate = new Runnable() {
		@Override
		synchronized public void run(){
			frame.removeCallbacks(frameUpdate);
			
			//update the positions of the bullets
			List<Bullet> player1Bullets = ((AnimationBoard) findViewById(R.id.the_background)).getPlayer1Bullets();
			for (int i = 0; i < player1Bullets.size(); i++){
				Bullet bullet = player1Bullets.get(i);
				bullet.updateBulletLocation();
				player1Bullets.set(i, bullet);
			}
			
			List<Bullet> player2Bullets = ((AnimationBoard) findViewById(R.id.the_background)).getPlayer2Bullets();
			for (int i = 0; i < player2Bullets.size(); i++){
				Bullet bullet = player2Bullets.get(i);
				bullet.updateBulletLocation();
				player2Bullets.set(i, bullet);
			}
			
			//add random bullet
			addRandomBullet();
			
			((AnimationBoard) findViewById(R.id.the_background)).invalidate();
			frame.postDelayed(frameUpdate, FRAME_RATE);
		}
	};
	
	private void addRandomBullet(){
		long currentTime = System.currentTimeMillis();
		long timeElapsed = currentTime - startTime;
		AnimationBoard animationBoard = (AnimationBoard) findViewById(R.id.the_background);
		if (timeElapsed >= 1000){
			animationBoard.addRandomBullet();
			startTime = System.currentTimeMillis();
		}
	}
	
	public void startGame(View view){
		Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);      
	}
	
	public void displaySettings(View view){
		
	}
	
	public void displayAbout(View view){
		
	}
}
