package com.example.weatherappwithkotlin.customenum

import com.example.weatherappwithkotlin.R

class BackgroundCollection {
        fun getIconByWeatherCode(weatherCode : Int) : Int {
            return when (weatherCode) {
                0 -> R.drawable.main_fragment_background
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