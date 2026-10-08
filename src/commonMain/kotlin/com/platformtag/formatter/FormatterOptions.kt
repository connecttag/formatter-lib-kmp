package com.platformtag.formatter

import kotlinx.datetime.TimeZone

enum class FormatterLanguage {
    Arabic,
    English,
}

enum class DigitStyle {
    Latin,
    ArabicIndic,
    EasternArabicIndic,
}

data class FormatterOptions(
    val language: FormatterLanguage = FormatterLanguage.Arabic,
    val digitStyle: DigitStyle = DigitStyle.ArabicIndic,
    val timeZone: TimeZone = TimeZone.currentSystemDefault(),
) {
    companion object {
        fun arabic(
            digitStyle: DigitStyle = DigitStyle.ArabicIndic,
            timeZone: TimeZone = TimeZone.currentSystemDefault(),
        ): FormatterOptions = FormatterOptions(
            language = FormatterLanguage.Arabic,
            digitStyle = digitStyle,
            timeZone = timeZone,
        )

        fun english(
            digitStyle: DigitStyle = DigitStyle.Latin,
            timeZone: TimeZone = TimeZone.currentSystemDefault(),
        ): FormatterOptions = FormatterOptions(
            language = FormatterLanguage.English,
            digitStyle = digitStyle,
            timeZone = timeZone,
        )

        fun forLanguageCode(
            languageCode: String?,
            arabicDigitStyle: DigitStyle = DigitStyle.ArabicIndic,
            englishDigitStyle: DigitStyle = DigitStyle.Latin,
            timeZone: TimeZone = TimeZone.currentSystemDefault(),
        ): FormatterOptions {
            return if (languageCode.isArabicLanguage) {
                arabic(digitStyle = arabicDigitStyle, timeZone = timeZone)
            } else {
                english(digitStyle = englishDigitStyle, timeZone = timeZone)
            }
        }
    }
}

internal data class NumberSymbols(
    val zero: Char,
    val groupingSeparator: Char,
    val decimalSeparator: Char,
)

internal fun DigitStyle.symbols(): NumberSymbols =
    when (this) {
        DigitStyle.Latin -> NumberSymbols('0', ',', '.')
        DigitStyle.ArabicIndic -> NumberSymbols('\u0660', '\u066c', '\u066b')
        DigitStyle.EasternArabicIndic -> NumberSymbols('\u06f0', '\u066c', '\u066b')
    }
