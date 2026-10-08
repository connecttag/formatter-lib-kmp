package com.platformtag.formatter

import kotlin.test.Test
import kotlin.test.assertEquals

class MessageTemplateFormatterTest {
    @Test
    fun replacesSupportedPlaceholdersInTheirOriginalOrder() {
        assertEquals(
            "Ride 42 has 3 stops",
            "Ride %s has %d stops".formatMessagePlaceholders(42, 3),
        )
    }

    @Test
    fun leavesMissingAndUnsupportedPlaceholdersUntouched() {
        assertEquals(
            "Ride 42 costs %.2f",
            "Ride %s costs %.2f".formatMessagePlaceholders(42, 10.5),
        )
    }
}
