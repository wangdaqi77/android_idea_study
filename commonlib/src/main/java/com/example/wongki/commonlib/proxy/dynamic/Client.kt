package com.example.wongki.commonlib.proxy.dynamic

class Client {
    companion object {
        fun test(){
            val proxy = ProxySubject(RealSubject()).bind()
            proxy.doSomething()
        }
    }
}