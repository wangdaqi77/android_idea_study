package com.wongki.work

import android.content.Context
import android.graphics.Bitmap

/**
 * @author  wangqi
 * date:    2019-08-16
 * email:   wangqi7676@163.com
 * desc:    OCR接口
 */
interface IOCR {
    /**
     *
     */
    fun process(context: Context, bitmapOrigin: Bitmap,ocrWorkListener: OCRWorkListener)
    fun clear()
}