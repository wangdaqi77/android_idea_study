package com.example.wongki.commonlib.proxy.dynamic

import com.example.wongki.commonlib.proxy.ISubject
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class ProxySubject(private val subject: ISubject) : InvocationHandler {
    private var proxyInstance: ISubject? = null

    fun bind(): ISubject {
        if (proxyInstance == null) {
            proxyInstance = Proxy.newProxyInstance(subject::class.java.classLoader, arrayOf(ISubject::class.java), this) as ISubject
        }
        return proxyInstance!!
    }

    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        if (args==null) {
            return method?.invoke(subject)
        }
        return method?.invoke(subject, args)
    }

}