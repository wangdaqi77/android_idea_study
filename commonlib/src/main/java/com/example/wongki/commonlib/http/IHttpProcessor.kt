package com.example.wongki.commonlib.http


interface IHttpProcessor {
    fun post(url :String,params:Map<String,Any>,callBack: IHttpCallBack)
}