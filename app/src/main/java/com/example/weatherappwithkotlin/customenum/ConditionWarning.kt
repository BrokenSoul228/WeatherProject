package com.example.weatherappwithkotlin.customenum

import com.example.weatherappwithkotlin.R

enum class ConditionWarning(code: List<Int>){
    WARNING_FOG(45,46),
    UMBRELLA(51,53,55,61,80,63,81,65,82,66,67,77,95,96,99),
    WARNING_DRIZZLE(56),
    DRESS(listOf(56,57,71,85,75,86,73),

    CLEAR(listOf(0), "Clear"),
    PARTLY_CLEAR(listOf(1), "Partly clear"),
    PARTLY_CLOUDY(listOf(2), "Partly cloudy"),
    MAINLY_CLOUDY(listOf(3), "Mainly cloudy"),
    FOG(listOf(45,48), "Fog"),
    LITTLE_DRIZZLE(listOf(51,56,57), "Little Drizzle"),
    MODERATE_DRIZZLE(listOf(53), "Moderate Drizzle"),
    POWER_DRIZZLE(listOf(55), "Powerful Drizzle"),
    LITTLE_RAIN(listOf(61,80), "Little Rain"),
    MODERATE_RAIN(listOf(63,81), "Moderate Rain"),
    POWERFUL_RAIN(listOf(5,82), "Powerful Rain"),
    FREEZING_RAIN(listOf(66,67), "Freezing Rain"),
    LITTLE_SNOW(listOf(71,85), "Little Snowfall"),
    MODERATE_SNOW(listOf(73), "Moderate Snowfall"),
    POWERFUL_SNOW(listOf(75,86), "Powerful Snowfall"),
    HAIL(listOf(77), "Hail"),
    THUNDER(listOf(95), "Thunder"),
    HAIL_THUNDER(listOf(96,99), "Thunder with hail");

        fun getWeatherConditionWarning(code: Int) : String {
            return when (code) {
                in listOf(45,46) -> WARNING_FOG.toString()
                in listOf(51,53,55,61,80,63,81,65,82,66,67,77,95,96,99) -> UMBRELLA.toString()
                in listOf(56) -> WARNING_DRIZZLE.toString()
                in listOf(56,57,71,85,75,86,73) -> DRESS.toString()
                else -> "Good weather"
            }
        }

    fun getWeatherConditionByCode(countryCode : Int) : String {
        return when (countryCode) {
            in listOf(0) -> CLEAR.toString()
            in listOf(1) ->PARTLY_CLEAR.toString()
            in listOf(2) -> PARTLY_CLOUDY.toString()
            in listOf(3) -> MAINLY_CLOUDY.toString()
            in listOf(45,48) -> FOG.toString()
            in listOf(51) -> LITTLE_DRIZZLE.toString()
            in listOf(53) -> MODERATE_DRIZZLE.toString()
            in listOf(55) -> POWER_DRIZZLE.toString()
            in listOf(56,57) -> LITTLE_DRIZZLE.toString()
            in listOf(61,80) -> LITTLE_RAIN.toString()
            in listOf(63,81) -> MODERATE_RAIN.toString()
            in listOf(65,82) -> POWERFUL_RAIN.toString()
            in listOf(66,67) -> FREEZING_RAIN.toString()
            in listOf(71,85) -> LITTLE_SNOW.toString()
            in listOf(73) -> MODERATE_SNOW.toString()
            in listOf(75,86) -> POWERFUL_SNOW.toString()
            in listOf(77) -> HAIL.toString()
            in listOf(95) -> THUNDER.toString()
            in listOf(96,99) -> HAIL_THUNDER.toString()
            else -> "No information"
        }
    }

    fun getIconByWeatherCode(countryCode : Int) : Int {
        return when (countryCode) {
            0 -> R.drawable.z_sunny_foreground
            1 -> R.drawable.z_sunny_foreground
            2 -> R.drawable.z_partly_cloudy_foreground
            3 -> R.drawable.z_partly_cloudy3_foreground
            45,48 -> R.drawable.z_cloudy_foreground
            51 -> R.drawable.z_little_rain_foreground
            53 -> R.drawable.z_medium_rain_foreground
            55 -> R.drawable.z_powerful_rain_foreground
            56,57 -> R.drawable.z_little_snow_foreground
            61,80 -> R.drawable.z_little_rain_foreground
            63,81 -> R.drawable.z_medium_rain_foreground
            65,82 -> R.drawable.z_powerful_rain_foreground
            66,67 -> R.drawable.z_snowfall_foreground
            71,85 -> R.drawable.z_snowfall_foreground
            73 -> R.drawable.z_snowfall_medium_foreground
            75,86 -> R.drawable.z_snowfall_powerfull_foreground
            77 -> R.drawable.z_medium_rain_foreground
            95 -> R.drawable.z_thunder_foreground
            96,99 -> R.drawable.z_thunder_foreground
            else -> R.drawable.ic_launcher_foreground
        }
    }

    fun getIconWeatherCode(weatherCode : Int) : Int {
        return when (weatherCode) {
            0 -> R.drawable.sunnycloudy
            1 -> R.drawable.sunnycloudy
            2 -> R.drawable.defaulticon
            3 -> R.drawable.defaulticon
            45,48 -> R.drawable.fog
            51 -> R.drawable.littlerain
            53 -> R.drawable.mediumrain
            55 -> R.drawable.hardrain
            56,57 -> R.drawable.verycloudy
            61,80 -> R.drawable.littlerain
            63,81 ->R.drawable.mediumrain
            65,82 -> R.drawable.powerrain
            66,67 -> R.drawable.powerrain
            71,85 -> R.drawable.snow
            73 -> R.drawable.snoww
            75,86 -> R.drawable.hardsnow
            77 -> R.drawable.hail
            95 -> R.drawable.thunder
            96,99 -> R.drawable.thunderpower
            else -> R.drawable.verycloudy
        }
    }
}