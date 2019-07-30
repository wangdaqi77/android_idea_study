package com.example.wongki.modularization

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import java.util.*
import kotlin.collections.HashMap

class Fireworks : View {
    var startFireworks = false
    var fireworking = false
    var maxRadius = 0
    val maxLineMun = 100
    var lineNum = 0
    var defRadius = 4

    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,-1)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()
        setBackgroundColor(Color.TRANSPARENT)
    }

    fun start(lineNum: Int, maxRadius: Int, translationDuration: Long = 1500, fireworksDuration: Long = 4000) {
        if (fireworking) {
            return
        }
        fireworking = true
        this.lineNum = if (lineNum < maxLineMun) lineNum else maxLineMun
        this.maxRadius = maxRadius

        generatePaints(lineNum)

        launch(translationDuration) {
            startFireworks(fireworksDuration)
        }


    }

    private fun generatePaints(num: Int) {
        cachePaints.clear()
        for (index in 1..num) {
            obtainPaint(index)
        }
    }

    val random = Random()
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (startFireworks) {
            //周长
            val radius = layoutParams.height / 2
            val C = (radius * 2 * 3.14)
            for (index in 1..lineNum) {
                val path: Path = generatePath(radius, C, index, lineNum)
                val paint: Paint = obtainPaint(index)
                canvas.drawPath(path, paint)
            }
//            val path = Path()
//            path.addCircle(radius.toFloat(),radius.toFloat(),defRadius.toFloat(), Path.Direction.CW)
//            canvas.drawPath(path, defPaint)
        } else {
            val defRadiusF = defRadius.toFloat()
            val path = Path()
            path.moveTo(defRadiusF, 0f)
            path.cubicTo(defRadiusF, 0f, 0f, 0f, defRadiusF, defRadiusF * 2)
            path.moveTo(defRadiusF, 0f)
            path.cubicTo(defRadiusF, 0f, defRadiusF * 2, 0f, defRadiusF, defRadiusF * 2)
            canvas.drawPath(path, defPaint)
        }


    }

    val defPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            strokeWidth = 1f
            color = Color.rgb(250, 250, 210)
            style = Paint.Style.FILL
        }
    }
    val cachePaints by lazy { HashMap<Int, Paint>() }
    private fun obtainPaint(index: Int): Paint {
        var paint = cachePaints[index]
        if (paint != null) return paint
        val a = random.nextInt(255)
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)
        val color = Color.rgb(r, g, b)
//        val color = Color.BLACK

        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1f
        paint.color = color
        cachePaints[index] = paint
        return paint
    }

    private fun generatePath(radius: Int, c: Double, index: Int, lineNum: Int) = Path().apply {

        val angle = 360f / lineNum * index // 角度
        val aL = c / 360 * angle

        //val position: Position = getPositionByAngle(angle)
        val x1 = 0f + radius
        val y1 = 0f + radius
        var y3 = y1 + Math.sin(angle.toDouble()) * radius
        var x3 = x1 + Math.cos(angle.toDouble()) * radius
        //angle:60.0 (x1:210.0,y1:210.0), (x3:418.69629851861646,y3:186.63646035746055)
        var x2 = x3 / 2
        var y2 = y3 / 2
        moveTo(x1, y1)
        cubicTo(x1, y1, x2.toFloat(), y2.toFloat(), x3.toFloat(), y3.toFloat())
        // Log.e("TEST","index:$index -> (x1:$x1,y1:$y1), (x2:$x2,y2:$y2), (x3:$x3,y3:$y3)")
        // Log.e("TEST","index:$index -> angle:$angle (x1:$x1,y1:$y1), (x3:$x3,y3:$y3)")

        //lineTo(x3.toFloat(),y3.toFloat())
    }

    private fun getPositionByAngle(angle: Float) =
            when (angle) {
                in 0..90 -> Position.TOP_L
                in 90..180 -> Position.TOP_R
                in 180..270 -> Position.BOTTOM_R
                in 270..360 -> Position.BOTTOM_L
                else -> Position.TOP_L
            }

    enum class Position {
        TOP_L,
        TOP_R,
        BOTTOM_L,
        BOTTOM_R,

    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun startFireworks(fireworksDuration: Long) {
        val animator = ObjectAnimator
                .ofInt(this, "radius", defRadius, maxRadius)
                .setDuration(fireworksDuration)

        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                setRadius(defRadius)
                startFireworks = false
                fireworking = false
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
                startFireworks = true
            }
        })
        animator.setInterpolator(DecelerateInterpolator())
        animator.start()
    }

    fun setRadius(radius: Int) {
        val size = radius * 2
        layoutParams.width = size
        layoutParams.height = size
        requestLayout()
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun launch(translationDuration: Long, onEnd: () -> Unit) {
        val parent = parent as ViewGroup
        val startY = parent.height - height / 2
        val animator = ObjectAnimator
                .ofFloat(this, "translationY", startY.toFloat(), 0f)
                .setDuration(translationDuration)
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator?) {
                // 开启烟花
                onEnd()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator?) {

            }

        })
        animator.setInterpolator(AccelerateInterpolator())
        animator.start()
    }

    override fun setTranslationX(translationX: Float) {
        super.setTranslationX(translationX)
    }

    override fun setTranslationY(translationY: Float) {
        super.setTranslationY(translationY)
    }

}