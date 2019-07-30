package com.example.wongki.commonlogin

import android.app.Activity
import android.content.Intent
import com.example.wongki.commonlib.modularization.ILoginProviderService

class LoginProviderService : ILoginProviderService {
    override fun login(context: Activity) {
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivityForResult(intent, ILoginProviderService.REQUEST_CODE)
    }
}