package com.wongki.work

import android.support.annotation.MainThread
import com.wongki.work.single.WorkListener

/**
 * @author  wangqi
 * date:    2019-08-17
 * email:   wangqi7676@163.com
 * desc:    .
 */

interface OCRWorkListener: WorkListener {
    /**
     * 成功
     */
    @MainThread
    fun onSuccess()

    /**
     * 失败
     */
    @MainThread
    fun onError(code: Int, message: String)

}