package com.example.memberlist

import com.example.memberlist.util.UrlConverter
import org.junit.Assert.assertEquals
import org.junit.Test

class UrlProcessorTest {
    @Test
    fun testProcessUrl() {
        val urls = listOf(
            "http://example.com" to "https://example.com",
            "https://example.com" to "https://example.com",
            "example.com" to "https://example.com",
            "www.example.com" to "https://www.example.com",
            "http://localhost" to "https://localhost",
            "http://192.168.1.1" to "https://192.168.1.1"
        )

        for ((input, expected) in urls) {
            assertEquals(expected, UrlConverter.convertHttpsUrl(input))
        }
    }
}