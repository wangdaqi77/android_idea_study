package com.example.wongki.modularization.animator

class MyFloatKeyframe : MyKeyframe {

    private var mValue: Float = 0f

    constructor(fraction: Float, value: Float) {
        this.mValue = value
        mFraction = fraction
        mValueType = Float::class.java
    }


    fun getFloatValue(): Float {
        return mValue
    }
}