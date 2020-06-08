package com.anthony.ima_demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anthony.ima_demo.aotter.ima.ImaActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()


    }

    private fun initView(){


        imaBtn.setOnClickListener {

            val intent = Intent()
            intent.setClass(this, ImaActivity::class.java)
            startActivity(intent)

        }


    }

}
