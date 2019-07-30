package com.example.wongki.commonlib.image.glide

import android.graphics.BitmapFactory
import android.util.Log
import java.net.HttpURLConnection
import java.net.URL
import java.util.HashSet
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

object GlideDefRequest : IGlideRequest, Runnable {
    private val MAX_CORE_NUM = Runtime.getRuntime().availableProcessors()
    private val mQueue: LinkedBlockingQueue<IGlideBuild> by lazy { LinkedBlockingQueue<IGlideBuild>() }
    private val mThreadPool by lazy { Executors.newCachedThreadPool() }
    private val mCache by lazy { HashMap<String, HashSet<Int>>() }
    private val mThreadCache by lazy { HashMap<Thread, HashSet<String>>() }

    init {
        for (index in 0 until MAX_CORE_NUM) {
            start()
        }
    }

    private fun start() {
        mThreadPool.execute(this)
    }

    override fun enqueue(glideBuild: IGlideBuild) {
        mQueue.add(glideBuild)
    }

    @Synchronized
    override fun clear(activityHashCode: Int) {
        Log.e("GLIDE", "clear()>>>>>>>Start  activityHashCode:$activityHashCode")
        // 移除Glide队列
        val queue = mQueue.iterator()
        while (queue.hasNext()) {
            val next = queue.next()
            if (next.mActivity?.get()?.hashCode() == activityHashCode) {
                queue.remove()
            }
        }

        // 移除内存缓存
        val iterator = mCache.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            val key = next.key
            val activities = next.value
            val activityIterator = activities.iterator()
            while (activityIterator.hasNext()) {
                val activity = activityIterator.next()
                if (activity == activityHashCode) {
                    activityIterator.remove()
                }
            }
            if (activities.size == 0) {
                // 移除缓存
                GlideCache.removeFromMemory(key)
            }
        }

        // 中止线程
        val threadIterator = mThreadCache.iterator()
        while (threadIterator.hasNext()) {
            val thread = threadIterator.next().key
            if (!thread.isInterrupted) {
                thread.interrupt()
                Log.e("GLIDE", "中止线程成功 -> thread:${thread.name}")
            }else{
                Log.e("GLIDE", "线程已经被中止过 -> thread:${thread.name}")
            }
        }
        for ( i in 0..100) {
            System.gc()
        }
        Log.e("GLIDE", "clear()>>>>>>>End  activityHashCode:$activityHashCode")
    }

    override fun run() {
        val currentThread = Thread.currentThread()
        Log.e("GLIDE", "run() -> thread:${currentThread.name}")
        while (true) {
            if (!currentThread.isInterrupted) {
                try {
                    val glideBuild = mQueue.take()
                    val activity = glideBuild.mActivity?.get()

                    val key = glideBuild.mKey
                    if (key.isNullOrEmpty()) {
                        Glide.glideDispatcher().dispatch(IGlideDispatcher.Type.ERROR, glideBuild)
                        return
                    }

                    addCache(key, activity.hashCode())
                    addThreadCache(Thread.currentThread(), key)

                    Log.e("GLIDE", "process()>>>>>>>Start  glideBuild.mKey:${glideBuild.mKey}")
                    process(glideBuild)
                    Log.e("GLIDE", "process()>>>>>>>End  glideBuild.mKey:${glideBuild.mKey}")

                } catch (e: InterruptedException) {
                    Log.e("GLIDE", "InterruptedException -> thread:${currentThread.name}")
                    removeThreadCache(currentThread)
                    start()
                    return
                }
            } else {
                Log.e("GLIDE", "isInterrupted -> thread:${currentThread.name}")
                removeThreadCache(currentThread)
                start()
                return
            }
        }
    }


    private fun process(glideBuild: IGlideBuild) {
       val key = glideBuild.mKey!!
        // 如果内存存在 直接取内存中的bitmap
        val fromMemory = GlideCache.getFromMemory(key)
        if (fromMemory != null) {
            Glide.glideDispatcher().dispatch(IGlideDispatcher.Type.SUCCESS, glideBuild)
            return
        }
        val fromDisk = GlideCache.getFromDisk(key)
        if (fromDisk != null) {
            GlideCache.put(key, fromDisk)
            Glide.glideDispatcher().dispatch(IGlideDispatcher.Type.SUCCESS, glideBuild)
            return
        }
        Glide.glideDispatcher().dispatch(IGlideDispatcher.Type.LOADING, glideBuild)
        //网络获取，成功之后存到内存和磁盘中
        try {
            val url = URL(glideBuild.mImageUrl)
            val connection = url.openConnection() as HttpURLConnection
            val inputStream = connection.inputStream
            val bitmap = BitmapFactory.decodeStream(inputStream)
            GlideCache.put(key, bitmap)
            Glide.glideDispatcher().dispatch(IGlideDispatcher.Type.SUCCESS, glideBuild)
        } catch (e: Exception) {
            Glide.glideDispatcher().dispatch(IGlideDispatcher.Type.ERROR, glideBuild)
        }


    }

    private fun addCache(key: String, hashCode: Int) {
        var set = mCache[key]
        if (set == null) {
            set = HashSet()
            mCache[key] = set
        }

        if (!set.contains(hashCode)) {
            set.add(hashCode)
        }
    }


    private fun addThreadCache(currentThread: Thread, key: String): Boolean {
        var keys = mThreadCache[currentThread]
        if (keys == null) {
            keys = HashSet()
            mThreadCache[currentThread] = keys
        }
        keys.add(key)
        Log.e("GLIDE", "currentThread:${currentThread.name} -> 缓存了${keys.size}个key")
        return true
    }

    @Synchronized
    private fun removeThreadCache(currentThread: Thread) {
        if (!currentThread.isInterrupted) {
            Log.e("GLIDE", "removeThreadCache 使thread死亡：${currentThread.name}")
            currentThread.interrupt()
        }
        if (mThreadCache.containsKey(currentThread)) {
            Log.e("GLIDE", "removeThreadCache thread死亡：${currentThread.name} 移除前 -> mThreadCache.size${mThreadCache.size}")
            mThreadCache.remove(currentThread)
            Log.e("GLIDE", "removeThreadCache thread死亡：${currentThread.name} 移除后 -> mThreadCache.size${mThreadCache.size}")
        }

    }
}
