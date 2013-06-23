package com.raffledoocious.blasterbattle;

import java.util.List;
import com.raffledoocious.battleblaster.R;
import com.raffledoocious.drawing.GameBoard;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MainActivity extends Activity {

	private Handler frame = new Handler();	
	private static final int FRAME_RATE = 20;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final View gameBoard = findViewById(R.id.the_board);
		gameBoard.setOnTouchListener(onTouchListener);
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
	
	private OnTouchListener onTouchListener = new OnTouchListener(){
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN){
				int x = (int) event.getX();
				int y = (int) event.getY();
				
				GameBoard board = (GameBoard) findViewById(R.id.the_board);
				if (y >= board.getBottomBarrierY()){
					board.addBullet(new Bullet(x, y, Player.One));
				}
				else if (y <= board.getTopBarrierY()){
					board.addBullet(new Bullet(x, y, Player.Two));
				}
			}
			
			return false;
		}
	};
	
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
