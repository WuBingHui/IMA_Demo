package com.anthony.ima_demo.aotter.impression.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.anthony.ima_demo.R
import com.anthony.ima_demo.aotter.impression.adapter.ImpressionAdapter
import kotlinx.android.synthetic.main.activity_impression.*

class ImpressionActivity : AppCompatActivity() {

    private var impressionAdapter: ImpressionAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_impression)

        initView()

    }

    private fun initView(){

        impressionAdapter = ImpressionAdapter(lifecycle)

        val linearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        impressionRecyclerView.layoutManager = linearLayoutManager

        impressionRecyclerView.adapter = impressionAdapter

        val list = mutableListOf<String>()

        list.add("asdsadsa")
        list.add("adasdsa")
        list.add("dsadsaasd")
        list.add("343254")
        list.add("AD")
        list.add("dsadasdad")
        list.add("AD")
        list.add("gfgdfgfd")
        list.add("AD")
        list.add("qqqqqq")

        impressionAdapter?.update(list)


    }

}