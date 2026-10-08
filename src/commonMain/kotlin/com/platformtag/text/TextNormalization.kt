package com.platformtag.text

public fun isArabicLanguageTag(languageTag: String?): Boolean {
    val normalized = languageTag.normalizedLanguageToken()
    return normalized == "ar" || normalized == "arabic"
}

public fun isEnglishLanguageTag(languageTag: String?): Boolean {
    val normalized = languageTag.normalizedLanguageToken()
    return normalized == "en" || normalized == "english"
}

public val String?.isArabicLanguage: Boolean
    get() = isArabicLanguageTag(this)

public val String?.isEnglishLanguage: Boolean
    get() = isEnglishLanguageTag(this)

public fun String?.normalizedLanguageToken(): String? =
    this?.trim()?.lowercaseStable()?.replace('_', '-')?.substringBefore('-')?.takeIf { it.isNotEmpty() }

public fun String.lowercaseStable(): String = lowercase()
public fun String.uppercaseStable(): String = uppercase()
