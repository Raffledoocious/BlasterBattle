package com.raffledoocious.manager;

import java.util.ArrayList;
import java.util.List;

import com.raffledoocious.battleblaster.R;
import com.raffledoocious.blasterbattle.Bullet;
import com.raffledoocious.blasterbattle.Player;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class BulletManager {
	private Bitmap player1BulletMap;
	private Bitmap player2BulletMap;
	private List<Bullet> player1Bullets;
	private List<Bullet> player2Bullets;
	
	private Paint p;
	
	private Rect bulletBounds;
	
	public BulletManager(Resources resources, int bulletSize, int bulletSpeed){
		p = new Paint();
		player1Bullets = new ArrayList<Bullet>();
		player2Bullets = new ArrayList<Bullet>();
		player1BulletMap = BitmapFactory.decodeResource(resources, R.drawable.player1bullet);
		player2BulletMap = BitmapFactory.decodeResource(resources, R.drawable.player2bullet);
		bulletBounds = new Rect(0,0, player1BulletMap.getWidth(), player1BulletMap.getHeight());
	}
	
	public List<Bullet> getPlayerBullets(Player player){
		switch(player){
		case One:
			return player1Bullets;
		case Two:
			return player2Bullets;
		default:
			return null;
		}
	}
	
	public void removeDestroyedBullets(){
		//remove destroyed bullets
		for (int i = 0; i < player1Bullets.size(); i++){
			if (player1Bullets.get(i).isDestroyed()){
				player1Bullets.remove(i);
			}
		}
		
		//remove destroyed bullets
		for (int i = 0; i < player2Bullets.size(); i++){
			if (player2Bullets.get(i).isDestroyed()){
				player2Bullets.remove(i);
			}
		}	
	}
	
	public void drawBullets(Canvas canvas){
		//draw player 1 bullets
		for (int i = 0; i < player1Bullets.size(); i++) {
			Bullet bullet = player1Bullets.get(i);
			canvas.drawBitmap(player1BulletMap, bullet.x, bullet.y, p);
		}
		
		//draw player 2 bullets
		for (int i = 0; i < player2Bullets.size(); i++) {
			Bullet bullet = player2Bullets.get(i);
			canvas.drawBitmap(player2BulletMap, bullet.x, bullet.y, p);
		}
	}
	
	public boolean detectCollisions(){
		boolean collisions = false;
		for (int i = 0; i < player1Bullets.size(); i++){
			for (int j = 0; j < player2Bullets.size(); j++){
				Bullet p1Bullet = player1Bullets.get(i);
				Bullet p2Bullet = player2Bullets.get(j);
				Rect p1BulletBounds = new Rect(p1Bullet.x, p1Bullet.y, p1Bullet.x + player1BulletMap.getWidth(), p1Bullet.y + player1BulletMap.getHeight());
				Rect p2BulletBounds = new Rect(p2Bullet.x, p2Bullet.y, p2Bullet.x + player2BulletMap.getWidth(), p2Bullet.y + player2BulletMap.getHeight());
				
				if (p1BulletBounds.intersect(p2BulletBounds)){
					p1Bullet.markBulletDestroyed();
					p2Bullet.markBulletDestroyed();
					collisions = true;
				}
			}
		}
		
		return collisions;
	}

	public int checkForScoringBullets(Player player, int height) {
		int scoreToAdd = 0;
		
		if (player == Player.One){
			//mark bullets which moved scored as destroyed
			for (int i = 0; i < player1Bullets.size(); i++){
				Bullet bullet = player1Bullets.get(i);
				if (bullet.y <= 0 && !bullet.isDestroyed()){
					bullet.markBulletDestroyed();
					scoreToAdd++;
				}
			}
		}
		else if (player == Player.Two){
			for (int i = 0; i < player2Bullets.size(); i++){
				Bullet bullet = player2Bullets.get(i);
				if (bullet.y >= height && !bullet.isDestroyed()){
					bullet.markBulletDestroyed();
					scoreToAdd++;
				}
			}
		}
		
		return scoreToAdd;
	}
	
	public Rect getBulletBounds(){
		return bulletBounds;
	}
}
