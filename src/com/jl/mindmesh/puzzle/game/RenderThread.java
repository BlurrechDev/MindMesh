package com.jl.mindmesh.puzzle.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class RenderThread extends Thread {
	private boolean running;
	private Canvas canvas;
	private SurfaceHolder surfaceHolder;
	private SurfacePanel surfacePanel;

	public RenderThread(SurfaceHolder holder, SurfacePanel panel) {
		surfaceHolder = holder;
		running = false;
		surfacePanel = panel;
	}

	void setRunning(boolean run) {
		running = run;
	}

	@Override
	public void run() {
		super.run();
		while (running) {
			canvas = surfaceHolder.lockCanvas();
			if (canvas != null) {
				surfacePanel.drawPanel(canvas);
				surfaceHolder.unlockCanvasAndPost(canvas);
			}
		}
	}

}
