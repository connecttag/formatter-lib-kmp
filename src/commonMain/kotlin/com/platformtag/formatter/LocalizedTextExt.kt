package com.platformtag.formatter

import com.platformtag.ext.cleanOrNull

/**
 * Resolves the localized value by preferred language, then fallback, then the opposite language.
 *
 * This helper is used in multiple modules where content provides `ar`, `en`, and optional
 * fallback text in a non-language-default order.
 */
fun resolveLocalizedText(
    ar: String?,
    en: String?,
    isArabic: Boolean,
    fallback: String? = null,
): String? {
    val cleanAr = ar.cleanOrNull()
    val cleanEn = en.cleanOrNull()
    val cleanFallback = fallback.cleanOrNull()

    return if (isArabic) {
        cleanAr ?: cleanFallback ?: cleanEn
    } else {
        cleanEn ?: cleanFallback ?: cleanAr
    }
}
