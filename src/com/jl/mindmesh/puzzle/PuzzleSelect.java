package com.jl.mindmesh.puzzle;

import java.io.IOException;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jl.mindmesh.R;
import com.jl.mindmesh.StringUtils;
import com.jl.mindmesh.widget.PackView;

public class PuzzleSelect extends ListActivity {
	ArrayAdapter<String> adapter;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		try {
		    String[] puzzles = getResources().getAssets().list("puzzles");
			for (int x = 0; x < puzzles.length; x++) {
			  puzzles[x] = StringUtils.clearNumbers(puzzles[x]);
			}
			if (puzzles.length != 0)  adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.label, puzzles);
		} catch (IOException e) { }
	    
		final ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("Puzzle Select");
    }

	public void loadList() {
		ListView lv = getListView();
		lv.setAdapter(adapter);
		lv.setDividerHeight(4);
        lv.setOnItemClickListener(new OnItemClickListener() {
				Toast lock = null;
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					if (view instanceof PackView) {
						PackView pack = (PackView) view;
						if (lock != null) lock.cancel();
						
						MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.mini_ding_sound);
						mPlayer.start();
						
						if (pack.isLocked()) {
							lock = Toast.makeText(getApplicationContext(), "You haven't unlocked that yet!", Toast.LENGTH_SHORT);
							lock.setGravity(Gravity.CENTER, 0, 0);
							lock.show();
							return;
						}
						if (pack.isPremiumLocked()) return;
						String puzzle = pack.getText().toString();
						loadLevelSelect(position + puzzle);
					}
				}
			});
	}

	@Override
	public void onResume() {
		super.onResume();
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

	public void loadLevelSelect(String puzzle) {
        Intent intent = new Intent(this, PackSelect.class);
        intent.putExtra("puzzle", puzzle);
        startActivity(intent);
	}

}
