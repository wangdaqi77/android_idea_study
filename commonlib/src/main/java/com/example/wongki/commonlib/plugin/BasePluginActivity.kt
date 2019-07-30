package com.example.wongki.commonlib.plugin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.*

abstract class BasePluginActivity : FragmentActivity(), IPluginActivity {
    companion object {
        const val EXT_FROM = "from"
    }

    private var proxyActivity: FragmentActivity? = null
    private var mFrom: Int = IPluginActivity.FROM_EXTERNAL

    override fun attach(context: FragmentActivity) {
        this.proxyActivity = context
    }

    override fun getIntent(): Intent {

        return when (mFrom) {
            //插件
            IPluginActivity.FROM_EXTERNAL -> {
                proxyActivity?.intent ?: super.getIntent()
            }
            else -> super.getIntent()
        }
    }

    override fun setContentView(layoutResID: Int) {
        when (mFrom) {
            IPluginActivity.FROM_EXTERNAL -> {
                proxyActivity?.setContentView(layoutResID) ?: super.setContentView(layoutResID)
            }
            else -> super.setContentView(layoutResID)
        }
    }

    override fun setContentView(view: View?) {
        when (mFrom) {
            IPluginActivity.FROM_EXTERNAL -> {
                proxyActivity?.setContentView(view) ?: super.setContentView(view)
            }
            else -> super.setContentView(view)
        }
    }

    override fun <T : View?> findViewById(id: Int): T {
        return when (mFrom) {
            IPluginActivity.FROM_EXTERNAL -> {
                proxyActivity?.findViewById<T>(id) ?: super.findViewById<T>(id)
            }
            else -> super.findViewById<T>(id)
        }
    }

    override fun getApplicationContext(): Context {
        return when (mFrom) {
            IPluginActivity.FROM_EXTERNAL -> {
                proxyActivity?.applicationContext ?: super.getApplicationContext()
            }
            else -> super.getApplicationContext()
        }
    }

    override fun getWindow(): Window {
        return when (mFrom) {
            IPluginActivity.FROM_EXTERNAL -> {
                proxyActivity?.window ?: super.getWindow()
            }
            else -> super.getWindow()
        }
    }

    override fun getWindowManager(): WindowManager {
        return when (mFrom) {
            IPluginActivity.FROM_EXTERNAL -> {
                proxyActivity?.windowManager ?: super.getWindowManager()
            }
            else -> super.getWindowManager()
        }
    }

    override fun getLayoutInflater(): LayoutInflater {
        return when (mFrom) {
            IPluginActivity.FROM_EXTERNAL -> {
                proxyActivity?.layoutInflater ?: super.getLayoutInflater()
            }
            else -> super.getLayoutInflater()
        }
    }

    override fun finish() {
        when (mFrom) {
            IPluginActivity.FROM_EXTERNAL -> {
                proxyActivity?.finish() ?: super.finish()
            }
            else -> super.getLayoutInflater()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return when (mFrom) {
            IPluginActivity.FROM_EXTERNAL -> {
                proxyActivity?.onTouchEvent(event) ?: super.onTouchEvent(event)
            }
            else -> super.onTouchEvent(event)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val intent = intent
        mFrom = intent.getIntExtra(EXT_FROM, IPluginActivity.FROM_INTERNAL)
        when (mFrom) {
            IPluginActivity.FROM_INTERNAL -> {
                super.onCreate(savedInstanceState)
            }
            IPluginActivity.FROM_EXTERNAL -> {

            }
        }

    }

    override fun onNewIntent(intent: Intent) {
        when (mFrom) {
            IPluginActivity.FROM_INTERNAL -> {
                super.onNewIntent(intent)
            }
            IPluginActivity.FROM_EXTERNAL -> {

            }
        }

    }

    override fun onRestart() {
        when (mFrom) {
            IPluginActivity.FROM_INTERNAL -> {
                super.onRestart()

            }
            IPluginActivity.FROM_EXTERNAL -> {
            }
        }
    }

    override fun onStart() {
        when (mFrom) {
            IPluginActivity.FROM_INTERNAL -> {
                super.onStart()

            }
            IPluginActivity.FROM_EXTERNAL -> {
            }
        }
    }

    override fun onResume() {
        when (mFrom) {
            IPluginActivity.FROM_INTERNAL -> {
                super.onResume()

            }
            IPluginActivity.FROM_EXTERNAL -> {
            }
        }
    }

    override fun onPause() {
        when (mFrom) {
            IPluginActivity.FROM_INTERNAL -> {
                super.onPause()

            }
            IPluginActivity.FROM_EXTERNAL -> {
            }
        }
    }

    override fun onStop() {
        when (mFrom) {
            IPluginActivity.FROM_INTERNAL -> {
                super.onStop()
            }
            IPluginActivity.FROM_EXTERNAL -> {
            }
        }
    }

    override fun onDestroy() {
        when (mFrom) {
            IPluginActivity.FROM_INTERNAL -> {
                super.onDestroy()
            }
            IPluginActivity.FROM_EXTERNAL -> {
            }
        }

    }
}