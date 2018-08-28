package com.jl.mindmesh.puzzle.game;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;

import com.jl.mindmesh.puzzle.design.grid.ImageGrid;
import com.jl.mindmesh.puzzle.design.grid.PrimeGrid;

public class SurfacePuzzle extends SurfacePanel {

	public SurfacePuzzle(Context ctx, AttributeSet attrSet) {
		super(ctx, attrSet);
	}

	@Override
	public void complete() {
		super.complete();
	}
	
	public void generatePuzzle() {
		if (puzzle.equalsIgnoreCase("WordMesh")) {
			wordMeshPuzzleGen();
		} else if (puzzle.equalsIgnoreCase("PrimeMesh")) {
			primeMeshPuzzleGen();
		} else {
			/// TODO(mb) Draw error message to improve application polish.
		}
	}
	
	public void primeMeshPuzzleGen() {
		grid = new PrimeGrid(5, 5);
		time = 0;
		mistakes = 0;
	}
	
	public void wordMeshPuzzleGen() {
	    int gridWidth = 4;
		String[] splitData = data.split("%");
		String letters = getLevelLayout(puzzleNumber, splitData);
		String answers = getLevelAnswer(puzzleNumber, splitData);
		if (mode.equalsIgnoreCase("Simple")) {
			gridWidth = 4;
		} else if (mode.equalsIgnoreCase("Easy")) {
			gridWidth = 5;
		} else if (mode.equalsIgnoreCase("Medium")) {
			gridWidth = 6;
			//Error
			//letters = "divineunholyavengecursedattackshield";
			//answers = "DIVINE/UNHOLY/AVENGE/CURSED/ATTACK/SHIELD";
		} else if (mode.equalsIgnoreCase("Hard")) {
			gridWidth = 7;
			//Error
		} else if (mode.equalsIgnoreCase("Extreme")) {
			gridWidth = 8;
			//Error
		}
		
		final String imagePath = "puzzles/" + uneditedPuzzle + "/" + uneditedMode + "/images/" + puzzleNumber + ".jpg";
		Bitmap guessImage = null;
	    try {
			Bitmap openedImage = BitmapFactory.decodeStream(getResources().getAssets().open(imagePath));
			guessImage = Bitmap.createScaledBitmap(openedImage, 200, 200, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		grid = new ImageGrid(gridWidth, letters, answers, guessImage);
		time = 0;
		mistakes = 0;
	}
	
	public String getLevelLayout(int index, String[] splitData) {
		if (index > levelCount) index = levelCount;
		if ((index - 1) * 2 >= splitData.length) {
			return splitData[splitData.length - 2];
		} else {
		    return splitData[(index - 1) * 2];
		}
	}
	
	public String getLevelAnswer(int index, String[] splitData) {
		if (index > levelCount) index = levelCount;
		if ((index * 2) - 1 >= splitData.length) {
			return splitData[splitData.length - 1];
		} else {
		    return splitData[(index * 2) - 1];
		}
	}

}
