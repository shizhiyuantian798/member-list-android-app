package com.example.memberlist.util

object UrlConverter {
    fun convertHttpsUrl(url: String): String {
        return when {
            url.startsWith("http://") -> "https://" + url.substring(7)
            url.startsWith("https://") -> url
            else -> "https://$url"
        }
    }
}