package com.example.wongki.modularization

import android.app.Activity
import android.arch.lifecycle.LiveData
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.example.wongki.commonlib.utils.UIUtils
import kotlinx.android.synthetic.main.activity_material_design.*

class MaterialDesignActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN.or(WindowManager.LayoutParams.FLAG_FULLSCREEN),WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_material_design)
        UIUtils.compatImmersiveStatusBar(this)
        UIUtils.compatHideNavigation(this)
        floating_action_button.setOnClickListener {
            //val intent = Intent(this,CustomRecyclerViewActivity::class.java)
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }


//        LiveData<Intent>().observe()
    }

}
