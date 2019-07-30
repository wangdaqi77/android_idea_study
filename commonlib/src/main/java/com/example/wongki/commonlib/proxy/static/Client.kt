package com.example.wongki.commonlib.proxy.static

import android.util.Log
import com.example.wongki.commonlib.proxy.ISubject

//客户
class Client:ISubject{
    override fun doSomething() {
        Log.i("TEST","client doSomething")
        // 交给代理类处理
        ProxySubject(RealSubject()).doSomething()
    }
}