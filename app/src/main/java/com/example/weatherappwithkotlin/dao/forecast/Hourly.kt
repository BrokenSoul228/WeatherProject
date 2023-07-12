package com.example.weatherappwithkotlin.dao.forecast

data class Hourly(
    val temperature: List<Double>,
    val time: List<String>,
    val weatherCode: List<Int>,
    val windSpeed: List<Double>
)