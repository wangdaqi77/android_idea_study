package com.example.wongki.commonlib.image.glide

import android.os.Handler
import android.os.Looper

object GlideDefDispatcher : IGlideDispatcher {
    private val mHandler: Handler by lazy { Handler(Looper.getMainLooper()) }

    override fun dispatch(type: IGlideDispatcher.Type, glideBuild: IGlideBuild) {
        when (type) {
            IGlideDispatcher.Type.LOADING -> {
                runOnMain {
                    val id = glideBuild.mLoadingResId
                    if (id != null) {
                        glideBuild.mImageView?.get()?.setImageResource(id)
                    }
                }
            }
            IGlideDispatcher.Type.SUCCESS -> {
                runOnMain {
                    // 从内存中获取Bitmap
                    val key = glideBuild.mKey
                    if (key != null) {
                        val fromMemory = GlideCache.getFromMemory(key)
                        if (fromMemory != null) {
                            val preShowImage = glideBuild.mListener?.preShowImage(fromMemory)
                            glideBuild.mImageView?.get()?.setImageBitmap(preShowImage ?: fromMemory)
                        }
                    }
                }
            }
            IGlideDispatcher.Type.ERROR -> {
                runOnMain {
                    val id = glideBuild.mErrorResId
                    glideBuild.mListener?.error()
                    if (id != null) {
                        glideBuild.mImageView?.get()?.setImageResource(id)
                    } else {
                        glideBuild.mImageView?.get()?.setImageDrawable(null)
                    }
                }
            }
        }
    }


    private fun runOnMain(run: () -> Unit) {
        mHandler.post(run)
    }

}