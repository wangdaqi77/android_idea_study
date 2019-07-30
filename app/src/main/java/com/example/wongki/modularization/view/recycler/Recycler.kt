package com.example.wongki.modularization.view.recycler

import android.util.SparseArray
import android.util.SparseIntArray
import android.view.View
import java.util.*

class Recycler(typeCount: Int) {
    lateinit var recycleViews: SparseArray<Stack<RecyclerView.ViewHolder?>>

    //lateinit var recycleViews :Stack<View>
    init {
        recycleViews = SparseArray(typeCount)
        for (i in 0 until typeCount) {
            recycleViews.put(i, Stack())
        }
    }

    fun recycle(itemType: Int, viewHolder: RecyclerView.ViewHolder) {
        val stack = recycleViews[itemType]
        stack.push(viewHolder)
    }

    fun get(itemType: Int): RecyclerView.ViewHolder? {

        var viewHolder: RecyclerView.ViewHolder?=null
        try {
            viewHolder = recycleViews[itemType].pop()
        } catch (e: Exception) {
        }

        return viewHolder
    }
}