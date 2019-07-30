package com.example.wongki.modularization.keep

import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.support.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class KeepJobService : JobService() {
    private val mHandler by lazy { Handler(Looper.getMainLooper()) }
    override fun onCreate() {
        super.onCreate()
        KeepHelper.log("$this onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        KeepHelper.log("$this onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onStartJob(params: JobParameters?): Boolean {
        KeepHelper.log("$this onStartJob  -> jobId: ${params?.jobId}")
        Thread{
            mHandler.post {
                if (KeepHelper.isServiceWork(this, LocalService::class.java.name)) {
                    KeepHelper.log("$this ${LocalService::class.java.name} -> running")
                }else{
                    KeepHelper.log("$this ${LocalService::class.java.name} -> stopped")
                    KeepHelper.startLocalService(this)
                }
                if (KeepHelper.isServiceWork(this, RemoteService::class.java.name)) {
                    KeepHelper.log("$this ${RemoteService::class.java.name} -> running")
                }else{
                    KeepHelper.log("$this ${RemoteService::class.java.name} -> stopped")
                    KeepHelper.startRemoteService(this)
                }
                jobFinished(params,false)
            }
        }.start()
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        KeepHelper.log("$this onStopJob  -> jobId: ${params?.jobId}")
//        KeepHelper.startScheduler(this)
        return false

    }


    override fun onDestroy() {
        KeepHelper.log("$this onDestroy")
        super.onDestroy()
    }



}