package com.example.weatherappwithkotlin.screen

import DaysFragment
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.weatherappwithkotlin.adapter.ViewPagerAdapter
import com.example.weatherappwithkotlin.databinding.ActivityMainScreenBinding
import com.example.weatherappwithkotlin.databinding.SearchbarLayoutItemBinding
import com.example.weatherappwithkotlin.dto.Constants
import com.example.weatherappwithkotlin.retrofit.GettingDataFromRetrofit
import com.example.weatherappwithkotlin.screen.fragment.HoursFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    private lateinit var fLocationClient: FusedLocationProviderClient

    private lateinit var text1 : TextView
    private lateinit var text2 : TextView
    private lateinit var text3 : TextView
    private lateinit var text4 : TextView
    private lateinit var text5 : TextView
    private lateinit var text6 : TextView
    private lateinit var text7: TextView
    private lateinit var image : ImageView

    private val PREFS_NAME = "WeatherAppPrefs"
    private val HOURS_LIST_KEY = "hoursList"
    private val DAYS_LIST_KEY = "daysList"
    private val searchHistory = mutableListOf<String>()
    private val tabLayoutHeaderList = listOf("Hours", "Days")
    private var hoursFragment = HoursFragment.newInstance(emptyList())
    private var daysFragment = DaysFragment.newInstance(emptyList())
    private var searchHistorySet = mutableSetOf<String>()

    private var pageViewInitializer = listOf(
        hoursFragment,
        daysFragment
    )


    override fun onCreateView(
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

    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchHistory.addAll(loadSearchHistory())
        setupAdapter()

        val sharedPreferences = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val hoursListJson = sharedPreferences?.getString(HOURS_LIST_KEY, null)
        val daysListJson = sharedPreferences?.getString(DAYS_LIST_KEY, null)

        if (!hoursListJson.isNullOrEmpty() && !daysListJson.isNullOrEmpty()) {
            val gson = Gson()
            val hoursListType = object : TypeToken<ArrayList<ViewPagerListItem>>() {}.type
            val daysListType = object : TypeToken<ArrayList<ViewPagerListItem>>() {}.type

            hoursFragment = HoursFragment.newInstance(gson.fromJson(hoursListJson, hoursListType))
            daysFragment = DaysFragment.newInstance(gson.fromJson(daysListJson, daysListType))
        }
        pageViewInitializer = listOf(
            hoursFragment,
            daysFragment
        )
        initAllFragments()
        loadForecastData()

        retrofitHelper = GettingDataFromRetrofit.getInstance()
        searchBar.doAfterTextChanged {
            retrofitHelper.getCityList(
                requireContext(),
                searchBar.text.toString(),
                searchBar
            )
        }

        searchBar.setOnItemClickListener { parent, _, position, _ ->
            val selectedCity = parent.getItemAtPosition(position).toString()
            updateSearchHistory(selectedCity)
            Log.d("STRING", searchHistorySet.toString())
            context?.let {
                retrofitHelper.getForecast(
                    selectedCity,
                    requireActivity(),
                    viewPager,
                    listOf(text1, text2, text3, text4, text5, text6, text7),
                    image,
                    this,
                    it
                )
            }
            searchBar.text = null
            searchBar.hint = null
            val inputDone = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputDone.hideSoftInputFromWindow(searchBar.windowToken, 0)
        }
    }

    private fun loadSearchHistory() : List<String>{
        val sharedCity = context?.getSharedPreferences("STRING", Context.MODE_PRIVATE)
        val searchHistoryJson = sharedCity?.getString("STRING", null)
        val gson = Gson()
        val searchHistoryType = object : TypeToken<Set<String>>() {}.type
        return (gson.fromJson(searchHistoryJson, searchHistoryType) ?: emptySet<String>()).toList()
    }

    private fun updateSearchHistory(newCity : String){
        searchHistorySet.add(newCity)
        if (searchHistorySet.size > 5){
            val iterator = searchHistorySet.iterator()
            for (i in 0 until searchHistorySet.size - 5){
                iterator.next()
                iterator.remove()
            }
        }

        val gson = Gson()
        val searchHistoryJson = gson.toJson(searchHistorySet)
        val sharedCity = context?.getSharedPreferences("STRING", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedCity?.edit() ?: return
        editor.putString("STRING", searchHistoryJson)
        editor.apply()
        val searchBarAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, searchHistorySet.toList())
        searchBar.setAdapter(searchBarAdapter)
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

    private fun setupAdapter() {
        searchBar = binding.SearchBarPreviewText
        val searchBarAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, searchHistory)
        searchBar.setAdapter(searchBarAdapter)
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
        fLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        informationTableContainer = binding.InformationTableContainer
        viewPageAdapter = ViewPagerAdapter(activity as FragmentActivity, pageViewInitializer)
        viewPageAdapter
        viewPager.adapter = viewPageAdapter
        TabLayoutMediator(tabLayout, viewPager) {tab, position -> tab.text = tabLayoutHeaderList[position]}.attach()
        val searchBarAdapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, emptyList<String>())
        searchBar.setAdapter(searchBarAdapter)
    }
}