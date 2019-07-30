package com.example.wongki.commonlib.obersever

import java.lang.reflect.ParameterizedType

object InterfaceManager {
    fun addFunction(function: AbsFunction) {
        val functionName = function.functionName
        when (function) {
            is HasParamHasReturnFunction<*, *> -> {
                val clazz = function.javaClass.genericSuperclass
                if (clazz is ParameterizedType) {
                    val actualTypeArguments = clazz.actualTypeArguments
                    hasParamHasReturn[functionName] = HasParamHasReturn(function, actualTypeArguments[0].toString(), actualTypeArguments[1].toString())
                }

            }
        }
    }

    fun <R> executeHasParamHasReturn(funName: String, param: Any, returnClazz: Class<R>): R? {
        val hasParamHasReturn = hasParamHasReturn[funName] ?: return null
        val javaClass = param.javaClass
        if (
                javaClass.toString()
                ==
                hasParamHasReturn.paramTypeName
                &&
                returnClazz.toString()
                ==
                hasParamHasReturn.returnTypeName) {
            val method = hasParamHasReturn.function.javaClass.getMethod("invoke", param.javaClass)
            val invoke = method?.invoke(hasParamHasReturn.function, param)
            if (invoke?.javaClass == returnClazz) {
                return invoke as R
            }
        }
        return null


    }

    val hasParamHasReturn by lazy { HashMap<String, HasParamHasReturn>() }
}

class HasParamHasReturn(val function: HasParamHasReturnFunction<*, *>, val paramTypeName: String, val returnTypeName: String)