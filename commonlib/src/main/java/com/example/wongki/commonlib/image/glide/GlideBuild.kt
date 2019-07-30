package com.example.wongki.commonlib.image.glide

import android.app.Activity
import android.widget.ImageView
import com.example.wongki.commonlib.utils.MD5Utils
import java.lang.ref.WeakReference

class GlideBuild : IGlideBuild {
    override var mActivity: WeakReference<Activity?>? = null
    override var mImageView: WeakReference<ImageView?>? = null
    override var mListener: GlideLoadFinishListener? = null
    override var mImageUrl: String? = null
    override var mLoadingResId: Int? = null
    override var mErrorResId: Int? = null
    override var mKey: String? = null

    override fun from(activity: Activity): IGlideBuild {
        this.mActivity = WeakReference(activity)
        return this
    }

    override fun load(imageUrl: String): IGlideBuild {
        this.mImageUrl = imageUrl
        if (!mImageUrl.isNullOrEmpty()) {
            this.mKey = MD5Utils.md5(mImageUrl)
        }
        return this
    }

    override fun loading(loadingResId: Int): IGlideBuild {
        this.mLoadingResId = loadingResId
        return this
    }

    override fun error(errorResId: Int): IGlideBuild {
        this.mErrorResId = errorResId
        return this
    }

    override fun listener(listener: GlideLoadFinishListener): IGlideBuild {
        mListener = listener
        return this
    }

    override fun into(imageView: ImageView) {
        this.mImageView = WeakReference(imageView)
        val request = Glide.glideRequest()
        request.enqueue(this)
    }


}
