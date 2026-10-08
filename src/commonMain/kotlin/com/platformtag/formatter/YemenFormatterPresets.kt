package com.platformtag.formatter

import com.platformtag.config.DefaultsValue

object CurrencySpecs {
    val YemeniRial = CurrencySpec(
        code = DefaultsValue.DEFAULT_CURRENCY_CODE,
        arabicSymbol = "ر.ي",
        englishSymbol = DefaultsValue.DEFAULT_CURRENCY_CODE,
        minorUnit = 0,
        symbolPosition = CurrencySymbolPosition.AfterAmount,
    )

    val SaudiRiyal = CurrencySpec(
        code = "SAR",
        arabicSymbol = "ر.س",
        englishSymbol = "SAR",
        minorUnit = 2,
        symbolPosition = CurrencySymbolPosition.AfterAmount,
    )

    val UsDollar = CurrencySpec(
        code = "USD",
        arabicSymbol = "$",
        englishSymbol = "$",
        minorUnit = 2,
        symbolPosition = CurrencySymbolPosition.BeforeAmount,
    )
}

fun MoneyFormatter.formatYemeniRial(
    amount: Long?,
    options: MoneyFormatOptions = MoneyFormatOptions(),
): String =
    format(
        amount = amount?.let { MoneyAmount(minorUnits = it, currency = CurrencySpecs.YemeniRial) },
        options = options,
    )
