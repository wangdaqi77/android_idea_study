package com.example.wongki.modularization

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.wongki.commonlib.image.glide.Glide
import com.example.wongki.commonlib.image.glide.GlideLoadFinishListener
import kotlinx.android.synthetic.main.activity_glide_test.*

class GlideTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glide_test)

    }

    fun load(view: View) {
        for (i in 0..99) {
            val imageView = ImageView(this)
            imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            ll_container.addView(imageView)
            Glide.with(this)
                    .loading(R.drawable.loading)
                    .load("http://dn.dengpaoedu.com/glide/" + i % 66 + ".jpeg")
                    .error(R.drawable.error)
                    .listener(object : GlideLoadFinishListener {
                        override fun error() {
                            Log.e("GLIDE", "error()")

                        }

                        override fun preShowImage(bitmap: Bitmap): Bitmap? {
                            return null
                        }

                    })
                    .into(imageView)
        }
    }

    override fun onDestroy() {
        ll_container.removeAllViews()
        super.onDestroy()
    }
}
