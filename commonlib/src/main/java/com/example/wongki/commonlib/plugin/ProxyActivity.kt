package com.example.wongki.commonlib.plugin

import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Bundle
import android.support.v4.app.FragmentActivity

class ProxyActivity : FragmentActivity() {
    companion object {
        const val EXT_APK_PATH = "apk_path"
        const val EXT_CLASS_NAME = "class_name"
    }

    private lateinit var pluginActivity: IPluginActivity
    private var pluginApk: PluginApk? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apkPath = intent.getStringExtra(EXT_APK_PATH)
        pluginApk = PluginManager.loadPluginApkByPath(apkPath)
        val pluginActivityClassName = intent.getStringExtra(EXT_CLASS_NAME)
        pluginActivity = classLoader.loadClass(pluginActivityClassName).newInstance() as IPluginActivity
        pluginActivity.attach(this)
        //调用插件Activity的onCreate，
        pluginActivity.onCreate(intent.extras)
    }

    override fun onRestart() {
        super.onRestart()
        pluginActivity.onRestart()
    }
    override fun onStart() {
        super.onStart()
        pluginActivity.onStart()
    }

    override fun onResume() {
        super.onResume()
        pluginActivity.onResume()
    }

    override fun onPause() {
        super.onPause()
        pluginActivity.onPause()
    }

    override fun onStop() {
        super.onStop()
        pluginActivity.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        pluginActivity.onDestroy()
    }
    override fun getClassLoader(): ClassLoader {
        return pluginApk?.classLoad ?: super.getClassLoader()
    }

    override fun getResources(): Resources {
        return pluginApk?.resources ?: super.getResources()
    }

    override fun getAssets(): AssetManager {

        return pluginApk?.assetManager ?: super.getAssets()
    }

}
