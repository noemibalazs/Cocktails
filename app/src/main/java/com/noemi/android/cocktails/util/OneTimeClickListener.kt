package com.noemi.android.cocktails.util

import android.os.SystemClock
import android.view.View

abstract class OnTimeClickListener(private val limit: Int = 900) : View.OnClickListener {

    private var threshHold = 0L
    abstract fun oneTimeClick(view: View)

    override fun onClick(view: View) {
        val current = SystemClock.elapsedRealtime()
        if (current - threshHold > limit) {
            threshHold = current
            oneTimeClick(view)
        }
    }
}