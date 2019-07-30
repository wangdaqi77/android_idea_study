package com.example.wongki.commonlib.event

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.wongki.commonlib.R
import com.example.wongki.commonlib.event.old.EventBus
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val eventTest = EventTest("测试", "哈哈")
        val eventGenericTest = EventTestGeneric<String>("测试", "哈哈","泛型String")
        Thread { EventBus.getDefault().post(eventGenericTest) }.start()
        send_event.setOnClickListener {
            EventBus.getDefault().post(eventTest)
        }
    }
}
