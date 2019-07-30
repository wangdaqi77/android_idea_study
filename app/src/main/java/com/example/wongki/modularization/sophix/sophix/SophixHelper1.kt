package com.example.wongki.modularization.sophix.sophix

import android.content.Context
import dalvik.system.DexFile

object SophixHelper1 {
    fun fix(context :Context, fixClassName:String, dexPath:String){
        val dexSophixFile = context.getDir("dex_sophix", Context.MODE_PRIVATE)
        if (!dexSophixFile.exists()){
            dexSophixFile.mkdirs()
        }
        val dexFile = DexFile.loadDex(dexPath, dexSophixFile.absolutePath, 0)
        val classLoad = object :ClassLoader(){
            override fun loadClass(name: String?): Class<*> {
                return super.loadClass(name)
            }
        }
        val fixClass = dexFile.loadClass(fixClassName, classLoad)
        val methods = fixClass.methods
        for (method in methods) {
            val annotation = method.getAnnotation<Sophix>(Sophix::class.java)
            if (annotation!=null) {
                val className = annotation.className
                val methodName = annotation.methodName

                val bugClass = context.classLoader.loadClass(className)
                val bugMethod = bugClass?.getMethod(methodName)
                if (bugMethod!=null) {
                    //native替换


                }

            }

        }


    }

}