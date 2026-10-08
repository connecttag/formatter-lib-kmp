package org.connecttag.lib.formatter

import com.platformtag.formatter.formatMessagePlaceholders as internalFormatMessagePlaceholders

public typealias MoneyFormatter = com.platformtag.formatter.MoneyFormatter
public typealias CurrencyRegistry = com.platformtag.formatter.CurrencyRegistry
public typealias CurrencySpec = com.platformtag.formatter.CurrencySpec
public typealias DistanceFormatter = com.platformtag.formatter.DistanceFormatter
public typealias CoordinateFormatter = com.platformtag.formatter.CoordinateFormatter
public typealias DecimalNumberFormatter = com.platformtag.formatter.DecimalNumberFormatter
public typealias DigitFormatter = com.platformtag.formatter.DigitFormatter
public fun String.formatMessagePlaceholders(vararg arguments: Any?): String =
    internalFormatMessagePlaceholders(*arguments)
public typealias PlatformDateTimeFormatter = com.platformtag.formatter.PlatformDateTimeFormatter
public typealias PhoneNumberFormatter = com.platformtag.formatter.PhoneNumberFormatter
public typealias UrlUtils = com.platformtag.formatter.UrlUtils
public typealias StringUtils = com.platformtag.formatter.StringUtils
