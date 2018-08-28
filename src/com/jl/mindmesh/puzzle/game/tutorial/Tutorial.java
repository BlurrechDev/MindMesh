package com.jl.mindmesh.puzzle.game.tutorial;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jl.mindmesh.R;

public class Tutorial extends Activity {

	LinearLayout myGallery;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);
        
        myGallery = (LinearLayout)findViewById(R.id.mygallery);
        myGallery.addView(insertPhoto());
        myGallery.addView(insertPhoto());
        myGallery.addView(insertPhoto());
        
		final ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("Tutorial");
    }

    View insertPhoto() {
    	LinearLayout layout = new LinearLayout(getApplicationContext());
    	layout.setLayoutParams(new LayoutParams(250, 250));
    	layout.setGravity(Gravity.CENTER);
    	
    	ImageView imageView = new ImageView(getApplicationContext());
    	imageView.setLayoutParams(new LayoutParams(220, 220));
    	imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    	imageView.setImageResource(R.drawable.ic_launcher);
    	
    	layout.addView(imageView);
    	return layout;
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
    
}
