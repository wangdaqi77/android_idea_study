package com.example.wongki.modularization.view.recycler

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import com.example.wongki.modularization.R
import kotlin.math.min

class RecyclerView : ViewGroup {
    var mAdapter: Adapter<in ViewHolder>? = null
    var mRecycler: Recycler? = null
    var mViews: ArrayList<ViewHolder> = ArrayList()
    var mFirstRow = 0 // 屏幕中第一个View在所有数据中的位置
    var mPreviousY = 0f//当前手指Y
    var mScrollY = 0
    var mNeedRelayout = false
    val heights: ArrayList<Int> = ArrayList()
    var touchSlop = 0

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
        mNeedRelayout = true
    }


    fun setAdapter(adapter: Adapter<in ViewHolder>) {
        this.mAdapter = adapter
        this.mRecycler = Recycler(adapter.getTypesCount())
        mNeedRelayout = true
        mPreviousY = 0f
        mScrollY = 0
        mFirstRow = 0
        mViews.clear()
        heights.clear()
        removeAllViews()
    }

    fun getAdapter() = mAdapter

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec)

        var h = 0
        val adapter = getAdapter()
        if (adapter != null) {
            for (i in 0 until adapter.getItemCount()) {
                val itemHeight = adapter.getItemHeight()
                h += adapter.getItemHeight()
                heights.add(itemHeight)
            }
        }

        val finalHeight = min(heightMeasureSize, h)
        var finalMode = when (finalHeight) {
            in ViewGroup.LayoutParams.WRAP_CONTENT..ViewGroup.LayoutParams.WRAP_CONTENT -> {
                MeasureSpec.AT_MOST
            }
            else -> {
                MeasureSpec.EXACTLY
            }
        }
        val finalHeightMeasureSpec = MeasureSpec.makeMeasureSpec(finalHeight, finalMode)
        super.onMeasure(widthMeasureSpec, finalHeightMeasureSpec)
    }

    fun sumArray(heights: List<Int>, startIndex: Int, count: Int): Int {
        var totalHeight = 0
        for (i in startIndex until startIndex + count) {
            totalHeight += heights[i]
        }
        return totalHeight
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val adapter = mAdapter ?: return

        if (mNeedRelayout) {
            mNeedRelayout = false
            var top = 0
            var bottom = 0
            val width = r - l
            val height = b - t
            for (position in 0..adapter.getItemCount()) {
                val itemHeight = adapter.getItemHeight()
                bottom += itemHeight
                makeAndStep(adapter, position, 0, top, width, bottom)
                top = bottom
                if (top >= height) {
                    break
                }
            }
        }
        Log.e("TEST", "views.size:${mViews.size}")
    }

    /**
     * 安装item
     */
    private fun makeAndStep(adapter: Adapter<in ViewHolder>, position: Int, left: Int, top: Int, right: Int, bottom: Int) {
        val viewHolder = obtainView(position, adapter, right - left, bottom - top)
//        val view = viewHolder.mView
//        val parent = view.parent
//        if (parent != this) {
//            if (parent is ViewGroup) {
//                parent.removeView(view)
//            }
//            addView(view)
//        }

        val view = viewHolder.mView
        addView(view)
        view.layout(0, top, right, bottom)
        mViews.add(viewHolder)


    }

    private fun obtainView(position: Int, adapter: Adapter<in ViewHolder>, width: Int, height: Int): ViewHolder {
        var viewHolder = mRecycler?.get(adapter.getItemViewType(position))
        if (viewHolder == null) {
            viewHolder = adapter.onCreateViewHolder(this, adapter.getItemViewType(position)) as? ViewHolder
        }

        if (viewHolder == null) {
            throw NullPointerException("onCreateViewHolder must be have")
        }
        val view = viewHolder.mView
        view.setTag(R.id.recycler_view_item_type, adapter.getItemViewType(position))
        adapter.onBindViewHolder(viewHolder, position)
        view.measure(
                MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        )
        return viewHolder
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    // 当滑动时才会拦截
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        Log.e("TEST", "onInterceptTouchEvent action:${ev.action}")
        var intercept = false
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mPreviousY = ev.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                if (Math.abs(ev.rawY - mPreviousY) > touchSlop) {
                    // 拦截
                    intercept = true
                }
            }
            MotionEvent.ACTION_UP -> {
            }

        }
        return intercept
    }

    // 消费滑动事件
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                val touchY = event.rawY
                val distance = mPreviousY - touchY
                Log.e("TEST", " distance：$distance  mPreviousY：$mPreviousY  touchY：$touchY  ")
                scrollBy1(0, distance.toInt())
                mPreviousY = touchY

//                // 上滑
//                if (distance > 0) {
//                    scrollBy(x.toInt(), distance.toInt())
//                    // 下滑
//                } else if (distance < 0) {
//
//                }
            }
        }
        return true
    }


    fun scrollBy1(x: Int, y: Int) {
        val adapter = mAdapter ?: return
        Log.e("TEST", "scrollBy y：$y  mScrollY：$mScrollY")
        mScrollY += y
        val height = height
        Log.e("TEST", "scrollBy  mScrollY：$mScrollY")
        if (mScrollY < 0 && mFirstRow == 0) {
            mScrollY = 0

        } else if (mScrollY > 0 && mFirstRow + mViews.size == adapter.getItemCount()) {
            mScrollY = 0
        }

        if (mScrollY > 0) {
            // 上滑

            // 第一条移除边界处理
            while (mScrollY >= heights[mFirstRow]) {
                // 移出去
                val viewHolder = mViews[0]
                removeView(viewHolder.mView)
                mScrollY -= heights[mFirstRow]
                mViews.remove(viewHolder)
                mRecycler?.recycle(adapter.getItemViewType(mFirstRow), viewHolder)
                mFirstRow++
            }

            // 加载底部布局
            // 有剩余高度
            while (getFillHeight() <= height) {
                val position = mFirstRow + mViews.size
                val obtainView = obtainView(position, adapter, width, heights[position])
                addView(obtainView.mView)
                mViews.add(obtainView)
            }

        } else if (mScrollY < 0) {
            //下滑
            Log.e("TEST", " 准备移除底部布局 current($mFirstRow, ${mFirstRow + mViews.size - 1})   mScrollY：$mScrollY")
            while (getFillHeight()>=height) {
                val viewHolder = mViews[mViews.size - 1]
                removeView(viewHolder.mView)
                mRecycler?.recycle(adapter.getItemViewType(mFirstRow + mViews.size - 1), viewHolder)
                mViews.remove(viewHolder)
            }

            Log.e("TEST", " 准备加载顶部布局 current($mFirstRow, ${mFirstRow + mViews.size - 1})   mScrollY：$mScrollY")
            // 最后一条移除边界处理
            while (mFirstRow>0&&mScrollY <= 0) {
                mFirstRow--
                val obtainView = obtainView(mFirstRow, adapter, width, heights[mFirstRow])
                addView(obtainView.mView, 0)
                mViews.add(0, obtainView)
                mScrollY += heights[mFirstRow]
            }
            Log.e("TEST", " 加载顶部布局结束 current($mFirstRow, ${mFirstRow + mViews.size - 1})   mScrollY：$mScrollY")
        }
        repositionViews()
    }

    private fun repositionViews() {
        var left: Int
        var top: Int
        var right: Int
        var bottom: Int
        left = 0
        right = width
        top = -mScrollY
        var row = mFirstRow
        for (view in mViews) {
            val rowHeight = heights[row]
            bottom = top + rowHeight
            //Log.e("TEST", "row:$row ->  left：$left  top：$top  right：$right   bottom：$bottom  ")

            view.mView.layout(left, top, right, bottom)
            top = bottom
            row++
        }
    }

    private fun getFillHeight() = sumArray(heights, mFirstRow, mViews.size-1) - mScrollY

    abstract class Adapter<VH : ViewHolder> {
        abstract fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH?
        abstract fun onBindViewHolder(holder: VH, position: Int)
        fun getItemViewType(position: Int): Int {
            return 0
        }

        abstract fun getItemCount(): Int
        abstract fun getTypesCount(): Int
        abstract fun getItemHeight(): Int

    }

    abstract class ViewHolder(val mView: View) {

    }
}