package com.example.weatherappwithkotlin.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.weatherappwithkotlin.R
import com.example.weatherappwithkotlin.customenum.ConditionWarning.Forecast.Companion.getForecastCondition
import com.example.weatherappwithkotlin.customenum.ConditionWarning.IconCollection.Companion.getIconCondition
import com.example.weatherappwithkotlin.dto.forecast.ForecastDTO
import com.example.weatherappwithkotlin.screen.ViewPagerListItem
import com.example.weatherappwithkotlin.screen.fragment.DaysFragment
import com.example.weatherappwithkotlin.screen.fragment.HoursFragment
import com.google.gson.Gson
import java.util.*

class RecyclerViewAdapter(private val forecastDTO: ForecastDTO, private val context: Context) {

    private val PREFS_NAME = "WeatherAppPrefs"
    private val HOURS_LIST_KEY = "hoursList"
    private val DAYS_LIST_KEY = "daysList"

    var hoursList = ArrayList<ViewPagerListItem>()
    var daysList = ArrayList<ViewPagerListItem>()

    @SuppressLint("SimpleDateFormat")
    fun fill(requireActivity: FragmentActivity, viewPager: ViewPager2, context: Context) {
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val currentCalendar = Calendar.getInstance()

        forecastDTO.hourly.weathercode.forEachIndexed { index, weatherCode ->
            val timeString = forecastDTO.hourly.time[index]
            val hour = parseHourFromTimeString(timeString)
            val dataTime = parseDayIsCurrent(timeString)
            val formattedTime = formatHourMinute(hour)

            if (isCurrentDay(dataTime) && hour <= 23 && hour >= currentCalendar.get(Calendar.HOUR_OF_DAY) && currentHour < 23 && dataTime <= currentCalendar.get(Calendar.DAY_OF_MONTH)
            ) {
                hoursList.add(
                    ViewPagerListItem(
                        formattedTime,
                        getForecastCondition(weatherCode),
                        "${forecastDTO.hourly.temperature_2m[index]}" + context.getText(R.string.Celsius),
                        getIconCondition(weatherCode)
                    )
                )
            }
        }

        forecastDTO.daily.weathercode.forEachIndexed { index, weatherCode ->
            val time = forecastDTO.daily.time[index]
            val data = parseDayOfWeek(time)
            daysList.add(
                ViewPagerListItem(
                    data,
                    getForecastCondition(weatherCode),
                    "${forecastDTO.daily.temperature_2m_min[index]}${context.getText(R.string.Celsius)}/${forecastDTO.daily.temperature_2m_max[index]}${
                        context.getText(
                            R.string.Celsius
                        )
                    }",
                    getIconCondition(weatherCode)
                )
            )
        }

        viewPager.adapter = ViewPagerAdapter(requireActivity, listOf(HoursFragment.newInstance(hoursList), DaysFragment.newInstance(daysList)))
        saveData()
        Log.d("ASAAAAAAAAAA", hoursList.toString())
    }

    fun saveData() {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        val gson = Gson()
        val hoursListJson = gson.toJson(hoursList)
        val daysListJson = gson.toJson(daysList)

        editor.putString(HOURS_LIST_KEY, hoursListJson)
        editor.putString(DAYS_LIST_KEY, daysListJson)

        editor.apply()
    }

    private fun isCurrentDay(dataTime: Int): Boolean {
        val currentCalendar = Calendar.getInstance()
        val currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH)
        return currentDay == dataTime
    }

    private fun formatHourMinute(hour: Int): String {
        return String.format("%02d:00", hour)
    }

    @SuppressLint("SimpleDateFormat")
    private fun parseHourFromTimeString(timeString: String): Int {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.time = formatter.parse(timeString) as Date
        return calendar.get(Calendar.HOUR_OF_DAY)
    }

    @SuppressLint("SimpleDateFormat")
    private fun parseDayIsCurrent(timeString: String): Int {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.time = formatter.parse(timeString) as Date
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    private fun parseDayOfWeek(data : String) : String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.time = formatter.parse(data) as Date
        return SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.time)
    }
}