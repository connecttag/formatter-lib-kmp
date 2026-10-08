package com.platformtag.formatter

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

data class RelativeDateTimeThresholds(
    val justNowMaxMillis: Long = 60_000L,
    val hourMaxMillis: Long = 60L * 60L * 1000L,
    val dayMaxMillis: Long = 24L * 60L * 60L * 1000L,
    val relativeMaxMillis: Long = 7L * 24L * 60L * 60L * 1000L,
)

object PlatformDateTimeFormatter {
    fun formatLocalTime(
        time: LocalTime,
        options: FormatterOptions = FormatterOptions.arabic(),
        use24Hour: Boolean = true,
    ): String {
        val minute = time.minute.toString().padStart(2, '0')
        val text = if (use24Hour) {
            "${time.hour.toString().padStart(2, '0')}:$minute"
        } else {
            val hour = when (val normalized = time.hour % 12) {
                0 -> 12
                else -> normalized
            }
            val suffix = when (options.language) {
                FormatterLanguage.Arabic -> if (time.hour < 12) "ص" else "م"
                FormatterLanguage.English -> if (time.hour < 12) "AM" else "PM"
            }
            "$hour:$minute $suffix"
        }
        return DigitFormatter.renderDigits(text, options.digitStyle)
    }

    fun formatRelative(
        epochMillis: Long,
        nowMillis: Long,
        options: FormatterOptions = FormatterOptions.arabic(),
        thresholds: RelativeDateTimeThresholds = RelativeDateTimeThresholds(),
    ): String {
        val diff = nowMillis - epochMillis
        if (diff < 0L || diff < thresholds.justNowMaxMillis) {
            return when (options.language) {
                FormatterLanguage.Arabic -> "الآن"
                FormatterLanguage.English -> "Now"
            }
        }

        val minutes = diff / 60_000L
        val hours = diff / (60L * 60L * 1000L)
        val days = diff / (24L * 60L * 60L * 1000L)

        return when {
            diff < thresholds.hourMaxMillis -> minuteText(minutes, options)
            diff < thresholds.dayMaxMillis -> hourText(hours, options)
            days == 1L -> yesterdayText(options)
            diff < thresholds.relativeMaxMillis -> dayText(days, options)
            else -> formatIsoDate(epochMillis, options.timeZone, options.digitStyle)
        }
    }

    fun formatIsoDate(
        epochMillis: Long,
        timeZone: TimeZone = TimeZone.currentSystemDefault(),
        digitStyle: DigitStyle = DigitStyle.Latin,
    ): String {
        val date = Instant.fromEpochMilliseconds(epochMillis).toLocalDateTime(timeZone).date
        return DigitFormatter.renderDigits(date.toIsoText(), digitStyle)
    }

    fun formatMonthShort(
        epochMillis: Long,
        options: FormatterOptions = FormatterOptions.arabic(),
    ): String {
        val monthNumber = Instant.fromEpochMilliseconds(epochMillis)
            .toLocalDateTime(options.timeZone)
            .month
            .number
        return when (options.language) {
            FormatterLanguage.Arabic -> arabicShortMonths[monthNumber - 1]
            FormatterLanguage.English -> englishShortMonths[monthNumber - 1]
        }
    }

    fun formatDayOfMonth(
        epochMillis: Long,
        options: FormatterOptions = FormatterOptions.arabic(),
    ): String {
        val day = Instant.fromEpochMilliseconds(epochMillis)
            .toLocalDateTime(options.timeZone)
            .day
        return DigitFormatter.renderDigits(day.toString(), options.digitStyle)
    }

    fun formatMonthDay(
        date: LocalDate,
        options: FormatterOptions = FormatterOptions.arabic(),
    ): String {
        val month = when (options.language) {
            FormatterLanguage.Arabic -> arabicShortMonths[date.month.number - 1]
            FormatterLanguage.English -> englishShortMonths[date.month.number - 1]
        }
        val day = DigitFormatter.renderDigits(date.day.toString(), options.digitStyle)
        return when (options.language) {
            FormatterLanguage.Arabic -> "$day $month"
            FormatterLanguage.English -> "$month $day"
        }
    }

    fun formatDayMonthYear(
        epochMillis: Long,
        options: FormatterOptions = FormatterOptions.arabic(),
    ): String {
        val date = Instant.fromEpochMilliseconds(epochMillis)
            .toLocalDateTime(options.timeZone)
            .date
        val month = when (options.language) {
            FormatterLanguage.Arabic -> arabicShortMonths[date.month.number - 1]
            FormatterLanguage.English -> englishShortMonths[date.month.number - 1]
        }
        val day = DigitFormatter.renderDigits(date.day.toString(), options.digitStyle)
        val year = DigitFormatter.renderDigits(date.year.toString(), options.digitStyle)
        return when (options.language) {
            FormatterLanguage.Arabic -> "$day $month $year"
            FormatterLanguage.English -> "$month $day, $year"
        }
    }

    fun formatDayMonthYearTime(
        epochMillis: Long,
        options: FormatterOptions = FormatterOptions.arabic(),
    ): String {
        return when (options.language) {
            FormatterLanguage.Arabic ->
                "${formatDayMonthYear(epochMillis, options)} - ${formatHourMinute12(epochMillis, options)}"
            FormatterLanguage.English ->
                "${formatDayMonthYear(epochMillis, options)} - ${formatHourMinute12(epochMillis, options)}"
        }
    }

    fun formatHourMinute12(
        epochMillis: Long,
        options: FormatterOptions = FormatterOptions.arabic(),
    ): String {
        val time = Instant.fromEpochMilliseconds(epochMillis)
            .toLocalDateTime(options.timeZone)
            .time
        val hour12 = when (val hour = time.hour % 12) {
            0 -> 12
            else -> hour
        }
        val minute = time.minute.toString().padStart(2, '0')
        val suffix = when (options.language) {
            FormatterLanguage.Arabic -> if (time.hour < 12) "ص" else "م"
            FormatterLanguage.English -> if (time.hour < 12) "AM" else "PM"
        }
        val rendered = DigitFormatter.renderDigits("$hour12:$minute", options.digitStyle)
        return "$rendered $suffix"
    }

    private fun minuteText(value: Long, options: FormatterOptions): String =
        when (options.language) {
            FormatterLanguage.Arabic -> arabicRelativeText(value, options, "دقيقة", "دقيقتين", "دقائق", "دقيقة")
            FormatterLanguage.English -> "${formatNumber(value, options)} ${englishUnit(value, "minute")} ago"
        }

    private fun hourText(value: Long, options: FormatterOptions): String =
        when (options.language) {
            FormatterLanguage.Arabic -> arabicRelativeText(value, options, "ساعة", "ساعتين", "ساعات", "ساعة")
            FormatterLanguage.English -> "${formatNumber(value, options)} ${englishUnit(value, "hour")} ago"
        }

    private fun dayText(value: Long, options: FormatterOptions): String =
        when (options.language) {
            FormatterLanguage.Arabic -> arabicRelativeText(value, options, "يوم", "يومين", "أيام", "يوم")
            FormatterLanguage.English -> "${formatNumber(value, options)} ${englishUnit(value, "day")} ago"
        }

    private fun yesterdayText(options: FormatterOptions): String =
        when (options.language) {
            FormatterLanguage.Arabic -> "أمس"
            FormatterLanguage.English -> "Yesterday"
        }

    private fun formatNumber(value: Long, options: FormatterOptions): String =
        DigitFormatter.renderDigits(value.toString(), options.digitStyle)

    private fun englishUnit(value: Long, singular: String): String =
        if (value == 1L) singular else "${singular}s"

    private fun arabicRelativeText(
        value: Long,
        options: FormatterOptions,
        singular: String,
        dual: String,
        plural: String,
        pluralFallback: String,
    ): String =
        when (value) {
            1L -> "منذ $singular"
            2L -> "منذ $dual"
            else -> "منذ ${formatNumber(value, options)} ${arabicUnit(value, singular, dual, plural, pluralFallback)}"
        }

    private fun arabicUnit(
        value: Long,
        singular: String,
        dual: String,
        plural: String,
        pluralFallback: String,
    ): String =
        when {
            value == 1L -> singular
            value == 2L -> dual
            value in 3L..10L -> plural
            else -> pluralFallback
        }

    private fun LocalDate.toIsoText(): String =
        "${year.toString().padStart(4, '0')}-${month.number.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}"

    private val arabicShortMonths = listOf(
        "ينا", "فبر", "مار", "أبر", "ماي", "يون",
        "يول", "أغس", "سبت", "أكت", "نوف", "ديس",
    )

    private val englishShortMonths = listOf(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
    )
}
