package com.example.aubergine_practical.common.extentionviews

import android.content.Context
import android.content.SharedPreferences

/**
 * This file contains context related extension functions
 */

fun Context.getPrefInstance(prefName: String): SharedPreferences =
    this.getSharedPreferences(prefName, Context.MODE_PRIVATE)