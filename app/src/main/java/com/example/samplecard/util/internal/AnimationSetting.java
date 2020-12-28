package com.example.samplecard.util.internal;

import android.view.animation.Interpolator;

import com.example.samplecard.util.Direction;

public interface AnimationSetting {
    Direction getDirection();

    int getDuration();

    Interpolator getInterpolator();
}
