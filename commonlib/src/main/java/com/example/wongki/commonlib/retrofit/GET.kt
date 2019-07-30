package com.example.wongki.commonlib.retrofit


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class GET(val path: String)
