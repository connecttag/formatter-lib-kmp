package com.platformtag.formatter

import kotlin.test.Test
import kotlin.test.assertEquals

class CoordinateFormatterTest {
    @Test
    fun formatDecimalCoordinateUsesFixedLatinFractionDigits() {
        assertEquals("15.369400", CoordinateFormatter.formatDecimalCoordinate(15.3694))
        assertEquals("-44.191000", CoordinateFormatter.formatDecimalCoordinate(-44.191))
    }
}
