package com.example.wongki.commonlib.image.glide

import android.app.Activity
import android.widget.ImageView
import java.lang.ref.WeakReference

interface IGlideBuild {
    var mActivity: WeakReference<Activity?>?
    var mImageView: WeakReference<ImageView?>?
    var mListener: GlideLoadFinishListener?
    var mImageUrl: String?
    var mLoadingResId: Int?
    var mErrorResId: Int?
    var mKey: String?

    fun from(activity: Activity): IGlideBuild

    fun load(imageUrl: String): IGlideBuild

    fun loading(loadingResId: Int): IGlideBuild

    fun error(errorResId: Int): IGlideBuild

    fun listener(listener: GlideLoadFinishListener): IGlideBuild

    fun into(imageView: ImageView)
}
