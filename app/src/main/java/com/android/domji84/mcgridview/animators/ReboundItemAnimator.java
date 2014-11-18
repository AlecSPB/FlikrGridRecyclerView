package com.android.domji84.mcgridview.animators;

import android.animation.AnimatorSet;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by domji84 on 18/11/14.
 */
public class ReboundItemAnimator extends RecyclerView.ItemAnimator {

	// create a system to run the physics loop for a set of springs.
	private SpringSystem springSystem = SpringSystem.create();
	private SpringConfig springConfig = new SpringConfig(70, 10);

	// hold the views to animate in runPendingAnimations
	private List<RecyclerView.ViewHolder> mViewHolders = new ArrayList<RecyclerView.ViewHolder>();

	@Override
	public void runPendingAnimations() {
		if (!mViewHolders.isEmpty()) {
			int animationDuration = 300;
			AnimatorSet animator;

			for (final RecyclerView.ViewHolder viewHolder : mViewHolders) {
				final View target = viewHolder.itemView;

				// add a spring to the system
				Spring spring = springSystem.createSpring();
				spring.setSpringConfig(springConfig);
				spring.setCurrentValue(0.0f);

				// add a listener to observe the motion of the spring
				spring.addListener(new SimpleSpringListener() {

					@Override
					public void onSpringUpdate(Spring spring) {
						// you can observe the updates in the spring
						// state by asking its current value in onSpringUpdate.
						float value = (float) spring.getCurrentValue();

						target.setScaleX(value);
						target.setScaleY(value);
					}
				});

				// set the spring in motion; moving from 0 to 1
				spring.setEndValue(1.0f);
			}
		}
	}

	@Override
	public boolean animateRemove(RecyclerView.ViewHolder viewHolder) {
		return false;
	}

	@Override
	public boolean animateAdd(RecyclerView.ViewHolder viewHolder) {
		viewHolder.itemView.setAlpha(0.0f);
		viewHolder.itemView.setScaleX(0);
		viewHolder.itemView.setScaleY(0);
		return mViewHolders.add(viewHolder);
	}

	@Override
	public boolean animateMove(RecyclerView.ViewHolder viewHolder, int i, int i2, int i3, int i4) {
		return false;
	}

	@Override
	public boolean animateChange(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2, int i, int i2, int i3, int i4) {
		return false;
	}

	@Override
	public void endAnimation(RecyclerView.ViewHolder viewHolder) {
	}

	@Override
	public void endAnimations() {
	}

	@Override
	public boolean isRunning() {
		return !mViewHolders.isEmpty();
	}

}