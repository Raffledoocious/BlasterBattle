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
//	private Bitmap player1BulletMap;
//	private Bitmap player2BulletMap;
	private List<Bullet> player1Bullets;
	private List<Bullet> player2Bullets;
	
	private Rect bulletBounds;
	
	public BulletManager(Resources resources){
		player1Bullets = new ArrayList<Bullet>();
		player2Bullets = new ArrayList<Bullet>();
//		player1BulletMap = BitmapFactory.decodeResource(resources, R.drawable.player1bullet);
//		player2BulletMap = BitmapFactory.decodeResource(resources, R.drawable.player2bullet);
//		bulletBounds = new Rect(0,0, player1BulletMap.getWidth(), player1BulletMap.getHeight());
	}
	
	public Rect getBulletBounds(){
		return bulletBounds;
	}
	
	public void addBullet(Bullet bullet){
		switch (bullet.getPlayer()){
			case One:
				player1Bullets.add(bullet);
			case Two:
				player2Bullets.add(bullet);
		}
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
}
