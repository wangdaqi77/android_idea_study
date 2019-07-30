package com.example.wongki.commonlib.event

data class EventTestGeneric<out T>(val first: String, val second: String,val t: T)