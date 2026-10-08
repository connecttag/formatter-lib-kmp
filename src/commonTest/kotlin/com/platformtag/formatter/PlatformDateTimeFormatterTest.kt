package com.platformtag.formatter

import kotlinx.datetime.TimeZone
import kotlinx.datetime.LocalTime
import kotlin.test.Test
import kotlin.test.assertEquals

class PlatformDateTimeFormatterTest {
    private val englishOptions = FormatterOptions.english(timeZone = TimeZone.UTC)
    private val arabicOptions = FormatterOptions.arabic(timeZone = TimeZone.UTC)

    @Test
    fun formatsRelativeTimeInEnglish() {
        val now = 1_000_000L

        assertEquals("Now", PlatformDateTimeFormatter.formatRelative(now - 10_000L, now, englishOptions))
        assertEquals("2 minutes ago", PlatformDateTimeFormatter.formatRelative(now - 120_000L, now, englishOptions))
        assertEquals("Yesterday", PlatformDateTimeFormatter.formatRelative(now - 86_400_000L, now, englishOptions))
    }

    @Test
    fun formatsRelativeTimeInArabic() {
        val now = 1_000_000L

        assertEquals("الآن", PlatformDateTimeFormatter.formatRelative(now - 10_000L, now, arabicOptions))
        assertEquals("منذ دقيقتين", PlatformDateTimeFormatter.formatRelative(now - 120_000L, now, arabicOptions))
        assertEquals("أمس", PlatformDateTimeFormatter.formatRelative(now - 86_400_000L, now, arabicOptions))
    }

    @Test
    fun fallsBackToIsoDateAfterRelativeWindow() {
        val result = PlatformDateTimeFormatter.formatRelative(
            epochMillis = 0L,
            nowMillis = 10L * 24L * 60L * 60L * 1000L,
            options = englishOptions,
        )

        assertEquals("1970-01-01", result)
    }

    @Test
    fun formatsLocalTimeUsingTheRequestedClockAndDigits() {
        assertEquals(
            "09:05",
            PlatformDateTimeFormatter.formatLocalTime(LocalTime(9, 5), englishOptions),
        )
        assertEquals(
            "٩:٠٥ م",
            PlatformDateTimeFormatter.formatLocalTime(LocalTime(21, 5), arabicOptions, use24Hour = false),
        )
    }
}
