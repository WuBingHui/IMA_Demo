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
 */
class ImpressionProvider(private val view: View, lifecycle: Lifecycle): LifecycleObserver {

    private var viewVisibilityPercentageCalculator: ViewVisibilityPercentageCalculator =
        ViewVisibilityPercentageCalculator()

    private var impressionRequest =
        ImpressionRequest()

    private var impressionCountDownTimer: ImpressionCountDownTimer =
        ImpressionCountDownTimer(
            impressionRequest
        )

    init {

        lifecycle.addObserver(this)

    }

    fun impressionRequest(@Nullable impressionRequest: ImpressionRequest): ImpressionProvider {

        this.impressionRequest = impressionRequest

        impressionCountDownTimer =
            ImpressionCountDownTimer(this.impressionRequest)

        return this
    }


    fun apply() {

        view.addOnAttachStateChangeListener(onAttachStateChangeListener)

    }


    private val onAttachStateChangeListener = object : View.OnAttachStateChangeListener {
        override fun onViewDetachedFromWindow(v: View?) {
            Log.e("view", "view 消失在螢幕上")

            view.viewTreeObserver.removeOnScrollChangedListener(onScrollChangedListener)
        }

        override fun onViewAttachedToWindow(v: View?) {
            Log.e("view", "view 出現在螢幕上")

            val rect = Rect()

            view.getLocalVisibleRect(rect)

            view.viewTreeObserver.addOnScrollChangedListener(onScrollChangedListener)
        }
    }


    private val onScrollChangedListener = ViewTreeObserver.OnScrollChangedListener {

        val percents = viewVisibilityPercentageCalculator.getVisibilityPercents(view)

        impressionCountDownTimer.checkPercent(percents)

        Log.e("Scroll", "${view.tag}-${percents}%")
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun pause(){

        Log.e("Lifecycle","pause")

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun resume(){
        Log.e("Lifecycle","resume")
    }

}