package com.example.weatherappwithkotlin.retrofit

import com.example.weatherappwithkotlin.dto.city.CityDTO
import com.example.weatherappwithkotlin.dto.forecast.ForecastDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroFit {

    @GET("v1/dwd-icon?&hourly=temperature_2m,weathercode,windspeed_10m&daily=weathercode,temperature_2m_max,temperature_2m_min&timezone=GMT")
    fun getForecastJson(
        @Query("latitude") latitude: Double = 34.05,
        @Query("longitude") longitude: Double = -118.24,
    ): Call<ForecastDTO>

    @GET("v1/search?")
    fun getCityJson(@Query("name") name: String): Call<CityDTO>
}