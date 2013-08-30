package com.raffledoocious.drawing;

import java.util.List;
import java.util.Random;

import com.raffledoocious.blasterbattle.Bullet;
import com.raffledoocious.blasterbattle.Player;
import com.raffledoocious.manager.BulletManager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class AnimationBoard extends View {

	private BulletManager bulletManager;
	private Paint p;
	private Random rand;
	
	public AnimationBoard(Context context, AttributeSet attrSet) {
		super(context, attrSet); 
		
		p = new Paint();
		rand = new Random();
		bulletManager = new BulletManager(getResources());
		// TODO Auto-generated constructor stub
	}
	
	synchronized public void addBullet(Player player){
//		Bullet bullet = null;
//		if (player == Player.One){
//			bullet = new Bullet(rand.nextInt(getWidth() - bulletManager.getBulletBounds().width()), getHeight() - 1, player);
//		}
//		else if (player == Player.Two){
//			bullet = new Bullet(rand.nextInt(getWidth() - bulletManager.getBulletBounds().width()), 1, player);
//		}
//		bulletManager.addBullet(bullet);		
	}
	
//	synchronized public List<Bullet> getPlayer1Bullets(){
//		//return bulletManager.getPlayerBullets(Player.One);
//	}
//	
//	synchronized public List<Bullet> getPlayer2Bullets(){
//		//return bulletManager.getPlayerBullets(Player.Two);
//	}
//	
	@Override
	synchronized public void onDraw(Canvas canvas)	{
//		bulletManager.removeDestroyedBullets();
//		bulletManager.checkForScoringBullets(getHeight(), Player.One);
//		bulletManager.checkForScoringBullets(getHeight(), Player.Two);
//		bulletManager.drawBullets(canvas, p);
//		bulletManager.detectCollisions();
	}

}
