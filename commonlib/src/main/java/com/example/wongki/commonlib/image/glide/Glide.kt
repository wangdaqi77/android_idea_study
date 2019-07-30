package com.example.wongki.commonlib.image.glide

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

object Glide {
    private var customGlideRequest: IGlideRequest? = null
    private var customGlideDispatcher: IGlideDispatcher? = null
    private val fragmentCache: HashMap<Int, Boolean> by lazy { HashMap<Int, Boolean>() }
    fun with(activity: Activity): IGlideBuild {
        val fragmentManager = activity.fragmentManager
        val tag = Glide::class.java.name
        val fragment = fragmentManager.findFragmentByTag(tag)
        val hashCode = activity.hashCode()
        if (fragmentCache[hashCode] != true) {
            fragmentCache[hashCode] = true
            val fragment = GlideRequestManagerFragment()
            fragmentManager
                    .beginTransaction()
                    .add(fragment, tag)
                    .commitAllowingStateLoss()

        }
        return GlideBuild().from(activity)
    }

    fun glideRequest(): IGlideRequest = customGlideRequest ?: GlideDefRequest
    fun glideDispatcher(): IGlideDispatcher = customGlideDispatcher ?: GlideDefDispatcher

    fun customGlideRequest(customGlideRequest: IGlideRequest) {
        this.customGlideRequest = customGlideRequest
    }

    fun customGlideDispacher(customGlideDispatcher: IGlideDispatcher) {
        this.customGlideDispatcher = customGlideDispatcher
    }
}