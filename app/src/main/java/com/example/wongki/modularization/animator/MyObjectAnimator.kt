package com.example.wongki.modularization.animator

import android.util.Log
import android.view.animation.Interpolator
import java.lang.ref.WeakReference
import java.lang.reflect.Method

class MyObjectAnimator : Runnable {
    override fun run() {
            mAnimStartTime = mAnimStartTime+16

        val target = mTarget.get()
        val method = mMethod
        if (target!=null&&method!=null){
            val duration = mAnimStartTime
            var fraction = duration/ mDuration.toFloat()
            Log.e("MyObjectAnimator","fraction$fraction")

            if (fraction>=1f) {
                mAnimStartTime = -1
                removeListeners(this)
                fraction = 1f
            }

            fraction = mInterpolator?.getInterpolation(fraction) ?:fraction

            val value = mPropertyValuesHolder.getValueByFraction(fraction)
            Log.e("MyObjectAnimator","value$value")
            method.invoke(target,value)

        }
    }

    companion object {
        private val listeners = HashSet<Runnable>()

        init {
            Thread {
                while (true){
                    Thread.sleep(16)
                    dispatch()
                }
            }.start()
        }

        private fun dispatch() {
            val iterator = listeners.iterator()
            while (iterator.hasNext()) {
                iterator.next().run()
            }
        }

        fun addListeners(listener:Runnable) {
            listeners.add(listener)
        }


        fun removeListeners(listener:Runnable) {
            listeners.remove(listener)
        }


        fun ofFloat(target: Any, propertyName: String, vararg values: Float): MyObjectAnimator {
            val anim = MyObjectAnimator(target, propertyName)
            anim.setFloatValues(*values)
            return anim
        }

    }

    constructor(target: Any, propertyName: String) {
        setTarget(target)
        setPropertyName(propertyName)
    }


    private lateinit var mPropertyValuesHolder: MyPropertyValuesHolder
    private lateinit var mPropertyName: String
    private var mMethod: Method? = null
    private var mAnimStartTime= -1L
    private lateinit var mTarget: WeakReference<Any?>

    private fun setFloatValues(vararg values: Float) {
        setValues(MyPropertyValuesHolder.ofFloat(mPropertyName, *values))
    }

    private fun setValues(propertyValuesHolder: MyPropertyValuesHolder) {
        this.mPropertyValuesHolder = propertyValuesHolder
    }

    fun start() {
        // 取消上一个
        mAnimStartTime = -1
        mMethod = findMethodByPropertyName()
        addListeners(this)
    }

    private fun findMethodByPropertyName(): Method? {
        val target = mTarget.get()
        if (target!=null) {
            val clazz =target::class.java
            val methodName = "set${mPropertyName.get(0).toUpperCase()}${mPropertyName.substring(1)}"
            return clazz.getMethod(methodName,Float::class.java)
        }
        return null
    }

    fun setTarget(target: Any) {
//        val oldTarget = getTarget()
//        if (oldTarget !== target) {
//            if (isStarted()) {
//                cancel()
//            }
//            mTarget = if (target == null) null else WeakReference(target)
//            // New target should cause re-initialization prior to starting
//            mInitialized = false
//        }
        mTarget = WeakReference(target)
    }


    fun setPropertyName(propertyName: String) {
        // mValues could be null if this is being constructed piecemeal. Just record the
        // propertyName to be used later when setValues() is called if so.
//        if (mValues != null) {
//            val valuesHolder = mValues[0]
//            val oldName = valuesHolder.getPropertyName()
//            valuesHolder.setPropertyName(propertyName)
//            mValuesMap.remove(oldName)
//            mValuesMap.put(propertyName, valuesHolder)
//        }
//        mPropertyName = propertyName
//        // New property/values/target should cause re-initialization prior to starting
//        mInitialized = false
        mPropertyName = propertyName
    }

    var mInterpolator: Interpolator?=null
    fun setInterpolator(interpolator: Interpolator) {
        mInterpolator = interpolator
    }

    var mDuration = 0L
    fun setDuration(duration: Long) {
        mDuration = duration
    }

}