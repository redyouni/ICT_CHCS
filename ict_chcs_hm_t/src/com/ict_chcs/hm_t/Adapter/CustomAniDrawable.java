package com.ict_chcs.hm_t.Adapter;

import android.graphics.drawable.AnimationDrawable;
import android.os.SystemClock;

public class CustomAniDrawable extends AnimationDrawable {

	private volatile int duration;// its volatile because another thread will
									// update its value
	private int currentFrame;
	boolean isRun = false;

	public CustomAniDrawable() {
		currentFrame = 0;
	}

	@Override
	public void run() {

		int n = getNumberOfFrames();
		currentFrame++;
		if (currentFrame >= n) {
			currentFrame = 0;
		}
		selectDrawable(currentFrame);
		scheduleSelf(this, SystemClock.uptimeMillis() + duration);

		isRun = true;
	}

	public void setDuration(int duration) {
		this.duration = duration;
		unscheduleSelf(this);
		selectDrawable(currentFrame);
		scheduleSelf(this, SystemClock.uptimeMillis() + duration);
	}

	public void stop() {
		if (isRun) {
			unscheduleSelf(this);
			isRun = false;
		}
	}

}