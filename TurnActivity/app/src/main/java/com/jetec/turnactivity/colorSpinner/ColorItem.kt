package com.jetec.turnactivity.colorSpinner


import com.jetec.turnactivity.customList.Colors

class ColorItem: java.io.Serializable {

    var color: Colors?
    var name: String



    constructor(colors: Colors?, colorName: String) {
        this.color = colors
        this.name = colorName
    }



}