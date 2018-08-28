package com.jl.mindmesh.puzzle.design.grid;

import java.util.List;
import java.util.Locale;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.jl.mindmesh.puzzle.design.Select;

public class CharGrid extends SelectGrid {
    private final char[] letters;
	private final String[] answerSplit;

    public CharGrid(int wdt, int hgt, String letterz, String answers) {
		super(wdt, hgt);
		letters = letterz.toCharArray();
		answerSplit = answers.split("/");
		setupPaint();
	}
	
	public CharGrid(int wdt, String letterz, String answers) {
		this(wdt, wdt, letterz, answers);
	}

	public void setupPaint() {
		paint.setUnderlineText(false);
		paint.setStrikeThruText(false);
		paint.setStrokeWidth(1);
		if (width == 4) {
			paint.setTextSize(64);
		} else {
		    paint.setTextSize(50 - width);
		}
	}

	public void draw(Canvas canvas) {
		super.draw(canvas);
		for (int index = 0; index < dimension; index++) {
			if (hidden[index]) continue;
			int x = (int) Math.floor(index / width);
			int y = index - (width * x);
			float currentX = startX + (x * size);
			float currentY = startY + (y * size);

			Select select = getSelectOf(index);
			if (select != null && select.correct) {
				hidden[index] = true;
				continue;
				//drawGrid(canvas, select.color, Paint.Style.FILL, new RectF(currentX, currentY, currentX + size, currentY + size));
			} else if (select != null) {
				deselect(index);
				///Maybe vubrate or some visual indicator
			}
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(getColorFor(index));
			if (oppositeRotate) canvas.rotate(180, ((float) (currentX + (size * 0.5))), ((float) (currentY + (size * 0.5))));
			canvas.drawText(String.valueOf(letters[index]).toUpperCase(Locale.US), ((float) (currentX + (size * 0.4))), ((float) (currentY + (size * 0.7))), paint);
			if (oppositeRotate) canvas.rotate(180, ((float) (currentX + (size * 0.5))), ((float) (currentY + (size * 0.5))));
		}
	}
	
	public boolean oppositeRotate = false;

	@Override
	public boolean completed() {
		boolean solved = true;
		for (Select select : selections) {
			if (!checkCompletion(select)) solved = false;
		}
		if (selections.size() != answerSplit.length) return false;
		return solved;
	}

	private String formWord(List<Integer> path) {
		String selectWord = "";
		for (int x : path) selectWord += letters[x];
		return selectWord;
	}

	/*
	 * Returns whether the selection is correct and updates the completion status.
	 * If the selection is not true for selected(), return false.
	 */
	public boolean checkCompletion(Select select) {
		if (!selected(select)) return false;
		String selectWord = formWord(select.path);
		for (String answer : answerSplit) {
			if (answer.equalsIgnoreCase(selectWord)) {
				select.correct = true;
				return true;
			}
		}
		select.correct = false;
		return false;
	}

}
