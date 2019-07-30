package com.example.wongki.commonlib.image.glide

import android.support.annotation.WorkerThread

interface IGlideDispatcher {
    enum class Type {
        LOADING,
        ERROR,
        SUCCESS
    }

    @WorkerThread
    fun dispatch(type: Type,glideBuild: IGlideBuild)
}