package com.example.weatherappwithkotlin.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var cityRetrofit : Retrofit
    private var forecastRetrofit : Retrofit

    init {
        val cityURL = "https://geocoding-api.open-meteo.com/"
        val forecastURL = "https://api.open-meteo.com/"

        cityRetrofit = Retrofit.Builder()
            .baseUrl(cityURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        forecastRetrofit = Retrofit.Builder()
            .baseUrl(forecastURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getCityRetrofit() : Retrofit {
        return cityRetrofit.create(Retrofit::class.java)
    }

    fun getForecastRetrofit() : Retrofit {
        return forecastRetrofit.create(Retrofit::class.java)
    }

    fun getForecast(): Retrofit {
        return forecastRetrofit.create(Retrofit::class.java)
    }

}
