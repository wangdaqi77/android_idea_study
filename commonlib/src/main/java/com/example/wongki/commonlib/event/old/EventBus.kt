package com.example.wongki.commonlib.event.old

import android.os.Handler
import android.os.Looper
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.util.concurrent.*

class EventBus {
    private val threadPool by lazy { Executors.newCachedThreadPool() }
    private val cacheCities by lazy { HashMap<Any, ArrayList<Station>>() }
    private val mHandler by lazy { Handler(Looper.getMainLooper()) }
    private val MODIFIERS_IGNORE =
            Modifier.PROTECTED or
            Modifier.PRIVATE or
            Modifier.ABSTRACT or
            Modifier.STATIC

    private constructor() {

    }

    companion object {
        private val instance by lazy { EventBus() }
        @Synchronized
        fun getDefault() = instance
    }

    fun register(city: Any) {
        if (cacheCities.containsKey(city)) {
            throw RuntimeException("register already")
        }

        var cacheStations = cacheCities[city]
        if (cacheStations == null) {
            cacheStations = ArrayList()
            cacheCities[city] = cacheStations
        }

        var javaClass: Class<*>? = city.javaClass
        while (javaClass != null) {
            val clazzName = javaClass.name
            if (clazzName.startsWith("java.") || clazzName.startsWith("javax.") || clazzName.startsWith("android.")) {
                break
            }
            val methods = javaClass.declaredMethods
            for (method in methods) {
                val modifiers = method.getModifiers()
                // 找到公共的方法和未添加忽略修饰符的方法
                if ((modifiers and Modifier.PUBLIC) != 0 && (modifiers and MODIFIERS_IGNORE) == 0) {
                    val subscribe = method.getAnnotation(Subscribe::class.java)
                    if (subscribe != null) {
                        val parameterTypes = method.parameterTypes
                        if (parameterTypes?.size != 1) {
                            throw RuntimeException("订阅只允许一个参数")
                        }

                        val station = Station(
                                method = method,
                                type = parameterTypes[0],
                                threadMode = subscribe.threadMode
                        )
                        cacheStations.add(station)
                    }
                }

            }

            javaClass = javaClass.superclass
        }


        if (cacheCities[city]?.isEmpty() == true) {
            throw RuntimeException("no subscribe method")
        }
    }

    fun post(person: Any?) {
        if (person == null) return

        //obj上车
        //启动
        val iterator = cacheCities.iterator()
        while (iterator.hasNext()) {
            val cache = iterator.next()
            //开往城市
            val city = cache.key
            val stations = cache.value
            //开往站点
            for (station in stations) {
                // 1.入参的class是否与缓存中订阅参数的class一致
                // 2.入参的class是否为缓存中订阅参数的子类
                if (station.type.isAssignableFrom(person.javaClass)) {
                    // 到站
                    arriveStation(person, city, station)
                }
            }
        }

    }

    private fun arriveStation(person: Any, city: Any, station: Station) {
        val threadMode = station.threadMode

        if (Looper.myLooper() == Looper.getMainLooper()) {
            when (threadMode) {
            //主线程 -> 主线程
                ThreadMode.MAIN -> invoke(person, city, station.method)
            //主线程 -> 子线程
                ThreadMode.BACKGROUND -> threadPool.execute { invoke(person, city, station.method) }
            }
        } else {
            when (threadMode) {
            //子线程 -> 主线程
                ThreadMode.MAIN -> mHandler.post { invoke(person, city, station.method) }
            //子线程 -> 子线程
                ThreadMode.BACKGROUND -> invoke(person, city, station.method)
            }
        }

    }

    private fun invoke(person: Any, city: Any, method: Method) {
        method.invoke(city, person)
    }

    fun unregister(city: Any) {
        if (cacheCities.containsKey(city)) {
            cacheCities.remove(city)
        } else {
            throw RuntimeException("please call register()")
        }
    }
}