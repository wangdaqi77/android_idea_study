package com.example.wongki.modularization.keep

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import com.example.wongki.modularization.R

class KeepActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keep)
        val window = window
        val params = WindowManager.LayoutParams().apply {
            gravity = Gravity.START or Gravity.TOP
            alpha = 0f
            width = 1
            height = 1
            x = 0
            y = 0
        }
        window.attributes = params
        KeepHelper.setKeepActivity(this)
        KeepHelper.log("1px Activity开启")
    }

    override fun onDestroy() {
        KeepHelper.log("1px Activity关闭")
        super.onDestroy()
    }
}
