package com.example.wongki.commonlib.image.glide

interface IGlideRequest {
    fun enqueue(glideBuild: IGlideBuild)
    fun clear(activityHashCode: Int)
}