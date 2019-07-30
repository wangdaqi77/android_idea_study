package com.example.wongki.commonlib.inject.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.wongki.commonlib.R

//@ContentView(R.layout.activity_test_inject_view)
class TestInjectViewActivity : AppCompatActivity() {

//    @JvmField  @BindView(R.id.btn_1)  var btn1: Button?=null
//    @BindView(R.id.tv_1)
    val tv1: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InjectView.inject(this)
        setContentView(R.layout.activity_test_inject_view)
    }
//
//
//    @OnClick({ R.id.btn_1, R.id.tv_1 })
//    fun click(view:View) {
//        when(view.id) {
//            R.id.btn_1->{
//                tv1?.text = "result:btn_1 click"
//
//            }
//            R.id.tv_1->{
//
//            }
//        }
//    }
//
//    override fun onDestroy() {
//        InjectView.destroy(this)
//        super.onDestroy()
//    }
}
