package com.example.wongki.modularization

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.wongki.modularization.view.recycler.RecyclerView
import kotlinx.android.synthetic.main.activity_custom_recycler_view.*

class CustomRecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_recycler_view)

        val list = ArrayList<String>()
        for (i in 0..1000) {
            list.add( "这是第${i}个条目")
        }
        recyclerView.setAdapter(object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
                val textView = TextView(parent.context)
                textView.setBackgroundColor(Color.RED)
                return TestViewHolder(textView)
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val item = list.get(position)
                val textView = holder.mView as TextView
                textView.setText(item)
            }

            override fun getItemCount(): Int = list.size

            override fun getTypesCount(): Int = 1
            override fun getItemHeight(): Int = 200

        })
    }



    class TestViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

}
