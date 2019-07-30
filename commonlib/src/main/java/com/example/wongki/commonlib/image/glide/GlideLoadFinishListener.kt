package com.example.wongki.commonlib.image.glide

import android.graphics.Bitmap
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread


interface GlideLoadFinishListener {
    @MainThread
    fun error()

    @WorkerThread
    fun preShowImage(bitmap: Bitmap): Bitmap?
}