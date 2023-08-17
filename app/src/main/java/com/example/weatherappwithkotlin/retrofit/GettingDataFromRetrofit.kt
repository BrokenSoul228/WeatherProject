package com.example.weatherappwithkotlin.retrofit

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.weatherappwithkotlin.R
import com.example.weatherappwithkotlin.adapter.CurrentCardAdapter
import com.example.weatherappwithkotlin.adapter.RecyclerViewAdapter
import com.example.weatherappwithkotlin.customenum.ConditionWarning
import com.example.weatherappwithkotlin.customenum.ConditionWarning.Companion.getWeatherConditionWarning
import com.example.weatherappwithkotlin.customenum.ConditionWarning.Forecast.Companion.getForecastCondition
import com.example.weatherappwithkotlin.dto.city.CityDTO
import com.example.weatherappwithkotlin.dto.forecast.ForecastDTO
import com.example.weatherappwithkotlin.screen.MainScreen
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar

class GettingDataFromRetrofit private constructor() {

    private var toastShow: Boolean = false
    private var toast: Toast? = null
    private val retrofitCityBuilder = Retrofit.Builder()
        .baseUrl("https://geocoding-api.open-meteo.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val retrofitForecastBuilder = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val retrofitCity = retrofitCityBuilder.create(RetroFit::class.java)
    private val retrofitForecast = retrofitForecastBuilder.create(RetroFit::class.java)

    companion object {
        @Volatile
        private var instance: GettingDataFromRetrofit? = null
        fun getInstance(): GettingDataFromRetrofit =
            instance ?: synchronized(this) {
                instance ?: GettingDataFromRetrofit().also { instance = it }
            }
    }

    fun showToastWithDelay(context: Context, message: String, delayMillis: Long) {
        if (!toastShow) {
            toastShow = true
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
            toast?.show()
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                toast?.cancel()
                toastShow = false
            }, delayMillis)
        }
    }

    fun getCityList(
        requiredContext: Context,
        name: String,
        searchBar: AutoCompleteTextView
    ) {
        val json = retrofitCity.getCityJson(name)
        var cityDTO: CityDTO

        json.enqueue(object : Callback<CityDTO> {

            override fun onResponse(call: Call<CityDTO>, response: Response<CityDTO>) {
                if (response.isSuccessful) {
                    cityDTO = response.body()!!
                    if (cityDTO.results.isEmpty()) {
                        if (name.length >= 4){
                            showToastWithDelay(requiredContext, message = "City not found", 1000)
                        }
                    } else {
                        val list = arrayListOf<String>()
                        for (item in cityDTO.results) {
                            list.add(item.name)
                            if (list.size > 5) {
                                break
                            }
                        }
                        searchBar.setAdapter(
                            ArrayAdapter(
                                requiredContext,
                                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                                list
                            )
                        )
                        Log.d("INFO", cityDTO.toString())
                    }
                }
            }

            override fun onFailure(call: Call<CityDTO>, t: Throwable) {
                showToastWithDelay(requiredContext, message = "Internet problem", 1000)
            }
        })
    }

    fun getForecast(
        text: String,
        requireActivity: FragmentActivity,
        viewPager: ViewPager2,
        listOfTextView: List<TextView>,
        imageView: ImageView,
        mainScreen: MainScreen,
        context: Context
    ) {
        val json = retrofitCity.getCityJson(text)
        var cityDTO: CityDTO

        json.enqueue(object : Callback<CityDTO> {
            override fun onResponse(call: Call<CityDTO>, response: Response<CityDTO>) {
                if (response.isSuccessful) {
                    cityDTO = response.body()!!
                    if (cityDTO.results.isNotEmpty()) {
                        getInnerForecast(
                            cityDTO,
                            requireActivity,
                            viewPager,
                            listOfTextView,
                            imageView,
                            mainScreen,
                            context
                        )
                    }
                }
            }

            override fun onFailure(call: Call<CityDTO>, t: Throwable) {
            }
        })
    }

    fun getInnerForecast(
        cityDto: CityDTO,
        requireActivity: FragmentActivity,
        viewPager: ViewPager2,
        listOfTextView: List<TextView>,
        imageView: ImageView,
        mainScreen: MainScreen,
        context: Context
    ) {
        val json = retrofitForecast.getForecastJson(cityDto.results[0].latitude, cityDto.results[0].longitude)
        var forecastDTO: ForecastDTO

        json.enqueue(object : Callback<ForecastDTO> {
            override fun onResponse(call: Call<ForecastDTO>, response: Response<ForecastDTO>) {
                if (response.isSuccessful) {
                    forecastDTO = response.body()!!
                    RecyclerViewAdapter(forecastDTO, requireActivity).fill(requireActivity, viewPager, context)
                    CurrentCardAdapter(forecastDTO,context).fill(
                        listOfTextView,
                        cityDto.results[0].name,
                        imageView
                    )
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
                        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

                        if (day == currentDay.toString() && hour == currentHour.toString()) {
                            mainScreen.saveForecastData(
                                cityDto.results[0].name,
                                getForecastCondition(forecastDTO.daily.weathercode[0]),
                                forecastDTO.hourly.temperature_2m[index].toString() + "${
                                    context.getText(R.string.Celsius)}" ,
                                forecastDTO.hourly.windspeed_10m[index].toString() + " " + context.getText(R.string.Speed),
                                forecastDTO.daily.temperature_2m_min[0].toString() + "${context.getText(
                                    R.string.Celsius)} / " + forecastDTO.daily.temperature_2m_max[0].toString() + context.getText(R.string.Celsius),
                                forecastDTO.daily.time[0],
                                getWeatherConditionWarning(forecastDTO.hourly.weathercode[0]),
                                ConditionWarning.BackgroundIcon.getBackgroundCondition(forecastDTO.hourly.weathercode[0])
                            )
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ForecastDTO>, t: Throwable) {
                Log.e("API Error", "Error occurred while fetching city list", t)
            }
        })
    }
}