package com.anthony.ima_demo.aotter.ima

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anthony.ima_demo.R
import kotlinx.android.synthetic.main.activity_ima.*

class ImaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ima)

        aotterPlayerView.initPlayerOnlyAd(getString(R.string.ad_tag_url))

        aotterPlayerView.isRepeatAd = false

        aotterPlayerView.play()

    }
}