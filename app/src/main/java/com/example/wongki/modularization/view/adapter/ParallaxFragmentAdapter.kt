package com.example.wongki.modularization.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.wongki.modularization.view.ParallaxFragment

class ParallaxFragmentAdapter(fm: FragmentManager, val fragments: ArrayList<ParallaxFragment>):FragmentPagerAdapter (fm) {

    override fun getItem(position: Int): Fragment=fragments[position]

    override fun getCount()=fragments.size

}