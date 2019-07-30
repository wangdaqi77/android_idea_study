package com.example.wongki.commonlib.plugin

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import com.example.wongki.commonlib.utils.AssetsHelper
import dalvik.system.DexClassLoader
import java.io.File

@SuppressLint("StaticFieldLeak")
object PluginManager {

    private val cachePluginApk by lazy { HashMap<String, PluginApk>() }
    private lateinit var context: Context


    fun init(context: Context) {
        this.context = context.applicationContext

    }

    fun downPluginApk(apkName: String,file:File) {
        // 模拟网络下载
        AssetsHelper.copyFilesFassets(context,apkName,file.absolutePath)
    }

    fun loadPluginApkByPath(apkPath: String?): PluginApk? {
        if (apkPath == null) return null

        cachePluginApk[apkPath]?.let {
            return it
        }

        // 初始化DexClassLoader
        // dex缓存文件夹
        val dexOutDir = this.context.getDir("dex", Context.MODE_PRIVATE) ?: return null
        val classLoad = DexClassLoader(apkPath, dexOutDir.absolutePath, null, context.classLoader)

        // 初始化AssetsManager
        val assetManager = AssetManager::class.java.newInstance()
        val method = AssetManager::class.java.getDeclaredMethod("addAssetPath", String::class.java)
        method.isAccessible = true
        method.invoke(assetManager, apkPath)

        // 初始化Resource
        val resources = Resources(assetManager, context.resources?.displayMetrics, context.resources?.configuration)

        // 初始化packageInfo
        val packageInfo = context.packageManager.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES or PackageManager.GET_SERVICES)
        // 初始化PluginApk
        val pluginApk = PluginApk(apkPath, classLoad, assetManager, resources, packageInfo)
        cachePluginApk[apkPath] = pluginApk
        return pluginApk
    }

}
