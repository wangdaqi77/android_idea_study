package com.example.wongki.modularization

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.wongki.modularization.view.BollView
import com.example.wongki.modularization.view.BubbleView
import com.example.wongki.modularization.view.PathMeasureTestView

class CustomViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val bollView = BollView(this)

        setContentView(PathMeasureTestView(this))
    }
}
