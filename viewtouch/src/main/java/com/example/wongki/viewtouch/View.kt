package com.example.wongki.viewtouch

open class View {
    private var left: Int = 0
    private var top: Int = 0
    private var right: Int = 0
    private var bottom: Int = 0
    var name = ""

    fun setRect(left: Int, top: Int, right: Int, bottom: Int) {
        this.left = left
        this.top = top
        this.right = right
        this.bottom = bottom
    }

    fun contains(x: Int, y: Int): Boolean {
        return (left < right && top < bottom  // check for empty first
                && x >= left && x < right && y >= top && y < bottom)
    }


    open fun dispatchTouchEvent(event: MotionEvent): Boolean {
        android.util.Log.e("TouchTest", " View.dispatchTouchEvent name：$name")
        var result = false
        val currentTouchListener = mTouchListener
        if (currentTouchListener != null) {
            result = currentTouchListener.onTouch(this, event)
        }
        if (!result && onTouchEvent(event)) {
            result = true
        }
        return result
    }

    open fun onTouchEvent(event: MotionEvent): Boolean {
        android.util.Log.e("TouchTest", " View.onTouchEvent name：$name")
        // 判断是否可点击？
        // 在此只演示可以点击
        var clickable = true
        if (clickable) {
            val clickListener = mClickListener
            if (clickListener != null) {
                clickListener.onClick(this)
                android.util.Log.e("TouchTest", " onTouchEvent name：$name  return true")
                return true
            }
        }
        return false
    }

    var mTouchListener: TouchListener? = null
    var mClickListener: ClickListener? = null

}

interface TouchListener {
    fun onTouch(view: View, event: MotionEvent): Boolean
}

interface ClickListener {
    fun onClick(view: View)
}