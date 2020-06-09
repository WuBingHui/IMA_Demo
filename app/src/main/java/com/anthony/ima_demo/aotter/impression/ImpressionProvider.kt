package com.anthony.ima_demo.aotter.impression

import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.annotation.IntRange
import androidx.annotation.Nullable
import com.anthony.ima_demo.aotter.impression.view.ImpressionRequest


/**
 * 2020/06/08 created by Anthony Wu
 * 曝光提供者
 */
class ImpressionProvider(private val view: View) {

    private var viewVisibilityPercentageCalculator: ViewVisibilityPercentageCalculator =
        ViewVisibilityPercentageCalculator()

    private  var impressionRequest = ImpressionRequest()

    fun impressionRequest(@Nullable impressionRequest: ImpressionRequest): ImpressionProvider {

        this.impressionRequest = impressionRequest

        return this
    }


    fun apply(){

        val rect =  Rect()

        view.getLocalVisibleRect(rect)

        view.viewTreeObserver.addOnScrollChangedListener(onScrollChangedListener)

    }


    private val onScrollChangedListener = ViewTreeObserver.OnScrollChangedListener {

        Log.e("view","${view.tag}-${viewVisibilityPercentageCalculator.getVisibilityPercents(view)}%")


    }

}