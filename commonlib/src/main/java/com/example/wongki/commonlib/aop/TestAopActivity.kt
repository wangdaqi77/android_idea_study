package com.example.wongki.commonlib.aop

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.wongki.commonlib.R
import kotlinx.android.synthetic.main.activity_test_aop.*

class TestAopActivity : AppCompatActivity(), IClick {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_aop)
        ClickAopInject.register(this, btn_1)
    }

    @ClickAop
    override fun click(view: View): Boolean {
        Log.i("TEST", "处理。。。 id:${btn_1.id}")
        return false
    }
}

