package com.jl.mindmesh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jl.mindmesh.puzzle.PuzzleSelect;
	
public class MainMenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		
		final View.OnClickListener buttonForward = new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		        switch (v.getId()) {
					case R.id.play_button:
						startActivity(new Intent(getApplicationContext(), PuzzleSelect.class));
						return;
					case R.id.options_button:
						startActivity(new Intent(getApplicationContext(), Options.class));
						return;
					case R.id.store_button:
						finish();
						return;
				}
		    }
		};
		((Button) findViewById(R.id.play_button)).setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_media_play, 0);
		((Button) findViewById(R.id.options_button)).setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_menu_view, 0);
		((Button) findViewById(R.id.store_button)).setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_menu_gallery, 0);
		
		((Button) findViewById(R.id.play_button)).setCompoundDrawablePadding(40);
		((Button) findViewById(R.id.options_button)).setCompoundDrawablePadding(40);
		((Button) findViewById(R.id.store_button)).setCompoundDrawablePadding(40);

		findViewById(R.id.play_button).setOnClickListener(buttonForward);
		findViewById(R.id.options_button).setOnClickListener(buttonForward);
		findViewById(R.id.store_button).setOnClickListener(buttonForward);
	}

}
