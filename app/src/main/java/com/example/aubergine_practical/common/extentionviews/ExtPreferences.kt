package com.example.aubergine_practical.common.extentionviews

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * To store String type of value in shared preferences
 * @param def default value
 * @param key key of particular value
 * */
fun SharedPreferences.prefString(def: String = "", key: String? = null) =
    preferenceManager(def, key, SharedPreferences::getString, SharedPreferences.Editor::putString)

/**
 * Generic extension function to actually store and get value in preferences.
 * @param defaultValue default value of generic type
 * @param key key for value
 * @param getter extension function for getting value from shared preferences
 * @param setter extension function for setting value into shared preferences
 * */
private inline fun <T> SharedPreferences.preferenceManager(
    defaultValue: T,
    key: String?,
    crossinline getter: SharedPreferences.(String, T) -> T,
    crossinline setter: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor
): ReadWriteProperty<Any, T> {
    return object : ReadWriteProperty<Any, T> {
        override fun getValue(thisRef: Any, property: KProperty<*>) =
            getter(key ?: property.name, defaultValue)

        override fun setValue(
            thisRef: Any, property: KProperty<*>,
            value: T
        ) =
            edit().setter(key ?: property.name, value).apply()
    }
}
