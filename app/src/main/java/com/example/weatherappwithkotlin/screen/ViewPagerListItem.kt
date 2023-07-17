package com.example.weatherappwithkotlin.screen

data class ViewPagerListItem (
    val date: String,
    val weatherCondition: String,
    val temperature: String,
    val iconIndex: Int,
)