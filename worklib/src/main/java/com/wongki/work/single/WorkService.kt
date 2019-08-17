package com.wongki.work.single

import android.app.IntentService
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.support.annotation.WorkerThread
import java.lang.RuntimeException

/**
 * @author  wangqi
 * date:    2019-08-17
 * email:   wangqi7676@163.com
 * desc:    只支持一个耗时任务
 */
abstract class WorkService<L : WorkListener> : IntentService("WorkService"), IWorkService<L> {

    private var listener: L? = null
    private val lock = Object()

    @Volatile
    private var state = WorkState.NONE

    companion object {
        private val sMainHandler by lazy { Handler(Looper.getMainLooper()) }
        fun runOnMainThread(runnable: Runnable) {
            if (Looper.getMainLooper() != Looper.myLooper()) {
                sMainHandler.post(runnable)
            } else {
                runnable.run()
            }
        }
    }


    /**
     * 处于工作状态
     */
    private fun inWork() = state == WorkState.START || state == WorkState.WORKING

    override fun setState(state: Int) {
        this.state = state
    }

    override fun getState(): Int = state


    override fun setWorkListener(listener: L) {
        synchronized(lock) {
            this.listener = listener
            lock.notifyAll()
        }
    }

    /**
     * [state] == [WorkState.START] 、[WorkState.WORKING] 才有可能有值
     */
    @WorkerThread
    override fun getWorkListener(): L? {
        if (!inWork()) return null

        synchronized(lock) {
            if (listener == null && inWork()) {
                // 等待bindService成功，通过WorkBinder设置listener
                lock.wait()
            }

        }
        return listener
    }


    final override fun onBind(intent: Intent?): IBinder? {
        return WorkBinder(this)
    }

    final override fun onHandleIntent(intent: Intent?) {
        if (getState() != WorkState.NONE) throw RuntimeException("已经有任务正在执行中...")
        setState(WorkState.START)
        val workListener = getWorkListener()
        workListener?.let {
            runOnMainThread(Runnable { workListener.onStart() })
        }

        setState(WorkState.WORKING)
        working(intent)
        setState(WorkState.COMPLETE)
    }

    final override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        if (inWork()) {
            listener?.onAbort()
        }

        setState(WorkState.NONE)
        listener = null
        super.onDestroy()
    }
}