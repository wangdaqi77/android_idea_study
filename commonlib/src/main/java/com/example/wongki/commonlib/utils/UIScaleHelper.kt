package com.example.wongki.commonlib.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

object UIScaleHelper {
    private const val STANDARD_WIDTH = 750
    private const val STANDARD_HEIGHT = 1334
    private var scaleHorizontal = 1.0f
    private var scaleVertical = 1.0f
    var ID_status_bar_height = -1//状态栏高度id

    fun init(context: Context) {
        val windowManger = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManger.defaultDisplay.getMetrics(displayMetrics)

        val widthPixels = displayMetrics.widthPixels
        val heightPixels = displayMetrics.heightPixels
        val statusBarHeight = getStatusBarHeight(context)

        //横屏
        if (widthPixels > heightPixels) {
            scaleHorizontal = heightPixels / STANDARD_WIDTH.toFloat()
            scaleVertical = (widthPixels - statusBarHeight) / STANDARD_HEIGHT.toFloat()
        } else {
            //竖屏
            scaleHorizontal = widthPixels / STANDARD_WIDTH.toFloat()
            scaleVertical = (heightPixels - statusBarHeight) / STANDARD_HEIGHT.toFloat()
        }
    }


    fun getScaleHorizontal() = scaleHorizontal
    fun getScaleVertical() = scaleVertical

    fun getScaleWidth(width: Int): Int = (width * scaleHorizontal + 0.5f).toInt()

    fun getScaleHeight(height: Int): Int = (height * scaleVertical + 0.5f).toInt()

    fun onConfigChange(context: Context) {
        init(context)
    }

    fun getStatusBarHeight(context: Context): Int {
        if (ID_status_bar_height == -1) {
            val id = getAndroidSystemProperty("com.android.internal.R\$dimen", "status_bar_height")
            if (id is Int) {
                ID_status_bar_height = id
            }

        }

        return if (ID_status_bar_height != -1) {
            context.resources.getDimensionPixelSize(ID_status_bar_height)
        } else {
            0
        }
    }


    fun getAndroidSystemProperty(clazzName: String, propertyName: String): Any? {
        try {
            val clazz = Class.forName(clazzName)
            val field = clazz.getField(propertyName)
            val instance = clazz.newInstance()
            return field.get(instance)
        } catch (e: Exception) {
        }

        return null

    }
}