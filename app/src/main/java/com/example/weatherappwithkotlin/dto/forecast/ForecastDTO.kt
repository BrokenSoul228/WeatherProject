package com.example.weatherappwithkotlin.dto.forecast

data class ForecastDTO(
    val daily: Daily,
    val elevation: Double,
    val generationtime_ms: Double,
    val hourly: Hourly,
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val timezone_abbreviation: String,
    val utc_offset_seconds: Int
)

