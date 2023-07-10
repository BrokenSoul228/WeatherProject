package com.example.weatherappwithkotlin.adapter

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.preference.PreferenceManager
import com.example.weatherappwithkotlin.customenum.BackgroundCollection
import com.example.weatherappwithkotlin.customenum.ConditionWarning
import com.example.weatherappwithkotlin.customenum.WeatherConditionCollection
import com.example.weatherappwithkotlin.customenum.WeatherIconCollection
import com.example.weatherappwithkotlin.daoclass.forecast.ForecastDTO
import com.squareup.picasso.Picasso
import java.util.*

class CurrentCardAdapter(private var forecastDTO: ForecastDTO) {

    @SuppressLint("SetTextI18n")
    fun fill(
        list: List<TextView>,
        name: String,
        listImage: List<ImageView>,
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
        list[2].text = WeatherConditionCollection().getWeatherConditionByCode(forecastDTO.daily.weathercode[0])
        list[4].text = forecastDTO.daily.temperature_2m_min[0].toString() + "°C / " + forecastDTO.daily.temperature_2m_max[0].toString() + "°C"
        list[5].text = forecastDTO.daily.time[0]
        list[6].text = ConditionWarning().getWeatherConditionWarning(forecastDTO.hourly.weathercode[0])

        val iconUrl = BackgroundCollection().getIconByWeatherCode(forecastDTO.hourly.weathercode[0])
        Picasso.get().load(iconUrl).into(listImage[0])
    }
}