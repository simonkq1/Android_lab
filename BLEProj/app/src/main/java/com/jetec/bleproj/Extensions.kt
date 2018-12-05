package com.jetec.bleproj

import android.util.DisplayMetrics
import android.view.Window


fun Window.getWidth(): Int {
    val displayMetrics = DisplayMetrics()
    this.windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.widthPixels
}

fun Window.getHeight(): Int {
    val displayMetrics = DisplayMetrics()
    this.windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.heightPixels
}