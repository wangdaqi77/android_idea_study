package com.example.wongki.commonlib.image.glide

import android.graphics.Bitmap
import android.util.Log

object GlideCache {
    val mMemoryCache by lazy { GlideMemoryCache() }
    fun put(key: String, bitmap: Bitmap) {
        mMemoryCache.put(key, bitmap)
    }

    fun getFromMemory(key: String): Bitmap? = mMemoryCache.get(key)

    fun getFromDisk(key: String): Bitmap? = null

    fun removeFromMemory(key: String) {
        val remove = mMemoryCache.remove(key)
        if (remove!=null) {
            Log.e("GLIDE", "removeFromMemory() -> key:$key")
        }
    }
}