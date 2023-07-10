package com.example.weatherappwithkotlin.daoclass.forecast

data class HourlyUnits(
    val temperature_2m: String,
    val time: String,
    val weathercode: String,
    val windspeed_10m: String
)