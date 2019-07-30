package com.example.wongki.commonlib.aop

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class ClickInvocationHandler(private val click: IClick) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method, vararg args: Any?): Any {
        return method.invoke(click, *args)
    }
}