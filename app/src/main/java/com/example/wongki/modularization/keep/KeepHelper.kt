package com.example.wongki.modularization.keep

import android.app.Activity
import android.app.ActivityManager
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import java.lang.ref.WeakReference

object KeepHelper {
    var keepActivity: WeakReference<Activity?>? = null
    fun onScreenOn(context: Context) {
        keepActivity?.get()?.finish()
    }

    fun onScreenOff(context: Context) {
        // 开启1px像素Activity
        val intent = Intent()
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.setClass(context, KeepActivity::class.java)
        context.startActivity(intent)
    }

    fun setKeepActivity(activity: KeepActivity) {
        this.keepActivity = WeakReference(activity)
    }

    fun log(message: String) {
        Log.e("KEEP", message)
    }

    val keepBroadcastReceiver by lazy { KeepBroadcastReceiver() }
    fun open(context: Context) {
        // 1.注册息屏亮屏的广播，当息屏时会打开1px的Activity
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        filter.addAction(Intent.ACTION_SCREEN_ON)
        context.registerReceiver(keepBroadcastReceiver, filter)

        // 2.开启前台服务
        // context.startService(Intent(context, ForegroundService::class.java))

        // 3.互拉服务
        // 开启本地服务
        startLocalService(context)
        // 开启远端服务
        startRemoteService(context)

        // 4.开启后台系统定时工作调度服务
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startScheduler(context)
        }
    }

    fun close(context: Context) {
        val intent = Intent(context, LocalService::class.java)
        intent.putExtra("unbind",true)
        context.startService(intent)


        val intent1 = Intent(context, RemoteService::class.java)
        intent1.putExtra("unbind",true)
        context.startService(intent1)

        context.unregisterReceiver(keepBroadcastReceiver)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mJobScheduler?.cancelAll()
        }
    }


    fun startLocalService(context: Context) {
        context.startService(Intent(context, LocalService::class.java))
    }

    fun startRemoteService(context: Context) {
        context.startService(Intent(context, RemoteService::class.java))
    }

    fun startKeepJobService(context: Context) {
        context.startService(Intent(context, KeepJobService::class.java))
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getJobInfo(context: Context): JobInfo {
        val builder = JobInfo.Builder(1, ComponentName(context, KeepJobService::class.java))
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            //小于7.0版本
            builder.setPeriodic(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS)
        } else {
            builder.setMinimumLatency(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS) //执行的最小延迟时间
            builder.setOverrideDeadline(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS)  //执行的最长延时时间
            builder.setMinimumLatency(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS)
            builder.setBackoffCriteria(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS, JobInfo.BACKOFF_POLICY_LINEAR)//线性重试方案
        }
//        builder.setPersisted(true) // 设置设备重启时，执行该任务
//        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
//        builder.setRequiresCharging(true) // 当插入充电器，执行该任务
        return builder.build()
    }

    private var mJobScheduler: JobScheduler? = null
    fun startScheduler(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mJobScheduler==null) {
                mJobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            }
            val jobInfo = getJobInfo(context)
            if (mJobScheduler?.schedule(jobInfo) == JobScheduler.RESULT_SUCCESS) {
                KeepHelper.log("$this startScheduler -> RESULT_SUCCESS")
            } else {
                KeepHelper.log("$this startScheduler -> RESULT_FAILURE")
            }
        }
    }


    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    fun isServiceWork(mContext: Context, serviceName: String): Boolean {
        var isWork = false
        val myAM = mContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        var myList: List<ActivityManager.RunningServiceInfo>? = null
        if (myAM != null) {
            myList = myAM.getRunningServices(100)
        }
        if (myList.isNullOrEmpty()) {
            return false
        }

        for (i in myList.indices) {
            val mName = myList[i].service.className
            if (mName == serviceName) {
                isWork = true
                break
            }
        }
        return isWork
    }

}
