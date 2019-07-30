package com.example.wongki.commonlib.plugin

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Bundle
import dalvik.system.DexClassLoader

class PluginApk {
    var apkPath: String
    var classLoad: DexClassLoader
    var assetManager: AssetManager
    var resources: Resources
    var packageInfo: PackageInfo

    constructor(apkPath: String, classLoad: DexClassLoader, assetManager: AssetManager, resources: Resources, packageInfo: PackageInfo) {
        this.apkPath = apkPath
        this.classLoad = classLoad
        this.assetManager = assetManager
        this.resources = resources
        this.packageInfo = packageInfo
    }

    fun launchActivity(context: Context, className: String, bundle: Bundle? = null) {
        val intent = Intent()
        intent.setClass(context, ProxyActivity::class.java)
        intent.putExtra(ProxyActivity.EXT_CLASS_NAME, className)
        intent.putExtra(ProxyActivity.EXT_APK_PATH, apkPath)
        intent.putExtra(BasePluginActivity.EXT_FROM, IPluginActivity.FROM_EXTERNAL)
        bundle?.let {
            intent.putExtras(it)
        }
        context.startActivity(intent)
    }

}