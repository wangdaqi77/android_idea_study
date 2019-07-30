package com.example.wongki.modularization.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.RESTART
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator

class ActivityLoadingView : View {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    val pointR = 6f // 6个点半径
    val pointColors = //6个点的颜色
            arrayOf(Color.GRAY, Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW, Color.CYAN)
    val pointPaint = Paint().apply {
        //6个点的画笔
        isAntiAlias = true
        style = Paint.Style.FILL
    }
    val BACKGROUND_COLOR = Color.WHITE

    val centerCircleR = 50f // 中心圆的半径
    var centerCircleX = 0f
    var centerCircleY = 0f
    val centerPaint = Paint().apply {
        //6个点的画笔
        isAntiAlias = true
        style = Paint.Style.FILL
        color = BACKGROUND_COLOR
    }
    val centerC = // 中间圆周长
            (centerCircleR * 2 * Math.PI).toFloat()


    fun init(context: Context) {
        startRotatePoint()
    }

    var distance = 0f
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (centerCircleX == 0f) {
            centerCircleX = w / 2f
            centerCircleY = h / 2f

            distance = (Math.hypot(centerCircleX.toDouble(), centerCircleY.toDouble()) * 2).toFloat()
        }
    }

    val threePaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        color = BACKGROUND_COLOR
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (mCurrentCenterCircleR != pointR) {
            drawBackground(canvas)
            drawCircle(canvas)
        } else {
            threePaint.strokeWidth = distance / 2 - mCurrentThreeR
            val r =mCurrentThreeR +  threePaint.strokeWidth/2
            canvas.drawCircle(centerCircleX, centerCircleY, r, threePaint)
        }
    }

    private fun drawCircle(canvas: Canvas) {
        val rotateAngle = Math.PI * 2 / pointColors.size
        for (i in 0 until pointColors.size) {
            val pointColor = pointColors[i]
            pointPaint.color = pointColor
            val a = (rotateAngle) * (i.toDouble()) + mCurrentRotateArc
            val pointX = centerCircleX + Math.cos(a) * mCurrentCenterCircleR
            val pointY = centerCircleY - Math.sin(a) * mCurrentCenterCircleR
            canvas.drawCircle(pointX.toFloat(), pointY.toFloat(), pointR, pointPaint)
        }
    }

    private fun drawBackground(canvas: Canvas) {
        canvas.drawColor(BACKGROUND_COLOR)
    }

    var mCurrentRotateArc = 0f
    private fun startRotatePoint() {
        mCurrentCenterCircleR = centerCircleR
        val animator = ValueAnimator.ofFloat((Math.PI * 2).toFloat(), 0f)
        animator.repeatCount = 2
        animator.duration = 1200
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener {
            mCurrentRotateArc = it.animatedValue as Float
            invalidate()
        }
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {


            }

            override fun onAnimationEnd(animation: Animator?) {
                startSecond()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
        animator.start()
    }

    var mCurrentCenterCircleR = 0f
    private fun startSecond() {
        val animator = ValueAnimator.ofFloat(pointR, centerCircleR)
        //animator.repeatCount = 2
        animator.duration = 1200
        animator.interpolator = OvershootInterpolator(10f)
        animator.addUpdateListener {
            mCurrentCenterCircleR = it.animatedValue as Float
            invalidate()
        }
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {


            }

            override fun onAnimationEnd(animation: Animator?) {
                startThree()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
        animator.reverse()
    }


    var mCurrentThreeR = 0f
    private fun startThree() {
        val animator = ValueAnimator.ofFloat(pointR, distance / 2)
        //animator.repeatCount = 2
        animator.duration = 1200
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener {
            mCurrentThreeR = it.animatedValue as Float
            invalidate()
        }
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {


            }

            override fun onAnimationEnd(animation: Animator?) {
                //startSecond()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
        animator.start()
    }
}