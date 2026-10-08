package com.platformtag.formatter

import kotlin.math.roundToInt

data class DistanceUnitLabels(
    val meter: String,
    val kilometer: String,
) {
    init {
        require(meter.isNotBlank()) { "Meter unit label must not be blank." }
        require(kilometer.isNotBlank()) { "Kilometer unit label must not be blank." }
    }

    companion object {
        fun arabic(): DistanceUnitLabels = DistanceUnitLabels(
            meter = "م",
            kilometer = "كم",
        )

        fun english(): DistanceUnitLabels = DistanceUnitLabels(
            meter = "m",
            kilometer = "km",
        )

        fun forLanguage(language: FormatterLanguage): DistanceUnitLabels =
            when (language) {
                FormatterLanguage.Arabic -> arabic()
                FormatterLanguage.English -> english()
            }
    }
}

data class DistanceFormatOptions(
    val formatterOptions: FormatterOptions = FormatterOptions.arabic(),
    val unitLabels: DistanceUnitLabels = DistanceUnitLabels.forLanguage(formatterOptions.language),
    val kilometerThreshold: Double = 1.0,
    val kilometerFractionDigits: Int = 1,
    val trimTrailingKilometerZeros: Boolean = false,
    val placeholder: String = "--",
) {
    init {
        require(kilometerThreshold > 0.0) { "Kilometer threshold must be greater than zero." }
        require(kilometerFractionDigits >= 0) { "Kilometer fraction digits must not be negative." }
    }
}

object DistanceFormatter {
    fun formatKilometers(
        distanceKm: Double?,
        options: DistanceFormatOptions = DistanceFormatOptions(),
    ): String {
        if (distanceKm == null) return options.placeholder

        val normalizedDistanceKm = distanceKm.coerceAtLeast(0.0)
        val (value, unit) = if (normalizedDistanceKm < options.kilometerThreshold) {
            val meters = (normalizedDistanceKm * METERS_PER_KILOMETER).roundToInt()
            DecimalNumberFormatter.format(
                value = meters.toDouble(),
                maxFractionDigits = 0,
                options = options.formatterOptions,
            ) to options.unitLabels.meter
        } else {
            DecimalNumberFormatter.format(
                value = normalizedDistanceKm,
                maxFractionDigits = options.kilometerFractionDigits,
                options = options.formatterOptions,
                trimTrailingFractionZeros = options.trimTrailingKilometerZeros,
            ) to options.unitLabels.kilometer
        }

        return "$value $unit"
    }

    private const val METERS_PER_KILOMETER = 1000.0
}
