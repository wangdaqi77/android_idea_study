package com.example.wongki.commonlib

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Process
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.example.wongki.commonlib.http.HttpHelper
import com.example.wongki.commonlib.http.OkHttpProcessor
import com.example.wongki.commonlib.hotfix.tinker.TinkerFixDexUtils

open class BaseApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        //TinkerFixDexUtils.loadFixedDex(this)
    }

    override fun onCreate() {
        super.onCreate()
        if (!isMainProcess()) return
        initialize()
    }

    private fun initialize() {
        if (!isMainProcess()) return
        initHttp()
    }

    private fun initHttp() {
        if (!isMainProcess()) return
        HttpHelper.init(OkHttpProcessor())
    }

    protected fun isMainProcess(): Boolean {
        val myPid = Process.myPid()
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
        if (activityManager != null) {
            val processes = activityManager.runningAppProcesses
            for (process in processes) {
                return process.pid == myPid && packageName == process.processName
            }


        }
        return false
    }
}