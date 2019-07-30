package com.example.wongki.modularization.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ParallaxFragment:Fragment() {
    private  var mLayoutView:View? =null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layoutId = arguments?.get("layout_id")?:-1
        if (layoutId is Int && layoutId!=-1) {
            val layoutInflater = ParallaxLayoutInflater(inflater, context)
            mLayoutView = layoutInflater.inflate(layoutId,null)
            return mLayoutView
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun getLayoutView() = mLayoutView
}