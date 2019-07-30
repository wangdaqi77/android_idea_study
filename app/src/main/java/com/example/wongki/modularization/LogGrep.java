package com.example.wongki.modularization;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LogGrep {

    /*核心代码*/
    public static void get(Context context) {
        try {
            /*命令的准备*/
            ArrayList<String> getLog = new ArrayList<String>();
            getLog.add("logcat");
            getLog.add("-d");
            getLog.add("-t");
            getLog.add("500");
            getLog.add("-v");
            getLog.add("time");
            ArrayList<String> clearLog = new ArrayList<String>();
            clearLog.add("logcat");
            clearLog.add("-c");

            Process process = Runtime.getRuntime().exec(getLog.toArray(new String[getLog.size()]));//抓取当前的缓存日志

            BufferedReader buffRead = new BufferedReader(new InputStreamReader(process.getInputStream()));//获取输入流
            Runtime.getRuntime().exec(clearLog.toArray(new String[clearLog.size()]));//清除是为了下次抓取不会从头抓取
            String str = null;
            File logFile = new File(context.getDir("log_grep", Context.MODE_PRIVATE), System.currentTimeMillis() + "log.txt");//打开文件
            FileOutputStream fos = new FileOutputStream(logFile, true);//true表示在写的时候在文件末尾追加
            String newline = System.getProperty("line.separator");//换行的字符串
            //Date date = new Date(System.currentTimeMillis());
            //String time = format.format(date);

            //Log.i(TAG, "thread");
            while ((str = buffRead.readLine()) != null) {//循环读取每一行
                //Runtime.getRuntime().exec(clearLog.toArray(new String[clearLog.size()]));
                //Log.i(TAG, str);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-");
                Date date = new Date(System.currentTimeMillis());
                String time = format.format(date);
                fos.write((time + str).getBytes());//加上年
                fos.write(newline.getBytes());//换行
            }
            fos.close();
            Runtime.getRuntime().exec(clearLog.toArray(new String[clearLog.size()]));
        } catch (Exception e) {

        }

    }
}
