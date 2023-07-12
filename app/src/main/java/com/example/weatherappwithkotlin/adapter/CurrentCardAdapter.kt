package com.example.weatherappwithkotlin.adapter

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import com.example.weatherappwithkotlin.customenum.ConditionWarning
import com.example.weatherappwithkotlin.dao.forecast.ForecastDTO
import java.util.*

class CurrentCardAdapter(private var forecastDTO: ForecastDTO) {

    @SuppressLint("SetTextI18n")
    fun fill(
        list: List<TextView>,
        name: String,
        imageView: ImageView,
    ) {
        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        for (index in forecastDTO.hourly.weathercode.indices) {
            var date = forecastDTO.hourly.time[index]
            var day = date.substring(8, 10)
            if (day.startsWith("0")) {
                day = day.substring(1)
            }
            var hour = date.substring(11, 13)
            if (hour.startsWith("0")) {
                hour = hour.substring(1)
            }
            if (day == currentDay.toString() && hour == currentHour.toString()) {
                list[1].text = forecastDTO.hourly.temperature_2m[index].toString() + ""
                list[3].text = forecastDTO.hourly.windspeed_10m[index].toString() + " km/h"
            }
        }
        list[0].text = name
        list[2].text = ConditionWarning().getWeatherConditionByCode(forecastDTO.daily.weathercode[0])
        list[4].text = forecastDTO.daily.temperature_2m_min[0].toString() + "°C / " + forecastDTO.daily.temperature_2m_max[0].toString() + "°C"
        list[5].text = forecastDTO.daily.time[0]
        list[6].text = ConditionWarning().getWeatherConditionWarning(forecastDTO.hourly.weathercode[0])
        imageView.setImageResource(ConditionWarning().getIconWeatherCode(forecastDTO.hourly.weathercode[0]))
    }
}