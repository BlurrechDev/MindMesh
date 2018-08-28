package com.jl.mindmesh.puzzle.game.gesture;

import android.content.*;
import android.support.v4.view.*;
import android.view.*;

public class GestureHandler extends GestureDetectorCompat {
	private final GestureListener gesture;

    public GestureHandler(Context context, GestureListener gl) {
		super(context, gl);
		gesture = gl;
	}

	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
			    gesture.onDownTap(event);
			    break;
		    case MotionEvent.ACTION_UP:
				gesture.onUpTap();
				break;
		}
		if (gesture.surface.grid.completed()) gesture.surface.complete();
		return true;
	}

}
