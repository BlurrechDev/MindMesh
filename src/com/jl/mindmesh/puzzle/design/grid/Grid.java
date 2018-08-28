package com.jl.mindmesh.puzzle.design.grid;

import android.graphics.*;
import android.view.*;
import com.jl.mindmesh.puzzle.design.*;

public class Grid extends Design {
	final float startX = 0, startY = 0;
	final int dimension;
	final Paint paint = new Paint();
	boolean[] hidden;
	
	public Grid(int wdt, int hgt) {
		width = wdt;
	    height = hgt;
		dimension = width * height;
		hidden = new boolean[dimension];
	}
	
	public Grid(int wdt) {
		this(wdt, wdt);
	}
	
	public void resize(Point displaySize) {
		size = displaySize.x / width;
	}
	
	int getIndex(MotionEvent event) {
		int xSquare = (int) Math.floor(event.getX() / size);
		int ySquare = (int) Math.floor(event.getY() / size);
		if (ySquare >= height) ySquare = height - 1;
		
		int index = ySquare + (xSquare * width); /// Changed from height
		return getClosestBound(index);
	}
	
	public void draw(Canvas canvas) {
		drawGrid(canvas, Color.BLACK, Paint.Style.STROKE, new RectF(startX, startY, startX + (width * size), startY + (height * size)));
		for (int index = 0; index < dimension; index++) {
			if (hidden[index]) continue;
			int x = (int) Math.floor(index / width);
			int y = index - (width * x);
			float currentX = startX + (x * size);
			float currentY = startY + (y * size);
			//RectF square = new RectF(currentX + 2, currentY + 2, currentX + size - 2, currentY + size - 2);
			RectF square = new RectF(currentX, currentY, currentX + size, currentY + size);
			drawGrid(canvas, Color.BLACK, Paint.Style.FILL, square);
			paint.setStrokeWidth(5);
			drawGrid(canvas, Color.LTGRAY, Paint.Style.STROKE, square);
		}
	}

	void drawGrid(Canvas canvas, int color, Paint.Style style, RectF rect) {
		paint.setColor(color);
		paint.setStyle(style);
		canvas.drawRect(rect, paint);
	}
	
	boolean adjacent(int index, int lastIndex) {
		if (!inBounds(index) || !inBounds(lastIndex)) return false;
		boolean commonRow = Math.floor(index / width) == Math.floor(lastIndex / width);
		return ((index == lastIndex - 1 && commonRow) || (index == lastIndex + 1 && commonRow) || (index + width) == lastIndex || (index - width) == lastIndex);
	}
	
	boolean inBounds(int index) {
		return index >= 0 && index < dimension;
	}
	
	int getClosestBound(int index) {
		if (index < 0) {
			return 0;
		} else if (index >= dimension) {
			return dimension - 1;
		} else {
			return index;
		}
	}
	
	boolean inside(float x, float y) {
		return (x > startX && x < startX + (width * size) && (y > startY && y < startY + (height * size)));
	}

}
