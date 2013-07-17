package com.raffledoocious.blasterbattle;

import java.util.List;
import com.raffledoocious.battleblaster.R;
import com.raffledoocious.drawing.GameBoard;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.support.v4.view.MotionEventCompat;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {	
	private Handler frame = new Handler();	
	private static final int FRAME_RATE = 20;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
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
		frame.removeCallbacks(frameUpdate);
		frame.postDelayed(frameUpdate, FRAME_RATE);
	}
	
	private OnTouchListener onTouchListener = new OnTouchListener(){
		public boolean onTouch(View v, MotionEvent event) {
			GameBoard board = (GameBoard) findViewById(R.id.the_board);
			
			if (board.getGameState() == GameState.Waiting){
				board.setGameState(GameState.Running);
			}
			else if (board.getGameState() == GameState.Running){
				int action = MotionEventCompat.getActionMasked(event);
				int index = MotionEventCompat.getActionIndex(event);
				
				if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN){
									
					int x = (int) Math.round(MotionEventCompat.getX(event, index));
					int y = (int) Math.round(MotionEventCompat.getY(event, index));
						
					//handles touches close to  right edge so bullet is not drawn off screen
					if (x > (board.getWidth() - board.getBulletWidth()) ){
						x = board.getWidth() - board.getBulletWidth();
					}
					
					//draw bullet for correct player depending on where touch was
					if (y >= board.getBottomBarrierY()){
						board.addBullet(new Bullet(x, y, Player.One));
					}
					else if (y <= board.getTopBarrierY()){
						board.addBullet(new Bullet(x, y, Player.Two));
					}
				}
			}
			else if (board.getGameState() == GameState.Ended){
				board.setGameState(GameState.Ended);
			}
			
			return true;
		}
	};
	
	private Runnable frameUpdate = new Runnable() {
		@Override
		synchronized public void run(){
			frame.removeCallbacks(frameUpdate);
			
			//update the positions of the bullets
			List<Bullet> player1Bullets = ((GameBoard) findViewById(R.id.the_board)).getPlayer1Bullets();
			for (int i = 0; i < player1Bullets.size(); i++){
				Bullet bullet = player1Bullets.get(i);
				bullet.updateBulletLocation();
				player1Bullets.set(i, bullet);
			}
			
			List<Bullet> player2Bullets = ((GameBoard) findViewById(R.id.the_board)).getPlayer2Bullets();
			for (int i = 0; i < player2Bullets.size(); i++){
				Bullet bullet = player2Bullets.get(i);
				bullet.updateBulletLocation();
				player2Bullets.set(i, bullet);
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
