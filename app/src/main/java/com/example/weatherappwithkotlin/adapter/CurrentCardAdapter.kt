package com.example.weatherappwithkotlin.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.example.weatherappwithkotlin.R
import com.example.weatherappwithkotlin.customenum.ConditionWarning.BackgroundIcon.Companion.getBackgroundCondition
import com.example.weatherappwithkotlin.customenum.ConditionWarning.Companion.getWeatherConditionWarning
import com.example.weatherappwithkotlin.customenum.ConditionWarning.Forecast.Companion.getForecastCondition
import com.example.weatherappwithkotlin.dto.forecast.ForecastDTO
import com.squareup.picasso.Picasso
import java.util.*

class CurrentCardAdapter(private var forecastDTO: ForecastDTO,private val context: Context) {

    @SuppressLint("SetTextI18n")
    fun fill(
        list: List<TextView>,
        name: String,
        imageView: ImageView,
    ) {
        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        for (index in forecastDTO.hourly.weathercode.indices) {
            val date = forecastDTO.hourly.time[index]
            var day = date.substring(8, 10)
            if (day.startsWith("0")) {
                day = day.substring(1)
            }
            var hour = date.substring(11, 13)
            if (hour.startsWith("0")) {
                hour = hour.substring(1)
            }
            if (day == currentDay.toString() && hour == currentHour.toString()) {
                list[1].text = "${forecastDTO.hourly.temperature_2m[index]}${context.getText(R.string.Celsius)}"
                list[3].text = forecastDTO.hourly.windspeed_10m[index].toString() +" "+ context.getText(R.string.Speed)
            }
        }
        list[0].text = name
        list[2].text = getForecastCondition(forecastDTO.daily.weathercode[0])
        list[4].text = forecastDTO.daily.temperature_2m_min[0].toString() + "${context.getText(R.string.Celsius)} / " + forecastDTO.daily.temperature_2m_max[0].toString() + "${context.getText(R.string.Celsius)}"
        list[5].text = forecastDTO.daily.time[0]
        list[6].text = getWeatherConditionWarning(forecastDTO.hourly.weathercode[0])

        val imageResourceId = getBackgroundCondition(forecastDTO.hourly.weathercode[0])
        Picasso.get()
            .load(imageResourceId)
            .into(imageView)
        preloadImages()
    }

    private fun preloadImages() {
        val imageResourceIds = getImageResourceIds()
        for (imageResourceId in imageResourceIds) {
            Picasso.get().load(imageResourceId).fetch()
        }
    }

    private fun getImageResourceIds(): List<Int> {
        return listOf(R.drawable.clearsky, R.drawable.cloudysky,R.drawable.fog1, R.drawable.littlerain,
            R.drawable.moderaterain,R.drawable.hardrain, R.drawable.snowstorm, R.drawable.moderatesnow,
            R.drawable.littlesnow,R.drawable.thunder)
    }

}