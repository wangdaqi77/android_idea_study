package com.wongki.work.single

import android.content.Intent
import android.support.annotation.RequiresApi
import android.support.v4.app.JobIntentService

/**
 * @author  wangqi
 * date:    2019-08-17
 * email:   wangqi7676@163.com
 * desc:    由于8.0后台工作的限制，最好使用JobIntentService
 */
@RequiresApi(26)
class WorkService26:JobIntentService() {
    override fun onHandleWork(intent: Intent) {

    }

}