package com.example.wongki.commonlib.proxy.static

import android.util.Log
import com.example.wongki.commonlib.proxy.ISubject

//代理类
class ProxySubject(private val realSubject: RealSubject) : ISubject {

    override fun doSomething() {
        Log.i("TEST", "proxy -> doSomething")
        realSubject.doSomething()
    }

}
