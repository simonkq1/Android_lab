package com.jetec.bleproj.Global

import java.util.*
import kotlin.collections.ArrayList

open class Localization {
    companion object {
        val DEFAULT: Localization = Localization()

        enum class Code {
            zh_TW, zh_CN, en
        }

    }

    private object en : Localization() {
        var data: MutableMap<String, String> = mutableMapOf(
                "T" to "Temperature",
                "H" to "Humidity",
                "C" to "CO2",
                "I" to "Analog",

                "NAME" to "Device Name",
                "PV" to "Offset",
                "EH" to "Alarm Max",
                "EL" to "Alarm Min",
                "CR" to "Color Change",
                "IH" to "Max Range",
                "IL" to "Min Range",
                "DP" to "Decimal Point",
                "ADR" to "Address",
                "INTER" to "Log Interval",
                "SPK" to "Alert",

                "ROW1" to "First",
                "ROW2" to "Second",
                "ROW3" to "Third",
                "ROW4" to "Fourth",
                "ROW5" to "Fifth",
                "ROW6" to "Sixth"
        )
    }


    private object zh_TW : Localization() {
        var data: MutableMap<String, String> = mutableMapOf(
                "T" to "溫度",
                "H" to "濕度",
                "C" to "二氧化碳",
                "I" to "類比訊號",

                "NAME" to "裝置名稱",
                "PV" to "補正",
                "EH" to "上限",
                "EL" to "下限",
                "CR" to "顏色轉換",
                "IH" to "最大量程",
                "IL" to "最小量程",
                "DP" to "小數點",
                "ADR" to "地址",
                "INTER" to "記錄間隔",
                "SPK" to "警報",

                "ROW1" to "第一排",
                "ROW2" to "第二排",
                "ROW3" to "第三排",
                "ROW4" to "第四排",
                "ROW5" to "第五排",
                "ROW6" to "第六排"
        )
    }

    private object zh_CN : Localization() {
        var data: MutableMap<String, String> = mutableMapOf(
                "T" to "温度",
                "H" to "湿度",
                "C" to "二氧化碳",
                "I" to "类比讯号",

                "NAME" to "装置名称",
                "PV" to "补正",
                "EH" to "上限",
                "EL" to "下限",
                "CR" to "颜色转换",
                "IH" to "最大量程",
                "IL" to "最小量程",
                "DP" to "小数点",
                "ADR" to "地址",
                "INTER" to "记录间隔",
                "SPK" to "警报",

                "ROW1" to "第一排",
                "ROW2" to "第二排",
                "ROW3" to "第三排",
                "ROW4" to "第四排",
                "ROW5" to "第五排",
                "ROW6" to "第六排"
        )
    }


    fun getValue(key: String, code: Code): String {
        when (code) {
            Code.en -> {
                return en.data[key] ?: key
            }
            Code.zh_TW -> {
                return zh_TW.data[key] ?: key

            }
            Code.zh_CN -> {
                return zh_CN.data[key] ?: key
            }
            else -> return en.data[key] ?: key
        }
    }

}

fun String.localized(): String {
    val locale = Locale.getDefault()

    val localize = Localization.DEFAULT
    when  {
        locale.toLanguageTag().contains("en") -> {
            return localize.getValue(this, Localization.Companion.Code.en)
        }

        locale.toLanguageTag().contains("zh") &&
                (locale.toLanguageTag().contains("TW") || locale.toLanguageTag().contains("Hant")) -> {
            return localize.getValue(this, Localization.Companion.Code.zh_TW)
        }

        locale.toLanguageTag().contains("zh") &&
                (locale.toLanguageTag().contains("CN") || locale.toLanguageTag().contains("Hans")) -> {
            return localize.getValue(this, Localization.Companion.Code.zh_CN)
        }

        locale.toLanguageTag().contains("zh") -> {
            return localize.getValue(this, Localization.Companion.Code.zh_TW)
        }

        else -> {
            return localize.getValue(this, Localization.Companion.Code.en)
        }
    }
}