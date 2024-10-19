package com.example.memberlist

import com.example.memberlist.util.FormValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FormValidationTest {

    private lateinit var formValidator: FormValidator

    @Before
    fun setup() {
        formValidator = FormValidator()
    }

    @Test
    fun testValidName() {
        assertTrue(formValidator.validateName("田中 太郎"))
        assertTrue(formValidator.validateName("Tanaka"))
        assertFalse(formValidator.validateName(""))
        assertFalse(formValidator.validateName("   "))
    }

    @Test
    fun testValidAge() {
        assertTrue(formValidator.validateAge("25"))
        assertTrue(formValidator.validateAge("0"))
        assertTrue(formValidator.validateAge("150"))
        assertFalse(formValidator.validateAge("-1"))
        assertFalse(formValidator.validateAge("151"))
        assertFalse(formValidator.validateAge("abc"))
        assertFalse(formValidator.validateAge(""))
    }

    // NB:　ValidateUrl is not created.
    // `Patterns.WEB_URL` is part of the Android API, so it does not work in pure JUnit tests.

}