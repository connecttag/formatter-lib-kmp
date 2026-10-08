package com.platformtag.formatter

import kotlin.test.Test
import kotlin.test.assertEquals

class DistanceFormatterTest {
    @Test
    fun formatsArabicMeterDistance() {
        val actual = DistanceFormatter.formatKilometers(0.5)

        assertEquals("٥٠٠ م", actual)
    }

    @Test
    fun formatsEnglishKilometerDistance() {
        val actual = DistanceFormatter.formatKilometers(
            distanceKm = 1.24,
            options = DistanceFormatOptions(
                formatterOptions = FormatterOptions.english(),
            ),
        )

        assertEquals("1.2 km", actual)
    }
}
