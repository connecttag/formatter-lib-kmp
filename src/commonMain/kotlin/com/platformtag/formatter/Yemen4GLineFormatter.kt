package com.platformtag.formatter

import kotlin.jvm.JvmInline

private val Yemen4GLinePattern = Regex("^10\\d{7}$")
private val Yemen4GLineSubstringPattern = Regex("10\\d{7}")

@JvmInline
value class PhoneNumber private constructor(val value: String) {
    companion object {
        fun from(input: String): PhoneNumber? {
            return normalizePhoneDigits(input)
                .takeIf { it.isNotEmpty() }
                ?.let(::PhoneNumber)
        }
    }

    override fun toString(): String = value
}

@JvmInline
value class Yemen4GLineNumber private constructor(val value: String) {
    companion object {
        fun from(input: String): Yemen4GLineNumber? {
            return normalizePhoneDigits(input)
                .takeIf { Yemen4GLinePattern.matches(it) }
                ?.let(::Yemen4GLineNumber)
        }

        fun extract(input: String): Yemen4GLineNumber? {
            return Yemen4GLineSubstringPattern
                .find(normalizePhoneDigits(input))
                ?.value
                ?.let(::Yemen4GLineNumber)
        }

        fun fromLenient(input: String): Yemen4GLineNumber? {
            return from(input) ?: extract(input)
        }
    }

    override fun toString(): String = value
}

fun isValidYemen4GPhoneNumber(phoneNumber: String): Boolean {
    return Yemen4GLineNumber.from(phoneNumber) != null
}

fun normalizeAsciiDigits(input: String): String {
    return DigitFormatter.normalizeToLatinDigits(input)
}

fun normalizePhoneDigits(input: String): String {
    return normalizeAsciiDigits(input)
}

fun normalizePhoneNumber(input: String): PhoneNumber? {
    return PhoneNumber.from(input)
}

fun normalizeYemen4GLineNumber(input: String): Yemen4GLineNumber? {
    return Yemen4GLineNumber.fromLenient(input)
}

fun normalizeYemen4GPhoneNumber(input: String): String? {
    return normalizeYemen4GLineNumber(input)?.value
}

fun extractYemen4GPhoneNumber(input: String): String? {
    return Yemen4GLineNumber.extract(input)?.value
}
