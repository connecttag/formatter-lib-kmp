package com.platformtag.formatter

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull

class PhoneNumberFormatterTest {
    private val rules = PhoneNumberRules(
        countryCode = "1",
        localLength = 10,
        mobilePrefixes = setOf("55"),
    )

    @Test
    fun normalizesConfiguredPhoneNumberVariants() {
        assertEquals("5512345678", PhoneNumberFormatter.normalize("55 123 45678", rules)?.localNumber)
        assertEquals("5512345678", PhoneNumberFormatter.normalize("05512345678", rules)?.localNumber)
        assertEquals("5512345678", PhoneNumberFormatter.normalize("+15512345678", rules)?.localNumber)
        assertEquals("5512345678", PhoneNumberFormatter.normalize("0015512345678", rules)?.localNumber)
    }

    @Test
    fun rejectsUnsupportedConfiguredPrefix() {
        val result = PhoneNumberFormatter.validate("7812345678", rules)

        assertIs<PhoneValidationResult.Invalid>(result)
        assertEquals(PhoneValidationFailure.UnsupportedPrefix, result.reason)
    }

    @Test
    fun formatsPhoneNumberForDisplay() {
        assertEquals(
            "+1 55 123 456 78",
            PhoneNumberFormatter.format("+15512345678", PhoneFormatStyle.International, rules),
        )
        assertEquals(
            "٥٥ ١٢٣ ٤٥٦ ٧٨",
            PhoneNumberFormatter.format("5512345678", PhoneFormatStyle.LocalGrouped, rules, DigitStyle.ArabicIndic),
        )
    }

    @Test
    fun returnsNullForInvalidNumberFormat() {
        assertNull(PhoneNumberFormatter.format("not-a-phone", PhoneFormatStyle.International, rules))
    }
}
