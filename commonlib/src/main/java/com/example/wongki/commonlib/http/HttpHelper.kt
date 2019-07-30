package com.example.wongki.commonlib.http

object HttpHelper:IHttpProcessor {

    private var mHttpProcessor: IHttpProcessor? = null
    fun init(httpProcessor: IHttpProcessor) {
        this.mHttpProcessor = httpProcessor
    }

    override fun post(url: String, params: Map<String, Any>, callBack: IHttpCallBack) {
        if (mHttpProcessor == null) {
            throw RuntimeException("please call init() before call post() ")
        }

        mHttpProcessor?.post(url, params, callBack)
    }

}