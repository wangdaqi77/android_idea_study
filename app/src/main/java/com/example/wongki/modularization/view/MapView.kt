package com.example.wongki.modularization.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Looper
import android.support.v4.graphics.PathParser
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.wongki.modularization.R
import org.w3c.dom.Element
import javax.xml.parsers.DocumentBuilderFactory

class MapView : View {
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    fun init(context: Context) {
        thread.start()
    }


    val strokePaint = Paint().apply {
        this.isAntiAlias = true
        strokeWidth = 1f
        this.style = Paint.Style.STROKE
    }
    val fillPaint = Paint().apply {
        this.isAntiAlias = true
        this.style = Paint.Style.FILL
    }
    var mapWidth = 0f
    var mapHeight = 0f
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        val iterator = mItems.iterator()

        canvas.scale(width / mapWidth, width / mapWidth)
        while (iterator.hasNext()) {
            val item = iterator.next()
            val path = item.path
            if (path == null) continue
            item.draw(canvas, fillPaint, strokePaint, mSelectItem == item)

        }




    }

    var mItems = ArrayList<ProvideItem>()
    @SuppressLint("RestrictedApi")
    private val thread = Thread {
        // 解析svg
        val openChinaMapRawResource = context.resources.openRawResource(R.raw.china)
        val documentBuilderFactory = DocumentBuilderFactory.newInstance()
        val documentMap = documentBuilderFactory.newDocumentBuilder().parse(openChinaMapRawResource)
        val rootElement = documentMap.documentElement
        // 转化成path
        val svgPathList = rootElement.getElementsByTagName("path")
        mItems.clear()

        var left = -1f
        var top = -1f
        var right = -1f
        var bottom = -1f
        for (index in 0 until svgPathList.length) {
            val item = svgPathList.item(index) as Element
            val pathData = item.getAttribute("android:pathData")
            val fillColor = item.getAttribute("android:fillColor")
            val strokeColor = item.getAttribute("android:strokeColor")
            val path = PathParser.createPathFromPathData(pathData)
            val provideItem = ProvideItem()
            provideItem.path = path
            provideItem.fillColor = Color.parseColor(fillColor)
            provideItem.strokeColor = Color.parseColor(strokeColor)
            mItems.add(provideItem)

            val rectF = RectF()
            path.computeBounds(rectF, true)
            if (left == -1f) {
                left = rectF.left
                top = rectF.top
                right = rectF.right
                bottom = rectF.bottom
            } else {
                if (rectF.left < left) {
                    left = rectF.left
                }

                if (rectF.top < top) {
                    top = rectF.top
                }

                if (rectF.right > right) {
                    right = rectF.right
                }

                if (rectF.bottom > bottom) {
                    bottom = rectF.bottom
                }


            }
        }

        mapWidth = right - left
        mapHeight = bottom - top

        Handler(Looper.getMainLooper()).post {
            invalidate()
        }

    }

    private var mSelectItem: ProvideItem? = null

    override fun onTouchEvent(event: MotionEvent): Boolean {
        for (item in mItems) {
            if (item.isTouch(event.x /(width / mapWidth), event.y / (width / mapWidth))) {
                mSelectItem = item
                invalidate()
                break
            }
        }
        return super.onTouchEvent(event)
    }

    class ProvideItem {
        fun draw(canvas: Canvas, fillPaint: Paint, strokePaint: Paint, isSelect: Boolean) {
            strokePaint.color = strokeColor
            fillPaint.color = if (isSelect) Color.BLUE else fillColor
            canvas.drawPath(path, fillPaint)
            canvas.drawPath(path, strokePaint)
        }

        fun isTouch(x: Float, y: Float): Boolean {
            val rectF = RectF()
            path?.computeBounds(rectF, true)
            val region = Region()
            region.setPath(path, Region(rectF.left.toInt(), rectF.top.toInt(), rectF.right.toInt(), rectF.bottom.toInt()))
            return region.contains(x.toInt(), y.toInt())
        }

        var path: Path? = null
        var fillColor: Int = Color.TRANSPARENT
        var strokeColor: Int = Color.TRANSPARENT
    }
}