package com.platformtag.formatter

import com.platformtag.config.DefaultsValue

/**
 * Registry for currency specifications.
 */
object CurrencyRegistry {
    private val specs = mapOf(
        DefaultsValue.DEFAULT_CURRENCY_CODE to CurrencySpec(
            code = DefaultsValue.DEFAULT_CURRENCY_CODE,
            arabicSymbol = "ر.ي",
            englishSymbol = DefaultsValue.DEFAULT_CURRENCY_CODE,
            minorUnit = 0,
            symbolPosition = CurrencySymbolPosition.AfterAmount
        ),
        "USD" to CurrencySpec(
            code = "USD",
            arabicSymbol = "$",
            englishSymbol = "$",
            minorUnit = 2,
            symbolPosition = CurrencySymbolPosition.BeforeAmount
        ),
        "SAR" to CurrencySpec(
            code = "SAR",
            arabicSymbol = "ر.س",
            englishSymbol = "SAR",
            minorUnit = 2,
            symbolPosition = CurrencySymbolPosition.AfterAmount
        ),
        "EGP" to CurrencySpec(
            code = "EGP",
            arabicSymbol = "ج.م",
            englishSymbol = "EGP",
            minorUnit = 2,
            symbolPosition = CurrencySymbolPosition.AfterAmount
        )
    )

    /**
     * Returns the currency specification for the given code.
     * Returns a default specification if the code is not recognized.
     */
    fun getSpec(code: String): CurrencySpec {
        val normalized = code.uppercaseStable()
        return specs[normalized] ?: CurrencySpec(
            code = normalized,
            arabicSymbol = normalized,
            englishSymbol = normalized,
            minorUnit = 2,
            symbolPosition = CurrencySymbolPosition.AfterAmount
        )
    }

    fun fromCode(
        code: String?,
        language: FormatterLanguage = FormatterLanguage.Arabic,
    ): CurrencyDisplayInfo {
        val normalized = code?.trim()?.uppercaseStable().orEmpty()
        val resolved = when (normalized) {
            "" -> null
            "RSU" -> "SAR"
            "RYE" -> DefaultsValue.DEFAULT_CURRENCY_CODE
            else -> normalized
        }
        val spec = resolved?.let { specs[it] }
        val fallbackCode = normalized.ifBlank { "UNKNOWN" }
        return CurrencyDisplayInfo(
            code = spec?.code ?: fallbackCode,
            symbol = spec?.symbolFor(language) ?: normalized,
            nameAr = spec?.arabicName() ?: normalized.ifBlank { "غير معروف" },
            nameEn = spec?.englishName() ?: normalized.ifBlank { "Unknown" },
            spec = spec,
        )
    }

    private fun CurrencySpec.arabicName(): String =
        when (code) {
            DefaultsValue.DEFAULT_CURRENCY_CODE -> "ريال يمني"
            "SAR" -> "ريال سعودي"
            "USD" -> "دولار أمريكي"
            "EGP" -> "جنيه مصري"
            else -> code
        }

    private fun CurrencySpec.englishName(): String =
        when (code) {
            DefaultsValue.DEFAULT_CURRENCY_CODE -> "Yemeni Rial"
            "SAR" -> "Saudi Riyal"
            "USD" -> "US Dollar"
            "EGP" -> "Egyptian Pound"
            else -> code
        }
}
