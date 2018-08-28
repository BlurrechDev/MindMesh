package com.jl.mindmesh.puzzle.design.grid;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

public class ImageGrid extends CharGrid {
	Bitmap behindImage;

    public ImageGrid(int wdt, int hgt, String letterz, String answers, Bitmap image) {
		super(wdt, hgt, letterz, answers);
		behindImage = image;
	}
    
    public ImageGrid(int wdt, String letterz, String answers, Bitmap image) {
		super(wdt, wdt, letterz, answers);
		behindImage = image;
	}
    
    @Override
	public void resize(Point displaySize) {
		super.resize(displaySize);
		behindImage = Bitmap.createScaledBitmap(behindImage, width * size, height * size, true);
	}
    
    @Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(behindImage, 0, 0, paint);
		super.draw(canvas);
	}

}
