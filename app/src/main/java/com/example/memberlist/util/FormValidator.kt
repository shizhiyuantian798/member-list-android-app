package com.example.memberlist.util

import android.util.Patterns

class FormValidator {
    fun validateName(name: String): Boolean {
        return name.trim().isNotEmpty()
    }

    fun validateAge(ageString: String): Boolean {
        val age = ageString.toIntOrNull()
        return age != null && age in 0..150
    }

    fun validateUrl(url: String): Boolean {
        return url.isEmpty() || Patterns.WEB_URL.matcher(url).matches()
    }
}