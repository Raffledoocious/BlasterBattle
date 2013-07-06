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
	
	private boolean detectCollisions()
	{
		boolean collisions = false;
		for (int i = 0; i < player1Bullets.size(); i++){
			for (int j = 0; j < player2Bullets.size(); j++){
				Bullet p1Bullet = player1Bullets.get(i);
				Bullet p2Bullet = player2Bullets.get(j);
				Rect p1BulletBounds = new Rect(p2Bullet.x, p1Bullet.y, p1Bullet.x + player1BulletMap.getWidth(), p1Bullet.y + player1BulletMap.getHeight());
				Rect p2BulletBounds = new Rect(p2Bullet.x, p2Bullet.y, p2Bullet.x + player2BulletMap.getWidth(), p2Bullet.y + player2BulletMap.getHeight());
				
				if (p1BulletBounds.intersect(p2BulletBounds)){
					p1Bullet.markBulletDestroyed();
					p2Bullet.markBulletDestroyed();
				}
			}
		}
		
		return collisions;
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
		
		//draw player 1 bullets
		for (int i = 0; i < player1Bullets.size(); i++) {
			Bullet bullet = player1Bullets.get(i);
			if (bullet.isDestroyed()){
				player1Bullets.remove(bullet);
			}
			else {
				canvas.drawBitmap(player1BulletMap, bullet.x, bullet.y, p);
			}	
		}
		
		//draw player 2 bullets
		for (int i = 0; i < player2Bullets.size(); i++) {
			Bullet bullet = player2Bullets.get(i);
			if (bullet.isDestroyed()){
				player2Bullets.remove(bullet);
			}
			else {
				canvas.drawBitmap(player2BulletMap, bullet.x, bullet.y, p);
			}
		}
		
		detectCollisions();
	}



	
	

}
