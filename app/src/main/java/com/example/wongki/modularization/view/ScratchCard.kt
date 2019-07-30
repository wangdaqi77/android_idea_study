package com.example.wongki.modularization.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.wongki.modularization.R

class ScratchCard : View {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    lateinit var resultBitmap: Bitmap
    lateinit var srcBitmap: Bitmap
    lateinit var dstBitmap: Bitmap
    lateinit var paint: Paint
    lateinit var path: Path
    fun init(context: Context) {

        resultBitmap = BitmapFactory.decodeResource(resources, R.drawable.qq)
        srcBitmap = BitmapFactory.decodeResource(resources, R.drawable.test)

        dstBitmap = Bitmap.createBitmap(srcBitmap.width, srcBitmap.height, Bitmap.Config.ARGB_8888)


        paint = Paint()
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = 30f

        setLayerType(LAYER_TYPE_SOFTWARE, paint)

        path = Path()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(resultBitmap, 0f, 0f, paint)

        val saveLayer = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), paint, Canvas.ALL_SAVE_FLAG)
        //离屏幕绘制
        val dstCanvas = Canvas(dstBitmap)
        dstCanvas.drawPath(path, paint)//操作都保存在了dstBitmap

        canvas.drawBitmap(dstBitmap, 0f, 0f, paint) //画目标图像
        val porterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)
        paint.xfermode = porterDuffXfermode// 图层混合模式， 目标图像和源图像叠加的地方 显示目标图像
        canvas.drawBitmap(srcBitmap, 0f, 0f, paint)//画源图像
        paint.xfermode = null
        canvas.restoreToCount(saveLayer)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

    }

    var mEventX = 0f
    var mEventY = 0f
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mEventX = event.x
                mEventY = event.y
                path.moveTo(mEventX, mEventY)

            }
            MotionEvent.ACTION_MOVE -> {


                val endX =( event.x - mEventX)/2+mEventX
                val endY =( event.y - mEventY)/2+mEventY

                path.quadTo(event.x, event.y,endX,endY)

                mEventX = event.x
                mEventY = event.y
            }
        }

        invalidate()
        return true
    }


}