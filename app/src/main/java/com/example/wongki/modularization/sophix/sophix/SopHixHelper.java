package com.example.wongki.modularization.sophix.sophix;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Enumeration;

import dalvik.system.DexFile;

public class SopHixHelper {
    public SopHixHelper() {

    }

    static {
        System.loadLibrary("native-fix");
    }

    /**
     *
     * @param context
     * @param dexPath 下载好的修复包的dex文件绝对路径
     */
    public void fix(Context context, String dexPath) {

        File dexSophixOptDir = context.getDir("dex_sophix", Context.MODE_PRIVATE);
        if (!dexSophixOptDir.exists()) {
            dexSophixOptDir.mkdirs ();
        }
        File dexSophixFile =new  File(dexSophixOptDir,"sophix.dex");
        if (dexSophixFile.exists()) {
            dexSophixFile.delete();
        }
        try {
            final DexFile dexFile = DexFile.loadDex(dexPath, dexSophixFile.getAbsolutePath(), 0);
            Enumeration<String> entries = dexFile.entries();
            ClassLoader classLoad = new ClassLoader() {
                public Class loadClass(String name) throws ClassNotFoundException {
                    Class aClass = dexFile.loadClass(name, this);
                    // bug类最终继承Object，补丁dex不存在Object，找不到的情况使用class.forName
                    if (aClass==null) {
                        aClass = Class.forName(name);
                    }
                    return aClass;
                }
            };
            while (entries.hasMoreElements()) {
                String s = entries.nextElement();
                Class<?> klass = classLoad.loadClass(s);
                Method[] methods = klass.getMethods();

                for (Method method : methods) {
                    Annotation annotation = method.getAnnotation(Sophix.class);
                    if (annotation != null) {
                        String className = ((Sophix) annotation).className();
                        String methodName = ((Sophix) annotation).methodName();

                        Class bugClass = Class.forName(className);
                        Method bugMethod = bugClass.getMethod(methodName);
                        if (bugMethod != null) {
                            //native替换
                            replace(method, bugMethod);
                        }

                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


    }

    public native static void replace(Method newMethod, Method bugMethod);
}