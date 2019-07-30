package com.example.wongki.modularization.tinker

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import com.example.wongki.commonlib.utils.Constants
import com.example.wongki.commonlib.utils.FileUitls
import com.example.wongki.commonlib.hotfix.tinker.TinkerFixDexUtils
import com.example.wongki.modularization.R
import java.io.File

class TestHotFixMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_hot_fix_main)
    }


    fun openBugActivity(view: View) {
        val intent = Intent()
        intent.setClass(this, BugActivity::class.java)
        startActivity(intent)
    }

    fun hotFix(view: View) {
        startFix()
    }

    private fun startFix() {
        // 下载dex
        val fixDex = File(Environment.getDataDirectory(), Constants.DEX_NAME)
        // 放到私有目录下的odex文件夹
        val targetDexFile = File(getDir(Constants.DEX_NAME, Context.MODE_PRIVATE).absolutePath + File.separator + Constants.DEX_NAME)
        if (targetDexFile.exists()) {
            targetDexFile.delete()
        }

        try {
            FileUitls.copyFile(fixDex, targetDexFile)
            //加载fix dex包
            TinkerFixDexUtils.loadFixedDex(this)
        } catch (e: Exception) {
        }
    }
}
