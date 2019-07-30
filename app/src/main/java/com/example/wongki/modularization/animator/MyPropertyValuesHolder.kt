package com.example.wongki.modularization.animator


open class MyPropertyValuesHolder {

    companion object {

        fun ofFloat(propertyName: String, vararg values: Float): MyPropertyValuesHolder {

            return MyFloatPropertyValuesHolder(propertyName, *values)
        }

    }

    private lateinit var mKeyframesSet: MyKeyframeSet
    private var mPropertyName: String
    private var mValueType: Class<*>? = null
    open fun setFloatValues(vararg values: Float) {
        mValueType = Float::class.java
        mKeyframesSet = MyKeyframeSet.ofFloat(*values)
    }

    fun getValueByFraction(fraction: Float): Float {
        val keyframes = mKeyframesSet.mKeyframes
        var value = 0f
        for (i in 0 until keyframes.size) {
            val keyframe = keyframes[i] as MyFloatKeyframe
            if (keyframe.mFraction == fraction) {
                value = keyframe.getFloatValue()
                return value
            }


        }

        var preKeyframe = keyframes[0] as MyFloatKeyframe
        for (i in 1 until keyframes.size) {
            val nextKeyframes = keyframes[i] as MyFloatKeyframe
            if(fraction>0.5f){
                fraction
            }
            if (fraction < nextKeyframes.mFraction) {
                val intervalFraction = (fraction - preKeyframe.mFraction) / (nextKeyframes.mFraction - preKeyframe.mFraction)
                value = preKeyframe.getFloatValue() + (nextKeyframes.getFloatValue() - preKeyframe.getFloatValue()) * intervalFraction
                return value
            }
            preKeyframe = nextKeyframes
        }
        return value
    }

    constructor (propertyName: String) {
        mPropertyName = propertyName
    }

    fun getPropertyName(): String {
        return mPropertyName
    }

}