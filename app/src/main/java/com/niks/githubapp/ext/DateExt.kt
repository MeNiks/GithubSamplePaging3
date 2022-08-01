package com.niks.githubapp.ext

import java.text.SimpleDateFormat
import java.util.*

fun getReadableDate(date: Date?, format: String = "dd/MM/yy, HH:mm z"): String {
    date ?: return ""
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.format(date) ?: ""
}