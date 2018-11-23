package com.jetec.custominterface

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main_button.apply {
            this.text = "Hello Simon"
        }
        main_button.setOnClickListener {
            toast(main_button.text)


        }

    }

    override fun onResume() {
        super.onResume()
        var gd = GradientDrawable()
        val width = window.getWidth()
        val height = window.getHeight()
        val diameter: Int = if (width > height) height / 2 else width / 2

        gd.apply {
            this.shape = GradientDrawable.OVAL
            this.setSize(diameter, diameter)
            this.cornerRadius = (diameter / 2).toFloat()
//            this.setColor(ContextCompat.getColor(this@MainActivity, R.color.colorBlue))

            this.colors = intArrayOf(R.color.colorBlue, R.color.colorAccent)
            this.gradientType = GradientDrawable.LINEAR_GRADIENT
            this.setGradientCenter(0.5f, 0.5f)
        }
        main_button.background = gd


    }
}
