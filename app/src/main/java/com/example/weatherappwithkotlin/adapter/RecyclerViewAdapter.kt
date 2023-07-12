package com.example.weatherappwithkotlin.adapter

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.weatherappwithkotlin.customenum.ConditionWarning
import com.example.weatherappwithkotlin.dao.forecast.ForecastDTO
import com.example.weatherappwithkotlin.dto.ViewPagerListItem
import com.example.weatherappwithkotlin.screen.fragment.DaysFragment
import com.example.weatherappwithkotlin.screen.fragment.HoursFragment
import java.util.*

class RecyclerViewAdapter(private val forecastDTO: ForecastDTO) {


    @RequiresApi(Build.VERSION_CODES.N)
    fun fill(requireActivity: FragmentActivity, viewPager: ViewPager2) {
        val hoursList = ArrayList<ViewPagerListItem>()
        val daysList = ArrayList<ViewPagerListItem>()
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val currentCalendar = Calendar.getInstance()

        for (index in forecastDTO.hourly.weatherCode.indices) {
            val timeString = forecastDTO.hourly.time[index]
            val hour = parseHourFromTimeString(timeString)
            val dataTime = parseDayIsCurrent(timeString)
            val formattedTime = formatHourMinute(hour)
            if (isHourInCurrentDay(hour) && hour <= 23 && hour >= currentCalendar.get(Calendar.HOUR_OF_DAY) && currentHour < 23 && dataTime <= currentCalendar.get(Calendar.DAY_OF_MONTH)) {
                hoursList.add(
                    ViewPagerListItem(
                        formattedTime,
                        ConditionWarning().getWeatherConditionByCode(forecastDTO.hourly.weatherCode[index]),
                        forecastDTO.hourly.temperature[index].toString() + "°C",
                        ConditionWarning().getIconByWeatherCode(forecastDTO.hourly.weatherCode[index])
                    )
                )
            }
        }

        for (index in forecastDTO.daily.weatherCode.indices) {
            daysList.add(
                ViewPagerListItem(
                    forecastDTO.daily.time[index],
                    ConditionWarning().getWeatherConditionByCode(forecastDTO.daily.weatherCode[index]),
                    forecastDTO.daily.temperatureMin[index].toString() + "°C / " + forecastDTO.daily.temperatureMax[index].toString() + "°C",
                    ConditionWarning().getIconByWeatherCode(forecastDTO.daily.weatherCode[index])
                )
            )
        }

        viewPager.adapter = ViewPagerAdapter(requireActivity, listOf(HoursFragment.newInstance(hoursList), DaysFragment.newInstance(daysList)))
    }

    fun isHourInCurrentDay(hour: Int): Boolean {
        val currentCalendar = Calendar.getInstance()
        val currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH)

        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.set(Calendar.HOUR_OF_DAY, hour)

        val hourDay = calendar.get(Calendar.DAY_OF_MONTH)

        return currentDay == hourDay
    }

    fun formatHourMinute(hour: Int): String {
        return String.format("%02d:00", hour)
    }
    @RequiresApi(Build.VERSION_CODES.N)
    fun parseHourFromTimeString(timeString: String): Int {
        val pattern = "yyyy-MM-dd'T'HH:mm"
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        val date = formatter.parse(timeString)
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.HOUR_OF_DAY)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun parseDayIsCurrent(timeString: String): Int {
        val pattern = "yyyy-MM-dd'T'HH:mm"
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        val date = formatter.parse(timeString)
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.DAY_OF_MONTH)
    }


}
