package com.example.wongki.commonlib.image.glide

import android.graphics.Bitmap
import android.util.LruCache

class GlideMemoryCache : LruCache<String, Bitmap?>((Runtime.getRuntime().maxMemory() / 8).toInt()) {
    override fun sizeOf(key: String?, value: Bitmap?): Int {
        return value?.byteCount ?: super.sizeOf(key, value)
    }
}