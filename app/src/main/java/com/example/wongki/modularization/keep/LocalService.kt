package com.example.wongki.modularization.keep

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder

class LocalService : ForegroundService() {
    private val binder by lazy { Binder() }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.getBooleanExtra("unbind",false) == true) {
            unbindService(mConnection)
            stopSelf()
        }else{
            bind()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    fun bind() {
        val remoteService = Intent(this, RemoteService::class.java)
        bindService(remoteService, mConnection, Service.BIND_IMPORTANT)
    }


    val mConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            KeepHelper.log("${this@LocalService}  onServiceDisconnected -> name:$name")
            KeepHelper.startRemoteService(this@LocalService)
            bind()
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            KeepHelper.log("${this@LocalService}  onServiceConnected -> name:$name")

        }
    }

}