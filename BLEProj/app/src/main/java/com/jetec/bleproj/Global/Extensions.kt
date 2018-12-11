package com.jetec.bleproj.Global

import android.content.Context
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



fun Double?.toSendableString(head: String): String? {
    if (this == null) { return null }

    val positive: String = if (this >= 0) "+" else if (this > -1) "-" else ""
    val intFormat = if (this >= 0) "%04d" else if (this > -1) "%04d" else "%05d"
    val intNum: Int = this.toInt()
    val decimalNum: Double = this % 1
    val formatInt: String = String.format(intFormat, intNum)
    val formatDecimal = String.format("%.1f", decimalNum).split(".")[1]
    val finalString: String = "$head$positive$formatInt.$formatDecimal"
    return finalString
}

fun String.codeToDoubleValue(): Double? {
    if (this.contains("+")) {
        val split = this.split("+")
        return split[1].toDouble()
    }else if (this.contains("-")) {
        val split = this.split("-")
        return -(split[1].toDouble())

    }else {
        return null
    }
}

