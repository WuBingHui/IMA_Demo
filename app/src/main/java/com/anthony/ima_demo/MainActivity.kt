package com.anthony.ima_demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        aotterPlayerView.initPlayerOnlyAd(getString(R.string.ad_tag_url))

        aotterPlayerView.isRepeatAd = false

        aotterPlayerView.play()

    }


}
