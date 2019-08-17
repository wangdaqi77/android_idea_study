package com.wongki.work

import android.content.Intent
import com.wongki.work.single.WorkService

/**
 * @author  wangqi
 * date:    2019-08-17
 * email:   wangqi7676@163.com
 * desc:    .
 */
class OCRWorkService : WorkService<OCRWorkListener>() {

    override fun working(intent: Intent?) {
        val workListener = getWorkListener()
        workListener?.let {
            runOnMainThread(Runnable { workListener.onSuccess() })
        }


    }
}