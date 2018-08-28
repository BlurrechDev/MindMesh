package com.jl.mindmesh.puzzle.design;

import android.graphics.*;
import java.util.*;

public class Select {
	public final LinkedList<Integer> path = new LinkedList<Integer>();
	public final int color = cycleColor();
	public boolean correct = false;

	private static int colorCount = -1;

	protected int cycleColor() {
		colorCount++;
		switch (colorCount) {
			case 0:
				return Color.BLUE;
			case 1:
			    return Color.YELLOW;
			case 2:
			    return Color.RED;
		    case 3:
			    return Color.GREEN;
		    case 4:
			    return Color.GRAY;
			case 5:
			    return Color.parseColor("#AA66CC"); // Lilacy Purple
			case 6:
			    return Color.parseColor("#CC3398");
			case 7:
			    return Color.parseColor("#FF8800"); // Orange
			case 8:
			    return Color.parseColor("#DC4935");
			default:
				colorCount = 0;
				return Color.CYAN;
		}
	}

}
