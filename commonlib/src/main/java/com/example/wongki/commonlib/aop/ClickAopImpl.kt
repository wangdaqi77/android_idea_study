package com.example.wongki.commonlib.aop

import android.util.Log
import android.view.View

class ClickAopImpl : IClick {
    override fun click(view: View): Boolean {
        Log.i("TEST", "切入成功，处理其他逻辑")
        return false
    }
}