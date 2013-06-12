package com.raffledoocious.drawing;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

public class GameBoard extends View {

	private List<Point> bulletField = null;
	private Paint p;
	
	public GameBoard(Context context, AttributeSet attrSet) {
		super(context, attrSet);
		
		p = new Paint();
	}
	
	@Override
	synchronized public void onDraw(Canvas canvas)
	{
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
	}

}
