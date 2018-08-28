package com.jl.mindmesh.puzzle.game.gesture;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.jl.mindmesh.puzzle.game.SurfacePanel;

public class GestureListener extends GestureDetector.SimpleOnGestureListener {
	public final SurfacePanel surface;

	public GestureListener(SurfacePanel surface) {
		this.surface = surface;
	}

    @Override
    public boolean onDoubleTap(MotionEvent event) {
		surface.grid.onTapDouble(event);
		return false;
    }


	public void onDownTap(MotionEvent event) {
		surface.grid.onTapDrag(event);
	}

	public void onUpTap() {
		surface.grid.onTapUp();
	}

}
