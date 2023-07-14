package com.example.weatherappwithkotlin.customenum

import android.graphics.Color
import com.example.weatherappwithkotlin.R

enum class ConditionWarning (private val code : List<Int>, private val message : String) {
    WARNING_FOG(listOf(45, 46), "Warning Fog"),
    UMBRELLA(
        listOf(51, 53, 55, 61, 80, 63, 81, 65, 82, 66, 67, 77, 95, 96, 99),
        "Take an umbrella"
    ),
    WARNING_DRIZZLE(listOf(56), "Warning Drizzle"),
    DRESS(listOf(56, 57, 71, 85, 75, 86, 73), "Dress warmly");

    companion object {
        fun getWeatherConditionWarning(code: Int): String {
            val matchingCondition = ConditionWarning.values().find { code in it.code }
            return matchingCondition?.message ?: "Good weather"
        }
    }

    enum class Forecast(private val code: List<Int>, private val message: String) {
        CLEAR(listOf(0), "Clear"),
        PARTLY_CLEAR(listOf(1), "Partly clear"),
        PARTLY_CLOUDY(listOf(2), "Partly cloudy"),
        MAINLY_CLOUDY(listOf(3), "Mainly cloudy"),
        FOG(listOf(45, 48), "Fog"),
        LITTLE_DRIZZLE(listOf(51, 56, 57), "Little Drizzle"),
        MODERATE_DRIZZLE(listOf(53), "Moderate Drizzle"),
        POWER_DRIZZLE(listOf(55), "Powerful Drizzle"),
        LITTLE_RAIN(listOf(61, 80), "Little Rain"),
        MODERATE_RAIN(listOf(63, 81), "Moderate Rain"),
        POWERFUL_RAIN(listOf(65, 82), "Powerful Rain"),
        FREEZING_RAIN(listOf(66, 67), "Freezing Rain"),
        LITTLE_SNOW(listOf(71, 85), "Little Snowfall"),
        MODERATE_SNOW(listOf(73), "Moderate Snowfall"),
        POWERFUL_SNOW(listOf(75, 86), "Powerful Snowfall"),
        HAIL(listOf(77), "Hail"),
        THUNDER(listOf(95), "Thunder"),
        HAIL_THUNDER(listOf(96, 99), "Thunder with hail");

        companion object {
            fun getForecastCondition(code: Int): String {
                val matchingCondition = Forecast.values().find { code in it.code }
                return matchingCondition?.message ?: "No Information"
            }
        }
    }

    enum class IconCollection(private val code: List<Int>, private val message: Int) {
        CLEAR(listOf(0), R.drawable.z_sunny_foreground),
        PARTLY_CLEAR(listOf(1), R.drawable.z_sunny_foreground),
        PARTLY_CLOUDY(listOf(2), R.drawable.z_partly_cloudy_foreground),
        MAINLY_CLOUDY(listOf(3), R.drawable.z_partly_cloudy3_foreground),
        FOG(listOf(45, 48), R.drawable.z_cloudy_foreground),
        LITTLE_DRIZZLE(listOf(51, 56, 57), R.drawable.z_little_rain_foreground),
        MODERATE_DRIZZLE(listOf(53), R.drawable.z_medium_rain_foreground),
        POWER_DRIZZLE(listOf(55), R.drawable.z_powerful_rain_foreground),
        LITTLE_RAIN(listOf(61, 80), R.drawable.z_little_rain_foreground),
        MODERATE_RAIN(listOf(63, 81), R.drawable.z_medium_rain_foreground),
        POWERFUL_RAIN(listOf(65, 82), R.drawable.z_powerful_rain_foreground),
        FREEZING_RAIN(listOf(66, 67), R.drawable.z_snowfall_foreground),
        LITTLE_SNOW(listOf(71, 85), R.drawable.z_snowfall_foreground),
        MODERATE_SNOW(listOf(73), R.drawable.z_snowfall_medium_foreground),
        POWERFUL_SNOW(listOf(75, 86), R.drawable.z_snowfall_powerfull_foreground),
        HAIL(listOf(77), R.drawable.z_medium_rain_foreground),
        THUNDER(listOf(95), R.drawable.z_thunder_foreground),
        HAIL_THUNDER(listOf(96, 99), R.drawable.z_thunder_foreground);

        companion object {
            fun getIconCondition(code: Int): Int {
                val matchingCondition = IconCollection.values().find { code in it.code }
                return matchingCondition?.message ?: R.drawable.z_sunny_foreground
            }
        }
    }

    enum class BackgroundIcon(private val code: List<Int>, private val message: Int) {
        CLEAR(listOf(0), R.drawable.sunnycloudy),
        PARTLY_CLEAR(listOf(1), R.drawable.sunnycloudy),
        PARTLY_CLOUDY(listOf(2), R.drawable.verycloudy),
        MAINLY_CLOUDY(listOf(3), R.drawable.verycloudy),
        FOG(listOf(45, 48), R.drawable.fogg),
        LITTLE_DRIZZLE(listOf(51, 56, 57), R.drawable.rainmini),
        MODERATE_DRIZZLE(listOf(53), R.drawable.mediumrain),
        POWER_DRIZZLE(listOf(55), R.drawable.powerraain),
        LITTLE_RAIN(listOf(61, 80), R.drawable.rainmini),
        MODERATE_RAIN(listOf(63, 81), R.drawable.mediumrain),
        POWERFUL_RAIN(listOf(65, 82), R.drawable.powerraain),
        FREEZING_RAIN(listOf(66, 67), R.drawable.mediumrain),
        LITTLE_SNOW(listOf(71, 85), R.drawable.snowfallpowerr),
        MODERATE_SNOW(listOf(73), R.drawable.mediumsnow),
        POWERFUL_SNOW(listOf(75, 86), R.drawable.snowpover),
        HAIL(listOf(77), R.drawable.snowpover),
        THUNDER(listOf(95), R.drawable.thunder1024),
        HAIL_THUNDER(listOf(96, 99), R.drawable.thunder1024);

        companion object {
            fun getBackgroundCondition(code: Int): Int {
                val matchingCondition = BackgroundIcon.values().find { code in it.code }
                return matchingCondition?.message ?: R.drawable.z_sunny_foreground
            }
        }
    }

    enum class TextColorCondition(
        private val code: List<Int>,
        private val message: String,
        private val color: Int
    ) {
        CLEAR(listOf(0), "Clear", R.color.red),
        PARTLY_CLEAR(listOf(1), "Partly clear", R.color.red),
        PARTLY_CLOUDY(listOf(2), "Partly cloudy", R.color.red),
        MAINLY_CLOUDY(listOf(3), "Mainly cloudy", R.color.red),
        FOG(listOf(45, 48), "Fog", R.color.red),
        LITTLE_DRIZZLE(listOf(51, 56, 57), "Little Drizzle", R.color.red),
        MODERATE_DRIZZLE(listOf(53), "Moderate Drizzle", R.color.red),
        POWER_DRIZZLE(listOf(55), "Powerful Drizzle", R.color.red),
        LITTLE_RAIN(listOf(61, 80), "Little Rain", R.color.red),
        MODERATE_RAIN(listOf(63, 81), "Moderate Rain", R.color.red),
        POWERFUL_RAIN(listOf(65, 82), "Powerful Rain", R.color.red),
        FREEZING_RAIN(listOf(66, 67), "Freezing Rain", R.color.red),
        LITTLE_SNOW(listOf(71, 85), "Little Snowfall", R.color.red),
        MODERATE_SNOW(listOf(73), "Moderate Snowfall", R.color.red),
        POWERFUL_SNOW(listOf(75, 86), "Powerful Snowfall", R.color.red),
        HAIL(listOf(77), "Hail", R.color.red),
        THUNDER(listOf(95), "Thunder", R.color.red),
        HAIL_THUNDER(listOf(96, 99), "Thunder with hail", R.color.red);
        companion object {
            fun getTextColorCondition(code: Int): String {
                val matchingCondition = values().find { code in it.code }
                return matchingCondition?.message ?: "No Information"
            }

            fun getForecastTextColor(code: Int): Int {
                val matchingCondition = values().find { code in it.code }
                return matchingCondition?.color ?: R.color.blue
            }
        }
    }
}