package com.example.wongki.modularization.keep

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class KeepBroadcastReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        when(intent?.action){
            Intent.ACTION_SCREEN_ON->{
                KeepHelper.log("屏幕打开")
                KeepHelper.onScreenOn(context)
            }
            Intent.ACTION_SCREEN_OFF->{
                KeepHelper.log("屏幕关闭")
                KeepHelper.onScreenOff(context)
            }
        }
    }
}