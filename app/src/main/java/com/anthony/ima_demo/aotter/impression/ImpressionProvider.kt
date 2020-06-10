package com.anthony.ima_demo.aotter.impression

import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.annotation.Nullable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent


/**
 * 2020/06/08 created by Anthony Wu
 * 曝光提供者
 * 這裡會接收到 view 與 lifecycle
 * 會依照職責配發任務下去給其他類，如下
 * ImpressionListener 負責曝光的回調監聽
 * ViewVisibilityPercentageCalculator 負責計算可見百分比
 * ImpressionRequest 客製化的百分比參數與停留時間
 * ImpressionCountDownTimer 曝光計時器
 */
class ImpressionProvider(private val view: View, lifecycle: Lifecycle) : LifecycleObserver {

    private var impressionListener: ImpressionListener? = null

    private var viewVisibilityPercentageCalculator: ViewVisibilityPercentageCalculator =
        ViewVisibilityPercentageCalculator()

    private var impressionRequest =
        ImpressionRequest()

    private var impressionCountDownTimer: ImpressionCountDownTimer =
        ImpressionCountDownTimer(
            impressionRequest
            , impressionListener
        )


    fun impressionListener(@Nullable impressionListener: ImpressionListener): ImpressionProvider {

        this.impressionListener = impressionListener

        return this
    }

    fun impressionRequest(@Nullable impressionRequest: ImpressionRequest): ImpressionProvider {

        this.impressionRequest = impressionRequest

        return this
    }


    fun apply() {

        view.addOnAttachStateChangeListener(onAttachStateChangeListener)

        impressionCountDownTimer =
            ImpressionCountDownTimer(this.impressionRequest, impressionListener)

    }


    private val onAttachStateChangeListener = object : View.OnAttachStateChangeListener {
        override fun onViewDetachedFromWindow(v: View?) {
            Log.e("view", "view 消失在螢幕上")

            view.viewTreeObserver.removeOnScrollChangedListener(onScrollChangedListener)

            lifecycle.removeObserver(this@ImpressionProvider)

            impressionListener?.onViewDetachedFromWindow()

        }

        override fun onViewAttachedToWindow(v: View?) {
            Log.e("view", "view 出現在螢幕上")

            val rect = Rect()

            view.getLocalVisibleRect(rect)

            view.viewTreeObserver.addOnScrollChangedListener(onScrollChangedListener)

            lifecycle.addObserver(this@ImpressionProvider)

            impressionListener?.onViewAttachedToWindow()

        }
    }


    private val onScrollChangedListener = ViewTreeObserver.OnScrollChangedListener {

        val percents = viewVisibilityPercentageCalculator.getVisibilityPercents(view)

        impressionCountDownTimer.checkPercent(percents)

        Log.e("Scroll", "${view.tag}-${percents}%")
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun pause() {

        Log.e("Lifecycle", "pause")

        impressionCountDownTimer.stop()

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun resume() {

        Log.e("Lifecycle", "resume")

        val percents = viewVisibilityPercentageCalculator.getVisibilityPercents(view)

        impressionCountDownTimer.checkPercent(percents)

    }

}