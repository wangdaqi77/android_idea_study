package com.example.wongki.commonlib.hotfix.tinker

import android.content.Context
import com.example.wongki.commonlib.utils.Constants
import com.example.wongki.commonlib.utils.ReflectUtils
import dalvik.system.DexClassLoader
import dalvik.system.PathClassLoader
import java.io.File
import java.lang.reflect.Array

object TinkerFixDexUtils {
    val loaderDex by lazy { HashSet<File>() }
    fun loadFixedDex(context: Context) {
        val fileDir = context.getDir(Constants.DEX_DIR, Context.MODE_PRIVATE)
        if (!fileDir.exists()) {
            return
        }
        val listFiles = fileDir.listFiles()
        for (file in listFiles) {
            if (file.name.endsWith(Constants.DEX_SUFFIX) && file.name != "classes.dex") {
                loaderDex.add(file)
            }
        }

        createDexClassLoader(context, fileDir)
    }

    /**
     * @param fileDir 自己存放的dex文件目录
     */
    private fun createDexClassLoader(context: Context, fileDir: File) {
        //创建临时解压目录
        val optimizedDir = fileDir.absolutePath + File.separator + "opt_dex"
        val file = File(optimizedDir)
        if (!file.exists()) {
            file.mkdirs()
        }

        for (dex in loaderDex) {
            val myClassLoader = DexClassLoader(dex.absolutePath, optimizedDir, null, context.classLoader)
            hotFix(myClassLoader, context)

        }

    }

    private fun hotFix(myClassLoader: DexClassLoader, context: Context) {
        // 获取系统的类加载器
        val systemClassLoader = context.classLoader as PathClassLoader

        // 获取自己的 dexElements
        val myDexElements = ReflectUtils.getDexElements(ReflectUtils.getPathList(myClassLoader))

        // 获取系统的 dexElements
        val systemDexElements = ReflectUtils.getDexElements(ReflectUtils.getPathList(systemClassLoader))
        // 合并dexlist，获取最新的dexlist
        val mergeDexElements = mergeDexElements(myDexElements, systemDexElements)


        //重新对系统的pathlist的DexElements赋值
        val pathList = ReflectUtils.getPathList(systemClassLoader)
        ReflectUtils.setField(pathList, pathList.javaClass, mergeDexElements)
    }

    private fun mergeDexElements(myDexElements: Any, systemDexElements: Any): Any {
        val myLen = Array.getLength(myDexElements)
        val systemLen = Array.getLength(systemDexElements)
        val localClass = systemDexElements.javaClass.componentType
        val result = Array.newInstance(localClass, myLen + systemLen)
        for (k in 0 until myLen + systemLen) {
            if (k < myLen) {
                // 从0开始遍历，如果前数组有值，添加到新数组的第一个位置
                Array.set(result, k, Array.get(myDexElements, k))
            } else {
                // 添加完前数组，再添加后数组，合并完成
                Array.set(result, k, Array.get(systemDexElements, k - myLen))
            }
        }
        return result
    }
}