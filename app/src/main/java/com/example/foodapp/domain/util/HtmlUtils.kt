package com.example.foodapp.domain.util

import androidx.core.text.HtmlCompat

object HtmlUtils {
    fun fromHtmlToString(html: String): String {
        return HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    }
}
