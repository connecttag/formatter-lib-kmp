package com.platformtag.formatter

data class PhoneNumberRules(
    val countryCode: String,
    val nationalPrefix: String = "0",
    val localLength: Int,
    val mobilePrefixes: Set<String>,
) {
    init {
        require(countryCode.all { it in '0'..'9' }) { "Country code must contain digits only." }
        require(localLength > 0) { "Local length must be positive." }
        require(mobilePrefixes.isNotEmpty()) { "At least one mobile prefix is required." }
    }
}

data class NormalizedPhoneNumber(
    val localNumber: String,
    val countryCode: String,
) {
    val internationalNumber: String get() = "+$countryCode$localNumber"
}

enum class PhoneValidationFailure {
    Empty,
    InvalidLength,
    UnsupportedPrefix,
}

sealed interface PhoneValidationResult {
    data class Valid(val number: NormalizedPhoneNumber) : PhoneValidationResult
    data class Invalid(val reason: PhoneValidationFailure) : PhoneValidationResult
}

enum class PhoneFormatStyle {
    LocalCompact,
    LocalGrouped,
    InternationalCompact,
    International,
}

object PhoneNumberFormatter {
    fun normalize(
        input: String,
        rules: PhoneNumberRules,
    ): NormalizedPhoneNumber? =
        when (val result = validate(input, rules)) {
            is PhoneValidationResult.Valid -> result.number
            is PhoneValidationResult.Invalid -> null
        }

    fun validate(
        input: String,
        rules: PhoneNumberRules,
    ): PhoneValidationResult {
        val digits = DigitFormatter.normalizeToLatinDigits(input)
        if (digits.isEmpty()) return PhoneValidationResult.Invalid(PhoneValidationFailure.Empty)

        val localNumber = toLocalNumber(digits, rules)
        if (localNumber.length != rules.localLength) {
            return PhoneValidationResult.Invalid(PhoneValidationFailure.InvalidLength)
        }
        if (rules.mobilePrefixes.none { localNumber.startsWith(it) }) {
            return PhoneValidationResult.Invalid(PhoneValidationFailure.UnsupportedPrefix)
        }

        return PhoneValidationResult.Valid(
            NormalizedPhoneNumber(
                localNumber = localNumber,
                countryCode = rules.countryCode,
            ),
        )
    }

    fun format(
        input: String,
        style: PhoneFormatStyle,
        rules: PhoneNumberRules,
        digitStyle: DigitStyle = DigitStyle.Latin,
    ): String? {
        val number = normalize(input, rules) ?: return null
        val value = when (style) {
            PhoneFormatStyle.LocalCompact -> number.localNumber
            PhoneFormatStyle.LocalGrouped -> groupLocal(number.localNumber)
            PhoneFormatStyle.InternationalCompact -> "+${number.countryCode}${number.localNumber}"
            PhoneFormatStyle.International -> "+${number.countryCode} ${groupLocal(number.localNumber)}"
        }
        return DigitFormatter.renderDigits(value, digitStyle)
    }

    private fun toLocalNumber(
        digits: String,
        rules: PhoneNumberRules,
    ): String {
        val internationalPrefix = "00${rules.countryCode}"
        return when {
            digits.startsWith(internationalPrefix) -> digits.removePrefix(internationalPrefix)
            digits.startsWith(rules.countryCode) &&
                digits.length == rules.countryCode.length + rules.localLength ->
                digits.removePrefix(rules.countryCode)
            digits.startsWith(rules.nationalPrefix) &&
                digits.length == rules.nationalPrefix.length + rules.localLength ->
                digits.removePrefix(rules.nationalPrefix)
            else -> digits
        }
    }

    private fun groupLocal(localNumber: String): String {
        if (localNumber.length <= 2) return localNumber
        val prefix = localNumber.take(2)
        val rest = localNumber.drop(2)
        return buildString {
            append(prefix)
            rest.chunked(3).forEach { group ->
                append(' ')
                append(group)
            }
        }
    }
}
