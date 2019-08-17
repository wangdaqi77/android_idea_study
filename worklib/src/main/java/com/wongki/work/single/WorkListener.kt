package com.wongki.work.single

import android.support.annotation.MainThread

/**
 * @author  wangqi
 * date:    2019-08-17
 * email:   wangqi7676@163.com
 * desc:    .
 */

interface WorkListener {
    /**
     * 开始工作
     */
    @MainThread
    fun onStart()

    /**
     * 由于系统异常导致工作为中止
     */
    @MainThread
    fun onAbort()
}