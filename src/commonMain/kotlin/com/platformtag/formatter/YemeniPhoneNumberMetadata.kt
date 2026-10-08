package com.platformtag.formatter

data class YemeniPhoneNumberMetadata(
    val providerKey: String,
    val numberTypeKey: String,
)

object YemeniPhoneProviderKeys {
    const val YEMEN_4G = "yemen4g"
    const val YEMEN_MOBILE = "yemen_mobile"
    const val YOU = "you"
    const val SABAFON = "sabafon"
    const val Y_TELECOM = "y_telecom"
    const val FIXED_LINE = "fixed_line"
}

object YemeniPhoneNumberTypeKeys {
    const val DATA_LINE = "data_line"
    const val MOBILE = "mobile"
    const val FIXED_LINE = "fixed_line"
}

fun detectYemeniPhoneNumberMetadata(input: String): YemeniPhoneNumberMetadata? {
    val digits = normalizeYemeniPhoneNumberMetadataDigits(input)
    return when {
        digits.startsWith("10") -> YemeniPhoneNumberMetadata(
            providerKey = YemeniPhoneProviderKeys.YEMEN_4G,
            numberTypeKey = YemeniPhoneNumberTypeKeys.DATA_LINE,
        )
        digits.startsWith("77") || digits.startsWith("78") -> YemeniPhoneNumberMetadata(
            providerKey = YemeniPhoneProviderKeys.YEMEN_MOBILE,
            numberTypeKey = YemeniPhoneNumberTypeKeys.MOBILE,
        )
        digits.startsWith("73") -> YemeniPhoneNumberMetadata(
            providerKey = YemeniPhoneProviderKeys.YOU,
            numberTypeKey = YemeniPhoneNumberTypeKeys.MOBILE,
        )
        digits.startsWith("71") -> YemeniPhoneNumberMetadata(
            providerKey = YemeniPhoneProviderKeys.SABAFON,
            numberTypeKey = YemeniPhoneNumberTypeKeys.MOBILE,
        )
        digits.startsWith("70") -> YemeniPhoneNumberMetadata(
            providerKey = YemeniPhoneProviderKeys.Y_TELECOM,
            numberTypeKey = YemeniPhoneNumberTypeKeys.MOBILE,
        )
        digits.startsWith("0") && digits.length >= 2 -> YemeniPhoneNumberMetadata(
            providerKey = YemeniPhoneProviderKeys.FIXED_LINE,
            numberTypeKey = YemeniPhoneNumberTypeKeys.FIXED_LINE,
        )
        else -> null
    }
}

private fun normalizeYemeniPhoneNumberMetadataDigits(input: String): String {
    val digits = input.filter(Char::isDigit)
    return when {
        digits.startsWith("00967") && digits.length > 11 -> digits.drop(5)
        digits.startsWith("967") && digits.length > 9 -> digits.drop(3)
        digits.startsWith("0") && digits.length == 10 && digits.drop(1).take(2) in mobilePrefixes -> {
            digits.drop(1)
        }
        else -> digits
    }
}

private val mobilePrefixes = setOf("77", "78", "73", "71", "70")
