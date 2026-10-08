package com.platformtag.formatter

import kotlin.test.Test
import kotlin.test.assertEquals

class MoneyFormatterTest {
    private val testCurrency = CurrencySpec(
        code = "TST",
        arabicSymbol = "TST",
        englishSymbol = "TST",
        minorUnit = 2,
    )

    @Test
    fun formatsMinorUnitsWithExplicitCurrency() {
        val result = MoneyFormatter.format(
            amount = MoneyAmount(minorUnits = 1250050, currency = testCurrency),
            options = MoneyFormatOptions(formatterOptions = FormatterOptions.english()),
        )

        assertEquals("12,500.5 TST", result)
    }

    @Test
    fun returnsPlaceholderForMissingAmount() {
        val result = MoneyFormatter.format(
            amount = null,
            options = MoneyFormatOptions(placeholder = "--"),
        )

        assertEquals("--", result)
    }

    @Test
    fun formatsMajorUnitsWithoutCurrency() {
        val result = MoneyFormatter.formatMajorUnits(
            amount = 12500.50,
            currency = testCurrency,
            options = MoneyFormatOptions(
                formatterOptions = FormatterOptions.english(),
                showCurrency = false,
            ),
        )

        assertEquals("12,500.5", result)
    }

    @Test
    fun formatsMajorUnitsWithZeroFractions() {
        val result = MoneyFormatter.formatMajorUnits(
            amount = 12500.50,
            currency = testCurrency,
            options = MoneyFormatOptions(
                formatterOptions = FormatterOptions.english(),
                showCurrency = false,
            ),
            maxFractionDigits = 0,
        )

        assertEquals("12,501", result)
    }

    @Test
    fun createsOptionsFromLanguageCode() {
        assertEquals(FormatterLanguage.Arabic, FormatterOptions.forLanguageCode("ar").language)
        assertEquals(FormatterLanguage.English, FormatterOptions.forLanguageCode("en").language)
    }
}
