package com.pablogd.componentbox.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date?.formatStringDate(): String {
    this?.let {
        return SimpleDateFormat("dd/MM/yyyy", Locale("es")).format(it)
    }
    return ""
}