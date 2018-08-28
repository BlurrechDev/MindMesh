package com.jl.mindmesh.puzzle.design;

import android.graphics.*;
import android.view.*;

public abstract class Design {
	public int width, height, size;

    public abstract void resize(Point displaySize);

	public abstract void draw(Canvas canvas);
	
	public void onTapUp() {
		
	}
	
	public void onTapDrag(MotionEvent event) {
		
	}
	
	public void onTapDouble(MotionEvent event) {
		
	}
	
	public boolean completed() {
		return true;
	}
}
