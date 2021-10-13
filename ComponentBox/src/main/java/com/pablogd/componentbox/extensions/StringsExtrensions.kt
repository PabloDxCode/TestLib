package com.pablogd.componentbox.extensions

import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.toSpanned

fun String?.addHtml(): Spanned = this?.let {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION") Html.fromHtml(this)
    }
} ?: "".toSpanned()

fun String?.notNull(): String = this ?: ""