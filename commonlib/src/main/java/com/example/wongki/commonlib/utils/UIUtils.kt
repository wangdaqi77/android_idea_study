package com.example.wongki.commonlib.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.example.wongki.commonlib.R
import com.example.wongki.commonlib.view.StatusBarView

object UIUtils {

    /**
     * 沉浸式状态栏
     */
    fun compatImmersiveStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
        val window = activity.window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
            var systemUiVisibility = window.decorView.systemUiVisibility
            //布局内容全面展示
            systemUiVisibility = systemUiVisibility.or(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            // 防止内容区域大小发生变化
            systemUiVisibility = systemUiVisibility.or(View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.decorView.systemUiVisibility = systemUiVisibility
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }


    /**
     * 隐藏虚拟按键
     */
    fun compatHideNavigation(activity: Activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
        val window = activity.window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.navigationBarColor = Color.TRANSPARENT
            var systemUiVisibility = window.decorView.systemUiVisibility
            // 隐藏虚拟导航栏
            systemUiVisibility = systemUiVisibility.or(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
            // 防止内容区域大小发生变化
            systemUiVisibility = systemUiVisibility.or(View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            // 添加flag会一直隐藏虚拟导航栏，否则如果有事件产生，虚拟导航栏便会出现
            systemUiVisibility = systemUiVisibility.or(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            //布局内容全面展示
            systemUiVisibility = systemUiVisibility.or(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            window.decorView.systemUiVisibility = systemUiVisibility


        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
    }

    fun fixFullScreenForImmersive(activity: Activity, toolBar: View) {
        val window = activity.window
        val decorView = window.decorView as ViewGroup
        var statusBarView = decorView.findViewById<StatusBarView?>(R.id.status_bar_view)
        if (statusBarView == null) {
            statusBarView = StatusBarView(activity)
            val statusBarHeight = UIScaleHelper.getStatusBarHeight(activity)
            val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, statusBarHeight)
            decorView.addView(statusBarView, layoutParams)
            decorView.findViewById<StatusBarView?>(R.id.status_bar_view)?.id = R.id.status_bar_view

            val toolBarLayoutParams = toolBar.layoutParams
            if (toolBarLayoutParams is ViewGroup.MarginLayoutParams) {
                toolBarLayoutParams.topMargin = statusBarHeight
            }
        }

    }
}