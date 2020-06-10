package com.anthony.ima_demo.aotter.impression.manager

import android.content.Context
import android.view.View
import androidx.annotation.Nullable
import androidx.lifecycle.Lifecycle
import com.anthony.ima_demo.aotter.impression.ImpressionProvider


/**
 * 2020/06/09 created by Anthony Wu
 * 曝光管理者
 * 該類是廣告曝光的核心類
 * 只接收view 與 lifecycle 並把 view 與 lifecycle 配發給 ImpressionProvider
 */
class ImpressionManager {


    private lateinit var context: Context


    fun with(@Nullable view: View,lifecycle: Lifecycle): ImpressionProvider {

        context = view.context

        return ImpressionProvider(view,lifecycle)

    }




}


