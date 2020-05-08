package com.anthony.ima_demo

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.net.Uri
import android.os.Build
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
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var player: SimpleExoPlayer? = null
    private var adsLoader: ImaAdsLoader? = null
    private var adsMediaSource: AdsMediaSource? = null

    private var audioManager: AudioManager? = null

    private var audioAttributes: AudioAttributes? = null

    private var audioFocusRequest: AudioFocusRequest? = null

    private var focusRequest: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializePlayer()

        audioManager = this.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        getAbandonAudioFocus()

        button.setOnClickListener {

            getAbandonAudioFocus()

        }

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
        adsLoader = ImaAdsLoader(this, Uri.parse(getString(R.string.ad_tag_url)))

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
        adsMediaSource =
            AdsMediaSource(mediaSource, dataSourceFactory, adsLoader, playerView)


        // Prepare the content and ad to be played with the SimpleExoPlayer.
        player?.prepare(adsMediaSource!!)


        // Set PlayWhenReady. If true, content and ads will autoPlay.
//        player?.playWhenReady = false

    }


    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT <= 23 || player == null) {
            if (playerView != null) {
                playerView.onResume()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            if (playerView != null) {
                playerView.onPause()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            if (playerView != null) {
                playerView.onPause()
            }
            releasePlayer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        adsLoader?.release()
    }

    private val adsLoaderListener = object : VideoAdPlayer.VideoAdPlayerCallback {

        override fun onVolumeChanged(p0: Int) {


        }

        override fun onResume() {


        }

        override fun onPause() {

//            releasePlayer()
//
//            initializePlayer()

            releaseAbandonAudioFocus()

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

    private val audioFocusChangeListener = AudioManager.OnAudioFocusChangeListener {

        when (it) {

            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT -> {

                player?.isPlayingAd?.let {
                    if (it) {
                        Log.e("FocusChangeListener", "AUDIOFOCUS_GAIN_TRANSIENT")
                        player?.playWhenReady = false

                    }
                }

            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {

                player?.isPlayingAd?.let {
                    if (it) {
                        Log.e("FocusChangeListener", "AUDIOFOCUS_LOSS_TRANSIENT")
                        player?.playWhenReady = true
                        releaseAbandonAudioFocus()
                    }
                }

            }

        }


    }

    private fun releaseAbandonAudioFocus() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            audioFocusRequest?.let {
                audioManager?.abandonAudioFocusRequest(it)
            }

        } else {
            audioManager?.abandonAudioFocus(audioFocusChangeListener)
        }

    }

    private fun getAbandonAudioFocus() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioAttributes =
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            audioAttributes?.let {
                audioFocusRequest =
                    AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                        .setAudioAttributes(it)
                        .setAcceptsDelayedFocusGain(true)
                        .setOnAudioFocusChangeListener(audioFocusChangeListener)
                        .build()
            }

            audioFocusRequest?.let {
                focusRequest = audioManager?.requestAudioFocus(it)
            }
        } else {

            focusRequest = audioManager?.requestAudioFocus(
                audioFocusChangeListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
            )

        }






        when (focusRequest) {

            AudioManager.AUDIOFOCUS_REQUEST_FAILED -> {

                Log.e("focusRequest", "AUDIOFOCUS_REQUEST_FAILED")

            }

            AudioManager.AUDIOFOCUS_REQUEST_GRANTED -> {

                Log.e("focusRequest", "AUDIOFOCUS_REQUEST_GRANTED")

                player?.playWhenReady = true

            }

        }

    }

}
