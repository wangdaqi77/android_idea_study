package com.example.wongki.commonlib.retrofit

import java.lang.IllegalArgumentException
import java.lang.NullPointerException

object Utils {
    fun <T> checkNotNull(any: T?, message: String): T {
        if (any == null) {
            throw NullPointerException(message)
        }
        return any
    }

    fun <T> validateServiceInterface(serviceClazz: Class<T>) {
        if (!serviceClazz.isInterface){
            throw IllegalArgumentException("API declarations must be interfaces.")
        }
        if (serviceClazz.interfaces.isNotEmpty()) {
            throw IllegalArgumentException("API interfaces must not extend other interfaces")
        }
    }
}