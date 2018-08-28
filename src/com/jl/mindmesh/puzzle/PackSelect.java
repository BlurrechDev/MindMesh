package com.jl.mindmesh.puzzle;

import java.io.IOException;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jl.mindmesh.R;
import com.jl.mindmesh.StringUtils;
import com.jl.mindmesh.puzzle.game.tutorial.Tutorial;
import com.jl.mindmesh.widget.PackView;

public class PackSelect extends ListActivity {
    private String puzzle;
	ArrayAdapter<String> adapter;
    String[] packs;
    ArrayList<Integer> levelCounts = new ArrayList<Integer>();

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		try {
		    puzzle = getIntent().getStringExtra("puzzle");
		    packs = getResources().getAssets().list("puzzles/" + puzzle);
			for (int x = 0; x < packs.length; x++) {
				if (packs[x].contains(".")) {
					packs[x] = packs[x].substring(0, packs[x].indexOf("."));
				}
				if (packs[x].contains("_")) {
					levelCounts.add(Integer.parseInt(packs[x].split("_")[1]));
					packs[x] = packs[x].substring(0, packs[x].indexOf("_"));
				}
				packs[x] = StringUtils.clearNumbers(packs[x]);
			}
            adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.label, packs);
		} catch (IOException e) { }
	    final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.package_actionbar_title);
    }
	
	public void loadList() {
		final ListView lv = getListView();
		lv.setAdapter(adapter);
		lv.setDividerHeight(4);
        lv.setOnItemClickListener(new OnItemClickListener() {
				Toast lock = null;
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					if (view instanceof PackView) {
						PackView pack = (PackView) view;
						
					    final String mode = pack.getText().toString();
					    final String uneditedMode = getEncodedPackName(parent.indexOfChild(view));
						
						MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.mini_ding_sound);
						mPlayer.start();
					    
					    if (mode.equalsIgnoreCase("Tutorial ")) {
					    	loadTutorial(uneditedMode);
					    	return;
					    } else {
							if (lock != null) lock.cancel();
							if (pack.isLocked()) {
								lock = Toast.makeText(getApplicationContext(), "You haven't unlocked that yet!", Toast.LENGTH_SHORT);
								lock.setGravity(Gravity.CENTER, 0, 0);
								lock.show();
								return;
							}
							if (pack.isPremiumLocked()) return;
							loadLevelSelect(mode, uneditedMode);
					    }
					}
				}
			});
        lv.setOnHierarchyChangeListener(new OnHierarchyChangeListener() {
        	int viewsReviewed = 0;
			@Override
			public void onChildViewAdded(View parent, View child) {
	            Class<? extends View> c = child.getClass();
	            if (c == PackView.class) {
	            	PackView p = (PackView) child;
	            	if (levelCounts.isEmpty()) return;
	            	p.levelCount = levelCounts.get(viewsReviewed);
	            	viewsReviewed++;
	            	p.setText(p.getText());
	            }
			}

			@Override
			public void onChildViewRemoved(View parent, View child) {}
        });
	}
	
	public String getEncodedPackName(int index) {
		try {
		    return getResources().getAssets().list("puzzles/" + puzzle)[index];
		} catch (Exception e) {
			return "Error";
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		levelCounts = new ArrayList<Integer>();
		loadList();
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {       
		if (menuItem.getItemId() == android.R.id.home) {
			finish();
			return true;
		} else {
			return false;
		}
    }
	
	public void loadTutorial(String unedtiedMode) {
        Intent intent = new Intent(this, Tutorial.class);
        startActivity(intent);
	}

	public void loadLevelSelect(String mode, String uneditedMode) {
        Intent intent = new Intent(this, LevelSelect.class);
        intent.putExtra("uneditedmode", uneditedMode);
        intent.putExtra("mode", mode);
		intent.putExtra("uneditedpuzzle", puzzle);
		intent.putExtra("puzzle", StringUtils.clearNumbers(puzzle));
        startActivity(intent);
	}

}
