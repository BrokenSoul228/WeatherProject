package com.example.weatherappwithkotlin.retrofit

import com.example.weatherappwithkotlin.dto.city.CityDTO
import com.example.weatherappwithkotlin.dto.forecast.ForecastDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroFit {

    @GET("v1/dwd-icon")
    fun getForecastJson(
        @Query("latitude") latitude: Double = 34.05,
        @Query("longitude") longitude: Double = -118.24,
        @Query("hourly") hourly: String = "temperature_2m,weathercode,windspeed_10m",
        @Query("daily") daily: String = "weathercode,temperature_2m_max,temperature_2m_min",
        @Query("timezone") timezone: String = "GMT"
    ): Call<ForecastDTO>

    @GET("v1/search?")
    fun getCityJson(@Query("name") name: String): Call<CityDTO>
}