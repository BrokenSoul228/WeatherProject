package com.example.weatherappwithkotlin.adapter

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.weatherappwithkotlin.R
import com.example.weatherappwithkotlin.customenum.ConditionWarning.Forecast.Companion.getForecastCondition
import com.example.weatherappwithkotlin.customenum.ConditionWarning.IconCollection.Companion.getIconCondition
import com.example.weatherappwithkotlin.customenum.ConditionWarning.TextColorCondition.Companion.getForecastTextColor
import com.example.weatherappwithkotlin.customenum.ConditionWarning.TextColorCondition.Companion.getTextColorCondition
import com.example.weatherappwithkotlin.dao.forecast.ForecastDTO
import com.example.weatherappwithkotlin.dto.ViewPagerListItem
import com.example.weatherappwithkotlin.screen.fragment.DaysFragment
import com.example.weatherappwithkotlin.screen.fragment.HoursFragment
import java.util.*

class RecyclerViewAdapter(private val forecastDTO: ForecastDTO) {

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    fun fill(requireActivity: FragmentActivity, viewPager: ViewPager2) {
        val hoursList = ArrayList<ViewPagerListItem>()
        val daysList = ArrayList<ViewPagerListItem>()
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val currentCalendar = Calendar.getInstance()

        forecastDTO.hourly.weathercode.forEachIndexed { index, weatherCode ->
            val timeString = forecastDTO.hourly.time[index]
            val hour = parseHourFromTimeString(timeString)
            val dataTime = parseDayIsCurrent(timeString)
            val formattedTime = formatHourMinute(hour)

            if (isHourInCurrentDay(hour) && hour <= 23 && hour >= currentCalendar.get(Calendar.HOUR_OF_DAY) && currentHour < 23 && dataTime <= currentCalendar.get(Calendar.DAY_OF_MONTH)) {
                hoursList.add(
                    ViewPagerListItem(
                        formattedTime,
                        getForecastCondition(weatherCode),
                        "${forecastDTO.hourly.temperature_2m[index]}°C",
                        getIconCondition(weatherCode)
                    )
                )
            }
        }

        forecastDTO.daily.weathercode.forEachIndexed { index, weatherCode ->
            daysList.add(
                ViewPagerListItem(
                    forecastDTO.daily.time[index],
                    getForecastCondition(weatherCode),
                    "${forecastDTO.daily.temperature_2m_min[index]}°C / ${forecastDTO.daily.temperature_2m_max[index]}°C",
                    getIconCondition(weatherCode)
                )
            )
        }

        viewPager.adapter = ViewPagerAdapter(requireActivity, listOf(HoursFragment.newInstance(hoursList), DaysFragment.newInstance(daysList)))
    }

    private fun isHourInCurrentDay(hour: Int): Boolean {
        val currentCalendar = Calendar.getInstance()
        val currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH)

        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.set(Calendar.HOUR_OF_DAY, hour)

        val hourDay = calendar.get(Calendar.DAY_OF_MONTH)

        return currentDay == hourDay
    }

    private fun formatHourMinute(hour: Int): String {
        return String.format("%02d:00", hour)
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    private fun parseHourFromTimeString(timeString: String): Int {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.time = formatter.parse(timeString) as Date
        return calendar.get(Calendar.HOUR_OF_DAY)
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    private fun parseDayIsCurrent(timeString: String): Int {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.time = formatter.parse(timeString) as Date
        return calendar.get(Calendar.DAY_OF_MONTH)
    }
}