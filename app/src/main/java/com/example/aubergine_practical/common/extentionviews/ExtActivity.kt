package com.example.aubergine_practical.common.extentionviews

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

/**
 * This file contains Activity related extension functions
 */
fun Activity.resToast(res:String){
    Toast.makeText(this, res, Toast.LENGTH_LONG).show()
}

fun Activity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = currentFocus
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}
