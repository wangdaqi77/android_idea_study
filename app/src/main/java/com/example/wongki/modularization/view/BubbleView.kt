package com.example.wongki.modularization.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class BubbleView : View {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }


    fun init(context: Context) {

    }

    var moveCircleR = 20f
    var SRC_MIN_R = 5f
    var SRC_MAX_R = 20f
    var MAX_DISTANCE = moveCircleR * 8
    var srcCircleR = SRC_MAX_R
    var textSize = 20f
    var mText = "12"
    var srcX = 0f
    var srcY = 0f
    var moveX = 0f
    var moveY = 0f
    var currentDistance = 0f //当前的距离
    var mCurrentState = State.DEF
    var MOVE_OFFSET = moveCircleR/4

    var mCurrentDistance = 0f
    var pointGX = 0f
    var pointGY = 0f

    var mPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = Color.RED
    }
    var mTextPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        color = Color.WHITE
        textSize = this@BubbleView.textSize
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        srcX = width / 2f
        srcY = height / 2f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        when (mCurrentState) {
            State.DEF -> {
                moveX = srcX
                moveY = srcY
                drawMoveCircle(canvas)
            }
            State.CONNECTION -> {
                drawSrcCircle(canvas)
                drawBei(canvas)
                drawMoveCircle(canvas)
            }
            State.SRC_DISMISS -> {
                drawMoveCircle(canvas)
            }
            State.DISMISS -> {
            }
        }


        // 触摸圆和不动圆贝塞尔连接


    }

    private fun drawSrcCircle(canvas: Canvas) {
        canvas.drawCircle(srcX, srcY, srcCircleR, mPaint)
    }

    private fun drawMoveCircle(canvas: Canvas) {
        canvas.drawCircle(moveX, moveY, moveCircleR, mPaint)
        val rect = Rect()
        val leading = mTextPaint.fontMetrics.leading
        mTextPaint.getTextBounds(mText, 0, mText.length, rect)
        val textX = moveX - rect.centerX()
        val textY = moveY - rect.centerY() - leading
        canvas.drawText(mText, textX, textY, mTextPaint)
    }

    fun reset() {
        mCurrentState = State.DEF
    }

    fun updateCurrentDistance(currentX: Float, currentY: Float) {
        mCurrentDistance = Math.hypot((currentY - srcY).toDouble(), (currentX - srcX).toDouble()).toFloat()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val currentX = event.x
        val currentY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                updateCurrentDistance(currentX, currentY)
                if (mCurrentState == State.DEF) {
                    if (mCurrentDistance < srcCircleR + MOVE_OFFSET) {
                        mCurrentState = State.CONNECTION
                    } else {
                        mCurrentState = State.DEF
                    }

                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (mCurrentState != State.DEF) {

                    updateCurrentDistance(currentX, currentY)

                    // 超过最大距离
                    if (mCurrentDistance > MAX_DISTANCE - MOVE_OFFSET) {
                        // SRC circle消失
                        mCurrentState = State.SRC_DISMISS
                    } else {
                        // 连接状态
                        srcCircleR = moveCircleR - mCurrentDistance / 8
                        mathBeiPoint(currentX, currentY)
                    }
                    moveX = currentX
                    moveY = currentY
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                mCurrentDistance = Math.hypot((currentY - srcY).toDouble(), (currentX - srcX).toDouble()).toFloat()
                if (mCurrentState != State.DEF) {
                    updateCurrentDistance(currentX, currentY)
                    if (mCurrentDistance > MAX_DISTANCE) {
                        // 回弹 动画结束后
                        mCurrentState = State.SRC_DISMISS
                    } else {
                        // 爆炸效果
                    }
                }

            }
        }
        return true
    }


    var srcTX = 0f
    var srcTY = 0f
    var srcBX = 0f
    var srcBY = 0f
    var moveTX = 0f
    var moveTY = 0f
    var moveBX = 0f
    var moveBY = 0f
    private fun mathBeiPoint(currentX: Float, currentY: Float) {
        pointGX = (currentX + srcX) / 2
        pointGY = (currentY + srcY) / 2

        val sinTheta = (currentY - srcY) / mCurrentDistance
        val cosTheta = (currentX - srcX) / mCurrentDistance


        srcTX = srcX - sinTheta * srcCircleR
        srcTY = srcY + cosTheta * srcCircleR
        srcBX = srcX + sinTheta * srcCircleR
        srcBY = srcY - cosTheta * srcCircleR

        moveTX = currentX - sinTheta * moveCircleR
        moveTY = currentY + cosTheta * moveCircleR
        moveBX = currentX + sinTheta * moveCircleR
        moveBY = currentY - cosTheta * moveCircleR
    }

    val path = Path()
    private fun drawBei(canvas: Canvas) {
        path.reset()
        path.moveTo(srcTX, srcTY)
        path.quadTo(pointGX, pointGY, moveTX, moveTY)
        path.lineTo(moveBX, moveBY)

        path.quadTo(pointGX, pointGY, srcBX, srcBY)

        path.lineTo(srcTX, srcTY)
        canvas.drawPath(path, mPaint)

    }

    enum class State {
        DEF, // 默认
        CONNECTION, // 移动
        SRC_DISMISS,//消失
        DISMISS,//消失
    }
}