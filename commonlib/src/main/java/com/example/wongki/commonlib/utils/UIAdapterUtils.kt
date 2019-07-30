package com.example.wongki.commonlib.utils

import android.app.Activity
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

object UIAdapterUtils {
    fun adapterForParentLinerLayout(view: View) {
        val layoutParams = view.layoutParams
        if (layoutParams is LinearLayout.LayoutParams) {
            if (layoutParams.width != LinearLayout.LayoutParams.WRAP_CONTENT
                    && layoutParams.width != LinearLayout.LayoutParams.MATCH_PARENT) {

                val width = layoutParams.width
                layoutParams.width = (UIScaleHelper.getScaleWidth(width))
            }

            if (layoutParams.height != LinearLayout.LayoutParams.WRAP_CONTENT
                    && layoutParams.height != LinearLayout.LayoutParams.MATCH_PARENT) {

                val height = layoutParams.height
                layoutParams.height = (UIScaleHelper.getScaleHeight(height))
            }

            val leftMargin = layoutParams.leftMargin
            val topMargin = layoutParams.topMargin
            val rightMargin = layoutParams.rightMargin
            val bottomMargin = layoutParams.bottomMargin
            layoutParams.leftMargin = UIScaleHelper.getScaleWidth(leftMargin)
            layoutParams.rightMargin = UIScaleHelper.getScaleWidth(rightMargin)
            layoutParams.topMargin = UIScaleHelper.getScaleHeight(topMargin)
            layoutParams.bottomMargin = UIScaleHelper.getScaleHeight(bottomMargin)
        }

        setViewPadding(view,view.paddingTop,view.paddingBottom,view.paddingLeft,view.paddingRight)
    }

    fun adapterForParentRelativeLayout(view: View) {
        val layoutParams = view.layoutParams
        if (layoutParams is RelativeLayout.LayoutParams) {
            if (layoutParams.width != LinearLayout.LayoutParams.WRAP_CONTENT
                    && layoutParams.width != LinearLayout.LayoutParams.MATCH_PARENT) {

                val width = layoutParams.width
                layoutParams.width = (UIScaleHelper.getScaleWidth(width))
            }

            if (layoutParams.height != LinearLayout.LayoutParams.WRAP_CONTENT
                    && layoutParams.height != LinearLayout.LayoutParams.MATCH_PARENT) {

                val height = layoutParams.height
                layoutParams.height = (UIScaleHelper.getScaleHeight(height))
            }

            val leftMargin = layoutParams.leftMargin
            val topMargin = layoutParams.topMargin
            val rightMargin = layoutParams.rightMargin
            val bottomMargin = layoutParams.bottomMargin
            layoutParams.leftMargin = UIScaleHelper.getScaleWidth(leftMargin)
            layoutParams.rightMargin = UIScaleHelper.getScaleWidth(rightMargin)
            layoutParams.topMargin = UIScaleHelper.getScaleHeight(topMargin)
            layoutParams.bottomMargin = UIScaleHelper.getScaleHeight(bottomMargin)
        }
        setViewPadding(view,view.paddingTop,view.paddingBottom,view.paddingLeft,view.paddingRight)

    }

    fun adapterForParentFrameLayout(view: View) {
        val layoutParams = view.layoutParams
        if (layoutParams is FrameLayout.LayoutParams) {
            if (layoutParams.width != LinearLayout.LayoutParams.WRAP_CONTENT
                    && layoutParams.width != LinearLayout.LayoutParams.MATCH_PARENT) {

                val width = layoutParams.width
                layoutParams.width = (UIScaleHelper.getScaleWidth(width))
            }

            if (layoutParams.height != LinearLayout.LayoutParams.WRAP_CONTENT
                    && layoutParams.height != LinearLayout.LayoutParams.MATCH_PARENT) {

                val height = layoutParams.height
                layoutParams.height = (UIScaleHelper.getScaleHeight(height))
            }

            val leftMargin = layoutParams.leftMargin
            val topMargin = layoutParams.topMargin
            val rightMargin = layoutParams.rightMargin
            val bottomMargin = layoutParams.bottomMargin
            layoutParams.leftMargin = UIScaleHelper.getScaleWidth(leftMargin)
            layoutParams.rightMargin = UIScaleHelper.getScaleWidth(rightMargin)
            layoutParams.topMargin = UIScaleHelper.getScaleHeight(topMargin)
            layoutParams.bottomMargin = UIScaleHelper.getScaleHeight(bottomMargin)
        }
        setViewPadding(view,view.paddingTop,view.paddingBottom,view.paddingLeft,view.paddingRight)

    }

    fun adapter(view: View, width: Int, height: Int, leftMargin: Int, topMargin: Int, rightMargin: Int, bottomMargin: Int) {
        val layoutParams = view.layoutParams
        if (layoutParams is ViewGroup.MarginLayoutParams) {

            layoutParams.width =
                    if (width != LinearLayout.LayoutParams.WRAP_CONTENT
                            && width != LinearLayout.LayoutParams.MATCH_PARENT)
                        (UIScaleHelper.getScaleWidth(width))
                    else
                        width

            layoutParams.height =
                    if (height != LinearLayout.LayoutParams.WRAP_CONTENT
                            && height != LinearLayout.LayoutParams.MATCH_PARENT)
                        (UIScaleHelper.getScaleWidth(height))
                    else
                        height


            layoutParams.leftMargin = UIScaleHelper.getScaleWidth(leftMargin)
            layoutParams.rightMargin = UIScaleHelper.getScaleWidth(rightMargin)
            layoutParams.topMargin = UIScaleHelper.getScaleHeight(topMargin)
            layoutParams.bottomMargin = UIScaleHelper.getScaleHeight(bottomMargin)
        }
    }


    /**
     * 设置view的内边距
     *
     * @param view
     * @param topPadding
     * @param bottomPadding
     * @param leftPadding
     * @param rightPadding
     */
    fun setViewPadding(view: View, topPadding: Int, bottomPadding: Int, leftPadding: Int, rightPadding: Int) {
        view.setPadding(UIScaleHelper.getScaleWidth(leftPadding),
                UIScaleHelper.getScaleHeight(topPadding),
                UIScaleHelper.getScaleWidth(rightPadding),
                UIScaleHelper.getScaleHeight(bottomPadding))
    }

    /**
     * 设置字号
     *
     * @param view
     * @param size
     */
    fun setTextSize(view: TextView, size: Int) {
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, UIScaleHelper.getScaleHeight(size).toFloat())
    }

    fun adapterActivity(activity: Activity) {
        val contentView = activity.window.decorView.findViewById<FrameLayout>(android.R.id.content)
        adapterViewGroup(contentView)
    }

    fun adapterViewGroup(viewGroup: ViewGroup) {
        val childCount = viewGroup.childCount
        for (index in 0 until childCount) {
            val view = viewGroup.getChildAt(index)
            adapterView(view)
            if (view is ViewGroup) {
                adapterViewGroup(view)
            }
        }
    }

    fun adapterView(view: View) {
        val layoutParams = view.layoutParams
        when (layoutParams) {
            is RelativeLayout.LayoutParams -> {
                adapterForParentRelativeLayout(view)
            }
            is LinearLayout.LayoutParams -> {
                adapterForParentLinerLayout(view)
            }
            is FrameLayout.LayoutParams -> {
                adapterForParentFrameLayout(view)
            }
        }
    }

}