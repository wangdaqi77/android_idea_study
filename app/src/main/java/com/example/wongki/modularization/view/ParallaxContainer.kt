package com.example.wongki.modularization.view

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.wongki.modularization.R
import com.example.wongki.modularization.bean.ParallaxViewTag
import com.example.wongki.modularization.view.adapter.ParallaxFragmentAdapter

class ParallaxContainer : FrameLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    val fragments: ArrayList<ParallaxFragment> = ArrayList()
    val fragmentLayoutIds = arrayListOf<Int>(
            R.layout.view_intro_1,
            R.layout.view_intro_2,
            R.layout.view_intro_3,
            R.layout.view_intro_4,
            R.layout.view_intro_5,
            R.layout.view_intro_6,
            R.layout.view_login
    )

    fun init(fragmentManager: FragmentManager) {
        if (fragments.size == 0) {
            for (fragmentLayoutId in fragmentLayoutIds) {
                val parallaxFragment = ParallaxFragment()
                val bundle = Bundle()
                bundle.putInt("layout_id", fragmentLayoutId)
                parallaxFragment.arguments = bundle
                fragments.add(parallaxFragment)
            }

            val viewPager = ViewPager(context)
            viewPager.id = R.id.parallax_pager
            viewPager.adapter = ParallaxFragmentAdapter(fragmentManager, fragments)
            val layoutParams = FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            addView(viewPager, 0, layoutParams)
            setEvent(viewPager)
        }
    }
    private fun setEvent(viewPager: ViewPager) {


        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                Log.e("TAG", "position:$position  positionOffset:$positionOffset  positionOffsetPixels:$positionOffsetPixels")

                try {
                    val outLayoutView = fragments[position].getLayoutView()
                    viewOut(outLayoutView, positionOffset, positionOffsetPixels)
                } catch (e: Exception) {
                }

                try {
                    val inLayoutView = fragments[position - 1].getLayoutView()
                    viewIn(inLayoutView, positionOffset, positionOffsetPixels)
                } catch (e: Exception) {
                }


            }

            override fun onPageSelected(position: Int) {
            }

        })
    }

    private fun viewIn(view: View?, positionOffset: Float, positionOffsetPixels: Int) {
        if (view == null) return
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                viewIn(view.getChildAt(i), positionOffset, positionOffsetPixels)
            }
        } else {
            val tag = view.getTag(R.id.parallax_view_tag)
            if (tag is ParallaxViewTag) {
                val x = view.x
                val y = view.y
                val alpha = view.alpha
                val xIn = tag.xIn
                val yIn = tag.yIn
                val alphaIn = tag.alphaIn
                val containerWidth = positionOffsetPixels / positionOffset
                val surplusOffsetPixels = positionOffsetPixels / positionOffset - positionOffsetPixels
                view.translationX = surplusOffsetPixels * xIn
                view.translationY = (0 - surplusOffsetPixels) * yIn
                view.alpha = 1.0f - surplusOffsetPixels * tag.alphaIn / containerWidth
            }
        }
    }

    private fun viewOut(view: View?, positionOffset: Float, positionOffsetPixels: Int) {
        if (view == null) return
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                viewOut(view.getChildAt(i), positionOffset, positionOffsetPixels)
            }
        } else {
            val tag = view.getTag(R.id.parallax_view_tag)
            if (tag is ParallaxViewTag) {
                val x = view.x
                val y = view.y
                val alpha = view.alpha
                val xOut = tag.xOut
                val yOut = tag.yOut
                val alphaOut = tag.alphaOut
                val containerWidth = positionOffsetPixels / positionOffset
                //  val surplusOffsetPixels = positionOffsetPixels / positionOffset * (1 - positionOffset)
                view.translationX = 0 - positionOffsetPixels * xOut
                view.translationY = 0 - positionOffsetPixels * yOut
                view.alpha = 1.0f - positionOffsetPixels * tag.alphaIn / containerWidth

            }
        }
    }

}