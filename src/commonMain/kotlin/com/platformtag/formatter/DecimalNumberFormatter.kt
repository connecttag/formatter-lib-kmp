package com.platformtag.formatter

object DecimalNumberFormatter {
    fun format(
        value: Double,
        maxFractionDigits: Int,
        options: FormatterOptions = FormatterOptions.arabic(),
        trimTrailingFractionZeros: Boolean = true,
    ): String {
        val scale = pow10(maxFractionDigits.coerceAtLeast(0))
        val negative = value < 0.0
        val scaled = kotlin.math.floor(kotlin.math.abs(value) * scale + 0.5).toLong()
        val major = if (scale == 1L) scaled else scaled / scale
        val minor = if (scale == 1L) 0L else scaled % scale
        val symbols = options.digitStyle.symbols()
        val majorText = groupDigits(major.toString(), symbols.groupingSeparator)
        val fractionText = if (scale == 1L || maxFractionDigits <= 0) {
            ""
        } else {
            val raw = minor.toString().padStart(maxFractionDigits, '0')
            val normalized = if (trimTrailingFractionZeros) raw.trimEnd('0') else raw
            if (normalized.isEmpty()) "" else "${symbols.decimalSeparator}$normalized"
        }
        val sign = if (negative) "-" else ""
        return DigitFormatter.renderDigits("$sign$majorText$fractionText", options.digitStyle)
    }

    private fun groupDigits(value: String, separator: Char): String {
        if (value.length <= 3) return value
        return value
            .reversed()
            .chunked(3)
            .joinToString(separator = separator.toString())
            .reversed()
    }

    private fun pow10(exponent: Int): Long {
        var result = 1L
        repeat(exponent) { result *= 10L }
        return result
    }
}
