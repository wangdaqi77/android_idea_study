package com.example.wongki.modularization.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import com.example.wongki.modularization.R
import java.util.ArrayList
import kotlin.math.max
import kotlin.math.min

class BollView : View {
    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    val mPaint = Paint()
    lateinit var bitmap: Bitmap
    lateinit var animator: ValueAnimator
    var d = 2
    var bolls = ArrayList<Boll>()
    fun init(context: Context) {
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.test)
        val width = bitmap.width
        val height = bitmap.height
        for (x in 0 until width) {
            for (y in 0 until height) {
                val boll = Boll(
                        color = bitmap.getPixel(x, y),
                        x = x * d + d / 2f,
                        y = y * d + d / 2f,
                        r = d / 2f
                )
                boll.vX = (Math.pow((-1).toDouble(), Math.ceil(Math.random() * 1000)) * 20 * Math.random()).toFloat()
                boll.vY = rangInt(-15, 35)
                boll.aX = 0f
                boll.aY = 0.98f
                bolls.add(boll)

            }
        }
        animator = ValueAnimator.ofInt(0, 1)
                .setDuration(2000)
        animator.setInterpolator(LinearInterpolator())
        animator.repeatCount = -1
        animator.addUpdateListener {
            updateBolls()
            invalidate()
        }
    }

    private fun rangInt(i: Int, j: Int): Float {
        val max = max(i, j)
        val min = min(i, j) - 1
        return (min + Math.ceil(Math.random() * (max - min))).toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate(100f, 100f)
        for (i in 0 until bolls.size) {
                val boll = bolls.get(i)
                mPaint.color = boll.color
                canvas.drawCircle(boll.x, boll.y, boll.r, mPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            startAnim()
        }
        return super.onTouchEvent(event)
    }

    private fun startAnim() {

        animator.start()
    }

    private fun updateBolls() {
        for (boll in bolls) {
            boll.x += boll.vX
            boll.y += boll.vY
            boll.vX += boll.aX
            boll.vY += boll.aY
        }
    }

}