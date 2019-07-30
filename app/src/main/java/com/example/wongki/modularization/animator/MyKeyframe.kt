package com.example.wongki.modularization.animator

import android.animation.TimeInterpolator


open class MyKeyframe {
    /**
     * The time at which mValue will hold true.
     */
    var mFraction: Float = 0f

    /**
     * The type of the value in this Keyframe. This type is determined at construction time,
     * based on the type of the `value` object passed into the constructor.
     */
    lateinit var mValueType: Class<*>

    /**
     * The optional time interpolator for the interval preceding this keyframe. A null interpolator
     * (the default) results in linear interpolation over the interval.
     */
    var mInterpolator: TimeInterpolator? = null

    companion object {

        fun ofFloat(fraction: Float, value: Float): MyKeyframe {
            return MyFloatKeyframe(fraction, value)
        }
    }
}