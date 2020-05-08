package com.anthony.ima_demo

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.ads.interactivemedia.v3.api.player.VideoAdPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.ads.AdsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var player: SimpleExoPlayer? = null
    private var adsLoader: ImaAdsLoader? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    private fun releasePlayer() {

        adsLoader?.setPlayer(null)
        adsLoader?.stop()
        adsLoader?.release()
        adsLoader = null
        playerView.player = null
        player?.release()
        player = null

    }

    private fun initializePlayer() {
        // Create a SimpleExoPlayer and set is as the player for content and ads.
        player = SimpleExoPlayer.Builder(this).build()

        // Create an AdsLoader with the ad tag url.
        //這邊的tag url代表一個廣告
        adsLoader = ImaAdsLoader(this,Uri.parse(getString(R.string.ad_tag_url)))

        adsLoader?.addCallback(adsLoaderListener)

        playerView.player = player



        adsLoader?.setPlayer(player)

        val dataSourceFactory: DataSource.Factory =
            DefaultDataSourceFactory(this, Util.getUserAgent(this, getString(R.string.app_name)))

        val mediaSourceFactory =
            ProgressiveMediaSource.Factory(dataSourceFactory)

        // Create the MediaSource for the content you wish to play.
        val mediaSource: MediaSource =
            mediaSourceFactory.createMediaSource(Uri.parse("getString(R.string.content_url)"))

        // Create the AdsMediaSource using the AdsLoader and the MediaSource.
        val adsMediaSource =
            AdsMediaSource(mediaSource, dataSourceFactory, adsLoader, playerView)

        // Prepare the content and ad to be played with the SimpleExoPlayer.
        player?.prepare(adsMediaSource)


        // Set PlayWhenReady. If true, content and ads will autoPlay.
        player?.playWhenReady = true

    }


    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
            if (playerView != null) {
                playerView.onResume()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT <= 23 || player == null) {
            if (playerView != null) {
                playerView.onResume();
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            if (playerView != null) {
                playerView.onPause();
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            if (playerView != null) {
                playerView.onPause();
            }
            releasePlayer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        adsLoader?.release()
    }

    private val adsLoaderListener = object :VideoAdPlayer.VideoAdPlayerCallback{
        override fun onVolumeChanged(p0: Int) {
        }

        override fun onResume() {
        }

        override fun onPause() {


            releasePlayer()
            initializePlayer()
        }

        override fun onLoaded() {
        }

        override fun onBuffering() {
        }

        override fun onError() {
        }

        override fun onEnded() {
        }

        override fun onPlay() {
        }

    }


}
