package com.jl.mindmesh.puzzle.design.grid;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Canvas;
import android.view.MotionEvent;

public class PrimeGrid extends Grid {
	ArrayList<Integer> numbers = new ArrayList<Integer>();
	int newNumber = 0;
	int numberToDraw = 7;
	
	public PrimeGrid(int wdt, int hgt) {
		super(wdt, hgt); ///Rectangle
	}
	
	public PrimeGrid(int wdt) {
		super(wdt); ///Square
	}
	
	public void draw(Canvas canvas) {
		super.draw(canvas);
	    newNumber++;
	    if (newNumber == 1000) {
	    	numberToDraw = new Random().nextInt(100);
	    }
	    canvas.drawText("" + numberToDraw, 100, 100, paint);
	}

	public void onTapDrag(MotionEvent event) {
//		if (inside(event.getX(), event.getY())) select(getIndex(event));
	}
	
	public boolean[] getBoundaries(int index) {
		boolean left = boundry(index, index - 1);
		boolean right = boundry(index, index + 1);
		boolean up = boundry(index, index - width);
		boolean down = boundry(index, index + width);
			
		boolean[] boundries = {left, right, up, down};
		return boundries;
	}
	
	public boolean boundry(int index, int boundry) {
		if (adjacent(index, boundry)) {
			return true;
		} else {
			return false;
		}
	}
	
}
