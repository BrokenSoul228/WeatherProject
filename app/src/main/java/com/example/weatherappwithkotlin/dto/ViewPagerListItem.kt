package com.example.weatherappwithkotlin.dto

import android.graphics.Color

data class ViewPagerListItem (
    val date: String,
    val weatherCondition: String,
    val temperature: String,
    val iconIndex: Int,
)