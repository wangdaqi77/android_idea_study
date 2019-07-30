package com.example.wongki.commonlib.event.old

import java.lang.reflect.Method

data class Station(
        val method: Method,
        val type: Class<*>,
        val threadMode: ThreadMode
)