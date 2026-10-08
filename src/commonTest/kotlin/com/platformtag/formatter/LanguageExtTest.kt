package com.platformtag.formatter

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LanguageExtTest {
    @Test
    fun isArabicLanguageAcceptsArabicLanguageAndRegionTags() {
        assertTrue("ar".isArabicLanguage)
        assertTrue("AR".isArabicLanguage)
        assertTrue("ar-YE".isArabicLanguage)
        assertTrue("ar_SA".isArabicLanguage)
        assertTrue("arabic".isArabicLanguage)
    }

    @Test
    fun isArabicLanguageRejectsOtherLanguagesAndBlankValues() {
        assertFalse("en".isArabicLanguage)
        assertFalse("en-US".isArabicLanguage)
        assertFalse("".isArabicLanguage)
        assertFalse(null.isArabicLanguage)
    }

    @Test
    fun englishLanguageTagAcceptsAliasesAndRegionTags() {
        assertTrue("en".isEnglishLanguage)
        assertTrue("english".isEnglishLanguage)
        assertTrue("en-US".isEnglishLanguage)
        assertTrue("EN-GB".isEnglishLanguage)
    }

    @Test
    fun stableCaseHelpersNormalizeTokensWithoutPlatformLocale() {
        assertEquals("primary_container", "Primary_Container".lowercaseStable())
        assertEquals("PRIMARY_CONTAINER", "primary_container".uppercaseStable())
    }
}
