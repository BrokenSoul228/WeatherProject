package com.example.weatherappwithkotlin.customenum

import com.example.weatherappwithkotlin.R

class IconsCollection {
    fun getIconByCountryCode(countryCode : String) : Int {
        when(countryCode){
            "BY" -> return R.drawable.belarus_flag_icon
            "RU" -> return R.drawable.russia_flag_icon
            "US" -> return R.drawable.united_states_flag_icon
        }
        return R.drawable.ic_launcher_background
    }
}