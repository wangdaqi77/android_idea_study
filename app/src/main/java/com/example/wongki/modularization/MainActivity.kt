package com.example.wongki.modularization

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.wongki.commonlib.aop.TestAopActivity
import com.example.wongki.commonlib.event.FirstActivity
import com.example.wongki.commonlib.http.HttpCallBack
import com.example.wongki.commonlib.http.HttpHelper
import com.example.wongki.commonlib.modularization.ILoginProviderService
import com.example.wongki.commonlib.modularization.ProviderServices
import com.example.wongki.commonlib.obersever.HasParamHasReturnFunction
import com.example.wongki.commonlib.obersever.InterfaceManager
import com.example.wongki.commonlib.plugin.PluginManager
import com.example.wongki.modularization.bean.TestHttpBean
import com.example.wongki.modularization.keep.KeepHelper
import com.example.wongki.modularization.sophix.sophix.test.SpohixTest
import com.example.wongki.modularization.tinker.TestHotFixMainActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import android.app.NotificationManager
import android.support.v4.app.NotificationCompat
import android.app.PendingIntent
import android.content.res.Configuration
import android.graphics.drawable.BitmapDrawable
import android.view.MotionEvent
import android.view.animation.LinearInterpolator
import com.example.wongki.commonlib.utils.UIAdapterUtils
import com.example.wongki.commonlib.utils.UIScaleHelper
import com.example.wongki.modularization.other.Study
import com.example.wongki.viewtouch.TouchTest
import com.wongki.work.OCRCore
import com.wongki.work.OCRWorkListener
import com.taobao.sophix.SophixManager


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Study.test()
        //Study.test1()

        Study.testQuickSort()
        // ScreenAdapter.adapter(this, 320, false)
        PluginManager.init(this)
        setContentView(R.layout.activity_main)

        UIAdapterUtils.adapterForParentLinerLayout(openLogin)
        UIAdapterUtils.adapterForParentLinerLayout(startKeep)
        InterfaceManager.addFunction(object : HasParamHasReturnFunction<String, String>("haParamHasReturnMethod") {
            override fun invoke(param: String): String {
                return "params:$param ,哈哈"
            }

        })

        val result = InterfaceManager.executeHasParamHasReturn<String>("haParamHasReturnMethod", "Maintivity", String::class.java)
        Log.e("TEST", "result:$result")


        HttpHelper.post("url", HashMap(), object : HttpCallBack<TestHttpBean>() {
            override fun onSuccess(t: TestHttpBean) {
                Log.e("TEST", "t = $t  10/3f:${10 / 3f}")
            }

            override fun onFailure(errCode: Int) {
            }

        })
//        Handler().postDelayed({
//            LogGrep.get(this@MainActivity) //抓取logcat日志
//        }, 2000)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            TouchTest.test(ev.x, ev.y)
        }
        return super.dispatchTouchEvent(ev)
    }

    fun openLogin(view: View) {
        ProviderServices.loginService?.login(this)

    }

    fun openEventTest(view: View) {
        val intent = Intent()
        intent.setClass(this, FirstActivity::class.java)
        startActivity(intent)
    }

    fun openAOPTest(view: View) {
        val intent = Intent()
        intent.setClass(this, TestAopActivity::class.java)
        startActivity(intent)
    }

    fun loadPluginActivity(view: View) {
        val downFile = File(filesDir, "plugintest.apk")
        if (!downFile.exists()) {
            val apkName = "pluginapp-debug.apk"
            PluginManager.downPluginApk(apkName, downFile)
            Toast.makeText(this, "下载成功", Toast.LENGTH_LONG)
        }

        val apkPath = downFile.absolutePath
        val bundle = Bundle()
        val className = "com.example.wongki.pluginapp.PluginActivity"
        PluginManager
                .loadPluginApkByPath(apkPath)// 加载插件apk
                ?.launchActivity(this, className, bundle)// 打开插件apk中的PluginActivity

    }


    fun startAnimator(view: View) {
        val imageView = iv
//        val path = AnimatorPath()
//        path.moveTo(0f, 0f)
//        path.cubicTo(1000f, 0f, 200f, -400f, 700f, 440f)
//        path.lineTo(0f, 0f)
//        path.start(imageView, 10000)
//        val animator = MyObjectAnimator.ofFloat(imageView, "translationX", 0f, 100f,400f)
//        animator.setInterpolator(LinearInterpolator())
//        animator.setDuration(5000L)
//        animator.start()
        val animator = ObjectAnimator.ofFloat(imageView, "translationX", 0f, 100f, 400f)
        animator
        animator.setInterpolator(LinearInterpolator())
        animator.setDuration(5000L)
        animator.start()

    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        UIScaleHelper.onConfigChange(this)
    }

    fun startFireworks(view: View) {
        fireworks.start(50, 50)
    }

    fun startHotFixTest(view: View) {

        val intent = Intent()
        intent.setClass(this, TestHotFixMainActivity::class.java)
        startActivity(intent)
    }

    fun openGlide(view: View) {

        val intent = Intent()
        intent.setClass(this, GlideTestActivity::class.java)
        startActivity(intent)
    }


    fun openTestJNI(view: View) {
        val stringFromJNI = Main().stringFromJNI()
        Toast.makeText(this, stringFromJNI, Toast.LENGTH_LONG).show()
    }

    fun openBug(view: View) {
        val test = SpohixTest().test()
        Toast.makeText(this, test, Toast.LENGTH_LONG).show()
    }

    fun openSophix(view: View) {
//        val dir = application.getDir("sophix", Context.MODE_PRIVATE)
//        if (!dir.exists()) {
//            dir.mkdirs()
//        }
//        val file = File(dir, "fix.dex")
//        if (file.exists()) {
//            SopHixHelper().fix(this, file.absolutePath)
//        } else {
//            Toast.makeText(this, "不存在补丁", Toast.LENGTH_LONG).show()
//
//        }
        SophixManager.getInstance().queryAndLoadNewPatch()
    }

    fun startKeep(view: View) {
        KeepHelper.open(this)
    }

    fun stopKeep(view: View) {
        KeepHelper.close(this)
    }

    fun openCustomView(view: View) {
        val intent = Intent(this, CustomViewActivity::class.java)
        startActivity(intent)
    }

    fun showNotification(view: View) {
        val mNotifyManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //获取PendingIntent
        val mainIntent = Intent(this, MainActivity::class.java)
        val mainPendingIntent = PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        //创建 Notification.Builder 对象
        val builder = NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.pp)
                //点击通知后自动清除
                .setAutoCancel(true)
                .setContentTitle("我是带Action的Notification")
                .setContentText("点我会打开MainActivity")
                .setContentIntent(mainPendingIntent)
        //发送通知
//        mNotifyManager.notify(3, builder.build())
    }

    fun openSmallRedBook(view: View) {
        val intent = Intent(this, SmallRedBookActivity::class.java)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            ILoginProviderService.REQUEST_CODE -> {
                when (resultCode) {
                    ILoginProviderService.LOGIN_SUCCESS -> {
                        findViewById<TextView>(R.id.tv_result).setText("result：登录成功")
                    }
                }
            }
        }
    }
}