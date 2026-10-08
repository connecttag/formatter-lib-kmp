package com.platformtag.formatter

/**
 * Applies positional values to the portable `%s` and `%d` placeholders used by
 * non-Compose message catalogs.
 *
 * This intentionally supports only the project's cross-platform placeholder
 * contract. Locale-sensitive number, money, and date values must be formatted
 * by their dedicated formatter before they are passed here.
 */
fun String.formatMessagePlaceholders(vararg arguments: Any?): String {
    if (arguments.isEmpty()) return this

    var formatted = this
    arguments.forEach { argument ->
        val stringIndex = formatted.indexOf("%s")
        val decimalIndex = formatted.indexOf("%d")
        val placeholderIndex = when {
            stringIndex < 0 -> decimalIndex
            decimalIndex < 0 -> stringIndex
            else -> minOf(stringIndex, decimalIndex)
        }
        if (placeholderIndex >= 0) {
            formatted = formatted.replaceRange(
                placeholderIndex,
                placeholderIndex + PLACEHOLDER_LENGTH,
                argument.toString(),
            )
        }
    }
    return formatted
}

private const val PLACEHOLDER_LENGTH = 2
