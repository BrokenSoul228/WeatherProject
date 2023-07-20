package com.example.weatherappwithkotlin.screen

import com.example.weatherappwithkotlin.retrofit.GettingDataFromRetrofit
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.weatherappwithkotlin.R
import com.example.weatherappwithkotlin.adapter.RecyclerViewAdapter
import com.example.weatherappwithkotlin.adapter.ViewPagerAdapter
import com.example.weatherappwithkotlin.databinding.ActivityMainScreenBinding
import com.example.weatherappwithkotlin.databinding.SearchbarLayoutItemBinding
import com.example.weatherappwithkotlin.dto.Constants
import com.example.weatherappwithkotlin.dto.forecast.Daily
import com.example.weatherappwithkotlin.dto.forecast.ForecastDTO
import com.example.weatherappwithkotlin.dto.forecast.Hourly
import com.example.weatherappwithkotlin.screen.fragment.DaysFragment
import com.example.weatherappwithkotlin.screen.fragment.HoursFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainScreen : Fragment() {
    private lateinit var binding: ActivityMainScreenBinding
    private lateinit var informationTableContainer : ConstraintLayout
    private lateinit var searchBar: AutoCompleteTextView
    private lateinit var bindingForSearch: SearchbarLayoutItemBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout : TabLayout
    private lateinit var viewPageAdapter : ViewPagerAdapter
    private lateinit var sharedPref: SharedPreferences
    private lateinit var retrofitHelper : GettingDataFromRetrofit
    private lateinit var forecastDTO : ForecastDTO

    private lateinit var text1 : TextView
    private lateinit var text2 : TextView
    private lateinit var text3 : TextView
    private lateinit var text4 : TextView
    private lateinit var text5 : TextView
    private lateinit var text6 : TextView
    private lateinit var text7: TextView
    private lateinit var image : ImageView

    private val tabLayoutHeaderList = listOf("Hours", "Days")
    private var hoursFragment = HoursFragment.newInstance(emptyList())
    private var daysFragment = DaysFragment.newInstance(emptyList())

    private var pageViewInitializer = listOf(
        hoursFragment,
        daysFragment
    )


    override fun onCreateView( //create view object with inflater
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ActivityMainScreenBinding.inflate(
            inflater,
            container,
            false
        )

        bindingForSearch = SearchbarLayoutItemBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAllFragments()
        loadForecastData()
        val dailyData = Daily(emptyList(), emptyList(), emptyList(), emptyList())
        val hourlyData = Hourly(emptyList(), emptyList(), emptyList(), emptyList())

         forecastDTO = ForecastDTO(
            daily = dailyData,
            elevation = 100.0,
            generationtime_ms = 1000.0,
            hourly = hourlyData,
            latitude = 40.7128,
            longitude = -74.0060,
            timezone = "America/New_York",
            timezone_abbreviation = "EDT",
            utc_offset_seconds = -14400
        )
        val recyclerViewAdapter = RecyclerViewAdapter(forecastDTO, requireContext())
        recyclerViewAdapter.fill(requireActivity(), viewPager, requireContext())
        recyclerViewAdapter.loadItems()
        retrofitHelper = GettingDataFromRetrofit.getInstance()
        searchBar.doAfterTextChanged {
                    retrofitHelper.getCityList(
                        requireContext(),
                        searchBar.text.toString(),
                        searchBar
                    )

        }

        searchBar.setOnItemClickListener { parent, _ , position, _ ->
            val selectedCity = parent.getItemAtPosition(position)
            context?.let {
                retrofitHelper.getForecast(
                    selectedCity.toString(),
                    requireActivity(),
                    viewPager,
                    listOf(text1, text2, text3, text4, text5, text6, text7),
                    image,
                    this,
                    it
                )
            }
            val inputDone = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputDone.hideSoftInputFromWindow(searchBar.windowToken, 0)
        }
    }

    fun saveForecastData(cityName : String, condition : String, temp : String, windSpeed : String, maxMin : String, time : String, warning : String?, icon : Int) {
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(Constants.CITY_NAME, cityName)
        editor.putString(Constants.CONDITION, condition)
        editor.putString(Constants.TEMP, temp)
        editor.putString(Constants.WIND_SPEED, windSpeed)
        editor.putString(Constants.MAX_MIN, maxMin)
        editor.putString(Constants.TIME, time)
        editor.putString(Constants.WARNING, warning)
        editor.putInt(Constants.Icon, icon)
        editor.apply()
    }

    private fun loadForecastData() {
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val cityName = sharedPref.getString(Constants.CITY_NAME, "")
        val condition = sharedPref.getString(Constants.CONDITION, "")
        val temp = sharedPref.getString(Constants.TEMP, "")
        val windSpeed = sharedPref.getString(Constants.WIND_SPEED, "")
        val maxMin = sharedPref.getString(Constants.MAX_MIN, "")
        val time = sharedPref.getString(Constants.TIME, "")
        val warning = sharedPref.getString(Constants.WARNING, "")
        val icon = sharedPref.getInt(Constants.Icon, R.drawable.littlerain)

        text1.text = cityName
        text2.text = temp
        text3.text = condition
        text4.text = windSpeed
        text5.text = maxMin
        text6.text = time
        text7.text = warning
        image.setImageResource(icon)
    }

    private fun initAllFragments() {
        searchBar = binding.SearchBarPreviewText
        viewPager = binding.MainViewPager
        tabLayout = binding.ScreenSwitcher
        text1 = binding.CityNameTextView
        text2 = binding.Temperature
        text3 = binding.WeatherCondition
        text4 = binding.WindSpeed
        text5 = binding.TemperatureInterval
        text6 = binding.LastForecastUpdateTime
        text7 = binding.InformationTableBackGround
        image = binding.BackgroundImage
        informationTableContainer = binding.InformationTableContainer
        viewPageAdapter = ViewPagerAdapter(activity as FragmentActivity, pageViewInitializer)
        viewPager.adapter = viewPageAdapter
        TabLayoutMediator(tabLayout, viewPager) {tab, position -> tab.text = tabLayoutHeaderList[position]}.attach()
        val searchBarAdapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, emptyList<String>())
        searchBar.setAdapter(searchBarAdapter)
    }
}