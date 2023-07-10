package com.example.weatherappwithkotlin.daoclass

data class WeatherData (
    val cityName : String,
    val longitude : String,
    val latitude : String,
    val hourlyTemperatures : List<String>,
    val dailyMaxTemperatures : List<String>,
    val dailyMinTemperatures : List<String>,
    val windSpeed : String,
    val weatherConditionCode : String,
    val countryIcon : String,
    val hourlyTime : List<String>,
    val dateList : List<String>
)