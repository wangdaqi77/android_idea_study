package com.example.wongki.modularization.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class PaintView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    val mPaint = Paint().apply {
        color = Color.RED
        textSize = 100f
        isAntiAlias = true
        strokeWidth = 10f
        style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //canvas?.drawText("哈哈哈哈哈g",0f,100f,mPaint)
        mPaint.setStrokeCap(Paint.Cap.SQUARE)
        val path = Path()
        path.moveTo(10f,10f)
        path.lineTo(200f,200f)
        path.lineTo(200f,10f)
        canvas.drawPath(path,mPaint)
//        canvas.drawRect(100f, 100f, 500f, 500f, mPaint)
//        canvas.drawLine(10f,20f,100f,200f)

    }

}