package com.platformtag.formatter

import com.platformtag.text.isArabicLanguageTag as textIsArabicLanguageTag
import com.platformtag.text.isEnglishLanguageTag as textIsEnglishLanguageTag
import com.platformtag.text.lowercaseStable as textLowercaseStable
import com.platformtag.text.normalizedLanguageToken as textNormalizedLanguageToken
import com.platformtag.text.uppercaseStable as textUppercaseStable

fun isArabicLanguageTag(languageTag: String?): Boolean =
    textIsArabicLanguageTag(languageTag)

fun isEnglishLanguageTag(languageTag: String?): Boolean =
    textIsEnglishLanguageTag(languageTag)

val String?.isArabicLanguage: Boolean
    get() = isArabicLanguageTag(this)

val String?.isEnglishLanguage: Boolean
    get() = isEnglishLanguageTag(this)

fun String?.normalizedLanguageToken(): String? =
    this.textNormalizedLanguageToken()

fun String.lowercaseStable(): String =
    this.textLowercaseStable()

fun String.uppercaseStable(): String =
    this.textUppercaseStable()
