package com.example.wongki.commonlib.retrofit

import java.lang.reflect.Method
import java.lang.reflect.Type

class ServiceMethod {
    constructor(method: Method) {

    }

    class Builder {
        private lateinit var retrofit: Retrofit

        private lateinit var method: Method

        private lateinit var methodAnnotations: Array<Annotation>

        private lateinit var parameterTypes: Array<Type>

        private lateinit var parameterAnnotations: Array<Array<Annotation>>

        private lateinit var httpMethod: String

        private lateinit var relativeUrl: String

        private var hasBody: Boolean = false

        private var isFormEncoded: Boolean = false

        constructor(retrofit: Retrofit, method: Method) {
            this.retrofit = retrofit
            this.method = method
            this.methodAnnotations = method.annotations
//            method.parameterTypes//获取参数的class对象数组，如果无参数 返回空数组
            this.parameterTypes = method.genericParameterTypes // 获取type（ParameterType） 可以获取参数的泛型类型
            this.parameterAnnotations = method.parameterAnnotations
        }

        private lateinit var parameterHandlers: Array<ParameterHandler>

        fun build(): ServiceMethod {
            val methodAnnotations = methodAnnotations
            val methodAnnotationsLength = methodAnnotations.size
            for (i in 0 until methodAnnotationsLength) {
                val annotation = methodAnnotations[i]
                parseMethodAnnotation(annotation)
            }

            val parameterCount = parameterAnnotations.size
//            this.parameterHandlers = Array<ParameterHandler>(parameterCount) { index ->
//                for (i in 0 until parameterCount) {
//                    val singleParameterAnnotations = parameterAnnotations[i]
//                    val parameterType = parameterTypes[i]
//                    return@Array parseParseParameter(i, parameterType, singleParameterAnnotations)
//                }
//                return@Array ParameterHandler()
//            }

            return ServiceMethod((method)) //TODO
        }

//        private fun parseParseParameter(i: Int, parameterType: Type, parameterAnnotations: Array<Annotation>): ParameterHandler {
//            var result : ParameterHandler? = null
//            val annotations = parameterAnnotations
//            val size = annotations.size
//            for (index in 0 until size) {
//                val annotation = annotations[index]
//                val annotationAction = this.parseParameterAnnotation(i, parameterType, annotations, annotation)
//            }
//
//        }

//        private fun parseParameterAnnotation(i: Int, parameterType: Type, annotations: Array<Annotation>, annotation: Annotation): ParameterHandler {
//
//        }


        private fun parseMethodAnnotation(annotation: Annotation) {
            when (annotation) {
                is GET -> {
                    parseHttpMethodAndPath("GET", annotation.path, false)
                }

                is POST -> {
                    parseHttpMethodAndPath("GET", annotation.path, true)
                }

                is FormUrlEncoded -> {
                    this.isFormEncoded = true
                }
            }
        }

        private fun parseHttpMethodAndPath(httpMethod: String, path: String, hasBody: Boolean) {
            this.httpMethod = httpMethod
            this.relativeUrl = path
            this.hasBody = hasBody
        }


    }
}
