package com.example.weatherappwithkotlin.customenum

import com.example.weatherappwithkotlin.R

enum class ConditionWarning (private val code : List<Int>, private val message : String) {
    WARNING_FOG(listOf(45, 46), "Warning Fog"),
    UMBRELLA(listOf(51, 53, 55, 61, 80, 63, 81, 65, 82, 66, 67, 77, 95, 96, 99),
        "Take an umbrella"),
    WARNING_DRIZZLE(listOf(56), "Warning Drizzle"),
    DRESS(listOf(56, 57, 71, 85, 75, 86, 73), "Dress warmly");

    companion object {
        fun getWeatherConditionWarning(code: Int): String? {
            val matchingCondition = ConditionWarning.values().find { code in it.code }
            return matchingCondition?.message
        }
    }

    enum class Forecast(private val code: Int, private val message: String) {
        CLEAR_SKY(0, "Clear sky"),
        MAINLY_CLEAR(1, "Mainly clear"),
        PARTLY_CLOUDY(2, "Partly cloudy"),
        OVERCAST(3, "Overcast"),
        FOG_AND_DEPOSITING_RIME(45, "Fog and depositing rime fog"),
        DRIZZLE_LIGHT(51, "Drizzle: Light intensity"),
        DRIZZLE_MODERATE(53, "Drizzle: Moderate intensity"),
        DRIZZLE_DENSE(55, "Drizzle: Dense intensity"),
        FREEZING_DRIZZLE_LIGHT(56, "Freezing Drizzle: Light intensity"),
        FREEZING_DRIZZLE_DENSE(57, "Freezing Drizzle: Dense intensity"),
        RAIN_SLIGHT(61, "Rain: Slight intensity"),
        RAIN_MODERATE(63, "Rain: Moderate intensity"),
        RAIN_HEAVY(65, "Rain: Heavy intensity"),
        FREEZING_RAIN_LIGHT(66, "Freezing Rain: Light intensity"),
        FREEZING_RAIN_HEAVY(67, "Freezing Rain: Heavy intensity"),
        SNOW_FALL_SLIGHT(71, "Snow fall: Slight intensity"),
        SNOW_FALL_MODERATE(73, "Snow fall: Moderate intensity"),
        SNOW_FALL_HEAVY(75, "Snow fall: Heavy intensity"),
        SNOW_GRAINS(77, "Snow grains"),
        RAIN_SHOWERS_SLIGHT(80, "Rain showers: Slight intensity"),
        RAIN_SHOWERS_MODERATE(81, "Rain showers: Moderate intensity"),
        RAIN_SHOWERS_VIOLENT(82, "Rain showers: Violent intensity"),
        SNOW_SHOWERS_SLIGHT(85, "Snow showers: Slight intensity"),
        SNOW_SHOWERS_HEAVY(86, "Snow showers: Heavy intensity"),
        THUNDERSTORM_SLIGHT_OR_MODERATE(95, "Thunderstorm: Slight or moderate"),
        THUNDERSTORM_WITH_SLIGHT_AND_HEAVY_HAIL(96, "Thunderstorm - slight and heavy hail"),
        THUNDERSTORM_WITH_SLIGHT_AND_HEAVY_HAIL_2(99, "Thunderstorm - slight and heavy hail");

        companion object {
            fun getForecastCondition(code: Int): String {
                val matchingCondition = Forecast.values().find { code == it.code }
                return matchingCondition?.message ?: "No Information"
            }
        }
    }

    enum class IconCollection(private val code: List<Int>, private val message: Int) {
        CLEAR(listOf(0,1), R.drawable.z_sunny_foreground),
        PARTLY_CLOUDY(listOf(2), R.drawable.z_partly_cloudy_foreground),
        MAINLY_CLOUDY(listOf(3), R.drawable.z_partly_cloudy3_foreground),
        FOG(listOf(45, 48), R.drawable.z_cloudy_foreground),
        LITTLE_DRIZZLE(listOf(51, 56, 57,61, 80), R.drawable.z_little_rain_foreground),
        MODERATE_DRIZZLE(listOf(53, 63 ,81,77), R.drawable.z_medium_rain_foreground),
        POWER_DRIZZLE(listOf(55, 65, 82), R.drawable.z_powerful_rain_foreground),
        FREEZING_RAIN(listOf(66, 67,71, 85), R.drawable.z_snowfall_foreground),
        MODERATE_SNOW(listOf(73), R.drawable.z_snowfall_medium_foreground),
        POWERFUL_SNOW(listOf(75, 86), R.drawable.z_snowfall_powerfull_foreground),
        THUNDER(listOf(95,96, 99), R.drawable.z_thunder_foreground);

        companion object {
            fun getIconCondition(code: Int): Int {
                val matchingCondition = IconCollection.values().find { code in it.code }
                return matchingCondition?.message ?: R.drawable.z_sunny_foreground
            }
        }
    }

    enum class BackgroundIcon(private val code: List<Int>, private val message: Int) {
        CLEAR(listOf(0,1), R.drawable.clearsky),
        PARTLY_CLOUDY(listOf(2,3), R.drawable.cloudysky),
        FOG(listOf(45, 48), R.drawable.fog1),
        LITTLE_DRIZZLE(listOf(51, 56, 57,61, 80), R.drawable.littlerain),
        MODERATE_DRIZZLE(listOf(53,63, 81,66, 67), R.drawable.moderaterain),
        POWER_DRIZZLE(listOf(55,65, 82), R.drawable.hardrain),
        LITTLE_SNOW(listOf(71, 85), R.drawable.littlesnow),
        MODERATE_SNOW(listOf(73), R.drawable.moderatesnow),
        POWERFUL_SNOW(listOf(75, 86,77), R.drawable.snowstorm),
        THUNDER(listOf(95,96, 99), R.drawable.thunder), ;

        companion object {
            fun getBackgroundCondition(code: Int): Int {
                val matchingCondition = BackgroundIcon.values().find { code in it.code }
                return matchingCondition?.message ?: R.drawable.z_sunny_foreground
            }
        }
    }
}