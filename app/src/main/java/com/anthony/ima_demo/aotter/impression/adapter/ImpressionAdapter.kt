package com.anthony.ima_demo.aotter.impression.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.anthony.ima_demo.R
import com.anthony.ima_demo.aotter.impression.ImpressionListener
import com.anthony.ima_demo.aotter.impression.manager.ImpressionManager
import com.anthony.ima_demo.aotter.impression.ImpressionRequest

class ImpressionAdapter(private val lifecycle: Lifecycle) : RecyclerView.Adapter<ImpressionAdapter.ViewHolder>() {

    private var impressionList = listOf<String>()

    fun update(impressionList: List<String>) {

        this.impressionList = impressionList

        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_impression,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return impressionList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(impressionList[position])


        holder.itemView.tag = position


    }

    private val impressionListener = object :ImpressionListener{
        override fun onImpressionSuccess() {

            Log.e("impressionListener","onImpressionSuccess")

        }

        override fun onViewAttachedToWindow() {
            Log.e("impressionListener","onViewAttachedToWindow")
        }

        override fun onViewDetachedFromWindow() {
            Log.e("impressionListener","onViewDetachedFromWindow")
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textView: TextView = itemView.findViewById(R.id.textView)

        fun bind(item: String) {

            textView.text = item


            if(item == "AD"){

                val impressionRequest =  ImpressionRequest()
                    .setVisibleRangePercent(70)
                    .dwellSeconds(10)

                ImpressionManager()
                    .with(itemView,lifecycle)
                    .impressionRequest(impressionRequest)
                    .impressionListener(impressionListener)
                    .apply()
            }


        }

    }

}
