package com.alex_podolian.stackexchangetestapp.utils

import java.text.SimpleDateFormat
import java.util.*

fun Long.formatDate(): String {
    return try {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.UK)
        val netDate = Date(this * 1000)
        sdf.format(netDate)
    } catch (e: Exception) {
        e.toString()
    }
}