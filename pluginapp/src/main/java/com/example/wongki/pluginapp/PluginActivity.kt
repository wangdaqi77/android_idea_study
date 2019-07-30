package com.example.wongki.pluginapp

import android.os.Bundle
import android.widget.Button
import com.example.wongki.commonlib.plugin.BasePluginActivity

class PluginActivity : BasePluginActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plugin)

        findViewById<Button>(R.id.back).setOnClickListener {
            finish()
        }
    }

}
