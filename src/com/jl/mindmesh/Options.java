package com.jl.mindmesh;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.jl.mindmesh.widget.ConfirmFragment;

public class Options extends /*ActionBar*/FragmentActivity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);
		
		final ImageView mute = (ImageView) findViewById(R.id.mute_button);
		mute.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
		mute.setOnClickListener(new View.OnClickListener() {
				@SuppressLint("ShowToast")
				Toast construction = Toast.makeText(getApplicationContext(), "There are currently no sounds.", Toast.LENGTH_SHORT);
				
			    @Override
				public void onClick(View v) {
					//construction.cancel();
					construction.show();
				}

			});
		
		final Button wipe = (Button) findViewById(R.id.wipe_button);
		wipe.setOnClickListener(new View.OnClickListener() {

			    @Override
				public void onClick(View v) {
					showDialog();
				}

		});
		
	    //ActionBar actionBar = getSupportActionBar();
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.options_button);
	}
	
	void showDialog() {
		DialogFragment newFragment = ConfirmFragment.newInstance(R.string.eraseprogress);
		newFragment.show(getSupportFragmentManager(), "dialog");
	}

	public void doPositiveClick() {
		getSharedPreferences("com.jl.mindmesh", Context.MODE_PRIVATE).edit().clear().commit();
		//navigateUpTo(new Intent(getApplicationContext(), MainMenuActivity.class)); API 16
		NavUtils.navigateUpTo(this, new Intent(this, MainMenuActivity.class));
	}

	public void doNegativeClick() { }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {       
		if (menuItem.getItemId() == android.R.id.home) {
          finish();
          return true;
		} else {
	      return false;
		}
    }

}
