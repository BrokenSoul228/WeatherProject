package com.example.weatherappwithkotlin.dto.city

data class CityDTO(
    val generationtime_ms: Double = 0.0,
    val results: List<Result> = emptyList()
)