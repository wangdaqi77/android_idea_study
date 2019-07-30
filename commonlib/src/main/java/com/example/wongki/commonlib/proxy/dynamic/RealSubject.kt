package com.example.wongki.commonlib.proxy.dynamic

import android.util.Log
import com.example.wongki.commonlib.proxy.ISubject

class RealSubject:ISubject{
    override fun doSomething() {
        Log.i("TEST","RealSubject -> doSomething")
    }
}