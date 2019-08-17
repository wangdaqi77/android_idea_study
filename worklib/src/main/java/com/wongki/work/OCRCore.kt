package com.wongki.work

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Bitmap
import android.os.Build
import android.os.IBinder
import com.wongki.work.single.WorkBinder

/**
 * @author  wangqi
 * date:    2019-08-16
 * email:   wangqi7676@163.com
 * desc:    .
 */
class OCRCore : IOCR {

    companion object {
        const val TAG = "OCRCore"
        const val EXT_BITMAP_ORIGIN = "bitmap_origin"
    }

    init {

    }

    override fun process(context: Context, bitmapOrigin: Bitmap, ocrWorkListener: OCRWorkListener) {
        val intent = Intent()
        intent.putExtra(EXT_BITMAP_ORIGIN, bitmapOrigin)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {

        } else {

        }

        intent.setClass(context, OCRWorkService::class.java)
        context.startService(intent)
        setListenerOnWorkService(context,intent,ocrWorkListener)
    }


    /**
     * 为工作服务设置监听器
     */
    private fun setListenerOnWorkService(context: Context, intent: Intent, ocrWorkListener: OCRWorkListener) {
        val serviceConnection = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {

            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder) {
                @Suppress("UNCHECKED_CAST")
                service as WorkBinder<OCRWorkListener>
                service.setWorkListener(ocrWorkListener)
                context.unbindService(this)
            }

        }
        context.bindService(
                intent,
                serviceConnection,
                Context.BIND_AUTO_CREATE
        )

    }

    override fun clear() {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}