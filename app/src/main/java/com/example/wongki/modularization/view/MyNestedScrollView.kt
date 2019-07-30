package com.example.wongki.modularization.view

import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet

class MyNestedScrollView : NestedScrollView {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        mScrollListener?.onScroll(l, t)
        super.onScrollChanged(l, t, oldl, oldt)
    }

    private var mScrollListener: ScrollListener? = null
    fun setScrollListener(scrollListener: ScrollListener) {
        this.mScrollListener = scrollListener
    }

    interface ScrollListener {
        fun onScroll(x: Int, y: Int)
    }
}