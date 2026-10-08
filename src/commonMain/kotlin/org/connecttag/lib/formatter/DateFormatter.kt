package org.connecttag.lib.formatter

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

public object DateFormatter {
    public fun formatTimestamp(timestamp: Long): String {
        val instant = Instant.fromEpochMilliseconds(timestamp)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return "${localDateTime.date} ${localDateTime.time.hour}:${localDateTime.time.minute}"
    }

    public fun formatIsoDateToDayMonthYear(date: String): String? {
        val parts = date.split("-")
        if (parts.size != 3) return null
        val year = parts[0].takeIf { it.length == 4 } ?: return null
        val month = parts[1].takeIf { it.length == 2 } ?: return null
        val day = parts[2].takeIf { it.length == 2 } ?: return null
        return "$day-$month-$year"
    }

    public fun formatHour12(hour: String): String {
        val value = hour.toIntOrNull() ?: return hour
        return when (value) {
            0 -> "12 AM"
            12 -> "12 PM"
            in 1..11 -> "$hour AM"
            in 13..23 -> "${(value - 12).toString().padStart(2, '0')} PM"
            else -> hour
        }
    }
}
