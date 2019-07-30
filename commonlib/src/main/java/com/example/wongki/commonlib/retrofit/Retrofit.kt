package com.example.wongki.commonlib.retrofit

import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.ConcurrentHashMap

class Retrofit {
    private lateinit var callFactory: Call.Factory
    private lateinit var baseUrl: HttpUrl
    private val serviceMethodCache by lazy { ConcurrentHashMap<Method, ServiceMethod?>() }

    constructor(callFactory: Call.Factory, baseUrl: HttpUrl) {
        this.callFactory = callFactory
        this.baseUrl = baseUrl
    }
//
//    @Suppress("UNCHECKED_CAST")
//    fun <T> create(serviceClazz: Class<T>): T {
//        Utils.validateServiceInterface(serviceClazz)
//        return Proxy.newProxyInstance(serviceClazz.classLoader, arrayOf(serviceClazz), object : InvocationHandler {
//            override fun invoke(proxy: Any?, method: Method, args: Array<out Any>?): Any {
//                // 将方法封装起来
//                val serviceMethod = this@Retrofit.loadServiceMethod(method)
//                // 处理参数
//                val paramsHandler: ParamsHandler = serviceMethod.handleParams()
//                // 创建okcall
//
//                // 返回方法对应的
//            }
//
//        }) as T
//    }

    private fun loadServiceMethod(method: Method): ServiceMethod {
        var serviceMethod = serviceMethodCache[method]
        if (serviceMethod != null) {
            return serviceMethod
        } else {
            synchronized(serviceMethodCache) {
                serviceMethod = serviceMethodCache[method]
                if (serviceMethod == null) {
                    serviceMethod = ServiceMethod.Builder(this, method).build()
                    serviceMethodCache[method] = serviceMethod
                }
                return serviceMethod as ServiceMethod
            }

        }

    }

    class Builder {
        private var baseUrl: HttpUrl? = null
        private var callFactory: Call.Factory? = null
        fun baseUrl(baseUrl: String): Retrofit.Builder {
            val httpUrl = HttpUrl.parse(baseUrl)
            return if (httpUrl == null) {
                throw IllegalArgumentException("Illegal URL: $baseUrl")
            } else {
                this.baseUrl(httpUrl)
            }
        }

        fun baseUrl(baseUrl: HttpUrl): Retrofit.Builder {
            Utils.checkNotNull(baseUrl, "baseUrl == null")
            val pathSegments = baseUrl.pathSegments()
            if ("" != pathSegments[pathSegments.size - 1]) {
                throw IllegalArgumentException("baseUrl must end in /: $baseUrl")
            } else {
                this.baseUrl = baseUrl
                return this
            }
        }

        fun build(): Retrofit {
            val baseUrl = this.baseUrl
            if (baseUrl == null) {
                throw IllegalStateException("Base URL required.")
            } else {
                var callFactory = this.callFactory
                if (callFactory == null) {
                    callFactory = OkHttpClient()
                }

                return Retrofit(callFactory, baseUrl)
            }
        }
//
//        fun client(okClient: OkHttpClient): Builder {
//
//        }

    }
}