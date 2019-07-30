package com.example.wongki.modularization.keep

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat

open class ForegroundService : Service() {
    private val localBinder by lazy { LocalBinder() }


    override fun onCreate() {
        super.onCreate()
        KeepHelper.log("$this onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        KeepHelper.log("$this onStartCommand")
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            // 4.3
            KeepHelper.log("$this 4.3开启前台服务")
            startForeground(SERVICE_ID, Notification())
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            // 7.0
            KeepHelper.log("$this 7.0开启前台服务")
            startForeground(SERVICE_ID, Notification())
            startService(Intent(this, InnerService::class.java))

        } else {
            //新版
            KeepHelper.log("$this 新版开启前台服务")
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel("channel", "xxx", NotificationManager.IMPORTANCE_MIN)
            notificationManager.createNotificationChannel(channel)
            startForeground(SERVICE_ID, NotificationCompat.Builder(this, "channel").build())
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        KeepHelper.log("$this onBind")
        return localBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        KeepHelper.log("$this onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        KeepHelper.log("$this onDestroy")
        super.onDestroy()
    }

    inner class LocalBinder : android.os.Binder()

    class InnerService : Service() {
        override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
            startForeground(SERVICE_ID, Notification())
            stopForeground(true)
            stopSelf()
            return super.onStartCommand(intent, flags, startId)
        }

        override fun onBind(intent: Intent?): IBinder? {
            return null
        }

    }

    companion object {
        const val SERVICE_ID = 1
    }

}