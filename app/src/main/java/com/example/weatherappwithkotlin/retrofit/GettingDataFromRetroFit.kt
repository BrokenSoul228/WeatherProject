package com.example.weatherappwithkotlin.retrofit

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.weatherappwithkotlin.R
import com.example.weatherappwithkotlin.adapter.CurrentCardAdapter
import com.example.weatherappwithkotlin.adapter.RecyclerViewAdapter
import com.example.weatherappwithkotlin.customenum.ConditionWarning
import com.example.weatherappwithkotlin.dao.city.CityDTO
import com.example.weatherappwithkotlin.dao.forecast.ForecastDTO
import com.example.weatherappwithkotlin.screen.MainScreen
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar
import kotlin.coroutines.coroutineContext

class GettingDataFromRetroFit {

    private var toastShow : Boolean = false
    private var toast : Toast? = null



    fun showToastWithDelay(context: Context, message: String, delayMillis: Long)
    {
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

    fun getCityList(requiredContext: Context, name: String,  searchBar : AutoCompleteTextView) {
        var cityURL = requiredContext.getString(R.string.CITY_URL)
        val retroFitBuilder = Retrofit.Builder()
            .baseUrl(cityURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retroFit = retroFitBuilder.create(RetroFit::class.java)
        val json = retroFit.getCityJson(name)
        var cityDTO : CityDTO

        json.enqueue(object : Callback<CityDTO> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(call: Call<CityDTO>, response: Response<CityDTO>) {
                if (response.isSuccessful) {
                    cityDTO = response.body()!!
                    if  (cityDTO.results.isEmpty()){
                        if (name.length >= 3)
                        Toast.makeText(requiredContext, "City not Found", Toast.LENGTH_LONG).show()
                    }
                    else {
                    val list = arrayListOf<String>()
                    for (item in cityDTO.results) {
                        list.add(item.name)
                        if(list.size > 5) {
                            break
                        }
                    }
                    searchBar.setAdapter(ArrayAdapter(requiredContext, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list))
                    Log.d("INFO", cityDTO.toString())
                }
            }
        }

            override fun onFailure(call: Call<CityDTO>, t: Throwable) {
                showToastWithDelay(requiredContext, message = "Internet problem" , 1000)
            }

        })
    }

    fun getForecast(
        text: String,
        requireActivity: FragmentActivity,
        viewPager: ViewPager2,
        listOfTextView: List<TextView>,
        imageView: ImageView,
        mainScreen: MainScreen
    ) {
        var cityURL = requireActivity.getString(R.string.CITY_URL)
        val retroFitBuilder = Retrofit.Builder()
            .baseUrl(cityURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retroFit = retroFitBuilder.create(RetroFit::class.java)
        val json = retroFit.getCityJson(text)
        var cityDTO: CityDTO

        json.enqueue(object : Callback<CityDTO> {
            override fun onResponse(call: Call<CityDTO>, response: Response<CityDTO>) {
                if (response.isSuccessful) {
                    cityDTO = response.body()!!
                    if (cityDTO.results.isNotEmpty()) {
                        getInnerForecast(cityDTO, requireActivity, viewPager, listOfTextView, imageView, mainScreen)
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
        mainScreen: MainScreen
    ) {
        val forecastURL = requireActivity.getString(R.string.Forecast_URL)
        val retroFitBuilder = Retrofit.Builder()
            .baseUrl(forecastURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retroFit = retroFitBuilder.create(RetroFit::class.java)
        val json = retroFit.getForecastJson(cityDto.results[0].latitude, cityDto.results[0].longitude)
        var forecastDTO : ForecastDTO

        json.enqueue(object : Callback<ForecastDTO> {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onResponse(call: Call<ForecastDTO>, response: Response<ForecastDTO>) {
                if (response.isSuccessful) {
                    forecastDTO = response.body()!!
                    RecyclerViewAdapter(forecastDTO).fill(requireActivity, viewPager)
                    CurrentCardAdapter(forecastDTO).fill(listOfTextView, cityDto.results[0].name, imageView)
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

                        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

                        if (day == currentDay.toString() && hour == currentHour.toString())
                        {
                            mainScreen.saveForecastData(cityDto.results[0].name,
                                ConditionWarning().getWeatherConditionByCode(forecastDTO.daily.weathercode[0]),
                                forecastDTO.hourly.temperature_2m[index].toString(),
                                forecastDTO.hourly.windspeed_10m[index].toString() + " km/h",
                                forecastDTO.daily.temperature_2m_min[0].toString() + "°C / " + forecastDTO.daily.temperature_2m_max[0].toString() + "°C",
                                forecastDTO.daily.time[0],
                                ConditionWarning().getWeatherConditionWarning(forecastDTO.hourly.weathercode[0]))
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