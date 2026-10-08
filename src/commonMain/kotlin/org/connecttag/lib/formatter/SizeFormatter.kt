package org.connecttag.lib.formatter

import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.roundToLong

public object SizeFormatter {
    public fun formatFileSize(size: Long, fractionDigits: Int = 2): String {
        if (size <= 0) return "0 B"
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt().coerceIn(units.indices)
        val value = size / 1024.0.pow(digitGroups.toDouble())
        return "${formatDecimal(value, fractionDigits.coerceIn(0, 4))} ${units[digitGroups]}"
    }

    public fun formatCompactFileSize(size: Long): String {
        if (size <= 0) return "0 B"
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt().coerceIn(units.indices)
        val value = size / 1024.0.pow(digitGroups.toDouble())
        val fractionDigits = if (value >= 10 || digitGroups == 0) 0 else 1
        return "${formatDecimal(value, fractionDigits)} ${units[digitGroups]}"
    }

    private fun formatDecimal(value: Double, fractionDigits: Int): String {
        val divisor = 10.0.pow(fractionDigits).roundToLong()
        val scaled = (value * divisor).roundToLong()
        val whole = scaled / divisor
        if (fractionDigits == 0) {
            return whole.toString()
        }

        val fraction = (scaled % divisor).toString().padStart(fractionDigits, '0')
        return "$whole.$fraction"
    }
}
