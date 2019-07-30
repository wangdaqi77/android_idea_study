package com.example.wongki.commonlib.event.old

import com.example.wongki.commonlib.event.old.ThreadMode

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Subscribe(val threadMode: ThreadMode = ThreadMode.MAIN)
