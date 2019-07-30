package com.example.wongki.modularization

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_small_red_book.*

class SmallRedBookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_small_red_book)
        parallax_container.init(supportFragmentManager)
    }
}
