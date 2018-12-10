package com.jetec.bleproj.Global

class StringFormatter {

    companion object {
        fun <T> export(num: T, head: String): String? {
            if (num is Int || num is Float || num is Double || (num is String && num.toBigDecimalOrNull() != null)) else {
                return null
            }

            var doubleNum: Double = if (num is String) num.toDouble() else num.toString().toDouble()
            val positive: String = if (doubleNum >= 0) "+" else if (doubleNum > -1) "-" else ""
            val intFormat = if (doubleNum >= 0) "%04d" else if (doubleNum > -1) "%04d" else "%05d"
            val intNum: Int = doubleNum.toInt()
            val decimalNum: Double = doubleNum % 1
            val formatInt: String = String.format(intFormat, intNum)
            val formatDecimal = String.format("%.1f", decimalNum).split(".")[1]
            val finalFormatedString: String = "$head$positive$formatInt.$formatDecimal"
            return finalFormatedString
        }
    }
}