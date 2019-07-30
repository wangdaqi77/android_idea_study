package com.example.wongki.modularization.animator

import kotlin.math.max

open class MyKeyframeSet {
    companion object {
        fun ofFloat(vararg values: Float): MyKeyframeSet {
            // 生成关键帧
            val keyframes = Array<MyFloatKeyframe>(max(2, values.size)) { index ->

                if (values.size == 1) {

                    when (index) {
                        1 -> {
                            MyKeyframe.ofFloat(0f, 0f) as MyFloatKeyframe
                        }
                        else -> {
                            MyKeyframe.ofFloat(1f, values[0])as MyFloatKeyframe
                        }
                    }
                } else {
                    if (index == 0) {
                        MyKeyframe.ofFloat(0f, values[0])as MyFloatKeyframe
                    } else {
                        MyKeyframe.ofFloat(index / (values.size - 1f), values[index])as MyFloatKeyframe
                    }
                }

            }
            return MyFloatKeyframeSet(*keyframes)
        }
    }

    lateinit var mKeyframes: Array<out MyKeyframe>
    constructor(vararg keyframes: MyKeyframe) {
        mKeyframes = keyframes
    }
}