package com.platformtag.formatter

object YemeniMobilePhoneRules {
    val Default = PhoneNumberRules(
        countryCode = "967",
        localLength = 9,
        mobilePrefixes = setOf("77", "73", "71", "70"),
    )
}

object YemeniMobilePhoneFormatter {
    fun normalize(input: String): NormalizedPhoneNumber? =
        PhoneNumberFormatter.normalize(input, YemeniMobilePhoneRules.Default)

    fun validate(input: String): PhoneValidationResult =
        PhoneNumberFormatter.validate(input, YemeniMobilePhoneRules.Default)

    fun format(
        input: String,
        style: PhoneFormatStyle = PhoneFormatStyle.International,
        digitStyle: DigitStyle = DigitStyle.Latin,
    ): String? =
        PhoneNumberFormatter.format(
            input = input,
            style = style,
            rules = YemeniMobilePhoneRules.Default,
            digitStyle = digitStyle,
        )
}
