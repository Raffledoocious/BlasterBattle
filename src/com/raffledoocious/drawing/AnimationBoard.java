package com.raffledoocious.drawing;

import java.util.List;
import java.util.Random;

import com.raffledoocious.blasterbattle.Bullet;
import com.raffledoocious.blasterbattle.Player;
import com.raffledoocious.manager.BulletManager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class AnimationBoard extends View {

	private BulletManager bulletManager;
	Random rand;
	
	public AnimationBoard(Context context, AttributeSet attrSet) {
		super(context, attrSet); 
		
		bulletManager = new BulletManager(getResources());
		rand = new Random();
	}
	
	synchronized public List<Bullet> getPlayer1Bullets(){
		return bulletManager.getPlayerBullets(Player.One);
	}
	
	synchronized public List<Bullet> getPlayer2Bullets(){
		return bulletManager.getPlayerBullets(Player.Two);
	}
	
	synchronized public void addRandomBullet(){
		int playerToAdd = rand.nextInt(2);
		Rect bulletBounds = bulletManager.getBulletBounds();
		int x = rand.nextInt(bulletBounds.width() + (int)(Math.random() * ((getWidth() - bulletBounds.width()) - bulletBounds.width()) + 1));
		if (playerToAdd == 0){	
			bulletManager.getPlayerBullets(Player.One).add(new Bullet(x, getHeight() - bulletBounds.width(), Player.One));	
		}
		else {
			bulletManager.getPlayerBullets(Player.Two).add(new Bullet(x, bulletBounds.width(), Player.Two));
		}			
	}
	
	@Override
	synchronized public void onDraw(Canvas canvas)	{
		bulletManager.removeDestroyedBullets();
		bulletManager.checkForScoringBullets(Player.One, getHeight());
		bulletManager.checkForScoringBullets(Player.Two, getHeight());
		bulletManager.drawBullets(canvas);
		bulletManager.detectCollisions();
	}

}
