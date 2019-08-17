package com.wongki.work.single

import android.content.Intent
import android.support.annotation.IntRange
import android.support.annotation.WorkerThread

/**
 * @author  wangqi
 * date:    2019-08-17
 * email:   wangqi7676@163.com
 * desc:    .
 */
interface IWorkService<L : WorkListener> {
    fun setWorkListener(listener: L)

    fun getWorkListener(): L?

    fun setState(@IntRange(from = WorkState.NONE.toLong(), to = WorkState.COMPLETE.toLong()) state: Int)

    fun getState(): Int

    @WorkerThread
    fun working(intent: Intent?)
}