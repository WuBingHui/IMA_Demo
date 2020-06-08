package com.anthony.ima_demo.aotter.ima

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.NonNull
import com.anthony.ima_demo.R
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.ads.AdsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.Util

class AotterPlayerView constructor(context: Context, attrs: AttributeSet?) :
    PlayerView(context, attrs) {


    private var adsLoader: ImaAdsLoader? = null

    private var playerController: SimpleExoPlayer? = null

    private var dataSourceFactory: DataSource.Factory? = null

    private var mediaSourceFactory: ProgressiveMediaSource.Factory? = null

    private var audioManager: AudioManager? = null

    private lateinit var audioAttributes: AudioAttributes

    private lateinit var audioFocusRequest: AudioFocusRequest

    private var focusRequest = -1

    private var onlyAd = false

    var isRepeatAd = false

    private var adUrl =""

    init {

        audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        initView()

    }

    private fun initView() {

        // Create a SimpleExoPlayer and set is as the player for content and ads.
        playerController = SimpleExoPlayer.Builder(context).build()

        dataSourceFactory =
            DefaultDataSourceFactory(
                context,
                Util.getUserAgent(context, context.getString(R.string.app_name))
            )

        mediaSourceFactory =
            ProgressiveMediaSource.Factory(dataSourceFactory)


    }

    fun initPlayerOnlyAd(@NonNull adUrl: String) {

        onlyAd = true

        this.adUrl = adUrl

        this.player = playerController

        this.adsLoader = ImaAdsLoader(context, Uri.parse(adUrl))

        this.adsLoader?.setPlayer(playerController)

        val mediaSource: MediaSource =
            mediaSourceFactory?.createMediaSource(Uri.parse(""))!!

        val adsMediaSource =
            AdsMediaSource(mediaSource, dataSourceFactory, this.adsLoader, this)

        playerController?.prepare(adsMediaSource)

        playerController?.addListener(playerEventListener)

        getAbandonAudioFocus()

    }


    private fun getAbandonAudioFocus() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            audioAttributes =
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()

            audioFocusRequest =
                AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                    .setAudioAttributes(audioAttributes)
                    .setAcceptsDelayedFocusGain(true)
                    .setOnAudioFocusChangeListener(audioFocusChangeListener)
                    .build()
        }

    }

    private fun requestAudioFocus() {

        focusRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            audioManager?.requestAudioFocus(audioFocusRequest)!!

        } else {

            audioManager?.requestAudioFocus(
                audioFocusChangeListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
            )!!

        }

    }

    fun play() {

        requestAudioFocus()

        when (focusRequest) {

            AudioManager.AUDIOFOCUS_REQUEST_GRANTED -> {
                if (!playerController?.isPlaying!!) {
                    playerController?.playWhenReady = true
                }
            }

            AudioManager.AUDIOFOCUS_REQUEST_DELAYED -> {

            }

            AudioManager.AUDIOFOCUS_REQUEST_FAILED -> {

            }

        }

    }


    private val audioFocusChangeListener = AudioManager.OnAudioFocusChangeListener {

        when (it) {

            AudioManager.AUDIOFOCUS_GAIN -> {

                Log.e("FocusChangeListener", "AUDIOFOCUS_GAIN")

                play()

            }
            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT -> {


                Log.e("FocusChangeListener", "AUDIOFOCUS_GAIN_TRANSIENT")

                play()

            }
            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE -> {
                Log.e("FocusChangeListener", "AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE")

            }
            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK -> {
                Log.e("FocusChangeListener", "AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK")

            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                Log.e("FocusChangeListener", "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK")

            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {

                Log.e("FocusChangeListener", "AUDIOFOCUS_LOSS_TRANSIENT")

                pause()
            }
            AudioManager.AUDIOFOCUS_LOSS -> {
                Log.e("FocusChangeListener", "AUDIOFOCUS_LOSS")

                //這邊的CallBack代表第三方的app請求的是長時間焦點,且不會再回調AUDIOFOCUS_GAIN恢復
                //如果要恢復播放必須使用者有明確的操作
                //ex.按下播放扭
                pause()

                releaseAbandonAudioFocus()

            }

        }

    }


    fun releasePlayer() {

        adsLoader?.setPlayer(null)
        adsLoader?.stop()
        adsLoader?.release()
        adsLoader = null
        this.player = null
        playerController?.removeListener(playerEventListener)
        playerController?.release()
        playerController = null

    }

    private fun releaseAbandonAudioFocus() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            audioManager?.abandonAudioFocusRequest(audioFocusRequest)

        } else {
            audioManager?.abandonAudioFocus(audioFocusChangeListener)
        }

    }

    private fun pause() {

        if (playerController?.isPlaying!!) {
            playerController?.playWhenReady = false
        }

    }

    private val playerEventListener = object : Player.EventListener {
        override fun onPlayerError(error: ExoPlaybackException) {
            releaseAbandonAudioFocus()
        }

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {

            //加載錯誤要釋放掉音頻焦點
            if (playbackState == ExoPlayer.STATE_IDLE) {

                releaseAbandonAudioFocus()

                if(onlyAd){
                    if(isRepeatAd){
                        releasePlayer()

                        initView()

                        initPlayerOnlyAd(adUrl)

                        play()
                    }
                }

            }
            //播放完畢也要釋放掉音頻焦點
            if (playbackState == ExoPlayer.STATE_ENDED) {
                releaseAbandonAudioFocus()
            }

        }
    }

}