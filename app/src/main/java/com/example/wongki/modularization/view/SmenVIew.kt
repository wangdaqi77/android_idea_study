package com.example.wongki.modularization.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class SmenVIew : View {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initPath()
    }

    val origin = Path()
    val pathDst = Path()
    var animatedValue = 0f
    val paint = Paint().apply {
        this.style = Paint.Style.STROKE
        this.strokeWidth = 10f
        this.isAntiAlias = true
        this.color = Color.RED
    }

    val pathMeasure = PathMeasure()


    val lenList = ArrayList<Float>()
    var totalLen = 0F
    private fun initPath() {
        origin.addCircle(300f, 300f, 100f, Path.Direction.CW)
        origin.moveTo(300f - 50, 300f - 50f)
        origin.lineTo(300f + 50f, 300f + 50f)
        origin.moveTo(300f + 50, 300f - 50f)
        origin.lineTo(300f - 50, 300f + 50f)

        pathMeasure.setPath(origin, false)
        do {
            val length = pathMeasure.length
            totalLen += length
            lenList.add(length)
        } while (pathMeasure.nextContour())
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        val animator = ValueAnimator.ofFloat(0f, 1f)

        animator.setDuration(2000)
        animator.addUpdateListener { animation ->
            animatedValue = animation.animatedValue as Float
            invalidate()

        }
        animator.start()

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        pathMeasure.setPath(origin, false)

        val length = totalLen
        val distance = animatedValue * length
        var lastTotal = 0F
        do {
            val len = pathMeasure.length
            val finalDistance = if (distance - lastTotal <0) Math.abs(lastTotal - distance) else distance -lastTotal
            pathMeasure.getSegment(0f, finalDistance, pathDst, true)
            canvas?.drawPath(pathDst, paint)
            lastTotal+=len
        } while (lastTotal < distance && pathMeasure.nextContour())

    }

}