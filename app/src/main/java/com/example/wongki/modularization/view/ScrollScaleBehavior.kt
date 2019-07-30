package com.example.wongki.modularization.view

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.util.AttributeSet
import android.view.View

class ScrollScaleBehavior : CoordinatorLayout.Behavior<View> {
    var mIsRunning = false
    var mFastOutSlowInInterpolator = FastOutSlowInInterpolator()
    var mLinearOutSlowInInterpolator = LinearOutSlowInInterpolator()

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        // 仅支持垂直滑动
        return axes == CoordinatorLayout.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        if (dyConsumed > 0 && !mIsRunning && child.visibility != View.GONE) {
            // 缩放隐藏
            child.scaleHideUi()
        } else if (dyConsumed < 0 && !mIsRunning && child.visibility == View.INVISIBLE) {
            // 缩放展示
            child.scaleShowUi()
        } else {

        }
    }

    private fun View.scaleHideUi() {
        ViewCompat.animate(this)
                .setDuration(1000L)
                .scaleX(0f)
                .scaleY(0f)
                .setInterpolator(mFastOutSlowInInterpolator)
                .setListener(object : ViewPropertyAnimatorListenerAdapter() {
                    override fun onAnimationStart(view: View?) {
                        mIsRunning = true
                        super.onAnimationStart(view)
                    }

                    override fun onAnimationEnd(view: View?) {
                        view?.visibility = View.INVISIBLE
                        mIsRunning = false
                        super.onAnimationEnd(view)
                    }

                    override fun onAnimationCancel(view: View?) {
                        mIsRunning = false
                        super.onAnimationCancel(view)
                    }
                })
                .start()
    }

    private fun View.scaleShowUi() {

        ViewCompat.animate(this)
                .setDuration(1000L)
                .scaleX(1F)
                .scaleY(1F)
                .setInterpolator(mLinearOutSlowInInterpolator)
                .setListener(object : ViewPropertyAnimatorListenerAdapter() {
                    override fun onAnimationStart(view: View?) {
                        view?.visibility = View.VISIBLE
                        mIsRunning = true
                        super.onAnimationStart(view)
                    }

                    override fun onAnimationEnd(view: View?) {
                        mIsRunning = false
                        super.onAnimationEnd(view)
                    }

                    override fun onAnimationCancel(view: View?) {
                        mIsRunning = false
                        super.onAnimationCancel(view)
                    }
                })
                .start()
    }
}


