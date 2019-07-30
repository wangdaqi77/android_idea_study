package com.example.wongki.viewtouch

import java.util.*



class ViewGroup : View() {

    private var childList: ArrayList<View> = ArrayList()
    private var childViews = childList.toArray()


    fun addView(view: View) {
        childList.add(view)
        childViews = childList.toArray()
    }

    /**
     * 检查该View能否接收触摸事件
     */
    fun onFilterTouchEventForSecurity(event: MotionEvent): Boolean {
        // 先检查View有没有设置被遮挡时不处理触摸事件的flag
//        return if (mViewFlags and FILTER_TOUCHES_WHEN_OBSCURED != 0
//                // 再检查受到该事件的窗口是否被其它窗口遮挡
//                && event.getFlags() and MotionEvent.FLAG_WINDOW_IS_OBSCURED != 0) {
//            // Window is obscured, drop this touch.
//            false
//        } else true
        return true
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {

        android.util.Log.e("TouchTest", " ViewGroup.dispatchTouchEvent name：$name")
        var handle = false
        if(onFilterTouchEventForSecurity(event)){
            var intercept = false
            if (event.action == MotionEvent.Action.DOWN) {
                // 重置拦截状态 清除TouchTargets
                resetTouchState()
            }

            intercept = onInterceptTouchEvent(event)
            var newTouchTarget: TouchTarget? = null
            if (!intercept) {
                for (i in childViews.size - 1..0) {
                    val child = childViews[i] as View

                    // 未触摸到了该子View
                    if (!child.contains(event.x.toInt(), event.y.toInt())) {
                        continue
                    }


                    newTouchTarget = getTouchTarget(child)
                    if (newTouchTarget != null) {
                        // 触摸id处理
                        break
                    }

                    // 找到目标
                    if (dispatchTransformedTouchEvent(child, event)) {
                        newTouchTarget = addTouchTarget(child)
                        break
                    }
                }
            }



            if (mFirstTouchTarget == null) {
                handle = dispatchTransformedTouchEvent(null, event)
            } else {

            }

        }



        return handle
    }

    private fun resetTouchState() {
        clearTouchTargets()
        // TODO 清除拦截状态
        // ...
    }

    private fun clearTouchTargets() {
        var tempTouchTarget: TouchTarget? = mFirstTouchTarget ?: return

        do {
            val next = tempTouchTarget?.next
            tempTouchTarget?.recyle()
            tempTouchTarget = next
        } while (tempTouchTarget != null)
        mFirstTouchTarget = null
    }

    private fun getTouchTarget(child: View): TouchTarget? {
        var tempTouchTarget = mFirstTouchTarget
        while (tempTouchTarget != null && tempTouchTarget.child != child) {
            tempTouchTarget = tempTouchTarget.next
        }
        return tempTouchTarget
    }

    private var mFirstTouchTarget: TouchTarget? = null
    private fun addTouchTarget(child: View): TouchTarget {
        val obtain = TouchTarget.obtain(child)
        obtain.next = mFirstTouchTarget
        mFirstTouchTarget = obtain
        return obtain
    }

    open fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        android.util.Log.e("TouchTest", " ViewGroup.onInterceptTouchEvent name：$name")
        return false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        android.util.Log.e("TouchTest", " ViewGroup.onTouchEvent name：$name")
        return super.onTouchEvent(event)
    }

    private fun dispatchTransformedTouchEvent(view: View?, event: MotionEvent): Boolean {
        return if (event.action != MotionEvent.Action.CANCEL) {
            if (view != null) {
                view.dispatchTouchEvent(event)
            } else {
                super.dispatchTouchEvent(event)
            }
        } else {
            false
        }

    }

    /**
     * 缓存池
     */
    class TouchTarget {

        var child: View? = null
        var next: TouchTarget? = null

        companion object {
            private val MAX_RECYCLED = 32
            private val sRecycleLock = arrayOfNulls<Any>(0)
            private var sRecycleBin: TouchTarget? = null
            private var sRecycledCount: Int = 0
            fun obtain(child: View): TouchTarget {
                val target: TouchTarget
                synchronized(sRecycleLock) {
                    if (sRecycleBin == null) {
                        target = TouchTarget()
                    } else {
                        target = sRecycleBin!!
                        sRecycleBin = target.next
                        sRecycledCount.dec()
                        target.next = null
                    }
                }

                target.child = child
                return target
            }

        }

        fun recyle() {
            synchronized(sRecycleLock) {
                if (sRecycledCount < MAX_RECYCLED) {
                    next = sRecycleBin
                    sRecycleBin = this
                    sRecycledCount.inc()
                } else {
                    next = null
                }
            }
            child = null
        }

    }
}