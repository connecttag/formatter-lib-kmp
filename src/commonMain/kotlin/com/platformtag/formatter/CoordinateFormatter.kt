package com.platformtag.formatter

import kotlin.math.abs
import kotlin.math.round

object CoordinateFormatter {
    fun formatDecimalCoordinate(value: Double, fractionDigits: Int = DEFAULT_COORDINATE_FRACTION_DIGITS): String {
        val digits = fractionDigits.coerceIn(0, MAX_COORDINATE_FRACTION_DIGITS)
        val scale = pow10(digits)
        val scaled = round(abs(value) * scale).toLong()
        val major = if (scale == 1L) scaled else scaled / scale
        val minor = if (scale == 1L) 0L else scaled % scale
        val fraction = if (digits == 0) {
            ""
        } else {
            "." + minor.toString().padStart(digits, '0')
        }
        val sign = if (value < 0.0 && scaled != 0L) "-" else ""
        return "$sign$major$fraction"
    }
}

private fun pow10(exponent: Int): Long {
    var result = 1L
    repeat(exponent) { result *= 10L }
    return result
}

private const val DEFAULT_COORDINATE_FRACTION_DIGITS = 6
private const val MAX_COORDINATE_FRACTION_DIGITS = 12
