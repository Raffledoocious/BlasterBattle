package com.raffledoocious.drawing;

import java.util.ArrayList;
import java.util.List;

import com.raffledoocious.battleblaster.R;
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

	private List<Point> bulletField;
	private Bitmap bulletMap;
	private Rect bulletBounds;
	private Paint p;
	private int topBarrierX;
	private int bottomBarrierX;
	private int velocity = 8;
	
	public GameBoard(Context context, AttributeSet attrSet) {
		super(context, attrSet);
		
		p = new Paint();
		bulletField = new ArrayList<Point>();
		bulletMap = BitmapFactory.decodeResource(getResources(), R.drawable.bullet);
		bulletBounds = new Rect(0,0, bulletMap.getWidth(), bulletMap.getHeight());
	}
	
	//accessors for the main drawing method
	synchronized public void addBullet(Point bullet){
		bulletField.add(bullet);
	}
	
	synchronized public List<Point> getBullets(){
		return bulletField;
	}
	
	synchronized public int getBulletWidth(){
		return bulletBounds.width();
	}
	
	synchronized public int getBulletHeight(){
		return bulletBounds.height();
	}
	
	synchronized public int getTopBarrierX(){
		return topBarrierX;
	}
	
	synchronized public int getBottomBarrierX(){
		return bottomBarrierX;
	}
	
	synchronized public int getVelocity(){
		return velocity;
	}
	
	@Override
	synchronized public void onDraw(Canvas canvas)	{
		//Draw a black background
		p.setColor(Color.BLACK);
		p.setAlpha(255);
		p.setStrokeWidth(1);
		canvas.drawRect(0, 0, getWidth(), getHeight(), p);
		
		//draw the game lines
		p.setColor(Color.YELLOW);
		p.setStrokeWidth(5);
		
		int topLineYStart = getHeight() - ( getHeight() / 8 );
		canvas.drawLine(0, topLineYStart, getWidth(), topLineYStart, p);
		canvas.drawLine(0, getHeight() / 8, getWidth(), getHeight() / 8, p);
		
		//draw the bullets
		for (int i = 0; i < bulletField.size(); i++) {
			if (bulletField.get(i).x >= 0) {
				canvas.drawBitmap(bulletMap, bulletField.get(i).x, bulletField.get(i).y, p);
			}
		}
		
	}

}
