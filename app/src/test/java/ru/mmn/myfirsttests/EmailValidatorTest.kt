package ru.mmn.myfirsttests

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class EmailValidatorTest {

    @Test
    fun emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertTrue(EmailValidator.isValidEmail("name@email.com"))
    }

    @Test
    fun emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
        assertTrue(EmailValidator.isValidEmail("name@email.co.uk"))
    }

    @Test
    fun emailValidator_InvalidEmailNoTld_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name@email"))
    }

    @Test
    fun emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name@email..com"))
    }

    @Test
    fun emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("@email.com"))
    }

    @Test
    fun emailValidator_EmptyString_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail(""))
    }

    @Test
    fun emailValidator_NullEmail_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail(null))
    }

    @Test
    fun emailValidator_EmailWithoutDomain_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name@"))
    }

    @Test
    fun emailValidator_InvalidEmailNoTldWithDot_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name@email."))
    }

    @Test
    fun emailValidator_OtherSymbolThanDot_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name@email!com"))
    }

    @Test
    fun emailValidator_OtherSymbolThanEmailCharacter_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name?email.com"))
    }

    @Test
    fun emailValidator_NameWithOnlyTld_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name@com"))
    }

    @Test
    fun emailValidator_NameWithOnlyTldWithDot_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name@.com"))
    }

    @Test
    fun emailValidator_DoubleEmailCharacter_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name@@email.com"))
    }

    @Test
    fun emailValidator_EmptyEmailCharacter_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("nameemail.com"))
    }

}