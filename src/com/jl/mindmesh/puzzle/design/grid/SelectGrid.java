package com.jl.mindmesh.puzzle.design.grid;

import java.util.concurrent.ConcurrentLinkedQueue;

import android.graphics.Color;
import android.view.MotionEvent;

import com.jl.mindmesh.puzzle.design.Select;

public class SelectGrid extends Grid {
	final ConcurrentLinkedQueue<Select> selections = new ConcurrentLinkedQueue<Select>();
	private Select current = new Select();

	public SelectGrid(int wdt, int hgt) {
		super(wdt, hgt);
	}
	
	public SelectGrid(int wdt) {
		this(wdt, wdt);
	}
	
	public void onTapUp() {
		saveSelect();
	}

	public void onTapDrag(MotionEvent event) {
		if (inside(event.getX(), event.getY())) select(getIndex(event));
	}
	
	public void onTapDouble(MotionEvent event) {
		if (deselect(getIndex(event))) {} //surface.mistakes++;
		clearSelect();
	}
	
	public Select getSelectOf(int index) {
		if (current.path.contains(index)) return current;
		for (Select select : selections) {
			if (select.path.contains(index)) return select;
		}
		return null;
	}
	
	public void select(int index) {
		if (!inBounds(index)) return;
		if (selected(getSelectOf(index))) {
			clearSelect();
		} else {
	        if (emptyOrAdjacent(index)) current.path.add(index);
		}
	}

	/*
	 * Removes the selection associated to a square.
	 * Returns whether successful.
	 */
	public boolean deselect(int index) {
		Select select = getSelectOf(index);
		if (selected(select)) {
			return selections.remove(select);
		} else {
			return false;
		}
	}

	public int getColorFor(int index) {
		Select select = getSelectOf(index);
		if (select != null) {
			return select.color;
		} else {
			return Color.WHITE;
		}
	}

	public boolean emptyOrAdjacent(int index) {
		if (!current.path.isEmpty()) {
			return adjacent(index, current.path.getLast().intValue());
		} else {
			return true;
		}
	}

	public void saveSelect() {
		if (current.path.size() >= 3) {
			selections.add(current);
			current = new Select();
		} else {
		    clearSelect();
		}
	}

	public void clearSelect() {
		current.path.clear();
	}

	public boolean selected(Select select) {
		return select != null && select != current;
	}
	
	public boolean[] getBoundaries(int index) {
		boolean left = boundry(index, index - 1);
		boolean right = boundry(index, index + 1);
		boolean up = boundry(index, index - width);
		boolean down = boundry(index, index + width);
			
		boolean[] boundries = {left, right, up, down};
		return boundries;
	}
	
	public boolean boundry(int index, int boundry) {
		if (adjacent(index, boundry)) {
			Select select = getSelectOf(boundry);
			return select == null || !select.path.contains(index);
		} else {
			return false;
		}
	}
	
}
