package com.raffledoocious.battleblaster;

import java.util.Random;

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
	
	//placeholder for drawing on the screen, this is to be replaced with user input
	private Point getRandomPoint(){
		Random r = new Random();
		
		GameBoard board = (GameBoard) findViewById(R.id.the_board);
		int minX = 0;
		int maxX = board.getWidth() - board.getBulletWidth();
		
		int minY = 0;
		int maxY = board.getHeight() - board.getBulletHeight();
		
		int x = r.nextInt(maxX - minX + 1);
		int y = r.nextInt(maxY - minY + 1);
		
		return new Point(x, y);
	}

	synchronized public void initGfx() {
		//generate two random points
		Point p1, p2;
		do {
			p1 = getRandomPoint();
			p2 = getRandomPoint();
		} while (Math.abs(p1.x - p2.x) < ((GameBoard)findViewById(R.id.the_board)).getBulletWidth());
		((GameBoard)findViewById(R.id.the_board)).addBullet(p1);
		((GameBoard)findViewById(R.id.the_board)).addBullet(p2);
		frame.removeCallbacks(frameUpdate);
		frame.postDelayed(frameUpdate, FRAME_RATE);
	}
	
	private Runnable frameUpdate = new Runnable() {
		@Override
		synchronized public void run(){
			frame.removeCallbacks(frameUpdate);
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
