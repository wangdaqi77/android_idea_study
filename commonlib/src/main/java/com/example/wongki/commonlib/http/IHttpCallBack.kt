package com.example.wongki.commonlib.http

interface IHttpCallBack{
  fun   onSuccess(str:String)

    fun onFailure(errCode:Int)
}