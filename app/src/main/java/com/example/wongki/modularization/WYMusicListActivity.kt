package com.example.wongki.modularization

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.example.wongki.commonlib.utils.UIAdapterUtils
import com.example.wongki.commonlib.utils.UIScaleHelper
import com.example.wongki.commonlib.utils.UIUtils
import com.example.wongki.commonlib.view.StatusBarView
import com.example.wongki.modularization.view.MyNestedScrollView
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_wymusic_list.*

class WYMusicListActivity : AppCompatActivity() {
    val IMAGE_URL_MEDIUM = "https://p3.music.126.net/iRbTMHGfy-grDtx7o2T_dA==/109951164009413034.jpg?param=400y400"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wymusic_list)
        initView()
    }

    var statusBarView: StatusBarView? = null
    private fun initView() {
        //沉浸式
        UIUtils.compatImmersiveStatusBar(this)
        UIUtils.compatHideNavigation(this)
        UIAdapterUtils.adapterActivity(this)
        lv_header_contail.setPadding(lv_header_contail.paddingLeft, lv_header_contail.paddingTop + UIScaleHelper.getStatusBarHeight(this), lv_header_contail.paddingRight, lv_header_contail.paddingBottom)
        UIUtils.fixFullScreenForImmersive(this, toolBar)
        statusBarView = findViewById<StatusBarView>(R.id.status_bar_view)
        setSupportActionBar(toolBar)
        postImage()
        initRecyclerView()
        initListener()
    }

    private fun setBg() {
        toolBarBg.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))
        header_bg.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))
        findViewById<StatusBarView>(R.id.status_bar_view).setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))
    }

    val MAX_DISTANCE = 490
    private fun initListener() {
        nsv_scrollview.setScrollListener(object : MyNestedScrollView.ScrollListener {
            override fun onScroll(x: Int, y: Int) {
                Log.e("TAG", "y:$y")
                updateToolBarBgAlpha(y)
            }

        })
    }

    private fun updateToolBarBgAlpha(scrolledY: Int) {
        var scrolledY = scrolledY
        if (scrolledY < 0) {
            scrolledY = 0
        }
//0  1
        val alpha = Math.abs(scrolledY) * 1.0f / MAX_DISTANCE
        val drawable = toolBarBg.drawable
        if (drawable != null) {
            //490
            if (scrolledY <= MAX_DISTANCE) {
                drawable.mutate().setAlpha((alpha * 255).toInt())
                toolBarBg.setImageDrawable(drawable)
            } else {
                //490
                drawable.mutate().setAlpha(255)
                toolBarBg.setImageDrawable(drawable)
            }
        }

//        if (scrolledY < 0) return
//
//        when (scrolledY) {
//            in Int.MIN_VALUE..0 -> {
//                toolBarBg.alpha = 1f
//            }
//            in 0..MAX_DISTANCE -> {
//                toolBarBg.alpha = 1 - scrolledY.toFloat() / MAX_DISTANCE
//            }
//            else -> {
//                toolBarBg.alpha = 1f
//            }
//        }

    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        music_recycler.layoutManager = layoutManager
        music_recycler.adapter = MusicAdapter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.music_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun postImage() {
        Glide.with(this)
                .load(IMAGE_URL_MEDIUM)
                .listener(object : RequestListener<String, GlideDrawable> {
                    override fun onException(e: Exception, model: String, target: Target<GlideDrawable>, isFirstResource: Boolean): Boolean {
                        Log.i("tuch", "onException: $e")
                        return false
                    }

                    override fun onResourceReady(resource: GlideDrawable, model: String, target: Target<GlideDrawable>, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        Log.i("tuch", "onResourceReady: ")
                        return false
                    }
                }).override(400, 400)
                .into(header_image_item)

        // "14":模糊度；"3":图片缩放3倍后再进行模糊
        Glide.with(this)
                .load(IMAGE_URL_MEDIUM)
                .error(R.drawable.stackblur_default)
                .placeholder(R.drawable.stackblur_default)
                .crossFade(500)
                .bitmapTransform(BlurTransformation(this, 200, 3))
                .into(object : SimpleTarget<GlideDrawable>() {
                    override fun onResourceReady(resource: GlideDrawable, glideAnimation: GlideAnimation<in GlideDrawable>) {
                        lv_header_contail.background = resource
                    }
                })
        Glide.with(this).load(IMAGE_URL_MEDIUM)
                .error(R.drawable.stackblur_default)
                .bitmapTransform(BlurTransformation(this, 250, 6))// 设置高斯模糊
                .listener(object : RequestListener<String, GlideDrawable> {
                    //监听加载状态
                    override fun onException(e: Exception, model: String, target: Target<GlideDrawable>, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: GlideDrawable, model: String, target: Target<GlideDrawable>, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        toolBar.setBackgroundColor(Color.TRANSPARENT)
                        toolBarBg.setImageAlpha(1)
                        toolBarBg.setVisibility(View.VISIBLE)
                        return false
                    }
                }).into(toolBarBg)

    }
}
