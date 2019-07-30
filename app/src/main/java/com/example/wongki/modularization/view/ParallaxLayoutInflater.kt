package com.example.wongki.modularization.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.example.wongki.modularization.R
import com.example.wongki.modularization.bean.ParallaxViewTag

class ParallaxLayoutInflater : LayoutInflater {
    constructor(original: LayoutInflater?, newContext: Context?) : super(original, newContext) {
        setupFactory()
    }

    private fun setupFactory() {
        if (factory2 !is ParallaxLayoutInflaterFactory) {
            factory2 = ParallaxLayoutInflaterFactory(this, factory)
        }

    }

    override fun cloneInContext(newContext: Context) = ParallaxLayoutInflater(this, newContext)


    class ParallaxLayoutInflaterFactory : LayoutInflater.Factory2 {
        private val factory: LayoutInflater.Factory?
        private val mInflater: ParallaxLayoutInflater
        private val sClassPrefixList = arrayOf("android.widget.", "android.webkit.", "android.view.")
        override fun onCreateView(parent: View?, name: String, context: Context?, attrs: AttributeSet?): View? {
            return onCreateView(name, context, attrs)
        }

        override fun onCreateView(name: String, context: Context?, attrs: AttributeSet?): View? {
            var view: View? = null
            if (context is LayoutInflater.Factory) {
                view = context.onCreateView(name, context, attrs)
            }
            if (factory != null && view == null) {
                view = factory.onCreateView(name, context, attrs)
            }

            if (view == null) {
                view = createViewOrFailQuietly(name, context, attrs)
            }

            if (view != null) {
                onViewCreated(view, context, attrs)
            }

            return view
        }

        @SuppressLint("ResourceType")
        private fun onViewCreated(view: View, context: Context?, attrs: AttributeSet?) {
            val attrIds = intArrayOf(R.attr.a_in, R.attr.a_out, R.attr.x_in, R.attr.x_out, R.attr.y_in, R.attr.y_out)
            val obtainStyledAttributes = context?.obtainStyledAttributes(attrs, attrIds)
            if (obtainStyledAttributes != null) {
                if (obtainStyledAttributes.length() > 0) {
                    val tag = ParallaxViewTag()
                    tag.alphaIn = obtainStyledAttributes.getFloat(0, 0f)
                    tag.alphaOut = obtainStyledAttributes.getFloat(1, 0f)
                    tag.xIn = obtainStyledAttributes.getFloat(2, 0f)
                    tag.xOut = obtainStyledAttributes.getFloat(3, 0f)
                    tag.yIn = obtainStyledAttributes.getFloat(4, 0f)
                    tag.yOut = obtainStyledAttributes.getFloat(5, 0f)
                    view.setTag(R.id.parallax_view_tag, tag)
                }
                obtainStyledAttributes.recycle()
            }

        }

        private fun createViewOrFailQuietly(name: String, context: Context?, attrs: AttributeSet?): View? {
            if (name.contains(".")) {
                return createViewOrFailQuietly(name, null, context, attrs)
            }

            for (prefix in sClassPrefixList) {
                val view = createViewOrFailQuietly(name, prefix, context, attrs)

                if (view != null) {
                    return view
                }
            }

            return null
        }

        private fun createViewOrFailQuietly(name: String, prefix: String?, context: Context?,
                                            attrs: AttributeSet?): View? {
            try {
                return mInflater.createView(name, prefix, attrs)
            } catch (ignore: Exception) {
                return null
            }

        }

        constructor(inflater: ParallaxLayoutInflater, factory: Factory?) {
            mInflater = inflater
            this.factory = factory
        }

    }
}