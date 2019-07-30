package com.example.wongki.commonlib.http

class OkHttpProcessor:IHttpProcessor {
    override fun post(url: String, params: Map<String, Any>, callBack: IHttpCallBack) {
        var resultJson = "{\"error_code\":1000, \"error_message\":\"访问成功。\"}"
        callBack.onSuccess(resultJson)
    }
}