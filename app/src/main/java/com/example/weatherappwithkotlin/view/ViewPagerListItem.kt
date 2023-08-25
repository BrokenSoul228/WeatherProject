package com.example.weatherappwithkotlin.view

data class ViewPagerListItem (
    val date: String,
    val weatherCondition: String,
    val temperature: String,
    val iconIndex: Int,
)