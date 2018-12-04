package com.jetec.custominterface

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.ContextMenu
import android.widget.Button

class CircleButton(context: Context): Button(context){

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        print("AAAAAAA")
    }
}