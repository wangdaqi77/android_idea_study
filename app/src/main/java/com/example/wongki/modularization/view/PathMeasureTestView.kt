package com.example.wongki.modularization.view

import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.RESTART
import android.content.Context
import android.graphics.*
import android.graphics.PathMeasure.POSITION_MATRIX_FLAG
import android.graphics.PathMeasure.TANGENT_MATRIX_FLAG
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.wongki.modularization.R

class PathMeasureTestView : View {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, -1)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }


    val TAG = "TAG"
    val mBitMap by lazy {
        val ops = BitmapFactory.Options()
        ops.inSampleSize = 16
        BitmapFactory.decodeResource(resources, R.drawable.arrow, ops)
    }
    val mPath = Path()
    val mPathMeasure = PathMeasure()
    val mMatrix = Matrix()
    val pos = FloatArray(2)
    val tan = FloatArray(2)

    val mPaint = Paint().apply {
        isAntiAlias = true
        color = Color.BLUE
        style = Paint.Style.STROKE
    }
    val mLinePaint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        style = Paint.Style.STROKE
    }


    var mFloat = 0f
//    val animator = ValueAnimator.ofFloat(0f, 1f)
//    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
//        super.onSizeChanged(w, h, oldw, oldh)
//        if (!animator.isStarted) {
//            animator.repeatCount = INFINITE
//            animator.duration = 50
//            animator.addUpdateListener {
//                mFloat += 2f
//                invalidate()
//            }
//            animator.start()
//        }
//    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawLine(width / 2f, 0f, width / 2f, height.toFloat(), mLinePaint)
        canvas.drawLine(0f, height / 2f, width.toFloat(), height / 2f, mLinePaint)
        canvas.translate(width / 2f, height / 2f)

        /*
                val pathMeasure = PathMeasure(mPath, false)
        val length = pathMeasure.length
        log("pathMeasure.length : $length")
        * */

//        mPath.reset()
//        mPath.addRect(-100f, -100f, 100f, 100f, Path.Direction.CW)
//
//        val dst = Path()
//        mPath.offset(100f, 100f, dst)
//
//        canvas.drawPath(mPath, mPaint)
//        canvas.drawPath(dst, mPaint)

        mPath.reset()
        mPath.addCircle(0f, 0f, 300f, Path.Direction.CW)
        mPathMeasure.setPath(mPath, false)

        mFloat+=0.01f
        if (mFloat>1) {
            mFloat = 0f
        }
        val distance = mPathMeasure.length * mFloat  // 1/8的位置
        Log.e(TAG, "长度：${mPathMeasure.length}  距离：$distance")

//        if (mPathMeasure.getPosTan(distance, pos, tan)) {
//            val a = Math.atan2(tan[1].toDouble(), tan[0].toDouble()) * 180 / Math.PI +90
//            Log.e(TAG, "角度：$a")
//            mMatrix.reset()
//            // 旋转角度
//            mMatrix.postRotate(a.toFloat(), mBitMap.width / 2f, mBitMap.height / 2f)
//            // 移动坐标
//            mMatrix.postTranslate(pos[0] - mBitMap.width / 2f, pos[1] - mBitMap.height / 2f)
//            canvas.drawPath(mPath, mPaint)
//            canvas.drawBitmap(mBitMap, mMatrix, null)
//        }


        mMatrix.reset()
        mPathMeasure.getMatrix(distance,mMatrix,POSITION_MATRIX_FLAG.or(TANGENT_MATRIX_FLAG))
        mMatrix.preRotate(90f)
        mMatrix.preTranslate(-mBitMap.width/2f,-mBitMap.height/2f)
        canvas.drawPath(mPath, mPaint)
        canvas.drawBitmap(mBitMap, mMatrix, null)

        invalidate()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
    }


    fun log(msg: String) {
        Log.e("TAG", msg)
    }
}