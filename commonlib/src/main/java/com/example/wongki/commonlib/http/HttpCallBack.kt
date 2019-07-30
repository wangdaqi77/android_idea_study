package com.example.wongki.commonlib.http

import com.google.gson.Gson
import java.lang.reflect.ParameterizedType

abstract class HttpCallBack<T> : IHttpCallBack {
    final override fun onSuccess(str: String) {
        val gson = Gson()
        val clazz: Class<T>? = getClazz(this)
        if (clazz == null) {
            onFailure(-99999)
            return
        }
        val t = gson.fromJson<T>(str, clazz)
        onSuccess(t)
    }

    private fun getClazz(httpCallBack: HttpCallBack<T>): Class<T>? {
        val genericSuperclass = httpCallBack.javaClass.genericSuperclass

        if (genericSuperclass is ParameterizedType) {
            val typeArguments = genericSuperclass.actualTypeArguments
            return typeArguments[0] as Class<T>
        }
        return null

    }

    abstract fun onSuccess(t: T)

}