package com.jl.mindmesh.puzzle.game;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jl.mindmesh.R;
import com.jl.mindmesh.puzzle.Score;
import com.jl.mindmesh.puzzle.design.Design;
import com.jl.mindmesh.puzzle.game.gesture.GestureHandler;
import com.jl.mindmesh.puzzle.game.gesture.GestureListener;

public abstract class SurfacePanel extends SurfaceView implements SurfaceHolder.Callback {
	private final GestureHandler gesture;
	protected final Context context;
	private final SharedPreferences sPref;
	private RenderThread renderThread;
	
	public Design grid;

	protected int gameState = GameState.PROGRESS;
	protected int time = 0;
	public int mistakes = 0;

	protected String puzzle = "puzzleless";
	protected int puzzleNumber = 0;
	protected String mode = "modeless";
	protected String data = "dataless";
	protected int levelCount = 0;
	
	protected String uneditedPuzzle = "puzzleless";
	protected String uneditedMode = "modeless";

	public SurfacePanel(Context ctx, AttributeSet attrSet) {
		super(ctx, attrSet);	    
		getHolder().addCallback(this);
		gesture = new GestureHandler(ctx, new GestureListener(this));
		sPref = ctx.getSharedPreferences("com.jl.mindmesh", Context.MODE_PRIVATE);
		context = ctx;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		renderThread = new RenderThread(holder, this);
		renderThread.setRunning(true);
		renderThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		renderThread.setRunning(false);
		boolean retry = true;
		while (retry) {
			try {
				renderThread.join();
				retry = false;
			} catch (Exception e) { }
		}
		renderThread = null;
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder surface, int arg1, int arg2, int arg3) {}
	
	public void setup(int puzzleNum, int newLevelCount, String puzzleName, String curMode, String puzzlePath, String modePath, String leveldata) {
		puzzleNumber = puzzleNum;
		levelCount = newLevelCount;
		puzzle = puzzleName;
		mode = curMode;
		data = leveldata;
		gameState = GameState.PROGRESS;
		
		uneditedPuzzle = puzzlePath;
		uneditedMode = modePath;
		
		generatePuzzle();
	}
	
	abstract void generatePuzzle();

	public int getCurrentCompletion() {
		return sPref.getInt(mode + puzzleNumber, Score.INCOMPLETE);
	}

	void drawPanel(Canvas canvas) {
		canvas.drawColor(Color.DKGRAY);
		switch (gameState) {
			case GameState.PROGRESS:
			    time++;
			    grid.draw(canvas);
				break;
			case GameState.COMPLETE:
				//drawCompletePopup(canvas);
				//complete();
				break;
		}
	}
	
	protected final Drawable scoreStar = getResources().getDrawable(android.R.drawable.star_on);
	protected final Drawable scoreless = getResources().getDrawable(android.R.drawable.star_off);
	public void drawCompletePopup(Canvas canvas) {
		canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher), 50, 50, new Paint());
	}

	public Drawable getStar(boolean filled) {
		return filled ? scoreStar : scoreless;
	}

	public boolean onTouchEvent(MotionEvent event) {
		//TODO(mb) Implement tap to continue on completion screen
		if (gameState == GameState.PROGRESS) {
			return gesture.onTouchEvent(event);
		} else {
			return false;
		}
	}

	public void complete() {
		MediaPlayer mPlayer = MediaPlayer.create(context, R.raw.ding_sound);
		mPlayer.start();
		sPref.edit().putInt(mode + puzzleNumber, Score.COMPLETE).commit();
		if (sPref.getInt(mode + (puzzleNumber + 1), Score.INCOMPLETE) == Score.LOCKED) {
			sPref.edit().putInt(mode + (puzzleNumber + 1), Score.INCOMPLETE).commit();
		}
		gameState = GameState.COMPLETE;
		//setVisibility(GONE);
		//((Activity) context).finish();
		((Activity) context).overridePendingTransition(android.R.anim.slide_in_left , android.R.anim.slide_out_right);
	}

}
