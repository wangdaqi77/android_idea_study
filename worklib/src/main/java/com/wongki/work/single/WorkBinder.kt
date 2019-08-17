package com.wongki.work.single

import android.os.Binder
import java.lang.ref.WeakReference

/**
 * @author  wangqi
 * date:    2019-08-17
 * email:   wangqi7676@163.com
 * desc:    .
 */
class WorkBinder<L : WorkListener>(ocrWork: IWorkService<L>) : Binder() {
    private val ocrWork: WeakReference<IWorkService<L>> = WeakReference(ocrWork)

    fun setWorkListener(listener: L) {
        ocrWork.get()?.setWorkListener(listener)
    }
}