package com.jl.mindmesh.adapter;

import java.io.IOException;

import android.content.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.*;
import android.widget.*;

import com.jl.mindmesh.*;

public class ImageAdapter extends BaseAdapter {
	    private Context context;
	    public Integer[] levelStatus = {
	            R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, 
	            R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
	            R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher
	    };
	    private final String uneditedPuzzle;
	    private final String uneditedMode;

	    public ImageAdapter(Context c, String puzzlePath, String modePath) {
	        context = c;
	        uneditedPuzzle = puzzlePath;
	        uneditedMode = modePath;
	    }
	    
	    public void setLength(int newLength) {
	    	levelStatus = new Integer[newLength];
	    	for (int x = 0; x < levelStatus.length; x++) {
	    		levelStatus[x] = R.drawable.ic_launcher;
	    	}
	    }

	    @Override
		public int getCount() {
	        return levelStatus.length;
	    }

	    @Override
		public Object getItem(int position) {
	        return null;
	    }

	    @Override
		public long getItemId(int position) {
	        return 0;
	    }

	    @Override
		public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView imageView;
	        if (convertView == null) {
	            imageView = new ImageView(context);
	            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	            imageView.setPadding(8, 8, 8, 8);
	        } else {
	            imageView = (ImageView) convertView;
	        }
	        if (levelStatus[position] == 324) {
	    		final String imagePath = "puzzles/" + uneditedPuzzle + "/" + uneditedMode + "/images/" + (position + 1) + ".jpg";
	    		Bitmap guessImage = null;
	    	    try {
	    			guessImage = BitmapFactory.decodeStream(context.getResources().getAssets().open(imagePath));
	    			guessImage = Bitmap.createScaledBitmap(guessImage, 200, 200, true);
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}
	        	imageView.setImageBitmap(guessImage);
	        } else {
		        imageView.setImageResource(levelStatus[position]);
	        }
	        return imageView;
	    }
		
	    public void updateLevelStatus(int index, int id) {
			levelStatus[index] = id;
		}
}
