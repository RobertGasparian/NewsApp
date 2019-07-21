package com.example.newsapp.app.util

import java.text.SimpleDateFormat
import java.util.*


fun String.convertBbcPubDate(): Date {
    val formatter = SimpleDateFormat("E, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
    return formatter.parse(this)
}

fun String.convertNewsPubDate(): Date {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
    return formatter.parse(this)
}