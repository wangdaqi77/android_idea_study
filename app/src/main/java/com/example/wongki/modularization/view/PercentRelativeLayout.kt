package com.example.wongki.modularization.view

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

class PercentRelativeLayout : RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
       val height =  MeasureSpec.getSize(heightMeasureSpec)
        for (i in 0 until childCount-1) {
            val view = getChildAt(i)
            val layoutParams = view.layoutParams
            if (layoutParams is LayoutParams) {
                // layoutParams.width = width * 0.5f//百分比
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
    override fun generateLayoutParams(attrs: AttributeSet?): RelativeLayout.LayoutParams {
        return LayoutParams(context,attrs)
    }


    class LayoutParams : RelativeLayout.LayoutParams {
        constructor(c: Context, attrs: AttributeSet) : super(c, attrs) {

        }

    }
}