package com.example.wongki.commonlib.plugin

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity

interface IPluginActivity {
    companion object {
        const val FROM_INTERNAL = 1
        const val FROM_EXTERNAL = 2
    }
    fun attach(context: FragmentActivity)

    fun onCreate(savedInstanceState:Bundle?)
    fun onNewIntent(intent: Intent)
    fun onRestart()
    fun onStart()
    fun onResume()
    fun onPause()
    fun onStop()
    fun onDestroy()
}