package com.example.weatherappwithkotlin.customenum

import com.example.weatherappwithkotlin.R

enum class ConditionWarning (code : Int , s : String){
    WarningFog(45 ,"Warning Fog"),
    WarningFog2(46 ,"Warning Fog"),
    Umbrella("Take an umbrella"),
    WarningDrizzle("Warning Drizzle"),
    Dress("Dress warmly"),
    Clear("Clear") ,
    PartlyClear("Partly clear"),
    PartlyCloudy("Partly cloudy"),
    MainlyCloudy("Mainly cloudy"),
    Fog("Fog"),
    LittleDrizzle("Little Drizzle"),
    ModerateDrizzle("Moderate Drizzle"),
    PowerDrizzle("Powerful Drizzle"),
    LittleRain("Little Rain"),
    ModerateRain("Moderate Rain"),
    PowerfulRain("Powerful Rain"),
    FreezingRain("Freezing Rain"),
    LitlleSnow("Little Snowfall"),
    ModerateSnow("Moderate Snowfall"),
    PowerfulSnow("Powerful Snowfall"),
    Hail("Hail"),
    Thunder("Thunder"),
    HailThunder("Thunder with hail");

        fun getWeatherConditionWarning(countryCode : Int) : String {
            return when (countryCode) {
                45,48 -> Enu.WarningFog.toString()
                51,53,55,61,80,63,81,65,82,66,67,77,95,96,99 -> Enu.Umbrella.toString()
                56,57 -> Enu.WarningDrizzle.toString()
                71,85 -> Enu.Dress.toString()
                73 -> Enu.Dress.toString()
                75,86 -> Enu.Dress.toString()
                else -> "Good weather"
            }
        }

    fun getWeatherConditionByCode(countryCode : Int) : String {
        return when (countryCode) {
            0 -> Enu.Clear.toString()
            1 -> Enu.PartlyClear.toString()
            2 -> Enu.PartlyCloudy.toString()
            3 -> Enu.MainlyCloudy.toString()
            45,48 -> Enu.Fog.toString()
            51 -> Enu.LittleDrizzle.toString()
            53 -> Enu.ModerateDrizzle.toString()
            55 -> Enu.PowerDrizzle.toString()
            56,57 -> Enu.LittleDrizzle.toString()
            61,80 -> Enu.LittleRain.toString()
            63,81 -> Enu.ModerateRain.toString()
            65,82 -> Enu.PowerfulRain.toString()
            66,67 -> Enu.FreezingRain.toString()
            71,85 -> Enu.LitlleSnow.toString()
            73 -> Enu.ModerateSnow.toString()
            75,86 -> Enu.PowerfulSnow.toString()
            77 -> Enu.Hail.toString()
            95 -> Enu.Thunder.toString()
            96,99 -> Enu.HailThunder.toString()
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