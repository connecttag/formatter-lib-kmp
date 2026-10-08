package com.platformtag.formatter

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class LocalizedTextExtTest {
    @Test
    fun resolveLocalizedTextPrefersRequestedLanguageThenFallbackThenOpposite() {
        assertEquals(
            "مرحبا",
            resolveLocalizedText(
                ar = "مرحبا",
                en = "Hello",
                isArabic = true,
                fallback = "Fallback",
            ),
        )
        assertEquals(
            "Fallback",
            resolveLocalizedText(
                ar = null,
                en = "Hello",
                isArabic = true,
                fallback = "Fallback",
            ),
        )
        assertEquals(
            "Hello",
            resolveLocalizedText(
                ar = "مرحبا",
                en = "Hello",
                isArabic = false,
                fallback = null,
            ),
        )
    }

    @Test
    fun resolveLocalizedTextFallsThroughToFallbackWhenPreferredLanguageMissing() {
        assertEquals(
            "Fallback",
            resolveLocalizedText(
                ar = null,
                en = null,
                isArabic = true,
                fallback = "Fallback",
            ),
        )
        assertEquals(
            "Fallback",
            resolveLocalizedText(
                ar = null,
                en = null,
                isArabic = false,
                fallback = "Fallback",
            ),
        )
    }

    @Test
    fun resolveLocalizedTextReturnsNullWhenNothingResolvable() {
        assertNull(resolveLocalizedText(null, "", isArabic = true, fallback = "   "))
    }
}
