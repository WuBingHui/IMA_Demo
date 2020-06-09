package com.anthony.ima_demo.aotter.impression.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anthony.ima_demo.R
import com.anthony.ima_demo.aotter.impression.ImpressionRequester
import com.anthony.ima_demo.aotter.impression.manager.ImpressionManager

class ImpressionAdapter : RecyclerView.Adapter<ImpressionAdapter.ViewHolder>() {

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

        val impressionRequester =
            ImpressionRequester()
                .setVisibleRangePercent(80)
                .setStopVisibleRangeSeconds(5)

        holder.itemView.tag = position

        ImpressionManager()
            .with(holder.itemView)
            .impressionRequester(impressionRequester)
            .apply()


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textView: TextView = itemView.findViewById(R.id.textView)

        fun bind(item: String) {

            textView.text = item
        }


    }

}
