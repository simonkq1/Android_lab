package com.jetec.turnactivity


import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

// TODO Extensions
fun EditText.hideKeyboard() {
    this.clearFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

