package com.anthony.ima_demo.aotter.ima

import android.content.Context
import android.net.Uri
import com.anthony.ima_demo.R
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ads.AdPlaybackState
import com.google.android.exoplayer2.source.ads.AdsLoader
import java.io.IOException

class AotterAdsLoader(private val context: Context) : AdsLoader {


    private var adPlaybackState: AdPlaybackState? = null

    private var eventListener: AdsLoader.EventListener? = null


    /**
     * 開始使用 AdsLoader 進行播放，該方法在主線程中被 AdsMediaSource 調用
     */
    override fun start(
        eventListener: AdsLoader.EventListener?,
        adViewProvider: AdsLoader.AdViewProvider?
    ) {

        this.eventListener = eventListener

        if (adPlaybackState != null) {

            eventListener?.onAdPlaybackState(adPlaybackState)

            return

        }

        adPlaybackState = AdPlaybackState(
            0 * C.MICROS_PER_SECOND
        )

        adPlaybackState = adPlaybackState?.withAdCount(0, 2)

        adPlaybackState =
            adPlaybackState?.withAdUri(0, 0, Uri.parse(context.getString(R.string.ad_tag_url)))

        adPlaybackState =
            adPlaybackState?.withAdUri(0, 1, Uri.parse("https://media.w3.org/2010/05/sintel/trailer.mp4"))

        eventListener?.onAdPlaybackState(adPlaybackState)
    }

    /** 通知 AdsLoader 播放器無法给定的廣告資源，
    該方法的實現應該更新 AdPlaybackState
     */
    override fun handlePrepareError(
        adGroupIndex: Int,
        adIndexInAdGroup: Int,
        exception: IOException?
    ) {


    }


    /** 停止使用廣告加載器進行播放並註銷事件監聽 */
    override fun stop() {


    }

    /**
     * 設置廣告資源支持的影片協議，如 DASH、HLS、SS
     */
    override fun setSupportedContentTypes(vararg contentTypes: Int) {


    }


    /**  當 AdsLoader 不再被使用時，釋放該AdsLoader */
    override fun release() {


    }

    /**
     * 設置要加載廣告的播放器
     */
    override fun setPlayer(player: Player?) {




    }


}