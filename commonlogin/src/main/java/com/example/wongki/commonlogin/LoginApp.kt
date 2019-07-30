package com.example.wongki.commonlogin

import android.app.Application
import com.example.wongki.commonlib.BaseApplication
import com.example.wongki.commonlib.modularization.IApp
import com.example.wongki.commonlib.modularization.ProviderServices

class LoginApp:BaseApplication(),IApp{

    companion object {
        private lateinit var appInstance: Application
        fun getInstance() = appInstance
    }
    override fun onCreate() {
        super.onCreate()
        if (!isMainProcess()) return
        initialize(this)
    }

    override fun initialize(app: Application) {
        appInstance = app
        ProviderServices.loginService = LoginProviderService()
    }
}