package com.raffledoocious.drawing;

import java.util.ArrayList;
import java.util.List;

import com.raffledoocious.battleblaster.R;
import com.raffledoocious.blasterbattle.Bullet;
import com.raffledoocious.blasterbattle.GameState;
import com.raffledoocious.blasterbattle.Player;
import com.raffledoocious.manager.BulletManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class GameBoard extends View {
	BulletManager bulletManager;
	
	private Paint p;
	private Paint textPaint;
	
	private int topBarrierY;
	private int bottomBarrierY;
	
	private int player1Score;
	private int player2Score;
	
	private long startTime;
	private Paint countdownPaint;
	private Paint scorePaint;
	
	private GameState gameState;
	
	public GameBoard(Context context, AttributeSet attrSet) {
		super(context, attrSet);
		
		p = new Paint();
		textPaint = new Paint();
		textPaint.setColor(Color.WHITE);
		textPaint.setTextSize(25);
		
		countdownPaint = new Paint();
		countdownPaint.setColor(Color.WHITE);
		countdownPaint.setTextSize(200);
		countdownPaint.setTextAlign(Align.CENTER);
		
		scorePaint = new Paint();
		scorePaint.setColor(Color.WHITE);
		scorePaint.setTextSize(100);
		scorePaint.setTextAlign(Align.CENTER);
		
		bulletManager = new BulletManager(getResources());
		gameState = GameState.Waiting;
	}	
	
	/*
	 * Accessors for various drawing elements
	 */
	synchronized public void addBullet(Bullet bullet){
		if (bullet.getPlayer() == Player.One){
			bulletManager.getPlayerBullets(Player.One).add(bullet);
		}
		else {
			bulletManager.getPlayerBullets(Player.Two).add(bullet);
		}
	}
	
	synchronized public List<Bullet> getPlayer1Bullets(){
		return bulletManager.getPlayerBullets(Player.One);
	}
	
	synchronized public List<Bullet> getPlayer2Bullets(){
		return bulletManager.getPlayerBullets(Player.Two);
	}
	
	synchronized public int getBulletWidth(){
		return bulletManager.getBulletBounds().width();
	}
	
	synchronized public int getBulletHeight(){
		return bulletManager.getBulletBounds().height();
	}
	
	synchronized public int getTopBarrierY(){
		return topBarrierY;
	}
	
	synchronized public int getBottomBarrierY(){
		return bottomBarrierY;
	}
	
	synchronized public GameState getGameState(){
		return gameState;
	}
	
	synchronized public void setGameState(GameState gameState){
		this.gameState = gameState;
	}
	
	synchronized public void setStartTime(long startTime){
		this.startTime = startTime;
	}
	
	@Override
	synchronized public void onDraw(Canvas canvas)	{
		bottomBarrierY = getHeight() - ( getHeight() / 8 );
		topBarrierY = getHeight() / 8;
		
		//Draw a black background
		p.setColor(Color.BLACK);
		p.setAlpha(255);
		p.setStrokeWidth(1);
		p.setStyle(Paint.Style.FILL);
		canvas.drawRect(0, 0, getWidth(), getHeight(), p);
		
		//draw the game lines
		p.setColor(Color.YELLOW);
		p.setStrokeWidth(5);
		
		canvas.drawLine(0, topBarrierY, getWidth(), topBarrierY, p);
		canvas.drawLine(0, bottomBarrierY, getWidth(), bottomBarrierY, p);
		
		if (gameState == GameState.Running){
			drawBullets(canvas);
		}
		else if (gameState == GameState.Starting){
			drawCountdown(canvas);
		}
		else if (gameState == GameState.Ended){
			drawScores(canvas);
		}
		else if (gameState == GameState.Waiting){
			player1Score = 0;
			player2Score = 0;
			drawStartMessage(canvas);
		}

	}
	
	private void drawStartMessage(Canvas canvas) {
		canvas.drawText("Place a finger on the screen.", 10, bottomBarrierY - 30, textPaint);
		RotateCanvas(canvas);		
		canvas.drawText("Place a finger on the screen.", 10, bottomBarrierY - 30, textPaint);
		canvas.restore();
	}

	private void drawScores(Canvas canvas) {
		canvas.drawText(String.valueOf(player1Score) + "-" + String.valueOf(player2Score), getWidth() / 2, getHeight() / 2, scorePaint);
		if (player1Score > player2Score){
			canvas.drawText("You lose!", 5, getHeight() - 5, textPaint);
			RotateCanvas(canvas);
			canvas.drawText("You win", 5, getHeight() - 5, textPaint);
			canvas.restore();
		}
		else if (player2Score > player1Score){
			canvas.drawText("You win!", 5, getHeight() - 5, textPaint);
			RotateCanvas(canvas);
			canvas.drawText("You lose!", 5, getHeight() - 5, textPaint);
			canvas.restore();
		}
		else {
			canvas.drawText("Tie!", 5, getHeight() - 5, textPaint);
			RotateCanvas(canvas);
			canvas.drawText("Tie!", 5, getHeight() - 5, textPaint);
			canvas.restore();
		}
	}
	
	private void drawCountdown(Canvas canvas){
		
		long currentTime = System.currentTimeMillis();
		long timeLeft = currentTime - startTime;
		int displayTime = 5 - ((int) Math.ceil(timeLeft) / 1000);
		if (displayTime <= 0) {
			gameState = GameState.Running;
			startTime = System.currentTimeMillis();
		}
		else
		{
			canvas.drawText(String.valueOf(displayTime), getWidth() / 2, getHeight() / 2, countdownPaint);
		}
	}

	private void drawBullets(Canvas canvas){
		//draw the time before the bullets
		long currentTime = System.currentTimeMillis();
		long timeLeft = currentTime - startTime;
		int displayTime = 30 - ((int) Math.ceil(timeLeft) / 1000);
		if (displayTime <= 0) {
			gameState = GameState.Ended;
			
		}
		else if (displayTime > 0 && displayTime <= 5){
			canvas.drawText(String.valueOf(displayTime), getWidth() / 2, getHeight() / 2, countdownPaint);
		}
		
		bulletManager.removeDestroyedBullets();		
		bulletManager.drawBullets(canvas);
		
		//draw player 2 score upside down
		RotateCanvas(canvas);
		canvas.drawText(String.valueOf(player2Score), 5, getHeight() - 5, textPaint);
		canvas.restore();

		//draw player 1 score
		canvas.drawText(String.valueOf(player1Score), 5, getHeight() - 5, textPaint);
		
		//update collisions to remove bullets in next frame
		bulletManager.detectCollisions();
		player1Score += bulletManager.checkForScoringBullets(Player.One, getHeight());
		player2Score += bulletManager.checkForScoringBullets(Player.Two, getHeight());
	}
	
	private void RotateCanvas(Canvas canvas){
		canvas.save(); 
        float py = this.getHeight()/2.0f;
        float px = this.getWidth()/2.0f;
        canvas.rotate(180, px, py);
	}
}
