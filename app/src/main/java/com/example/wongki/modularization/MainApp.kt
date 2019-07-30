package com.example.wongki.modularization

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.wongki.commonlib.BaseApplication
import com.example.wongki.commonlib.modularization.IApp
import com.example.wongki.commonlib.modularization.ProviderPath
import com.example.wongki.commonlib.utils.UIScaleHelper
import com.example.wongki.modularization.keep.KeepHelper
import com.taobao.sophix.PatchStatus
import com.taobao.sophix.SophixManager


class MainApp : BaseApplication(), IApp {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SophixManager.getInstance().setContext(this)
                .setAppVersion(BuildConfig.VERSION_NAME)
                .setEnableDebug(true)
                .setPatchLoadStatusStub { mode, code, info, handlePatchVersion ->
                    // 补丁加载回调通知
                    if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                        // 表明补丁加载成功
                        Log.e("Sophix", "补丁加载成功 code:$code")
                    } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                        // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                        // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3

                        Log.e("Sophix", "新补丁生效需要重启 code:$code")
                        val intent = packageManager.getLaunchIntentForPackage(packageName)
                        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        SophixManager.getInstance().killProcessSafely()
                    } else {
                        // 其它错误信息, 查看PatchStatus类说明

                        Log.e("Sophix", "其它错误信息,查看PatchStatus类说明 code:$code")
                    }
                }.initialize()
    }

    override fun onCreate() {
        super.onCreate()
        if (!isMainProcess()) return
        KeepHelper.log("KEEP  APPLICATION : $this  thread : ${Thread.currentThread().name}   ${Runtime.getRuntime()}")
        initialize(this)

        registerActivityLifecycleCallbacks(object :Application.ActivityLifecycleCallbacks{
            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityResumed(activity: Activity?) {
            }

            override fun onActivityStarted(activity: Activity?) {
            }

            override fun onActivityDestroyed(activity: Activity?) {
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

            }

        })
        UIScaleHelper.init(this)
    }

    override fun initialize(app: Application) {
        if (!isMainProcess()) return
        val paths = ProviderPath.paths
        for (path in paths) {
            val newInstance = classLoader.loadClass(path).newInstance()
            if (newInstance is IApp) {
                newInstance.initialize(app)
            }
        }
    }
}