package com.example.wongki.commonlib.event

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.wongki.commonlib.R
import com.example.wongki.commonlib.event.old.EventBus
import com.example.wongki.commonlib.event.old.Subscribe
import com.example.wongki.commonlib.event.old.ThreadMode
import kotlinx.android.synthetic.main.activity_first.*

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)


        EventBus.getDefault().register(this)

        open_second.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onEvent(event: EventTest) {
        Log.e("TEST", "thread -> ${Thread.currentThread().name}, event -> $event")
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent1(event: EventTestGeneric<String>) {
        Log.e("TEST", "String: thread -> ${Thread.currentThread().name}, event -> $event")
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent2(event: EventTestGeneric<Boolean>) {
        Log.e("TEST", "Boolean: thread -> ${Thread.currentThread().name}, event -> $event")
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
