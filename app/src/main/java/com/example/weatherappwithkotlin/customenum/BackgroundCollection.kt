package com.example.weatherappwithkotlin.customenum

import com.example.weatherappwithkotlin.R

class BackgroundCollection {
        fun getIconByWeatherCode(weatherCode : Int) : Int {
            return when (weatherCode) {
                0 -> R.drawable.main_fragment_background
                1 -> R.drawable.main_fragment_background
                2 -> R.drawable.littlecloudy
                3 -> R.drawable.veryclouudy
                45,48 -> R.drawable.smoke
                51 -> R.drawable.littlerain
                53 -> R.drawable.mediumrain
                55 -> R.drawable.hardrain
                56,57 -> R.drawable.veryclouudy
                61,80 -> R.drawable.littlerain
                63,81 ->R.drawable.mediumrain
                65,82 -> R.drawable.hardrain
                66,67 -> R.drawable.hardrain
                71,85 -> R.drawable.snow
                73 -> R.drawable.snoww
                75,86 -> R.drawable.hardsnow
                77 -> R.drawable.hail
                95 -> R.drawable.thunderr
                96,99 -> R.drawable.thunderpower
                else -> R.drawable.veryclouudy
            }
        }
}