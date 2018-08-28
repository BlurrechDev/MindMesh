package com.jl.mindmesh.puzzle;

import java.io.InputStream;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.jl.mindmesh.R;
import com.jl.mindmesh.StringUtils;
import com.jl.mindmesh.adapter.ImageAdapter;
import com.jl.mindmesh.puzzle.game.LevelView;

public class LevelSelect extends Activity {
	private String mode;
	private String uneditedMode;
	private String puzzle;
	private String uneditedPuzzle;
	private String data;
	
	private int levelCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.level_select);
		
		mode = getIntent().getExtras().getString("mode");
		uneditedMode = getIntent().getExtras().getString("uneditedmode");
		puzzle = getIntent().getStringExtra("puzzle");
		uneditedPuzzle = getIntent().getStringExtra("uneditedpuzzle");
		
		try {
		    InputStream puzzleData = getResources().getAssets().open("puzzles/" + uneditedPuzzle + "/" + uneditedMode + "/" + "data.xml"); //must receive unedited path
			data = StringUtils.convertStreamToString(puzzleData, false);
			String[] splitContents = data.split("%");
			levelCount = Integer.parseInt(splitContents[0]);
			data = data.replaceFirst(splitContents[0] + "%", "");
		} catch (Exception exception) { }
	
		final ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.level_select);
	}
	
	public void updateDisplayedDifficulty() {
		((TextView) findViewById(R.id.difficulty)).setText(mode + " " + getString(R.string.pack));
	}
	
	@Override
	public void onResume() {
		super.onResume();
		updateProgress(new ImageAdapter(this, uneditedPuzzle, uneditedMode));
	    ((GridView) findViewById(R.id.level_grid)).setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
					loadPuzzleLevel(position + 1);
				}
		});
	    updateDisplayedDifficulty();
	}
	
	public void updateProgress(ImageAdapter i) {
		i.setLength(levelCount);
		SharedPreferences sPref = getSharedPreferences("com.jl.mindmesh", Context.MODE_PRIVATE);
		for (int x = 2; x <= i.levelStatus.length; x++) {
			int status = sPref.getInt(mode + (x - 2), Score.INCOMPLETE);
			int xStat = sPref.getInt(mode + (x - 1), Score.INCOMPLETE);
			if ((status == Score.INCOMPLETE || status == Score.LOCKED) && (xStat == Score.INCOMPLETE || xStat == Score.LOCKED)) {
				sPref.edit().putInt(mode + x, Score.LOCKED).commit();
			}
		}
		for (int x = 0; x < i.levelStatus.length; x++) {
			int status = sPref.getInt(mode + (x + 1), Score.INCOMPLETE);
			switch (status) {
			    case Score.COMPLETE:
			    	i.updateLevelStatus(x, 324);
			    	break;
				case Score.LOCKED:
				    i.updateLevelStatus(x, android.R.drawable.ic_lock_lock);
				    break;
				case Score.INCOMPLETE:
				default:
				    i.updateLevelStatus(x, android.R.drawable.picture_frame);
					break;
					
			}
		}
		((GridView) findViewById(R.id.level_grid)).setAdapter(i);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {       
		if (menuItem.getItemId() == android.R.id.home) {
	      if (lock != null) lock.cancel();
	      finish();
          return true;
		} else {
	      return false;
		}
    }
	
	Toast lock = null;
	public void loadPuzzleLevel(int levelNumber) {
		int status = getSharedPreferences("com.jl.mindmesh", Context.MODE_PRIVATE).getInt(mode + levelNumber, Score.INCOMPLETE);
		if (lock != null) lock.cancel();
		if (status == Score.LOCKED) {
			lock = Toast.makeText(getApplicationContext(), "You haven't unlocked that yet!", Toast.LENGTH_SHORT);
			lock.show();
			return;
		}
    	Intent intent = new Intent(this, LevelView.class);
    	intent.putExtra("mode", mode);
    	intent.putExtra("uneditedmode", uneditedMode);
    	
        intent.putExtra("puzzle", puzzle);
        intent.putExtra("uneditedpuzzle", uneditedPuzzle);
        
        intent.putExtra("levelcount", levelCount);
    	intent.putExtra("puzzlenumber", levelNumber);
    	intent.putExtra("leveldata", data);
    	startActivity(intent);
	}

}
