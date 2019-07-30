package com.example.wongki.commonlib.aop

import android.view.View
import java.lang.reflect.Proxy

object ClickAopInject {
    fun register(obj: IClick, vararg view: View) {
        var clazz: Class<*>? = obj::class.java
        while (clazz != null) {
            // 1.获取所有的方法
            val methods = obj::class.java.declaredMethods
            // 2.找到aop方法
            var proxy: IClick? = null
            for (method in methods) {
                val annotation = method.getAnnotation(ClickAop::class.java)
                if (annotation != null) {
                    // 3.代理
                    val clickInvocationHandler = ClickInvocationHandler(ClickAopImpl())
                    proxy = Proxy.newProxyInstance(obj::class.java.classLoader, arrayOf(IClick::class.java), clickInvocationHandler) as IClick

                }
            }
            for (v in view) {
                v.setOnClickListener {
                    // 4.aop切入
                    if (proxy == null || !proxy.click(v)) {
                        obj.click(v)
                    }
                }
            }

            clazz = clazz?.superclass
        }

    }
}
