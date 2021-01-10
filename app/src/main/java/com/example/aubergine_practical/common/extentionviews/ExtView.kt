package com.example.aubergine_practical.common.extentionviews

import android.util.Base64
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

/**
 * This file contains View related extension functions
 */

fun View.snack(@StringRes msg: Int) {
    Snackbar.make(this, context.getString(msg), Snackbar.LENGTH_SHORT).show()
}

fun View.snack(msg: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, msg, duration).show()
}