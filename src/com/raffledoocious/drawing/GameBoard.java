package com.raffledoocious.drawing;

import java.util.ArrayList;
import java.util.List;

import com.raffledoocious.battleblaster.R;
import com.raffledoocious.blasterbattle.Bullet;
import com.raffledoocious.blasterbattle.Player;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class GameBoard extends View {

	private List<Bullet> player1Bullets;
	private List<Bullet> player2Bullets;
	private Bitmap player1BulletMap;
	private Bitmap player2BulletMap;
	private Rect bulletBounds;
	private Paint p;
	private int topBarrierY;
	private int bottomBarrierY;
	
	public GameBoard(Context context, AttributeSet attrSet) {
		super(context, attrSet);
		
		p = new Paint();
		player1Bullets = new ArrayList<Bullet>();
		player2Bullets = new ArrayList<Bullet>();
		player1BulletMap = BitmapFactory.decodeResource(getResources(), R.drawable.player1bullet);
		player2BulletMap = BitmapFactory.decodeResource(getResources(), R.drawable.player2bullet);
		bulletBounds = new Rect(0,0, player1BulletMap.getWidth(), player1BulletMap.getHeight());

	}
	
	//accessors for the main drawing method
	synchronized public void addBullet(Bullet bullet){
		if (bullet.getPlayer() == Player.One){
			player1Bullets.add(bullet);
		}
		else {
			player2Bullets.add(bullet);
		}
		
	}
	
	synchronized public List<Bullet> getPlayer1Bullets(){
		return player1Bullets;
	}
	
	synchronized public List<Bullet> getPlayer2Bullets(){
		return player2Bullets;
	}
	
	synchronized public int getBulletWidth(){
		return bulletBounds.width();
	}
	
	synchronized public int getBulletHeight(){
		return bulletBounds.height();
	}
	
	synchronized public int getTopBarrierY(){
		return topBarrierY;
	}
	
	synchronized public int getBottomBarrierY(){
		return bottomBarrierY;
	}
	
	/*
	 * iterates over all bullets in field and checks for collisions
	 */
	private List<Point> checkForCollisions(){
		List<Point> collisions = new ArrayList<Point>();
		for(Bullet player1Bullet : player1Bullets){
			for (Bullet player2Bullet : player2Bullets){
				if (player1Bullet.y == player2Bullet.y) {
					int player1LowerX = player1Bullet.x - (bulletBounds.width() / 2);
					int player1UpperX = player1Bullet.x + (bulletBounds.width() / 2);
					int player2LowerX = player2Bullet.x - (bulletBounds.width() / 2);
					int player2UpperX = player2Bullet.x + (bulletBounds.width() / 2);
					
					//bullet explosion will be based on corners of bullet, 
					//might need to clean this up in the future
					if (player1LowerX <= player2UpperX && player1LowerX >= player2LowerX){
						collisions.add(new Point(player1LowerX, player1Bullet.y));
					}
					else if (player1UpperX <= player2UpperX && player1UpperX >= player2LowerX){
						collisions.add(new Point(player1UpperX, player1Bullet.y));	
					}
				}
				
			}
		}
		return null;
	}
	
	@Override
	synchronized public void onDraw(Canvas canvas)	{
		bottomBarrierY = getHeight() - ( getHeight() / 8 );
		topBarrierY = getHeight() / 8;
		
		//Draw a black background
		p.setColor(Color.BLACK);
		p.setAlpha(255);
		p.setStrokeWidth(1);
		canvas.drawRect(0, 0, getWidth(), getHeight(), p);
		
		//draw the game lines
		p.setColor(Color.YELLOW);
		p.setStrokeWidth(5);
		
		canvas.drawLine(0, topBarrierY, getWidth(), topBarrierY, p);
		canvas.drawLine(0, bottomBarrierY, getWidth(), bottomBarrierY, p);
		
		//draw the bullets
		for (int i = 0; i < player1Bullets.size(); i++) {
			Bullet bullet = player1Bullets.get(i);
			
			if (bullet.x >= 0) {
				if (bullet.getPlayer() == Player.One){
					canvas.drawBitmap(player1BulletMap, bullet.x, bullet.y, p);	
				}
				else {
					canvas.drawBitmap(player2BulletMap, bullet.x, bullet.y, p);
				}
				
			}
		}
		
	}

}
