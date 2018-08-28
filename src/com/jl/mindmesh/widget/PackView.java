package com.jl.mindmesh.widget;

import android.content.*;
import android.util.*;
import android.view.Gravity;
import android.widget.*;

import com.jl.mindmesh.puzzle.*;

public class PackView extends TextView {
    SharedPreferences sPref;
	final String[] lockedPackages = {"Medium", "Hard", "Extreme", "Obscure"};
	final String[] premiumPackages = {"Master", "Ultimate", "Tablet Novice", "Tablet Advanced"};
	public int levelCount = 2;
	
	public PackView(Context context, AttributeSet attrSet) {
		super(context, attrSet);
		sPref = context.getSharedPreferences("com.jl.mindmesh", Context.MODE_PRIVATE);
	}
	
	public void setIcon(int id) {
		if (getText().toString().contains(" ")) {
			id = 0;
			setGravity(Gravity.CENTER);
		}
	    setCompoundDrawablesWithIntrinsicBounds(0, 0, id, 0);
	}
	
	protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
		super.onTextChanged(text, start, lengthBefore, lengthAfter);
		setIcon(getIconFromStatus(getScoreStatus()));
	}
	
	public boolean isLocked() {
		if (lockedPackages == null) return false;
		String mode = getText().toString();
		for (int x = 0; x < lockedPackages.length; x++) {
			if (lockedPackages[x].equalsIgnoreCase(mode)) return true;
		}
		return false;
	}
	
	public boolean isPremiumLocked() {
		if (premiumPackages == null) return false;
		String mode = getText().toString();
		for (int x = 0; x < premiumPackages.length; x++) {
			if (premiumPackages[x].equalsIgnoreCase(mode)) return true;
		}
		return false;
	}
	

	public int getIconFromStatus(int status) {
		switch (status) {
			case Score.LOCKED:
			    return android.R.drawable.ic_lock_lock;
			case Score.PREMIUM_LOCKED:
			    return android.R.drawable.ic_lock_idle_lock;
			case Score.COMPLETE:
			    return android.R.drawable.btn_star_big_on;
			case Score.INCOMPLETE:
			    return android.R.drawable.btn_star_big_off;
			default:
			    return android.R.drawable.btn_star_big_off;
		}
	}
	
	public int getScoreStatus() {
		if (isPremiumLocked()) return Score.PREMIUM_LOCKED;
		if (isLocked()) return Score.LOCKED;
		
		String mode = getText().toString();
		
		int lowestScore = Score.COMPLETE;
		for (int x = 1; x < levelCount + 1; x++) {
			try {
			    int score = sPref.getInt(mode + x, Score.INCOMPLETE);
				if (score < lowestScore) lowestScore = score;
			} catch (Exception e) { }
		}
		return lowestScore;
	}
	
}
