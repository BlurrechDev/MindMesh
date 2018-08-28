package com.jl.mindmesh.puzzle.game;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.EditText;
import com.jl.mindmesh.Options;
import com.jl.mindmesh.R;

public class LevelView extends FragmentActivity {
	private String mode;
	private String uneditedMode;
	private String uneditedPuzzle;
	private String puzzle;
	private int puzzleNumber;
	private int levelCount;
	private String levelData;
	
	private SurfacePuzzle surface;
	private EditText text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.level_view);
	}
	
	public void createPuzzle() {
		mode = getIntent().getExtras().getString("mode");
		uneditedMode = getIntent().getExtras().getString("uneditedmode");
		
		puzzle = getIntent().getExtras().getString("puzzle");
		uneditedPuzzle = getIntent().getExtras().getString("uneditedpuzzle");
		
		puzzleNumber = getIntent().getExtras().getInt("puzzlenumber");
		levelData = getIntent().getExtras().getString("leveldata");
		levelCount = getIntent().getExtras().getInt("levelcount");
	    
		surface = (SurfacePuzzle) findViewById(R.id.puzzle_view);
		text = (EditText) findViewById(R.id.editText1);
		refresh(); ///Not an actual refresh but otherwise code is the same here.
	}

	public void setupActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(mode + ": " + puzzleNumber);
	}

	private void refresh() {
		surface.setup(puzzleNumber, levelCount, puzzle, mode, uneditedPuzzle, uneditedMode, levelData);
		surface.grid.resize(getDisplaySize(getWindowManager().getDefaultDisplay()));
		
		text.setY(surface.grid.size * surface.grid.height);
		text.setX(0);
		
		setupActionBar();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return false;
	}
	
	@Override
	public void onPause() {
	    super.onPause();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (surface == null) {
		    createPuzzle();
		    setupActionBar();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
			case android.R.id.home:
			    finish();
				return true;
			case R.id.action_refresh:
			    refresh();
				return true;
			case R.id.action_settings:
			    startActivity(new Intent(this, Options.class));
			    return true;
		    default:
			    return false;
		}
    }
	
	@SuppressWarnings("deprecation")
	private static Point getDisplaySize(final Display display) {
	    final Point point = new Point();
	    try {
	        display.getSize(point);
	    } catch (java.lang.NoSuchMethodError ignore) { // Older device
	        point.x = display.getWidth();
	        point.y = display.getHeight();
	    }
	    return point;
	}

}
