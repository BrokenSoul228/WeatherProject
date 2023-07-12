package com.example.weatherappwithkotlin.dao.forecast

data class ForecastDTO(
    val daily: Daily,
    val elevation: Double,
    val hourly: Hourly,
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
)