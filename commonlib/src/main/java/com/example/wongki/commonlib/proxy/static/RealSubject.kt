package com.example.wongki.commonlib.proxy.static

import android.util.Log
import com.example.wongki.commonlib.proxy.ISubject

//委托人
class RealSubject :ISubject{
    override fun doSomething() {
        Log.i("TEST","RealSubject -> doSomething")
    }

}
