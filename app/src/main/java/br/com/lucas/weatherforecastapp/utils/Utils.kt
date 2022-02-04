package br.com.lucas.weatherforecastapp.utils

import java.text.SimpleDateFormat

fun Int.formatDate(): String {
    val sdf = SimpleDateFormat("EEE, MMM d")
    val date = java.util.Date(this.toLong() * 1000)

    return sdf.format(date)
}

fun Int.formatDateTime(): String {
    val sdf = SimpleDateFormat("hh:mm:aa")
    val date = java.util.Date(this.toLong() * 1000)

    return sdf.format(date)
}

fun Double.formatDecimals(): String {
    return " %.0f".format(this)
}