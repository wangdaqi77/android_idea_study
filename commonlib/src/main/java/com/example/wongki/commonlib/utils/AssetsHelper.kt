package com.example.wongki.commonlib.utils

import android.content.Context
import java.io.File
import java.io.FileOutputStream

object AssetsHelper {
    /**
     * 从assets目录中复制整个文件夹内容
     * @param  context  Context 使用CopyFiles类的Activity
     * @param  oldPath  String  原文件路径  如：/aa
     * @param  newPath  String  复制后路径  如：xx:/bb/cc
     */
    fun copyFilesFassets(context: Context, oldPath: String, newPath: String) {
        try {
            val fileNames = context.assets.list(oldPath)//获取assets目录下的所有文件及目录名
            if (fileNames.isNotEmpty()) {//如果是目录
                val file = File(newPath)
                file.mkdirs()//如果文件夹不存在，则递归
                for (fileName in fileNames) {
                    copyFilesFassets(context, "$oldPath/$fileName", "$newPath/$fileName")
                }
            } else {//如果是文件
                val `is` = context.assets.open(oldPath)
                val fos = FileOutputStream(File(newPath))
                val buffer = ByteArray(1024 * 8)
                var byteCount = 0
                while (`is`.read(buffer).also { byteCount = it } != -1) {//循环从输入流读取 buffer字节
                    fos.write(buffer, 0, byteCount)//将读取的输入流写入到输出流
                }
                fos.flush()//刷新缓冲区
                `is`.close()
                fos.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}