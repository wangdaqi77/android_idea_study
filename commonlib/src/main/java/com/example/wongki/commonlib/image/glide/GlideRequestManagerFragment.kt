package com.example.wongki.commonlib.image.glide

import android.app.Fragment
import android.content.Context

class GlideRequestManagerFragment:Fragment() {

    override fun onAttach(context: Context?) {
        super.onAttach(context)

    }

    override fun onDetach() {
        // 清空对应的缓存
        Glide.glideRequest().clear(activity.hashCode())
        super.onDetach()
    }
}