package com.jetec.bleproj.Global

typealias SettingList = LCDCommand.Companion.SettingList
typealias InitialSettings = LCDCommand.Companion.InitialSettings
class LCDCommand {



    companion object {

        val ENGE: String = "ENGE"
        val PASS: String = "PASS"
        val GUES: String = "GUES"
        val INIT: String = "INIT"

        val T: String = "Temperature"
        val H: String = "Humidity"
        val C: String = "CO2"
        val I: String = "Analog"

        val NAME: String = "NAME"
        val SPK: String = "SPK"

        val PV1: String = "PV1"
        val EH1: String = "EH1"
        val EL1: String = "EL1"
        val CR1: String = "CR1"
        val PV2: String = "PV2"
        val EH2: String = "EH2"
        val EL2: String = "EL2"
        val CR2: String = "CR2"
        val PV3: String = "PV3"
        val EH3: String = "EH3"
        val EL3: String = "EL3"
        val CR3: String = "CR3"

        val INT: String = "INTER"
        val ADR: String = "ADR"
        val LOG: String = "LOG"

        val PV: String = "PV"
        val EH: String = "EH"
        val EL: String = "EL"
        val CR: String = "CR"
        val IH: String = "IH"
        val IL: String = "IL"
        val DP: String = "DP"


        class SettingList {
            companion object {
                val DEF: List<String> = listOf(LCDCommand.NAME, LCDCommand.SPK)
                val SET: List<String> = listOf(LCDCommand.PV, LCDCommand.EH, LCDCommand.EL, LCDCommand.CR)
                val L1: List<String> = listOf(LCDCommand.PV1, LCDCommand.EH1, LCDCommand.EL1, LCDCommand.CR1)
                val L2: List<String> = listOf(LCDCommand.PV2, LCDCommand.EH2, LCDCommand.EL2, LCDCommand.CR2)
                val L3: List<String> = listOf(LCDCommand.PV3, LCDCommand.EH3, LCDCommand.EL3, LCDCommand.CR3)
                val I: List<String> = listOf(LCDCommand.DP, LCDCommand.IH, LCDCommand.IL)
                val L: List<String> = listOf(LCDCommand.INT)
                val M: List<String> = listOf(LCDCommand.ADR)
            }
        }

        class InitialSettings {
            companion object {

                val DEF: Map<String, Any> = mapOf(
                        LCDCommand.NAME to "JTC",
                        LCDCommand.SPK to 1.toDouble())
                val T: Map<String, Any> = mapOf(
                        LCDCommand.PV to 0.toDouble(),
                        LCDCommand.EH to 65.toDouble(),
                        LCDCommand.EL to 0.toDouble(),
                        LCDCommand.CR to 65.toDouble())
                val H: Map<String, Any> = mapOf(
                        LCDCommand.PV to 0.toDouble(),
                        LCDCommand.EH to 99.toDouble(),
                        LCDCommand.EL to 0.toDouble(),
                        LCDCommand.CR to 99.toDouble())
                val C: Map<String, Any> = mapOf(
                        LCDCommand.PV to 0.toDouble(),
                        LCDCommand.EH to 5000.toDouble(),
                        LCDCommand.EL to 0.toDouble(),
                        LCDCommand.CR to 5000.toDouble())
                val I: Map<String, Any> = mapOf(
                        LCDCommand.DP to 0.toDouble(),
                        LCDCommand.PV to 0.toDouble(),
                        LCDCommand.EH to 9999.toDouble(),
                        LCDCommand.EL to (-999).toDouble(),
                        LCDCommand.CR to 9999.toDouble(),
                        LCDCommand.IH to 9999.toDouble(),
                        LCDCommand.IL to (-999).toDouble())
                val L: Map<String, Any> = mapOf(
                        LCDCommand.INT to 30)
                val M: Map<String, Any> = mapOf(
                        LCDCommand.ADR to 1.toDouble())


            }

        }
    }

}


