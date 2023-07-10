
package com.example.weatherappwithkotlin.screen

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.weatherappwithkotlin.adapter.ViewPagerAdapter
import com.example.weatherappwithkotlin.databinding.ActivityMainScreenBinding
import com.example.weatherappwithkotlin.databinding.SearchbarLayoutItemBinding
import com.example.weatherappwithkotlin.retrofit.GettingDataFromRetroFit
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
    private lateinit var sharedPref : SharedPreferences

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
    private var pageViewInitializator = listOf(
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
        searchBar.doAfterTextChanged {
            GettingDataFromRetroFit().getCityList(
                requireContext(),
                searchBar.text.toString(),
                searchBar
            )
        }

        searchBar.setOnItemClickListener { parent, view, position, id ->
            val selectedCity = parent.getItemAtPosition(position)
            GettingDataFromRetroFit().getForecast(
                selectedCity.toString(),
                requireActivity(),
                viewPager,
                listOf(text1, text2, text3, text4, text5, text6, text7),
                listOf(image)
            )
        }
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
        viewPageAdapter = ViewPagerAdapter(activity as FragmentActivity, pageViewInitializator)
        viewPager.adapter = viewPageAdapter
        TabLayoutMediator(tabLayout, viewPager) {tab, position -> tab.text = tabLayoutHeaderList[position]}.attach()
        val searchBarAdapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, emptyList<String>())
        searchBar.setAdapter(searchBarAdapter)
    }

}