package com.example.weatherappwithkotlin.dto

data class ViewPagerListItem (
    val date: String,
    val weatherCondition: String,
    val temperature: String,
    val iconIndex: Int
) {

}