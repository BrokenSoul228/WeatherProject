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
import com.example.weatherappwithkotlin.adapter.CurrentCardAdapter
import com.example.weatherappwithkotlin.adapter.RecyclerViewAdapter
import com.example.weatherappwithkotlin.daoclass.city.CityDTO
import com.example.weatherappwithkotlin.daoclass.forecast.ForecastDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL_CITY = "https://geocoding-api.open-meteo.com/"
const val BASE_URL_FORECAST = "https://api.open-meteo.com/"

class GettingDataFromRetroFit {

    private var toastShow : Boolean = false
    private var toast : Toast? = null


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

    fun getCityList(requiredContext: Context, name: String,  searchBar : AutoCompleteTextView) {

        val retroFitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL_CITY)
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
                        Toast.makeText(requiredContext, "Город не найден", Toast.LENGTH_LONG).show()
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
        listOfImageView: List<ImageView>
    ) {

        val retroFitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL_CITY)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retroFit = retroFitBuilder.create(RetroFit::class.java)
        val json = retroFit.getCityJson(text)
        var cityDTO: CityDTO

        json.enqueue(object : Callback<CityDTO> {
            override fun onResponse(call: Call<CityDTO>, response: Response<CityDTO>) {
                if (response.isSuccessful) {
                    cityDTO = response.body()!!
                    if(cityDTO.results.isNotEmpty()) {
                        getInnerForecast(cityDTO, requireActivity, viewPager, listOfTextView, listOfImageView)
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
        listOfImageView:List<ImageView>
    ) {

        val retroFitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL_FORECAST)
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
                    CurrentCardAdapter(forecastDTO).fill(listOfTextView, cityDto.results[0].name, listOfImageView, requireActivity )
                }
            }

            override fun onFailure(call: Call<ForecastDTO>, t: Throwable) {
                Log.e("API Error", "Error occurred while fetching city list", t)
            }
        })
    }
}