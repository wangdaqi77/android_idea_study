package com.example.wongki.modularization.keep

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder

class RemoteService : ForegroundService() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.getBooleanExtra("unbind", false) == true) {
            unbindService(mConnection)
            stopSelf()
            Runtime.getRuntime().exit(0)
        } else {
            bind()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    fun bind() {
        val localService = Intent(this, LocalService::class.java)
        bindService(localService, mConnection, Service.BIND_IMPORTANT)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return super.onBind(intent)
    }



    val mConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            KeepHelper.log("${this@RemoteService}  onServiceDisconnected -> name:$name")
            KeepHelper.startLocalService(this@RemoteService)
            bind()
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            KeepHelper.log("${this@RemoteService}  onServiceConnected -> name:$name")
        }
    }
}