package com.jetec.custominterface

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import kotlinx.android.synthetic.main.dialog.*
import org.jetbrains.anko.runOnUiThread
import kotlin.concurrent.thread


class MyDialog(context: Context) : Dialog(context) {

    private var count: Double = 0.0
        set(value) {
            context.runOnUiThread {
                numCount.text = "${String.format("%.1f", value)} s"
            }
            field = value
        }
    private var isChecked: Boolean = false
    private var isStart: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog)
        this.window?.setLayout((window.getWidth() * 0.8).toInt(), (window.getHeight() * 0.7).toInt())

        cancelButton.apply { this.text = "Cancel" }
        cancelButton.setOnClickListener { this.dismiss() }
        okButton.apply { this.text = "Ok" }

        okButton.setOnClickListener {
            okButtonAction()
        }
    }


    fun okButtonAction () {

        if (!isChecked) {
            var i: Int = 3
            okButton.isEnabled = false
            thread {
                while (i >= 0) {
                    context.runOnUiThread {
                        okButton.text = "Check ($i)"
                    }
                    Thread.sleep(1000)
                    i--
                    if (i == 0) {
                        context.runOnUiThread {
                            isChecked = true
                            okButton.text = "Ok"
                            okButton.isEnabled = true
                        }
                        break
                    }
                }
            }

        } else {

            isStart = !isStart
            if (!isStart) {
                isChecked = false
                okButton.text = "START"
                return
            }else {

                okButton.text = "STOP"
            }
            thread {
                while (isStart) {
                    count += 0.1
                    Thread.sleep(100)
                }
            }
        }
    }


}