package com.example.wongki.commonlib.utils

import dalvik.system.BaseDexClassLoader

object ReflectUtils {
    /**
     * 通过反射获取BaseDexClassLoader对象中的PathList对象
     *
     * @param baseDexClassLoader BaseDexClassLoader对象
     * @return PathList对象
     */
    @Throws(NoSuchFieldException::class, IllegalAccessException::class, IllegalArgumentException::class, ClassNotFoundException::class)
    fun getPathList(baseDexClassLoader: Any): Any {
        return getField(baseDexClassLoader, Class.forName("dalvik.system.BaseDexClassLoader"), "pathList")
    }

    /**
     * 通过反射获取BaseDexClassLoader对象中的PathList对象，再获取dexElements对象
     *
     * @param paramObject PathList对象
     * @return dexElements对象
     */
    @Throws(NoSuchFieldException::class, IllegalAccessException::class, IllegalArgumentException::class)
    fun getDexElements(paramObject: Any): Any {
        return getField(paramObject, paramObject.javaClass, "dexElements")
    }


    @Throws(NoSuchFieldException::class, IllegalAccessException::class, IllegalArgumentException::class)
    private fun getField(obj: Any, clazz: Class<*>, field: String): Any {
        val localField = clazz.getDeclaredField(field)
        localField.isAccessible = true
        return localField.get(obj)
    }

    /**
     * 给某属性赋值，并设置私有可访问
     *
     * @param obj   该属性所属类的对象
     * @param clazz 该属性所属类
     * @param value 值
     */
    @Throws(NoSuchFieldException::class, IllegalAccessException::class, IllegalArgumentException::class)
    fun setField(obj: Any, clazz: Class<*>, value: Any) {
        val localField = clazz.getDeclaredField("dexElements")
        localField.isAccessible = true
        localField.set(obj, value)
    }
}
