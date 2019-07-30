package com.example.wongki.commonlib.modularization

import android.app.Activity
import android.content.Context

interface ILoginProviderService{
    companion object {
        const val REQUEST_CODE = 1
        const val LOGIN_SUCCESS =1000
        const val LOGIN_FAILTURE =2000
    }
    fun login(context: Activity)
}